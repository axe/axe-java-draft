package com.axe.math;


public class Quaternion3 
{
	
	public float x, y, z, w;
	
	public Quaternion3() 
	{
	}
	
	public Quaternion3(float x, float y, float z, float w) 
	{
		set(x, y, z, w);
	}
	
	public Quaternion3(Vec3f v, float angle) 
	{
		set(v, angle);
	}
	
	public void set(float x, float y, float z, float w) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(Vec3f v, float angle) 
	{
		float half = angle * 0.5f;
		float sin = Numbers.sin(half) / v.length();
		float cos = Numbers.cos(half);
		x = v.x * sin;
		y = v.y * sin;
		z = v.z * sin;
		w = cos;
	}
	
	public Quaternion3 setEuler(float yaw, float pitch, float roll)
	{
		final float hr = roll * 0.5f;
		final float shr = Numbers.sin(hr);
		final float chr = Numbers.cos(hr);
		final float hp = pitch * 0.5f;
		final float shp = Numbers.sin(hp);
		final float chp = Numbers.cos(hp);
		final float hy = yaw * 0.5f;
		final float shy = Numbers.sin(hy);
		final float chy = Numbers.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
		y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
		z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
		w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
		return this;
	}
	
	public void invert() 
	{
		x = -x;
		y = -y;
		z = -z;
	}
	
	public void multiply(Quaternion3 a, Quaternion3 b) 
	{
		x = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
		y = a.w * b.y + a.y * b.w + a.z * b.x - a.x * b.z;
		z = a.w * b.z + a.z * b.w + a.x * b.y - a.y * b.x;
		w = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
	}
	
	public void multiply(float ax, float ay, float az, float aw, float bx, float by, float bz, float bw) 
	{
		x = aw * bx + ax * bw + ay * bz - az * by;
		y = aw * by + ay * bw + az * bx - ax * bz;
		z = aw * bz + az * bw + ax * by - ay * bx;
		w = aw * bw - ax * bx - ay * by - az * bz;
	}
	
	private static final Quaternion3 rot = new Quaternion3();
	
	public void rotate(Vec3f v)
	{
		float m = v.normal();
		rot.multiply(v.x, v.y, v.z, 0f, -x, -y, -z, w);
		rot.multiply(x, y, z, w, rot.x, rot.y, rot.z, rot.w);
		v.set(rot.x, rot.y, rot.z);
		v.scale( m );
	}
	
	public Quaternion3 copy() 
	{
		return new Quaternion3(x, y, z, w);
	}
	
}