package com.axe.gfx;

public abstract class AbstractVertexBuffer implements VertexBuffer
{

	protected VertexMesh mesh;
	protected int position;
	
	protected AbstractVertexBuffer()
	{
	}

	public void reset(VertexMesh mesh)
	{
		this.mesh = mesh;
		this.position = 0;
		this.data().position( 0 );
	}
	
	public VertexMesh mesh()
	{
		return mesh;
	}
	
	public void next()
	{
		final int s = mesh.format().stride;
		
		data().position( position += s );
	}
	
	public void vertex(int index)
	{
		final int s = mesh.format().stride;
		
		data().position( position = index * s );
	}
	
	public int capacity()
	{
		final int s = mesh.format().stride;
		
		return data().capacity() / s;
	}
	
	public int vertices()
	{
		final int s = mesh.format().stride;
		final int p = data().position();
		
		return (p + s - 1) / s;
	}
	
	public int remaining()
	{
		final int s = mesh.format().stride;
		final int p = data().position();
		final int r = data().remaining();
		
		return (r + p % s) / s;
	}
	
}
