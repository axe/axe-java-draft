package com.axe.math;

import java.io.Serializable;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorVec3i;

public class Vec3i implements Attribute<Vec3i>, Serializable
{
	
	private static final long serialVersionUID = -6351119441833706561L;
	
	public static final Vec3i RIGHT = new Vec3i(1, 0, 0);
	public static final Vec3i LEFT = new Vec3i(-1, 0, 0);
	public static final Vec3i UP = new Vec3i(0, 1, 0);
	public static final Vec3i DOWN = new Vec3i(0, -1, 0);
	public static final Vec3i NEAR = new Vec3i(0, 0, 1);
	public static final Vec3i FAR = new Vec3i(0, 0, -1);
	public static final Vec3i ZERO = new Vec3i(0, 0, 0);
	public static final Vec3i ONE = new Vec3i(1, 1, 1);
	
	public int x, y, z;

	public Vec3i() 
	{
	}
	
	public Vec3i(Vec3i v) 
	{
		set(v);
	}
	
	public Vec3i(int v) 
	{
		x = y = z = v;
	}
	
	public Vec3i(int x, int y, int z) 
	{
		set(x, y, z);
	}
	
	public Vec3i( Vec3f v) 
	{
		set( (int)v.x, (int)v.y, (int)v.z );
	}
	
	public void set(int x, int y, int z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void cross(Vec3i a, Vec3i b) 
	{
		x = a.y * b.z - b.y * a.z;
		y = a.z * b.x - b.z * a.x;
		z = a.x * b.y - b.x * a.y;
	}
	
	public float norm(int x, int y, int z) 
	{
		set(x, y, z); 
		return normal();
	}
	
	public void cross(Vec3i r, Vec3i o, Vec3i l) 
	{
		float x0 = r.x-o.x, y0 = r.y-o.y, z0 = r.z-o.z;
		float x1 = l.x-o.x, y1 = l.y-o.y, z1 = l.z-o.z;
		float d0 = (float)(1.0 / Math.sqrt((x0*x0)+(y0*y0)+(z0*z0)));
		float d1 = (float)(1.0 / Math.sqrt((x1*x1)+(y1*y1)+(z1*z1)));
		x0 *= d0; y0 *= d0; z0 *= d0;
		x1 *= d1; y1 *= d1; z0 *= d1;
		x = (int)(y0 * z1 - y1 * z0);
		y = (int)(z0 * x1 - z1 * x0);
		z = (int)(x0 * y1 - x1 * y0);
	}
	
	public Vec3i reflect(Vec3i v, Vec3i n) 
	{
		set(v); adds(n, -2f * v.dot(n));
		return this;
	}
	
	public Vec3i refract(Vec3i v, Vec3i n) 
	{
		reflect(v, n); neg();
		return this;
	}
	public void div(float s) {
		if (s != 0.0) {
			s = 1f / s;
			x *= s;
			y *= s;
			z *= s;
		}
	}
	public void mul(float s) {
		x *= s;
		y *= s;
		z *= s;
	}
	public void add(int vx, int vy, int vz) {
		x += vx; 
		y += vy; 
		z += vz;
	}
	public void add(Vec3i v, float scale) {
		x += v.x * scale; 
		y += v.y * scale;
		z += v.z * scale;
	}
	public void average(Vec3i[] v) {
		if (v.length > 0) {
			x = y = z = 0;
			for (int i = 0; i < v.length; i++) {
				add(v[i]);
			}
			div(v.length);
		}
	}
	
	@Override
	public Vec3i get() 
	{
		return this;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(x + y * 1290 + z * 1664100);
	}
	
	public boolean equals(Object o) {
		if (o instanceof Vec3i) {
			return isEqual((Vec3i)o);
		}
		return false;
	}
	
	@Override
	public String toString() 
	{
		return String.format("{%d, %d, %d}", x, y, z);
	}
	
	@Override
	public Vec3i clone() 
	{
		return new Vec3i( x, y, z );
	}
	
	@Override
	public CalculatorVec3i getCalculator()
	{
		return CalculatorVec3i.INSTANCE;
	}

	public static Vec3i inter(Vec3i s, Vec3i e, float delta) 
	{
		return new Vec3i((int)((e.x - s.x) * delta) + s.x,
				(int)((e.y - s.y) * delta) + s.y, 
				(int)((e.z - s.z) * delta) + s.z);
	}

}