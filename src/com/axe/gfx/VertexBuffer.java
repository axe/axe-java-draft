package com.axe.gfx;

import java.nio.ByteBuffer;

public interface VertexBuffer 
{

	// clear buffer data
	public void reset(VertexMesh vb); 

	// select mesh for reading
	default public boolean load()
	{
		return load( mesh(), 0, mesh().vertices() );
	}
	
	default public boolean load(VertexMesh mesh)
	{
		return load( mesh, 0, mesh.vertices() );
	}

	public boolean load(VertexMesh mesh, int offset, int count);

	// the current mesh being read or written to.
	public VertexMesh mesh();

	// move position to next vertex. only necessary when writing or
	// reading directly.
	public void next(); 
					
	// set position given vertex index
	public void vertex(int index); 

	// maximum number of vertex
	public int capacity(); 

	// number of vertices currently written
	public int vertices(); 

	// number of vertices currently available
	public int remaining();

	// the current byte buffer being written to
	public ByteBuffer data();
	

	default public boolean write(WritableVertex vertex)
	{
		final boolean has = remaining() > 0;
		
		if (has)
		{
			vertex.write( data() );
			next();
		}
		
		return has;
	}

	default public int write(WritableVertex[] vertex)
	{
		return write( vertex, 0, vertex.length );
	}

	default public int write(WritableVertex[] vertex, int offset)
	{
		return write( vertex, offset, vertex.length - offset );
	}

	default public int write(WritableVertex[] vertex, int offset, int length)
	{
		final int end = offset + length;
		int written = 0;
		
		for (int i = offset; i < end; i++)
		{
			if ( write( vertex[ i ] ) )
			{
				written++;
			}
			else
			{
				break;
			}
		}
		
		return written;
	}

	default public boolean read(ReadableVertex vertex)
	{
		final boolean has = remaining() > 0;
		
		if (has)
		{
			vertex.read( data() );
			next();
		}
		
		return has;
	}

	default public int read(ReadableVertex[] vertex)
	{
		return read( vertex, 0, vertex.length );
	}

	default public int read(ReadableVertex[] vertex, int offset)
	{
		return read( vertex, offset, vertex.length - offset );
	}

	default public int read(ReadableVertex[] vertex, int offset, int length)
	{
		final int end = offset + length;
		int read = 0;
		
		for (int i = offset; i < end; i++)
		{
			if ( read( vertex[ i ] ) )
			{
				read++;
			}
			else
			{
				break;
			}
		}
		
		return read;
	}

	// with the current - update the given buffer
	default public boolean save()
	{
		return save( 0 );
	}

	// update part of the buffer
	public boolean save(int offset); 
	
}