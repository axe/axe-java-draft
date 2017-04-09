package com.axe.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DataUtility
{
	
	private static DataFormat defaultFormat = null;
	
	public static void setDefaultFormat( DataFormat format )
	{
		defaultFormat = format;
	}
	
	public static DataFormat getDefaultFormat()
	{
		return defaultFormat;
	}
	
	public static final int BYTE_SHIFT = 7;
	public static final int BYTE_MASK = 0x7F;
	public static final int BYTE_MORE = 0x80;

	private static final int BYTES_BYTE = 1;
	private static final int BYTES_INT = 4;
	private static final int BYTES_LONG = 8;
	private static final int[] VALUE_BYTES = {
		/*0*/ 0, 
		/*1*/ 1 << (0 * 7),
		/*2*/ 1 << (1 * 7),
		/*3*/ 1 << (2 * 7),
		/*4*/ 1 << (3 * 7),
		/*5*/ 1 << (4 * 7),
		/*6*/ 1 << (5 * 7),
		/*7*/ 1 << (6 * 7),
		/*8*/ 1 << (7 * 7),
	};
	
	public static int readInt( InputStream in ) throws IOException
	{
		int x = 0;
		int b = 0;
		int shift = 0;
		
		do
		{
			b = in.read();
			
			if ( b == -1 ) 
			{
				return x;
			}
			
			x |= (b & BYTE_MASK) << shift;
			shift += BYTE_SHIFT;
		} 
		while ( (b & BYTE_MORE) == BYTE_MORE );
		
		return x;
	}
	
	public static int writeInt( OutputStream out, int value ) throws IOException
	{
		int bytes = 0;
		
		do
		{
			out.write( (value & BYTE_MASK) | (value > BYTE_MASK ? BYTE_MORE : 0) );
			value >>= BYTE_SHIFT;
			bytes++;
		} while ( value > 0 );
		
		return bytes;
	}
	
	public static long readLong( InputStream in ) throws IOException
	{
		long x = 0;
		int b = 0;
		long shift = 0;
		
		do
		{
			b = in.read();
			
			if ( b == -1 ) 
			{
				return x;
			}
			
			x |= (b & BYTE_MASK) << shift;
			shift += BYTE_SHIFT;
		} 
		while ( (b & BYTE_MORE) == BYTE_MORE );
		
		return x;
	}
	
	public static int writeLong( OutputStream out, long value ) throws IOException
	{
		int bytes = 0;
		
		do
		{
			out.write( (int)((value & BYTE_MASK) | (value > BYTE_MASK ? BYTE_MORE : 0)) );
			value >>= BYTE_SHIFT;
			bytes++;
		} while ( value > 0 );
		
		return bytes;
	}
	
	public static int getInt( ByteBuffer in )
	{
		int x = 0;
		int b = 0;
		int shift = 0;
		
		do
		{
			b = in.get() & 0xFF;
			x |= (b & BYTE_MASK) << shift;
			shift += BYTE_SHIFT;
		} 
		while ( (b & BYTE_MORE) == BYTE_MORE );
		
		return x;
	}
	
	public static int putInt( ByteBuffer out, int value )
	{
		int bytes = 0;
		
		do
		{
			out.put( (byte)((value & BYTE_MASK) | (value > BYTE_MASK ? BYTE_MORE : 0)) );
			value >>= BYTE_SHIFT;
			bytes++;
		} while ( value > 0 );
		
		return bytes;
	}
	
	public static long getLong( ByteBuffer in )
	{
		long x = 0;
		int b = 0;
		long shift = 0;
		
		do
		{
			b = in.get() & 0xFF;
			x |= (b & BYTE_MASK) << shift;
			shift += BYTE_SHIFT;
		} 
		while ( (b & BYTE_MORE) == BYTE_MORE );
		
		return x;
	}
	
	public static int putLong( ByteBuffer out, long value )
	{
		int bytes = 0;
		
		do
		{
			out.put( (byte)((value & BYTE_MASK) | (value > BYTE_MASK ? BYTE_MORE : 0)) );
			value >>= BYTE_SHIFT;
			bytes++;
		} 
		while ( value > 0 );
		
		return bytes;
	}
	
	public static int sizeOf( int value )
	{
		for (int i = BYTES_INT; i > BYTES_BYTE; i--) 
		{
			if ( value >= VALUE_BYTES[i] ) 
			{
				return i;	
			}
		}
		
		return BYTES_BYTE;
	}
	
	public static int sizeOf( long value )
	{
		for (int i = BYTES_LONG; i > BYTES_BYTE; i--) 
		{
			if ( value >= VALUE_BYTES[i] ) 
			{
				return i;	
			}
		}
		
		return BYTES_BYTE;
	}
	
	public static <I extends InputModel> I convertFromOutput( OutputModel out, I in )
	{
		// out.copyTo( in );
		
		return in;
	}
	
	public static <O extends OutputModel> O convertFromInput( InputModel in, O out )
	{
		in.copyTo( out );
		
		return out;
	}
	
	public static <T extends DataModel> T clone(T model, T out)
	{
		try
		{
			DataFormat format = getDefaultFormat();
			DataBuffer buffer = new DataBuffer( 4096, 2048 );

			OutputModel clone = format.newOutput( "clone" );
			model.write( clone );
			clone.output( buffer.out );
			
			InputModel cloned = format.read( buffer.in );
			out.read( cloned );
			
			return out;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}
	
	public static OutputModel convertFromInput( InputModel in, DataFormat format )
	{
		OutputModel out = format.newOutput( in.getName() );
		
		in.copyTo( out );
		
		return out;
	}
	
	public static void readFile( File file, ByteBuffer in ) throws Exception
	{
		RandomAccessFile stream = new RandomAccessFile( file, "r" );
		
		try
		{
			in.clear();
			
			stream.getChannel().read( in );
			
			in.flip();
		}
		finally
		{
			stream.close();
		}
	}
	
	public static void writeFile( File file, ByteBuffer out ) throws Exception
	{
		RandomAccessFile stream = new RandomAccessFile( file, "rw" );
		
		try
		{
			FileChannel channel = stream.getChannel();
			
			int pos = out.position();
			out.flip();
			
			channel.write( out );
			
			out.limit( out.capacity() );
			out.position( pos );
		}
		finally
		{
			stream.close();
		}
	}
	
	public static ByteBuffer resize( ByteBuffer buffer, int expectedCapacity )
	{
		ByteBuffer result = ( buffer != null ? buffer : ByteBuffer.allocateDirect( expectedCapacity ) );
		
		if ( result.capacity() < expectedCapacity )
		{
			result = ByteBuffer.allocateDirect( expectedCapacity );
			result.put( buffer );
			result.clear();
		}
		
		return result;
	}
	
	public static ByteBuffer read( File file ) throws IOException
	{
		FileInputStream fis = new FileInputStream( file );
		
		return read( fis.getChannel().size(), fis, true );
	}
	
	public static ByteBuffer read( long size, InputStream input, boolean close ) throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocateDirect( (int)size );
		byte[] data = new byte[ 4096 ];
		int read = 0;
		
		while ( (read = input.read( data ) ) > 0 )
		{
			if ( buffer.remaining() < read )
			{
				return null;
			}
			
			buffer.put( data, 0, read );
		}
		
		buffer.clear();
		
		if ( close )
		{
			input.close();
		}
		
		return buffer;
	}
	
	public static int transfer( InputStream from, OutputStream to, int chunkSize ) throws IOException
	{
		byte[] data = new byte[ chunkSize ];
		int totalRead = 0;
		int read = 0;
		
		while ( (read = from.read( data )) > 0 )
		{
			to.write( data, 0, read );
			totalRead += read;
		}
		
		return totalRead;
	}
	
	public static OutputStream toOutputStream(final ByteBuffer out)
	{
		if (out == null)
		{
			return null;
		}
		
		return new OutputStream()
		{
			@Override
			public void write( int b ) throws IOException
			{
				out.put( (byte)b );
			}
			
			@Override
			public void write(byte[] b, int offset, int length)
			{
				out.put( b, offset, length );
			}
		};
	}
	
	public static InputStream syphon(final InputStream in, final OutputStream out)
	{
		if (in == null)
		{
			return null;
		}
		
		return new InputStream()
		{
			@Override
			public int read() throws IOException
			{
				int b = in.read();
				
				if (b != -1)
				{
					out.write( b );
				}
				
				return b;
			}
			
			@Override
			public int available() throws IOException
			{
				return in.available();
			}
			
			@Override
			public void close() throws IOException
			{
				in.close();
				out.close();
			}
			
			@Override
			public long skip(long bytes) throws IOException
			{
				return in.skip( bytes );
			}
			
			@Override
			public int read(byte[] b, int offset, int length) throws IOException
			{
				int actualLength = in.read( b, offset, length );
				
				if (actualLength != -1)
				{
					out.write( b, offset, actualLength );	
				}
				
				return actualLength;
			}
		};
	}
	
	public static InputStream toInputStream(final ByteBuffer in)
	{
		if (in == null)
		{
			return null;
		}
		
		return new InputStream()
		{
			public int mark = -1;
			
			@Override
			public int read() throws IOException
			{
				return in.hasRemaining() ? in.get() & 0xFF : -1;
			}
			
			@Override
			public int available()
			{
				return in.remaining();
			}
			
			@Override
			public void mark(int m)
			{
				mark = m;
			}
			
			@Override
			public void reset()
			{
				in.position( mark );
				mark = -1;
			}
			
			@Override
			public boolean markSupported()
			{
				return true;
			}
			
			@Override
			public void close()
			{
				in.position( 0 );
			}
			
			@Override
			public long skip(long bytes)
			{
				long skipped = Math.min( bytes, in.remaining() );
				
				in.position( in.position() + (int)skipped );
				
				return skipped;
			}
			
			@Override
			public int read(byte[] b, int offset, int length)
			{
				int read = Math.min( in.remaining(), length );
				
				in.get( b, offset, read );
				
				return read == 0 ? -1 : read;
			}
		};
	}
	
}
