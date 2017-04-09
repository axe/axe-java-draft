
package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorBound3f;


public class Bound3f implements Attribute<Bound3f>
{

	public float l, r, t, b, n, f;

	public Bound3f()
	{
	}

	public Bound3f( float v )
	{
		l = r = t = b = n = f = v;
	}

	public Bound3f( float x, float y, float z )
	{
		clear( x, y, z );
	}

	public Bound3f( float left, float top, float right, float bottom, float near, float far )
	{
		set( left, top, right, bottom, near, far );
	}

	public Bound3f( Bound3f x )
	{
		set( x );
	}

	public void include( float x, float y, float z )
	{
		if (x < l) l = x;
		if (x > r) r = x;
		if (y > t) t = y;
		if (y < b) b = y;
		if (z < n) n = z;
		if (z > f) f = z;
	}

	public void include( Vec3f v )
	{
		include( v.x, v.y, v.z );
	}

	public void clear( float x, float y, float z )
	{
		l = r = x;
		t = b = y;
		n = f = z;
	}

	public void clear( Vec3f v )
	{
		clear( v.x, v.y, v.z );
	}
	
	public boolean isNegative()
	{
		return r < l || b < t || f < n;
	}

	public void set( float left, float top, float right, float bottom, float near, float far )
	{
		l = left;
		r = right;
		t = top;
		b = bottom;
		n = near;
		f = far;
	}
	
	public void set(Vec3f min, Vec3f max)
	{
		l = min.x;
		t = max.y;
		n = min.z;
		r = max.x;
		b = min.y;
		f = max.z;
	}

	public void rect( float x, float y, float z, float width, float height, float depth )
	{
		set( x, y, x + width, y - height, z, z + depth );
	}

	public void line( float x0, float y0, float z0, float x1, float y1, float z1 )
	{
		l = Math.min( x0, x1 );
		r = Math.max( x0, x1 );
		t = Math.max( y0, y1 );
		b = Math.min( y0, y1 );
		n = Math.min( z0, z1 );
		f = Math.max( z0, z1 );
	}

	public void ellipse( float cx, float cy, float cz, float rw, float rh, float rz )
	{
		rw = Math.abs( rw );
		rh = Math.abs( rh );
		rz = Math.abs( rz );
		l = cx - rw;
		r = cx + rw;
		t = cy + rh;
		b = cy - rh;
		n = cz - rz;
		f = cz + rz;
	}

	public void center( float x, float y, float z )
	{
		float w = width() * 0.5f;
		float h = height() * 0.5f;
		float d = depth() * 0.5f;
		l = x - w;
		r = x + w;
		t = y + h;
		b = y - h;
		n = z - d;
		f = z + d;
	}

	public void move( Vec3f delta )
	{
		move( delta.x, delta.y, delta.z );
	}

	public void move( float dx, float dy, float dz )
	{
		l += dx;
		r += dx;
		t += dy;
		b += dy;
		n += dz;
		f += dz;
	}

	public void expand( float radius )
	{
		expand( radius, radius, radius );
	}
	
	public void expand( float gx, float gy, float gz )
	{
		l -= gx;
		r += gx;
		t += gy;
		b -= gy;
		n -= gz;
		f += gz;
	}

	public void expand( float dl, float dt, float dr, float db, float dn, float df )
	{
		l -= dl;
		r += dr;
		t += dt;
		b -= db;
		n -= dn;
		f += df;
	}

	public void zoom( float sx, float sy, float sz )
	{
		float hw = width() * 0.5f * Math.abs( sx );
		float hh = height() * 0.5f * Math.abs( sy );
		float hd = depth() * 0.5f * Math.abs( sz );
		ellipse( cx(), cy(), cz(), hw, hh, hd );
	}

	public float width()
	{
		return (r - l);
	}

	public float height()
	{
		return (t - b);
	}

	public float depth()
	{
		return (f - n);
	}

	public float area()
	{
		return (r - l) * (t - b) * (f - n);
	}

	public float cx()
	{
		return (l + r) * 0.5f;
	}

	public float cy()
	{
		return (t + b) * 0.5f;
	}

	public float cz()
	{
		return (n + f) * 0.5f;
	}

	public float dx( float dx )
	{
		return (r - l) * dx + l;
	}

	public float dy( float dy )
	{
		return (b - t) * dy + t;
	}

	public float dz( float dz )
	{
		return (f - n) * dz + n;
	}

	public Vec3f delta( Vec3f delta, Vec3f out )
	{
		return delta( delta.x, delta.y, delta.z, out );
	}

	public Vec3f delta( float dx, float dy, float dz, Vec3f out )
	{
		out.x = dx( dx );
		out.y = dy( dy );
		out.z = dz( dz );

		return out;
	}

	public void getCorners( Vec3f[] x )
	{
		x[0].set( l, t, n );
		x[1].set( l, b, n );
		x[2].set( l, t, f );
		x[3].set( l, b, f );
		x[4].set( r, t, n );
		x[5].set( r, b, n );
		x[6].set( r, t, f );
		x[7].set( r, b, f );
	}

	public void getBorder( Line3f[] x )
	{
		// n => f
		x[0].set( l, t, n, l, t, f );
		x[1].set( l, b, n, l, b, f );
		x[2].set( r, t, n, r, t, f );
		x[3].set( r, b, n, r, b, f );
		// l => r
		x[4].set( l, t, n, r, t, n );
		x[5].set( l, b, n, r, b, n );
		x[6].set( l, t, f, r, t, f );
		x[7].set( l, b, f, r, b, f );
		// b => t
		x[8].set( l, b, n, l, t, n );
		x[9].set( r, b, n, r, t, n );
		x[10].set( l, b, f, l, t, f );
		x[11].set( r, b, f, r, t, f );
	}

	public void intersection( Bound3f x, Bound3f y )
	{
		l = Math.max( x.l, y.l );
		r = Math.min( x.r, y.r );
		t = Math.min( x.t, y.t );
		b = Math.max( x.b, y.b );
		n = Math.max( x.n, y.n );
		f = Math.min( x.f, y.f );
	}

	public void union( Bound3f x, Bound3f y )
	{
		l = Math.min( x.l, y.l );
		r = Math.max( x.r, y.r );
		t = Math.max( x.t, y.t );
		b = Math.min( x.b, y.b );
		n = Math.min( x.n, y.n );
		f = Math.max( x.f, y.f );
	}

	public boolean intersects( Bound3f x )
	{
		return !outside( x );
	}
	
	public boolean outside( Bound3f x )
	{
		return (x.l >= r || x.r <= l || x.t <= b || x.b >= t || x.n >= f || x.f <= n);
	}

	public boolean touches( Bound3f x )
	{
		return !(x.l > r || x.r < l || x.t < b || x.b > t || x.n > f || x.f < n);
	}

	public boolean contains( Bound3f x )
	{
		return !(x.l < l || x.r > r || x.t > t || x.b < b || x.n < n || x.f > f);
	}

	public boolean inside( Bound3f x )
	{
		return !(x.l <= l || x.r >= r || x.t >= t || x.b <= b || x.n <= n || x.f >= f);
	}
	
	public boolean isValid()
	{
		return this.isFinite() && l <= r && b <= t && n <= f;
	}

	public String toString()
	{
		return String.format( "{%.2f, %.2f, %.2f, %.2f, %.2f, %.2f}", l, t, n, width(), height(), depth() );
	}

	public String toExtentString()
	{
		return String.format( "{T=%.2f B=%.2f R=%.2f L=%.2f N=%.2f F=%.2f}", t, b, r, l, n, f );
	}

	public boolean contains( float x, float y, float z )
	{
		return !(x < l || x > r || y > t || y < b || z > f || z < n);
	}

	public boolean contains( Vec3f v )
	{
		return contains( v.x, v.y, v.z );
	}

	public float intersectsRay( Vec3f origin, Vec3f dir )
	{
		float invdirx = (dir.x == 0 ? 0 : 1.0f / dir.x);
		float invdiry = (dir.y == 0 ? 0 : 1.0f / dir.y);
		float invdirz = (dir.z == 0 ? 0 : 1.0f / dir.z);

		float t1 = (l - origin.x) * invdirx;
		float t2 = (r - origin.x) * invdirx;
		float t3 = (b - origin.y) * invdiry;
		float t4 = (t - origin.y) * invdiry;
		float t5 = (n - origin.z) * invdirz;
		float t6 = (f - origin.z) * invdirz;

		float tmin = Math.max( Math.max( Math.min( t1, t2 ), Math.min( t3, t4 ) ), Math.min( t5, t6 ) );
		float tmax = Math.min( Math.min( Math.max( t1, t2 ), Math.max( t3, t4 ) ), Math.max( t5, t6 ) );
		
		return (tmax >= 0 && tmin <= tmax) ? tmin : -1.0f;
	}

	public void scale( float sx, float sy, float sz )
	{
		l *= sx;
		r *= sx;
		t *= sy;
		b *= sy;
		f *= sz;
		n *= sz;
	}

	public float distanceSq( Vec3f v )
	{
		return 0f;
	}

	@Override
	public Bound3f get()
	{
		return this;
	}

	@Override
	public Bound3f clone()
	{
		return new Bound3f( l, t, r, b, n, f );
	}

	@Override
	public CalculatorBound3f getCalculator()
	{
		return CalculatorBound3f.INSTANCE;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(t + b * 36 + r * 1296 + l * 46656 + n * 1679616 + f * 60466176);
	}

}
