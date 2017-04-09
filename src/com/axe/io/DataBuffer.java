package com.axe.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


public class DataBuffer
{

	private int write;
	private int read;
	private int resize;
	private byte[] bytes;
	
	public final OutputStream out;
	public final InputStream in;
	
	public DataBuffer(int initialCapacity, int resizeAmount)
	{
		this.bytes = new byte[initialCapacity];
		this.resize = resizeAmount;
		this.out = new Output();
		this.in = new Input();
	}
	
	private class Output extends OutputStream
	{
		private void ensureSize(int s)
		{
			if (write + s >= bytes.length)
			{
				int minimumSize = write + s;
				int desiredSize = bytes.length + resize;
				int nextSize = Math.max( minimumSize, desiredSize );
				
				bytes = Arrays.copyOf( bytes, nextSize );
			}
		}
		
		@Override
		public void write( int b ) throws IOException
		{
			ensureSize( 1 );
			
			bytes[write++] = (byte)b;
		}
		
		@Override
		public void write(byte[] b, int offset, int length)
		{
			ensureSize( length );
			
			System.arraycopy( b, offset, bytes, write, length );
			
			write += length;
		}
	}
	
	private class Input extends InputStream
	{
		private int mark = -1;
		
		@Override
		public int read() throws IOException
		{
			return read < write ? bytes[read++] : -1;
		}
		
		@Override
		public int available()
		{
			return (write - read);
		}
		
		@Override
		public void mark(int m)
		{
			mark = m;
		}
		
		@Override
		public void reset()
		{
			read = mark;
			mark = -1;
		}
		
		@Override
		public boolean markSupported()
		{
			return true;
		}
		
		@Override
		public long skip(long bytes)
		{
			long skipped = Math.min( bytes, available() );
			
			read += skipped;
			
			return skipped;
		}
		
		@Override
		public int read(byte[] b, int offset, int length)
		{
			int bytesRead = Math.min( available(), length );

			System.arraycopy( bytes, read, b, offset, bytesRead );
			
			read += bytesRead;
			
			return bytesRead == 0 ? -1 : bytesRead;
		}
	}
	
}
