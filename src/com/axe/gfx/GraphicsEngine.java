package com.axe.gfx;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.axe.util.Buffers;

public interface GraphicsEngine 
{

	public VertexMesh newVertexMesh(VertexMeshType type, VertexFormat format);
	
	public VertexBuffer newVertexBuffer(int chunkSizeInBytes, boolean autoExpand);
	
	default public VertexBuffer newVertexBufferFor(VertexMeshType type, VertexFormat format, int chunkSizeInVertices, boolean autoExpand)
	{
		VertexMesh mesh = newVertexMesh( type, format );
		VertexBuffer buffer = newVertexBuffer( format.stride * chunkSizeInVertices, autoExpand );
		
		buffer.reset( mesh );
		
		return buffer;
	}
	
	default public ByteBuffer newDataBuffer(int count, DataType type)
	{
		return Buffers.bytes( count * type.bytes );
	}
	
	default public ShortBuffer newIndexBuffer(int indexCount)
	{
		return Buffers.shorts( indexCount );
	}
	
	default public IntBuffer newLargeIndexBuffer(int indexCount)
	{
		return Buffers.ints( indexCount );
	}
	
	default public ByteBuffer newSmallIndexBuffer(int indexCount)
	{
		return Buffers.bytes( indexCount );
	}
	
	public ShaderObject newShaderObject( ShaderObjectType type, String code );
	
	public Shader newShader( ShaderObject ... objects );
	
	public Shader newShader( String ... objectPaths );

	public Shader newShader( Class<Enum<?>> enumClass, ShaderObject ... objects );
	
	public Shader newShader( Class<Enum<?>> enumClass, String ... objectPaths );

	public String getVersion();
	
	public String getGraphicsCard();
	
}
