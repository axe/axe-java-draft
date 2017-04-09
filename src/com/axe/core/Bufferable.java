package com.axe.core;

import java.nio.ByteBuffer;


public interface Bufferable
{
	public void write( ByteBuffer out );
	
	public void read( ByteBuffer in );
	
	public int bytes();
}
