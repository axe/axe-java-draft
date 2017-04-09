package com.axe.gfx;


public enum DataType 
{
	Byte( 1 ), 
	Ubyte( 1 ), 
	Short( 2 ), 
	Ushort( 2 ),
	Integer( 4 ), 
	Uint ( 4 ),
	Float( 4 ), 
	Double( 8 );

	public final int bytes;

	private DataType(int bytes) 
	{
		this.bytes = bytes;
	}
}