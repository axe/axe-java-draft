package com.axe.net;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Server
{

	public String host;
	public int port;
	public int magicNumber;
	public ServerSocketChannel socket;
	public Set<Client> clients;
	public final ServerListener listener;
	
	public Server(String host, int port, int magicNumber, ServerListener listener)
	{
		this.host = host;
		this.port = port;
		this.magicNumber = magicNumber;
		this.listener = listener;
	}
	
	public void broadcast( Object message, Client exceptFor )
	{
		for (Client c : clients)
		{
			if (c.receivesBroadcasts && c != exceptFor)
			{
				c.out.add( message );
			}
		}
	}
	
	public void init() throws Exception
	{
		socket = ServerSocketChannel.open();
		socket.bind( new InetSocketAddress( port ) );
		socket.configureBlocking( false );
		
		clients = new HashSet<Client>();
		
		listener.onInit( this );
	}
	
	public void update() throws Exception
	{
		listener.onUpdateBegin( this );
		
		boolean sleep = true;
		
		for (;;) 
		{
			try
			{
				SocketChannel clientSocket = socket.accept();
				
				if (clientSocket == null)
				{
					break;
				}
				
				sleep = false;
				
				initializeClient( new Client( clientSocket, this, magicNumber ) );	
			}
			catch (Exception e)
			{
				listener.onAcceptError( this, e );
				
				break;
			}
		}
		
		Iterator<Client> iter = clients.iterator();
		
		while (iter.hasNext())
		{
			Client c = iter.next();
			
			if (!updateClient( c ))
			{
				iter.remove();
			}
			else if (c.packetsRead > 0 || c.messagesSent > 0)
			{
				sleep = false;
			}
		}
		
		listener.onUpdateEnd( this );
		
		if (sleep)
		{
			try
			{
				Thread.sleep( 1 );
			}
			catch (Exception e)
			{
				// catch some sleep for not doing anything!
			}
		}
	}
	
	private void initializeClient(Client c)
	{
		try
		{
			c.init();
			
			listener.onClientConnect( this, c );
			
			clients.add( c );
		}
		catch (Exception e)
		{
			listener.onClientFailedConnect( this, c, e );
			
			System.err.println( "Error initializing accepted client" );
		}
	}
	
	private boolean updateClient(Client c)
	{
		if (!c.socket.isOpen())
		{
			return false;
		}
		
		if ( c.isClosed() )
		{
			listener.onClientClose( this, c, null );
			
			return false;
		}
		
		boolean success = true;
		
		try
		{
			c.read();
			c.update();
			
			listener.onClientUpdate( this, c );
			
			if (c.readyToSend)
			{
				c.send();
			}
		}
		catch (Exception e)
		{
			success = false;
			
			System.err.println( "Error with client, closing connection and removing them." );
			
			listener.onClientClose( this, c, e );
			
			c.close();
		}
		
		return success;
	}

	public void close()
	{
		for (Client c : clients)
		{
			c.close();
		}
		
		try
		{
			socket.close();	
		}
		catch (Exception e)
		{
			// swallow
		}
	}
	
}
