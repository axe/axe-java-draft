
package com.axe.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;


public class SerializedMessage<M> implements Message<M>
{

	public int id;
	public Class<M> type;
	public M lastSized;
	public int lastSizeOf;
	public ByteBuffer lastSizedBuffer = ByteBuffer.allocate( 8192 );

	public SerializedMessage( int id, Class<M> type )
	{
		this.id = id;
		this.type = type;
	}

	@Override
	public M read( ByteBuffer in )
	{
		ByteBufferInputStream bbis = new ByteBufferInputStream( in );

		try
		{
			ObjectInputStream ois = new ObjectInputStream( bbis );
			M result = (M)ois.readObject();
			ois.close();
			
			return result;
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	@Override
	public void write( ByteBuffer out, M m )
	{
		if (m != lastSized)
		{
			sizeOf( m );
		}

		out.put( lastSizedBuffer );
		lastSizedBuffer.position( 0 );
	}

	@Override
	public int sizeOf( M m )
	{
		if (m == lastSized)
		{
			return lastSizeOf;
		}

		lastSizedBuffer.clear();

		ByteBufferOutputStream bbos = new ByteBufferOutputStream( lastSizedBuffer );

		try
		{
			ObjectOutputStream oos = new ObjectOutputStream( bbos );
			oos.writeObject( m );
			oos.close();
			lastSizedBuffer.flip();
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}

		lastSized = m;
		lastSizeOf = lastSizedBuffer.limit();

		return lastSizeOf;
	}

	@Override
	public int id()
	{
		return id;
	}

	@Override
	public Class<M> type()
	{
		return type;
	}

	private class ByteBufferOutputStream extends OutputStream
	{

		public final ByteBuffer buffer;

		public ByteBufferOutputStream( ByteBuffer buffer )
		{
			this.buffer = buffer;
		}

		public void write( int b ) throws IOException
		{
			buffer.put( (byte)b );
		}
	}

	private class ByteBufferInputStream extends InputStream
	{

		public final ByteBuffer buffer;

		public ByteBufferInputStream( ByteBuffer buffer )
		{
			this.buffer = buffer;
		}

		public int read() throws IOException
		{
			return !buffer.hasRemaining() ? -1 : buffer.get() & 0xFF;
		}
	}

}
