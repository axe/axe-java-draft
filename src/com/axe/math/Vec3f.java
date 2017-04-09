package com.axe.math;

import java.io.Serializable;

import com.axe.core.Attribute;
import com.axe.core.Factory;
import com.axe.math.calc.CalculatorVec3f;

public class Vec3f implements Attribute<Vec3f>, Vec<Vec3f>, Serializable
{
	
	private static final long serialVersionUID = 2108149669637913963L;

	public static final Factory<Vec3f> FACTORY = new Factory<Vec3f>() {
		public Vec3f create() {
			return new Vec3f();
		}
	};

	public static final Vec3f RIGHT = new Vec3f(1, 0, 0);
	public static final Vec3f LEFT = new Vec3f(-1, 0, 0);
	public static final Vec3f UP = new Vec3f(0, 1, 0);
	public static final Vec3f DOWN = new Vec3f(0, -1, 0);
	public static final Vec3f NEAR = new Vec3f(0, 0, -1);
	public static final Vec3f FAR = new Vec3f(0, 0, 1);
	public static final Vec3f ONE = new Vec3f(1, 1, 1);
	public static final Vec3f ZERO = new Vec3f();

	public float x, y, z;
	
	public Vec3f() 
	{
	}
	
	public Vec3f(Vec3f v) 
	{
		set(v);
	}
	
	public Vec3f(float v) 
	{
		x = y = z = v;
	}
	
	public Vec3f(float x, float y) 
	{
		set(x, y, 0);
	}
	
	public Vec3f(float x, float y, float z) 
	{
		set(x, y, z);
	}
	
	public Vec3f(Vec3f v, float dx, float dy, float dz) 
	{
		set(v.x + dx, v.y + dy, v.z + dz);
	}
	
	public void set(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void cross(Vec3f a, Vec3f b) 
	{
		x = a.y * b.z - b.y * a.z;
		y = a.z * b.x - b.z * a.x;
		z = a.x * b.y - b.x * a.y;
	}
	
	public float normal(Vec3f origin, Vec3f target) 
	{
		set(target);
		sub(origin);
		return normal();
	}
	
	public float normal(float x, float y, float z) 
	{
		set(x, y, z); 
		return normal();
	}
	
	public float normal(Vec3f a, Vec3f b, Vec3f c) 
	{
		x = a.y * (b.z - c.z) + b.y * (c.z - a.z) + c.y * (a.z - b.z); 
		y = a.z * (b.x - c.x) + b.z * (c.x - a.x) + c.z * (a.x - b.x);
		z = a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y);
		
		float sq = ( x * x + y * y + z * z );
		
		if (sq != 0 && sq != 1)
		{
			sq = Numbers.sqrt( sq );
			float length = 1f / sq;
			x *= length;
			y *= length;
			z *= length;
		}
		
		return sq;
	}
	
	public void cross(Vec3f r, Vec3f o, Vec3f l) 
	{
		float x0 = r.x-o.x, y0 = r.y-o.y, z0 = r.z-o.z;
		float x1 = l.x-o.x, y1 = l.y-o.y, z1 = l.z-o.z;
		float d0 = (float)(1.0 / Math.sqrt((x0*x0)+(y0*y0)+(z0*z0)));
		float d1 = (float)(1.0 / Math.sqrt((x1*x1)+(y1*y1)+(z1*z1)));
		
		x0 *= d0; y0 *= d0; z0 *= d0;
		x1 *= d1; y1 *= d1; z0 *= d1;
		x = y0 * z1 - y1 * z0;
		y = z0 * x1 - z1 * x0;
		z = x0 * y1 - x1 * y0;
	}
	
	public void rotateY(float angle) 
	{
		rotateY( Numbers.cos( angle ), Numbers.sin( angle ) );
	}
	
	public void rotateY( float cos, float sin ) 
	{
		float ox = x;
		float oz = z;
		x = (cos * ox + sin * oz);
		z = (cos * oz - sin * ox);
	}
	
	public void rotateZ(float angle) 
	{
		rotateZ( Numbers.cos( angle ), Numbers.sin( angle ) );
	}
	
	public void rotateZ( float cos, float sin ) 
	{
		float ox = x;
		float oy = y;
		x = (cos * ox + sin * oy);
		y = (cos * oy - sin * ox);
	}
	
	public void rotateX(float angle) 
	{
		rotateX( Numbers.cos( angle ), Numbers.sin( angle ) );
	}
	
	public void rotateX( float cos, float sin ) {
		float oy = y;
		float oz = z;
		y = (cos * oy + sin * oz);
		z = (cos * oz - sin * oy);
	}
	
	public static float projectScalar(Vec3f ray, Vec3f onto) 
	{
		float bdot = onto.lengthSq();
		if (bdot != 0) {
			bdot = ray.dot( onto ) / Numbers.sqrt( bdot );
		}
		return bdot;
	}
	
	public static float projectUnitScalar(Vec3f ray, Vec3f ontoUnit) 
	{
		return ray.dot( ontoUnit );
	}
	
	public float project(Vec3f ray, Vec3f onto) 
	{
		float bdot = onto.lengthSq();
		if (bdot != 0) {
			set( onto );
			scale( ray.dot( onto ) / bdot );	
		}
		return bdot;
	}
	
	public float reject(Vec3f ray, Vec3f onto) 
	{
		float bdot = onto.lengthSq();
		if (bdot != 0) {
			set( ray );
			adds( onto, -ray.dot( onto ) / bdot );
		}
		return bdot;
	}
	
	public void clampOnUnitAxis(float min, float max, Vec3f axis) 
	{
		float ps = projectUnitScalar( this, axis );
		
		if (ps == 0) {
			return;
		}
		
		if (ps < min) {
//			float d = length();
			scale( min / ps );
//			System.out.println( "min: " + min + " previous: " + d + " projected: " + ps + " resulting: " + length() );
		} else if (ps > max) {
//			float d = length();
			scale( max / ps );
//			System.out.println( "max: " + max + " previous: " + d + " projected: " + ps + " resulting: " + length() );
		}
	}
	
	public void clampOnAxis(float min, float max, Vec3f axis) 
	{
		float ps = projectScalar( this, axis );
		
		if (ps == 0) {
			return;
		}
		
		if (ps < min) {
			scale( min / ps );
//			System.out.println( "min: " + min + " resulting: " + length() );
		} else if (ps > max) {
			scale( max / ps );
//			System.out.println( "max: " + max + " resulting: " + length() );
		}
	}
	
	public void add(float vx, float vy, float vz) {
		x += vx; y += vy; z += vz;
	}
	
	public void average(Vec3f[] v) 
	{
		if (v.length > 0) 
		{
			x = y = z = 0f;
			for (int i = 0; i < v.length; i++) {
				add(v[i]);
			}
			divs(v.length);
		}
	}
	
	@Override
	public Vec3f get() 
	{
		return this;
	}
	
	public float yaw() 
	{
		return (float)Math.atan2( x, z );
	}
	
	public float pitch() 
	{
		return (float)Math.atan2( y, (float)Math.sqrt(x * x + z * z) );
	}
	
	public float angle(Vec3f v) 
	{
		float a = length();
		float b = v.length();
		
		if ( a == 0 || b == 0 ) {
			return 0;
		}
		
		return (float)Math.acos( dot( v ) / (a * b) );
	}
	
	public float angleNormalized(Vec3f v) 
	{
		return (float)Math.acos( dot(v) );
	}

	public void move( float t, Vec3f velocity )
	{
		adds( velocity, t );
	}
	
	public Vec3i floor( Vec3i out )
	{
		out.x = (int)Math.floor( x );
		out.y = (int)Math.floor( y );
		out.z = (int)Math.floor( z );
		return out;
	}

	@Override
	public String toString() 
	{
		return String.format("{%.2f, %.2f, %.2f}", x, y, z);
	}
	
	@Override
	public Vec3f clone() 
	{
		return new Vec3f(x, y, z);
	}
	
	@Override
	public CalculatorVec3f getCalculator()
	{
		return CalculatorVec3f.INSTANCE;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(x + y * 1290 + z * 1664100);
	}

	@Override
	public Vec3f rotate(Vec3f cossin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec3f rotate(Vec3f out, Vec3f cossin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec3f rotaten(Vec3f cossin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec3f unrotate(Vec3f cossin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec3f unrotate(Vec3f out, Vec3f cossin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec3f unrotaten(Vec3f cossin) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Vec3f mul(Vec3f v, float s) 
	{
		return new Vec3f(v.x * s, v.y * s, v.z * s);
	}
	
	public static Vec3f inter(Vec3f s, Vec3f e, float delta) 
	{
		return new Vec3f((e.x - s.x) * delta + s.x,
				(e.y - s.y) * delta + s.y, (e.z - s.z) * delta + s.z);
	}
	
	public static Vec3f newAdd(Vec3f a, Vec3f b) 
	{
		return new Vec3f(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	
	public static Vec3f newNeg(Vec3f v) 
	{
		return new Vec3f(-v.x, -v.y, -v.z);
	}
	
	public static Vec3f newScaled(Vec3f a, float scale) 
	{
		return new Vec3f(a.x * scale, a.y * scale, a.z * scale);
	}
	
	public static Vec3f newCross(Vec3f a, Vec3f b) {
		Vec3f x = new Vec3f();
		x.cross( a, b );
		return x;
	}
	
	public static Vec3f newUnitCross(Vec3f a, Vec3f b) 
	{
		Vec3f x = new Vec3f();
		x.cross( a, b );
		x.normal();
		return x;
	}
	
	public static Vec3f newNormal(Vec3f origin, Vec3f point) 
	{
		Vec3f x = new Vec3f();
		x.set( point );
		x.sub( origin );
		x.normal();
		return x;
	}
	
	public static Vec3f newNormal(Vec3f v) 
	{
		Vec3f x = new Vec3f();
		x.normal( v );
		return x;
	}
	
	public static void setComplementBasis(Vec3f a, Vec3f b, Vec3f n) 
	{
	    if (Math.abs(n.x) >= Math.abs(n.y))
	    {
	        // n.x or n.z is the largest magnitude component, swap them
	        float invLength = 1.0f / (float)Math.sqrt(n.x * n.x + n.z * n.z);
	        a.x = -n.z * invLength;
	        a.y = 0.0f;
	        a.z = +n.x * invLength;
	        b.x = n.y * a.z;
	        b.y = n.z * a.x - n.x * a.z;
	        b.z = -n.y * a.x;
	    }
	    else
	    {
	        // n.y or n.z is the largest magnitude component, swap them
	        float invLength = 1.0f / (float)Math.sqrt(n.y * n.y + n.z * n.z);
	        a.x = 0.0f;
	        a.y = +n.z * invLength;
	        a.z = -n.y * invLength;
	        b.x = n.y * a.z - n.z * a.y;
	        b.y = -n.x * a.z;
	        b.z = n.x * a.y;
	    }
	}
	
}