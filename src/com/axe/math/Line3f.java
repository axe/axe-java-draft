package com.axe.math;

public class Line3f 
{

	public final Vec3f s;
	public final Vec3f e;
	
	public Line3f() {
		this( new Vec3f(), new Vec3f() );
	}

	public Line3f(float x0, float y0, float z0, float x1, float y1, float z1) {
		this ( new Vec3f(x0, y0, z0), new Vec3f(x1, y1, z1) );
	}

	public Line3f(Line3f line) {
		this( line.s, line.e );
	}
	
	public Line3f(Vec3f s, Vec3f e) {
		this.s = s;
		this.e = e;
	}
	
	public void set(Line3f line) {
		s.set(line.s);
		e.set(line.e);
	}
	
	public void set(float x0, float y0, float z0, float x1, float y1, float z1) {
		s.set(x0, y0, z0);
		e.set(x1, y1, z1);
	}
	
	public void set(Vec3f start, Vec3f end) {
		s.set(start);
		e.set(end);
	}
	
	public float length() {
		return s.distance(e);
	}
	
	public Vec3f dir() {
		Vec3f v = new Vec3f();
		v.normal(e.x - s.x, e.y - s.y, e.z - s.z);
		return v;
	}
	
	public Vec3f diff() {
		return new Vec3f(e.x - s.x, e.y - s.y, e.z - s.z);
	}
	
	public String toString() {
		return String.format("{(%.2f, %.2f, %.2f) => (%.2f, %.2f ,%.2f)}", s.x, s.y, s.z, e.x, e.y, e.z);
	}
	
}
