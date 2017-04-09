package com.axe.math;

import java.io.Serializable;

import com.axe.core.Factory;

public class Vec4f implements Serializable
{
	private static final long serialVersionUID = 6065062711339366453L;

	public float x, y, z, w;
	
	public Vec4f() {
	}
	public Vec4f(float v) {
		set(v, v, v, v);
	}
	public Vec4f(float x, float y) {
		set(x, y, 0, 0);
	}
	public Vec4f(float x, float y, float z) {
		set(x, y, z, 0);
	}
	public Vec4f(float x, float y, float z, float w) {
		set(x, y, z, w);
	}
	
	public void set(Vec4f v) {
		set( v.x, v.y, v.z, v.w );
	}
	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public static Factory<Vec4f> FACTORY = new Factory<Vec4f>() {
		public Vec4f create(){
			return new Vec4f();
		}
	};
	
}