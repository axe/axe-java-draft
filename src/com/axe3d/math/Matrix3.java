package com.axe3d.math;

import com.axe.math.Matrix;
import com.axe.math.Vec3f;

public class Matrix3 implements Matrix<Vec3f> 
{
	public static final int VALUES = 16;
	
	public static final int M00 = 0;
	public static final int M01 = 4;
	public static final int M02 = 8;
	public static final int M03 = 12;
	public static final int M10 = 1;
	public static final int M11 = 5;
	public static final int M12 = 9;
	public static final int M13 = 13;
	public static final int M20 = 2;
	public static final int M21 = 6;
	public static final int M22 = 10;
	public static final int M23 = 14;
	public static final int M30 = 3;
	public static final int M31 = 7;
	public static final int M32 = 11;
	public static final int M33 = 15;
	
	private static float[] temp0 = new float[VALUES];
	private static float[] temp1 = new float[VALUES];
	
	private static Vec3f EYE = new Vec3f();
	private static Vec3f UP = new Vec3f();
	private static Vec3f FORWARD = new Vec3f();
	private static Vec3f SIDE = new Vec3f();
	
	public float[] value = new float[VALUES];
	
	public Matrix3()
	{
		this.identity();
	}
	
	public Matrix3(float[] value)
	{
		this.set(value);
	}
	
	@Override
	public Vec3f createVector()
	{
		return new Vec3f();
	}
	
	@Override
	public Matrix<Vec3f> identity() 
	{
		setIdentity( this.value );
		return this;
	}

	@Override
	public float determinant() 
	{
		float[] m = this.value;
		
		return m[M30] * m[M21] * m[M12] * m[M03] - 
				m[M20] * m[M31] * m[M12] * m[M03] - 
				m[M30] * m[M11] * m[M22] * m[M03] + 
				m[M10] * m[M31] * m[M22] * m[M03] + 
				m[M20] * m[M11] * m[M32] * m[M03] - 
				m[M10] * m[M21] * m[M32] * m[M03] - 
				m[M30] * m[M21] * m[M02] * m[M13] + 
				m[M20] * m[M31] * m[M02] * m[M13] + 
				m[M30] * m[M01] * m[M22] * m[M13] - 
				m[M00] * m[M31] * m[M22] * m[M13] - 
				m[M20] * m[M01] * m[M32] * m[M13] + 
				m[M00] * m[M21] * m[M32] * m[M13] + 
				m[M30] * m[M11] * m[M02] * m[M23] - 
				m[M10] * m[M31] * m[M02] * m[M23] - 
				m[M30] * m[M01] * m[M12] * m[M23] + 
				m[M00] * m[M31] * m[M12] * m[M23] + 
				m[M10] * m[M01] * m[M32] * m[M23] - 
				m[M00] * m[M11] * m[M32] * m[M23] - 
				m[M20] * m[M11] * m[M02] * m[M33] + 
				m[M10] * m[M21] * m[M02] * m[M33] + 
				m[M20] * m[M01] * m[M12] * m[M33] - 
				m[M00] * m[M21] * m[M12] * m[M33] - 
				m[M10] * m[M01] * m[M22] * m[M33] + 
				m[M00] * m[M11] * m[M22] * m[M33];
	}

	
	@Override
	public Matrix3 ortho( Vec3f min, Vec3f max )
	{
		return ortho( min.x, max.y, max.x, min.y, min.z, max.z );
	}
	
	public Matrix3 ortho(float left, float top, float right, float bottom)
	{
		return ortho( left, top, right, bottom, -1, 1 );
	}
	
	public Matrix3 ortho(float left, float top, float right, float bottom, float near, float far)
	{
		float[] m = this.value;
		float dx = (right - left);
		float dy = (top - bottom);
		float dz = (far - near);
		float xo = 2 / dx;
		float yo = 2 / dy;
		float zo = -2 / dz;
		float tx = -(right + left) / dx;
		float ty = -(top + bottom) / dy;
		float tz = -(far + near) / dz;
		
		m[M00] = xo;
		m[M10] = 0;
		m[M20] = 0;
		m[M30] = 0;
		
		m[M01] = 0;
		m[M11] = yo;
		m[M21] = 0;
		m[M31] = 0;
		
		m[M02] = 0;
		m[M12] = 0;
		m[M22] = zo;
		m[M32] = 0;
		
		m[M03] = tx;
		m[M13] = ty;
		m[M23] = tz;
		m[M33] = 1;
		
		return this;
	}
	
	public Matrix3 perspective(float fov, float aspectRatio, float near, float far)
	{
		float[] m = this.value;
		float dz = (near - far);
		float cotangent = (float)(1.0 / Math.tan((fov * (Math.PI / 180)) / 2.0));
		float l_a1 = (far + near) / dz;
		float l_a2 = (2 * far * near) / dz;
		
		m[M00] = cotangent / aspectRatio;
		m[M10] = 0;
		m[M20] = 0;
		m[M30] = 0;
		
		m[M01] = 0;
		m[M11] = cotangent;
		m[M21] = 0;
		m[M31] = 0;
		
		m[M02] = 0;
		m[M12] = 0;
		m[M22] = l_a1;
		m[M32] = -1;
		
		m[M03] = 0;
		m[M13] = 0;
		m[M23] = l_a2;
		m[M33] = 0;
		
		return this;
	}
	
	public Matrix3 lookAt(float eyex, float eyey, float eyez, float cx, float cy, float cz, float upx, float upy, float upz)
	{
		Vec3f e = EYE;
		Vec3f f = FORWARD;
		Vec3f u = UP;
		Vec3f s = SIDE;
		
		e.set( eyex, eyey, eyez );
		
		f.set( cx, cy, cz );
		f.sub( e );
		
		s.cross( f, u );
		s.normal();

		u.cross( s, f );
		
		return lookAt( e, f, u, s );
	}
	
	public Matrix3 lookAt(Vec3f eye, Vec3f forward, Vec3f up, Vec3f right)
	{
		float[] m = this.value;
		
		m[M00] = right.x;
		m[M01] = right.y;
		m[M02] = right.z;
		m[M03] = -eye.x;
		
		m[M10] = up.x;
		m[M11] = up.y;
		m[M12] = up.z;
		m[M13] = -eye.y;
		
		m[M20] = -forward.x;
		m[M21] = -forward.y;
		m[M22] = -forward.z;
		m[M23] = -eye.z;
	
		m[M30] = 0;
		m[M31] = 0;
		m[M32] = 0;
		m[M33] = 1;
		
		return this;
	}
	
	@Override
	public Vec3f transform(Vec3f point, Vec3f out) 
	{
		return transform( point.x, point.y, point.z, out );
	}
	
	public Vec3f transform(float x, float y, float z, Vec3f out) 
	{
		float[] m = this.value;
		float w = m[M30] * x + m[M31] * y + m[M32] * z + m[M33];
		float inv_w = 1f / w;
		
		out.x = (m[M00] * x + m[M01] * y + m[M02] * z + m[M03]) * inv_w;
		out.y = (m[M10] * x + m[M11] * y + m[M12] * z + m[M13]) * inv_w;
		out.z = (m[M20] * x + m[M21] * y + m[M22] * z + m[M23]) * inv_w;
		
		return out;
	}

	@Override
	public Vec3f transform(Vec3f point) 
	{
		return transform( point, new Vec3f() );
	}

	@Override
	public Vec3f transformVector(Vec3f vector, Vec3f out) 
	{
		return transformVector( vector.x, vector.y, vector.z, out );
	}

	public Vec3f transformVector(float x, float y, float z, Vec3f out) 
	{
		float[] m = this.value;
		
		out.x = m[M00] * x + m[M01] * y + m[M02] * z;
		out.y = m[M10] * x + m[M11] * y + m[M12] * z;
		out.z = m[M20] * x + m[M21] * y + m[M22] * z;
		
		return out;
	}

	@Override
	public Vec3f transformVector(Vec3f vector) 
	{
		return transformVector( vector, new Vec3f() );
	}

	@Override
	public Matrix<Vec3f> translate(Vec3f by, Matrix<Vec3f> out) 
	{
		return out.set( multiply( this.value, getTranslation( by.x, by.y, by.z, temp0 ) ) );
	}
	
	@Override
	public Matrix<Vec3f> pretranslate(Vec3f by)
	{
		float[] m = this.value;
		m[M03] += by.x;
		m[M13] += by.y;
		m[M23] += by.z;
		return this;
	}

	@Override
	public Vec3f getTranslation(Vec3f out) 
	{
		float[] m = this.value;
		out.x = m[M03];
		out.y = m[M13];
		out.z = m[M23];
		return out;
	}

	@Override
	public Matrix<Vec3f> setTranslation(Vec3f by) 
	{
		getTranslation( by.x, by.y, by.z, this.value );
		return this;
	}

	@Override
	public Matrix<Vec3f> scale(Vec3f by, Matrix<Vec3f> out) 
	{
		return out.set( multiply( this.value, getScaling( by.x, by.y, by.z, temp0 ) ) );
	}

	@Override
	public Matrix<Vec3f> scale(float by, Matrix<Vec3f> out) 
	{
		return out.set( multiply( this.value, getScaling( by, by, by, temp0 ) ) );
	}
	
	@Override
	public Matrix<Vec3f> prescale(Vec3f by)
	{
		return prescale( by.x, by.y, by.z );
	}
	
	@Override
	public Matrix<Vec3f> prescale(float by)
	{
		return prescale( by, by, by );
	}
	
	public Matrix<Vec3f> prescale(float x, float y, float z)
	{
		float[] m = this.value;
		m[M00] *= x;
		m[M11] *= y;
		m[M22] *= z;
		return this;
	}
	
	@Override
	public Vec3f getScaling(Vec3f out) 
	{
		float[] m = this.value;
		out.x = (float)Math.sqrt(m[M00] * m[M00] + m[M01] * m[M01] + m[M02] * m[M02]);
		out.y = (float)Math.sqrt(m[M10] * m[M10] + m[M11] * m[M11] + m[M12] * m[M12]);
		out.z = (float)Math.sqrt(m[M20] * m[M20] + m[M21] * m[M21] + m[M22] * m[M22]);
		return out;
	}

	@Override
	public Matrix<Vec3f> setScaling(Vec3f by) 
	{
		getScaling( by.x, by.y, by.z, this.value );
		return this;
	}

	@Override
	public Matrix<Vec3f> setScaling(float by) 
	{
		getScaling( by, by, by, this.value );
		return this;
	}

	@Override
	public Matrix<Vec3f> setRotation(float cos, float sin, Vec3f axis) 
	{
		getAxisRotation( cos, sin, axis, this.value );
		return this;
	}

	@Override
	public Matrix<Vec3f> rotate(float cos, float sin, Vec3f axis, Matrix<Vec3f> out) 
	{
		return out.set( getAxisRotation( cos, sin, axis, temp0 ) );
	}

	// TODO skewXY
	// TODO skewXZ
	// TODO skewYZ
	// TODO rotationX
	// TODO rotationY
	// TODO rotationZ
	// TODO quaternions
	
	@Override
	public Matrix<Vec3f> transpose(Matrix<Vec3f> out) 
	{
		float[] tmp = temp0;
		float[] m = this.value;
		
		tmp[M00] = m[M00];
		tmp[M01] = m[M10];
		tmp[M02] = m[M20];
		tmp[M03] = m[M30];
		tmp[M10] = m[M01];
		tmp[M11] = m[M11];
		tmp[M12] = m[M21];
		tmp[M13] = m[M31];
		tmp[M20] = m[M02];
		tmp[M21] = m[M12];
		tmp[M22] = m[M22];
		tmp[M23] = m[M32];
		tmp[M30] = m[M03];
		tmp[M31] = m[M13];
		tmp[M32] = m[M23];
		tmp[M33] = m[M33];
		
		return set(tmp);
	}

	@Override
	public Matrix<Vec3f> invert(Matrix<Vec3f> out) 
	{
		float d = determinant();
		
		if (d == 0) return null;

		float invd = 1.0f / d;
		float[] dst = temp0;
		float[] m = this.value;

		dst[M00] = invd * (m[M12] * m[M23] * m[M31] - m[M13] * m[M22] * m[M31] + m[M13] * m[M21] * m[M32] - m[M11] * m[M23] * m[M32] - m[M12] * m[M21] * m[M33] + m[M11] * m[M22] * m[M33]);
		dst[M01] = invd * (m[M03] * m[M22] * m[M31] - m[M02] * m[M23] * m[M31] - m[M03] * m[M21] * m[M32] + m[M01] * m[M23] * m[M32] + m[M02] * m[M21] * m[M33] - m[M01] * m[M22] * m[M33]);
		dst[M02] = invd * (m[M02] * m[M13] * m[M31] - m[M03] * m[M12] * m[M31] + m[M03] * m[M11] * m[M32] - m[M01] * m[M13] * m[M32] - m[M02] * m[M11] * m[M33] + m[M01] * m[M12] * m[M33]);
		dst[M03] = invd * (m[M03] * m[M12] * m[M21] - m[M02] * m[M13] * m[M21] - m[M03] * m[M11] * m[M22] + m[M01] * m[M13] * m[M22] + m[M02] * m[M11] * m[M23] - m[M01] * m[M12] * m[M23]);
		dst[M10] = invd * (m[M13] * m[M22] * m[M30] - m[M12] * m[M23] * m[M30] - m[M13] * m[M20] * m[M32] + m[M10] * m[M23] * m[M32] + m[M12] * m[M20] * m[M33] - m[M10] * m[M22] * m[M33]);
		dst[M11] = invd * (m[M02] * m[M23] * m[M30] - m[M03] * m[M22] * m[M30] + m[M03] * m[M20] * m[M32] - m[M00] * m[M23] * m[M32] - m[M02] * m[M20] * m[M33] + m[M00] * m[M22] * m[M33]);
		dst[M12] = invd * (m[M03] * m[M12] * m[M30] - m[M02] * m[M13] * m[M30] - m[M03] * m[M10] * m[M32] + m[M00] * m[M13] * m[M32] + m[M02] * m[M10] * m[M33] - m[M00] * m[M12] * m[M33]);
		dst[M13] = invd * (m[M02] * m[M13] * m[M20] - m[M03] * m[M12] * m[M20] + m[M03] * m[M10] * m[M22] - m[M00] * m[M13] * m[M22] - m[M02] * m[M10] * m[M23] + m[M00] * m[M12] * m[M23]);
		dst[M20] = invd * (m[M11] * m[M23] * m[M30] - m[M13] * m[M21] * m[M30] + m[M13] * m[M20] * m[M31] - m[M10] * m[M23] * m[M31] - m[M11] * m[M20] * m[M33] + m[M10] * m[M21] * m[M33]);
		dst[M21] = invd * (m[M03] * m[M21] * m[M30] - m[M01] * m[M23] * m[M30] - m[M03] * m[M20] * m[M31] + m[M00] * m[M23] * m[M31] + m[M01] * m[M20] * m[M33] - m[M00] * m[M21] * m[M33]);
		dst[M22] = invd * (m[M01] * m[M13] * m[M30] - m[M03] * m[M11] * m[M30] + m[M03] * m[M10] * m[M31] - m[M00] * m[M13] * m[M31] - m[M01] * m[M10] * m[M33] + m[M00] * m[M11] * m[M33]);
		dst[M23] = invd * (m[M03] * m[M11] * m[M20] - m[M01] * m[M13] * m[M20] - m[M03] * m[M10] * m[M21] + m[M00] * m[M13] * m[M21] + m[M01] * m[M10] * m[M23] - m[M00] * m[M11] * m[M23]);
		dst[M30] = invd * (m[M12] * m[M21] * m[M30] - m[M11] * m[M22] * m[M30] - m[M12] * m[M20] * m[M31] + m[M10] * m[M22] * m[M31] + m[M11] * m[M20] * m[M32] - m[M10] * m[M21] * m[M32]);
		dst[M31] = invd * (m[M01] * m[M22] * m[M30] - m[M02] * m[M21] * m[M30] + m[M02] * m[M20] * m[M31] - m[M00] * m[M22] * m[M31] - m[M01] * m[M20] * m[M32] + m[M00] * m[M21] * m[M32]);
		dst[M32] = invd * (m[M02] * m[M11] * m[M30] - m[M01] * m[M12] * m[M30] - m[M02] * m[M10] * m[M31] + m[M00] * m[M12] * m[M31] + m[M01] * m[M10] * m[M32] - m[M00] * m[M11] * m[M32]);
		dst[M33] = invd * (m[M01] * m[M12] * m[M20] - m[M02] * m[M11] * m[M20] + m[M02] * m[M10] * m[M21] - m[M00] * m[M12] * m[M21] - m[M01] * m[M10] * m[M22] + m[M00] * m[M11] * m[M22]);

		return out.set( dst );
	}

	@Override
	public Matrix<Vec3f> multiply(Matrix<Vec3f> base, Matrix<Vec3f> by, Matrix<Vec3f> out) 
	{
		return out.set( multiply( base.get(), by.get() ) );
	}

	@Override
	public Matrix<Vec3f> set(float[] values) 
	{
		float[] m = this.value;
		m[0] = values[0];
		m[1] = values[1];
		m[2] = values[2];
		m[3] = values[3];
		m[4] = values[4];
		m[5] = values[5];
		m[6] = values[6];
		m[7] = values[7];
		m[8] = values[8];
		m[9] = values[9];
		m[10] = values[10];
		m[11] = values[11];
		m[12] = values[12];
		m[13] = values[13];
		m[14] = values[14];
		m[15] = values[15];
		return this;
	}

	@Override
	public Matrix<Vec3f> create() 
	{
		return new Matrix3();
	}

	@Override
	public Matrix<Vec3f> create(float[] values) 
	{
		return new Matrix3( values );
	}

	@Override
	public float[] get() 
	{
		return value;
	}
	
	// TODO
	
	private static void setIdentity(float[] out)
	{
		out[M00] = 1;
		out[M01] = 0;
		out[M02] = 0;
		out[M03] = 0;
		out[M10] = 0;
		out[M11] = 1;
		out[M12] = 0;
		out[M13] = 0;
		out[M20] = 0;
		out[M21] = 0;
		out[M22] = 1;
		out[M23] = 0;
		out[M30] = 0;
		out[M31] = 0;
		out[M32] = 0;
		out[M33] = 1;
	}
	
	private static float[] getTranslation(float x, float y, float z, float[] out)
	{
		setIdentity( out );
		out[M03] = x;
		out[M13] = y;
		out[M23] = z;
		return out;
	}
	
	private static float[] getScaling(float x, float y, float z, float[] out)
	{
		setIdentity( out );
		out[M00] = x;
		out[M11] = y;
		out[M22] = z;
		return out;
	}
	
	/*
	
	private static float[] getSkewing(float x, float y, float z, float[] out)
	{
		setIdentity( out );
		out[M10] = x;
		out[M01] = y;
		return out;
	}
	
	private static float[] getRotation(float yaw, float pitch, float roll, float[] out)
	{
		return out;
	}
	
	*/
	
	private static float[] getAxisRotation(float cos, float sin, Vec3f axis, float[] out)
	{
		float oc = 1.0f - cos;
		out[M00] = oc * axis.x * axis.x + cos;
		out[M10] = oc * axis.x * axis.y - axis.z * sin;
		out[M20] = oc * axis.z * axis.x + axis.y * sin;
		out[M01] = oc * axis.x * axis.y + axis.z * sin;
		out[M11] = oc * axis.y * axis.y + cos;
		out[M21] = oc * axis.y * axis.z - axis.x * sin;
		out[M02] = oc * axis.z * axis.x - axis.y * sin;
		out[M12] = oc * axis.y * axis.z + axis.x * sin;
		out[M22] = oc * axis.z * axis.z + cos;
		return out;
	}
	
	private static float[] multiply(float[] a_, float[] b_)
	{
		float[] out = temp1;
		
		out[M00] = a_[M00] * b_[M00] + a_[M01] * b_[M10] + a_[M02] * b_[M20];
		out[M01] = a_[M00] * b_[M01] + a_[M01] * b_[M11] + a_[M02] * b_[M21];
		out[M02] = a_[M00] * b_[M02] + a_[M01] * b_[M12] + a_[M02] * b_[M22];

		out[M10] = a_[M10] * b_[M00] + a_[M11] * b_[M10] + a_[M12] * b_[M20];
		out[M11] = a_[M10] * b_[M01] + a_[M11] * b_[M11] + a_[M12] * b_[M21];
		out[M12] = a_[M10] * b_[M02] + a_[M11] * b_[M12] + a_[M12] * b_[M22];

		out[M20] = a_[M20] * b_[M00] + a_[M21] * b_[M10] + a_[M22] * b_[M20];
		out[M21] = a_[M20] * b_[M01] + a_[M21] * b_[M11] + a_[M22] * b_[M21];
		out[M22] = a_[M20] * b_[M02] + a_[M21] * b_[M12] + a_[M22] * b_[M22];

		return out;
	}

}
