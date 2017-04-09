package com.axe.gfx;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.axe.util.Buffers;

public interface VertexMesh 
{
	
	public VertexFormat format();
	
	public VertexMeshType type();
	
	public int vertices();
	
	public int size();
	
	public void destroy();
	
	 // is the given mesh valid?
	public boolean isValid();
	
	// draw entire mesh
	default public void draw()
	{
		draw( 0, vertices() );
	}
	
	 // draw part of mesh
	public void draw(int offset, int vertices);
	
	// draw vertices at the given indices

	default public void draw(short[] indices) 
	{
		draw( Buffers.shorts( indices ) );
	}

	default public void draw(int[] indices) 
	{
		draw( Buffers.ints( indices ) );
	}

	default public void draw(byte[] indices) 
	{
		draw( Buffers.bytes( indices ) );
	}
	
	public void draw(ShortBuffer indices);
	
	public void draw(IntBuffer indices);
	
	public void draw(ByteBuffer indices);
	
	public void draw(ByteBuffer indices, DataType indexType);
	
}