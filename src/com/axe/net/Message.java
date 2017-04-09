
package com.axe.net;

import java.nio.ByteBuffer;


public interface Message<M>
{

	public M read( ByteBuffer in );

	public void write( ByteBuffer out, M m );

	public int sizeOf( M m );
	
	public int id();
	
	public Class<M> type();
	
}
