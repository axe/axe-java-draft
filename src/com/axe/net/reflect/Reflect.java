package com.axe.net.reflect;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public interface Reflect
{
	public Object read(ByteBuffer in);
	public void set(Object obj, Field field, ByteBuffer in) throws IllegalAccessException;
	public void write(Object obj, ByteBuffer out);
	public int sizeOf(Object obj);
}
