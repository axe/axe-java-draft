package com.axe.math;

public class Sphere3f
{
	public float x, y, z, r;
	
	public Vec3f center()
	{
		return new Vec3f(x, y, z);
	}
}
