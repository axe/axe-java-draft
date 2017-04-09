package com.axe.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Buffers 
{
	
	public static ShortBuffer shorts(int count) 
	{
		return bytes(count << 1).asShortBuffer();
	}
	
	public static ShortBuffer shorts(short ... values) 
	{
		return (ShortBuffer)shorts(values.length).put(values).flip();
	}
	
	public static IntBuffer ints(int count) 
	{
		return bytes(count << 2).asIntBuffer();
	}
	
	public static IntBuffer ints(int ... values) 
	{
		return (IntBuffer)ints(values.length).put(values).flip();
	}
	
	public static ByteBuffer bytes(int count) 
	{
		return ByteBuffer.allocateDirect(count).order(ByteOrder.nativeOrder());
	}
	
	public static ByteBuffer bytes(byte[] values) 
	{
		return (ByteBuffer)bytes(values.length).put(values, 0, values.length).flip();
	}
	
	public static FloatBuffer floats(int count) 
	{
		return bytes(count << 2).asFloatBuffer();
	}
	
	public static FloatBuffer floats(float ... values) 
	{
		return (FloatBuffer)floats(values.length).put(values).flip();
	}
	
	public static DoubleBuffer doubles(int count) 
	{
		return bytes(count << 3).asDoubleBuffer();
	}
	
	public static DoubleBuffer doubles(double ... values) 
	{
		return (DoubleBuffer)doubles(values.length).put(values).flip();
	}
	
}