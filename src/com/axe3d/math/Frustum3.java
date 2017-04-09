package com.axe3d.math;

import com.axe.math.Bound3f;
import com.axe.math.Plane3f;
import com.axe.math.Sphere3f;
import com.axe.math.Vec3f;
import com.axe.math.Vec3i;

public class Frustum3 
{
	public static final int Outside = -1;
	public static final int Inside = 1;
	public static final int Intersects = 2;
	
	public static final int Left = 0;
	public static final int Right = 1;
	public static final int Bottom = 2;
	public static final int Top = 3;
	public static final int Near = 4;
	public static final int Far = 5;
	
	private static final int PLANE_COUNT = 6;
	
	public final Plane3f[] planes = {
			new Plane3f(), new Plane3f(), new Plane3f(), 
			new Plane3f(), new Plane3f(), new Plane3f()
	};

	public void update(Vec3f position, Vec3f direction, Vec3f up, Vec3f right, float fov, float near, float far)
	{
		// TODO
	}

	public Plane3f plane(int i)
	{
		return planes[i];
	}

	public float distance(Vec3f v)
	{
		float distance = planes[0].distance(v);
		
		for (int i = 1; i < PLANE_COUNT; i++) 
		{
			float d = planes[i].distance(v);
			
			if (d >= 0) 
			{
				if (distance < 0) 
				{
					distance = d;
				}
				else 
				{
					distance = (d < distance ? d : distance);
				}
			}
			else if (d > distance) 
			{
				distance = d;
			}
		}
		return distance;
	}
	
	public int sign(Vec3f v)
	{
		return (int)Math.signum(distance(v));
	}
	
	public float distance(Vec3i v)
	{
		return distance(new Vec3f(v.x, v.y, v.z));
	}
	
	public int sign(Vec3i v)
	{
		return (int)Math.signum(distance(v));
	}
	
	public int sign(Bound3f b)
	{
		int inside = 0;
		
		for (int i = 0; i < PLANE_COUNT; i++) 
		{
			int sign = planes[i].sign( b );
			
			if ( sign == Plane3f.BACK )
			{
				return Outside;
			}
			if ( sign == Plane3f.FRONT )
			{
				inside++;
			}
		}
		
		return (inside == PLANE_COUNT ? Inside : Intersects);
	}
	
	public float distance(Sphere3f s) 
	{
		float d = distance(s.center());
		int g = (int)Math.signum(d);
		return d - (g * s.r);
	}
	
	public int sign(Sphere3f s)
	{
		return (int)Math.signum(distance(s));
	}
	
}
