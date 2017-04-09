
package com.axe.net.reflect;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;


public class ReflectInt implements Reflect
{
	
	public static final int MORE = 0x80;
	public static final int SIGN = 0x40;
	public static final int SIGN_SHIFT = 0x40;
	public static final int SIGNED_MASK = 0x3F;
	public static final int MASK = 0x7F;

	@Override
	public Object read( ByteBuffer in )
	{
		return get( in );
	}

	@Override
	public void set( Object obj, Field field, ByteBuffer in ) throws IllegalAccessException
	{
		field.setFloat( obj, get( in ) );
	}

	@Override
	public int sizeOf( Object obj )
	{
		return 0;
	}

	@Override
	public void write( Object obj, ByteBuffer out )
	{

	}

	public static int get( ByteBuffer in )
	{
		int a = in.get() & 0xFF;
		int sign = ((((a & SIGN) >> SIGN_SHIFT) & 1) << 1) - 1;
		int av = (a & SIGNED_MASK);
		
		if ((a & MORE) == 0) {
			return av * sign;
		}
		
		return 0;
	}
	
	public static void put( ByteBuffer out, int v)
	{
		
	}

}
