package com.axe.math;

public class Plane3f
{
	public static final int ON_PLANE = 0;
	public static final int FRONT = 1;
	public static final int BACK = -1;
	public static final int INTERSECTS = 2;

	public float a, b, c, d;

	public Plane3f()
	{
		this(1, 0, 0, 0);
	}
	
	public Plane3f(Vec3f normal, Vec3f position)
	{
		set(normal, position);
	}

	public Plane3f(float a, float b, float c, float d)
	{
		set(a, b, c, d);
	}
	
	public void set(Vec3f normal, Vec3f position)
	{
		this.a = normal.x;
		this.b = normal.y;
		this.c = normal.z;
		this.d = -a * position.x - b * position.y - c * position.z;
	}
	
	public void set(float a, float b, float c, float d)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public void norm()
	{
		float mag = (float)Math.sqrt(a * a + b * b + c * c);
		if (mag != 0) {
			mag = 1f / mag;
			a *= mag;
			b *= mag;
			c *= mag;
			d *= mag;
		}
	}

	public int sign(Bound3f b)
	{
		boolean dif = false;
		int s = sign(b.l, b.t, b.n);
		dif = dif || (sign(b.l, b.t, b.f) != s);
		dif = dif || (sign(b.r, b.t, b.n) != s);
		dif = dif || (sign(b.r, b.t, b.f) != s);
		dif = dif || (sign(b.l, b.b, b.n) != s);
		dif = dif || (sign(b.l, b.b, b.f) != s);
		dif = dif || (sign(b.r, b.b, b.n) != s);
		dif = dif || (sign(b.r, b.b, b.f) != s);
		return (dif ? INTERSECTS : s);
	}

	public int sign(Vec3f [] vs)
	{
		boolean dif = false;
		int s = sign(vs[0]);
		for (int i = 1; i < vs.length; i++) {
			dif |= (sign(vs[i]) != s);
		}
		return (dif ? INTERSECTS : s);
	}

	public int sign(float x, float y, float z)
	{
		return (int)Math.signum(distance(x, y, z));
	}

	public int sign(Line3f l)
	{
		int s = sign(l.s);
		int e = sign(l.e);
		return (s == 0 ? e : (e == 0 || s == e ? s : INTERSECTS));
	}
	
	public int sign(Vec3f v)
	{
		return (int)Math.signum(distance(v));
	}

	public int sign(Sphere3f s)
	{
		return (int)Math.signum(distance(s));
	}

	
	public float distance(float x, float y, float z)
	{
		return a * x + b * y + c * z + d;
	}

	public float distance(Vec3f v)
	{
		return a * v.x + b * v.y + c * v.z + d;
	}
	
	public float distance(Vec3i v)
	{
		return a * v.x + b * v.y + c * v.z + d;
	}

	public float distance(Sphere3f s)
	{
		float d = distance(s.x, s.y, s.z);
		d -= (int)Math.signum(d) * s.r;
		return d;
	}
	
	public float distance(Line3f x)
	{
		float ds = distance(x.s);
		float de = distance(x.e);
		int ss = (int)Math.signum(ds);
		int se = (int)Math.signum(de);
		if (ss != se) {
			return 0;
		}
		return Math.min(ds * ss, de * se);
	}
	
	
	
	public Line3f clip(Line3f x, int side)
	{
		Vec3f dir = x.diff();
		Vec3f normal = new Vec3f(a, b, c);
		
		float d = normal.dot(dir);
		float sd = distance(x.s);
		int q = (int)Math.signum(sd);
		
		if (d == 0.0) {
			return (q == side || sign(x.e) == side ? x : null);
		}

		float u = -sd / d;
		
		if (u >= 1.0 || u <= 0.0) {
			return (q == side || sign(x.e) == side ? x : null);
		}
		
		if (q == side || q == ON_PLANE) {
			x.e.interpolate(x.s, x.e, u);
		}
		else {
			x.s.interpolate(x.s, x.e, u);
		}
		
		return x;
	}

}
