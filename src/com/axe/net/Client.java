
package com.axe.net;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;


public class Client
{

	private static final int HEADER_SIZE = 28;
	private static final int HEADER_PACKET_SIZE_OFFSET = 24;
	private static final int MESSAGE_ID_SIZE = 4;
	
	public Queue<Object> out = new ArrayDeque<Object>();
	public Queue<Object> in = new ArrayDeque<Object>();
	
	public String host;
	public int port;
	public long updateRateMillis = 50;
	public int bufferSize = 3800;
	public boolean receivesBroadcasts;
	public int magicNumber;
	
	public Server server;
	public Object attachment;

	public boolean closed;
	public int messagesSent;
	public int packetsRead;
	public int packetIndex;
	public int lastSentPacketSize;
	public int lastReceivedPacketSize;
	public long lastReceivedPacketTime;
	public long pingTime;
	public SocketAddress address;
	public long lastUpdate;
	public boolean readyToSend;
	public SocketChannel socket;
	public ByteBuffer bufferIn;
	public ArrayDeque<ByteBuffer> bufferOutQueue;
	public ArrayDeque<ByteBuffer> bufferOutPool;
	public Packet packet;

	public long durationRead;
	public long durationUpdate;
	public long durationWrite;
	public long durationSend;

	public Client()
	{
	}

	public Client( String host, int port, int magicNumber )
	{
		this.host = host;
		this.port = port;
		this.magicNumber = magicNumber;
	}

	public Client( SocketChannel socket, Server server, int magicNumber )
	{
		this.socket = socket;
		this.server = server;
		this.magicNumber = magicNumber;
	}

	public void init() throws Exception
	{
		assert bufferSize != 0 : "Buffer size must be non-zero";

		lastUpdate = System.currentTimeMillis();

		if (socket == null)
		{
			assert host != null : "Host must be non-null";
			assert port != 0 : "Port must be non-zero";

			address = new InetSocketAddress( host, port );
			socket = SocketChannel.open();
			socket.configureBlocking( false );
			socket.connect( address );
		}
		else
		{
			socket.configureBlocking( false );
			address = socket.getRemoteAddress();
		}

		bufferIn = ByteBuffer.allocateDirect( bufferSize );
		bufferOutQueue = new ArrayDeque<ByteBuffer>();
		bufferOutPool = new ArrayDeque<ByteBuffer>();

		packet = new Packet();
	}

	public void close()
	{
		if (closed)
		{
			return;
		}
		
		try
		{
			closed = true;

			if (socket.isOpen())
			{
				socket.close();
			}
		}
		catch (Exception e)
		{
			System.err.println( "Error closing socket" );

			e.printStackTrace();
		}
	}

	public boolean isClosed()
	{
		return closed;
	}

	public void update() throws Exception
	{
		if (closed)
		{
			return;
		}

		long startTime = System.nanoTime();
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastUpdate;

		readyToSend = (elapsedTime >= updateRateMillis);

		if (readyToSend)
		{
			while (elapsedTime >= updateRateMillis)
			{
				lastUpdate += updateRateMillis;
				elapsedTime -= updateRateMillis;
			}

			assert lastUpdate <= currentTime : "Client.lastUpdate was over adjusted.";
		}

		if (socket.finishConnect())
		{
			write();
		}

		durationUpdate = System.nanoTime() - startTime;
	}

	private void write() throws Exception
	{
		if (closed)
		{
			return;
		}

		long startTime = System.nanoTime();

		assert bufferOutQueue != null : "Client.bufferOutQueue must be non-null, try calling Client.init() first.";
		assert bufferOutPool != null : "Client.bufferOutPool must be non-null, try calling Client.init() first.";
		assert socket != null : "Client.socket must be non-null, try calling Client.init() first.";
		assert socket.isOpen() : "Socket must be open to write to.";

		while (!bufferOutQueue.isEmpty())
		{
			ByteBuffer next = bufferOutQueue.peek();

			socket.write( next );

			if (next.hasRemaining())
			{
				break;
			}

			bufferOutQueue.poll();
			bufferOutPool.push( next );
		}

		durationWrite = System.nanoTime() - startTime;
	}

	public void sendAllNow() throws Exception
	{
		if (closed)
		{
			return;
		}

		while (!out.isEmpty())
		{
			send();
		}

		while (!bufferOutQueue.isEmpty())
		{
			write();
		}
	}

	public void send() throws Exception
	{
		if (closed)
		{
			return;
		}
		
		if (!socket.finishConnect())
		{
			return;
		}

		long startTime = System.nanoTime();

		ByteBuffer bufferOut = bufferOutPool.isEmpty() ? ByteBuffer.allocateDirect( bufferSize ) : bufferOutPool.pop();

		bufferOut.clear();
		bufferOut.putInt( magicNumber );
		bufferOut.putInt( packetIndex );
		bufferOut.putLong( System.nanoTime() );
		bufferOut.putLong( lastReceivedPacketTime );
		bufferOut.putInt( 0 );

		messagesSent = 0;

		while (!out.isEmpty() && tryPut( out.peek(), bufferOut ))
		{
			out.poll();

			messagesSent++;
		}

		bufferOut.flip();

		if (messagesSent > 0)
		{
			lastSentPacketSize = bufferOut.limit() - HEADER_SIZE;

			bufferOut.putInt( HEADER_PACKET_SIZE_OFFSET, lastSentPacketSize );

			bufferOutQueue.offer( bufferOut );

			packetIndex++;

			write();
		}
		else
		{
			bufferOutPool.push( bufferOut );
		}

		durationSend = System.nanoTime() - startTime;
	}

	private <M> boolean tryPut( Object dataObject, ByteBuffer dest )
	{
		M data = (M)dataObject;
		Message<M> message = MessageRegistry.getMessage( data );
		int sizeOf = message.sizeOf( data );

		if (dest.remaining() < sizeOf + MESSAGE_ID_SIZE)
		{
			return false;
		}

		dest.putInt( message.id() );
		message.write( dest, data );

		return true;
	}

	public void read() throws Exception
	{
		if (closed) 
		{
			return;
		}
		
		if (!socket.finishConnect())
		{
			return;
		}

		long startTime = System.nanoTime();

		assert socket.isOpen() : "Socket must be open to read from.";

		in.clear();

		packetsRead = 0;

		int read = socket.read( bufferIn );

		if (read == -1)
		{
			close();

			return;
		}

		bufferIn.flip();

		while (readBuffer())
		{
			packetsRead++;
		}

		durationRead = System.nanoTime() - startTime;
	}

	private boolean readBuffer()
	{
		if (bufferIn.remaining() < HEADER_SIZE)
		{
			unflip( bufferIn );

			return false;
		}

		packet.messages.clear();
		packet.ids.clear();

		int packetMagicNumber = bufferIn.getInt();
		
		if (packetMagicNumber != magicNumber)
		{
			close();
			
			return false;
		}
		
		packet.packetIndex = bufferIn.getInt();
		packet.packetTime = bufferIn.getLong();
		packet.receivedTime = bufferIn.getLong();
		packet.packetSize = bufferIn.getInt();

		assert packet.packetIndex >= 0 : "Packet index must be >= 0.";
		assert packet.packetTime > 0 : "Packet time must be > 0.";
		assert packet.packetSize > 0 : "Packet size must be > 0.";
		assert packet.packetSize < bufferSize : "Packet size must be < " + bufferSize + ".";

		if (bufferIn.remaining() < packet.packetSize)
		{
			unflip( bufferIn );

			return false;
		}
		
		pingTime = System.nanoTime() - packet.receivedTime;

		int messageCount = 0;
		int finalPosition = bufferIn.position() + packet.packetSize;

		while (bufferIn.position() < finalPosition)
		{
			int messageId = bufferIn.getInt();

			assert messageId > 0 : "Message IDs must be > 0.";

			Message<?> message = MessageRegistry.getMessage( messageId );

			assert message != null : "Message for id " + messageId + " is null.";

			Object data = message.read( bufferIn );

			if (data != null)
			{
				in.add( data );
				packet.messages.add( data );
				packet.ids.add( messageId );
				messageCount++;
			}
		}

		assert bufferIn.position() == finalPosition : "Buffer position did not match expected packet length.";

		int remaining = bufferIn.remaining();
		bufferIn.compact();
		bufferIn.position( 0 );
		bufferIn.limit( remaining );

		for (int i = 0; i < messageCount; i++)
		{
			int messageId = packet.ids.get( i );
			Object data = packet.messages.get( i );

			MessageRegistry.handleMessage( this, data, messageId, packet );
		}

		lastReceivedPacketSize = packet.packetSize;
		lastReceivedPacketTime = packet.packetTime;
		
		return true;
	}

	private void unflip( ByteBuffer bb )
	{
		int readMax = bb.limit();
		bb.limit( bb.capacity() );
		bb.position( readMax );
	}

	public <T> T attachment()
	{
		return (T)attachment;
	}

	public <T> T attachment( Class<T> type )
	{
		return (T)attachment;
	}

}
