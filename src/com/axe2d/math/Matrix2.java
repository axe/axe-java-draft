package com.axe2d.math;

import com.axe.math.Matrix;
import com.axe.math.Numbers;
import com.axe.math.Vec2f;
import com.axe.math.Vec3f;

public class Matrix2 implements Matrix<Vec2f> 
{
	public static final int VALUES = 9;
	
	public static final int M00 = 0;
	public static final int M01 = 3;
	public static final int M02 = 6;
	public static final int M10 = 1;
	public static final int M11 = 4;
	public static final int M12 = 7;
	public static final int M20 = 2;
	public static final int M21 = 5;
	public static final int M22 = 8;
	
	private static float[] temp0 = new float[VALUES];
	private static float[] temp1 = new float[VALUES];
	
	public float[] value = new float[VALUES];
	
	public Matrix2()
	{
		this.identity();
	}
	
	public Matrix2(float[] value)
	{
		this.set(value);
	}
	
	@Override
	public Vec2f createVector()
	{
		return new Vec2f();
	}
	
	@Override
	public Matrix2 identity() 
	{
		setIdentity( this.value );
		return this;
	}

	@Override
	public float determinant() 
	{
		float[] m = this.value;
		
		return m[M00] * m[M11] * m[M22] + 
			   m[M01] * m[M12] * m[M20] + 
			   m[M02] * m[M10] * m[M21] - 
			   m[M00] * m[M12] * m[M21] - 
			   m[M01] * m[M10] * m[M22] - 
			   m[M02] * m[M11] * m[M20];
	}
	
	@Override
	public Matrix2 ortho( Vec2f min, Vec2f max )
	{
		return ortho( min.x, max.y, max.x, min.y );
	}
	
	public Matrix2 ortho(float left, float top, float right, float bottom)
	{
		float[] m = this.value;
		float dx = (right - left);
		float dy = (top - bottom);
		float xo = 2 / dx;
		float yo = 2 / dy;
		float tx = -(right + left) / dx;
		float ty = -(top + bottom) / dy;
		
		m[M00] = xo;
		m[M10] = 0;
		m[M20] = 0;
		
		m[M01] = 0;
		m[M11] = yo;
		m[M21] = 0;
		
		m[M02] = tx;
		m[M12] = ty;
		m[M22] = 1;
		
		return this;
	}

	@Override
	public Vec2f transform(Vec2f point, Vec2f out) 
	{
		return transform( point.x, point.y, out );
	}
	
	public Vec2f transform(float x, float y, Vec2f out) 
	{
		float[] m = this.value;
		float invw = 1f / (m[M20] * x + m[M21] * y + m[M22]);
		
		out.x = (m[M00] * x + m[M01] * y + m[M02]) * invw;
		out.y = (m[M10] * x + m[M11] * y + m[M12]) * invw;
		
		return out;
	}

	@Override
	public Vec2f transform(Vec2f point) 
	{
		return transform( point, new Vec2f() );
	}

	@Override
	public Vec2f transformVector(Vec2f vector, Vec2f out) 
	{
		return transformVector( vector.x, vector.y, out );
	}

	public Vec2f transformVector(float x, float y, Vec2f out)
	{
		float[] m = this.value;
		
		out.x = m[M00] * x + m[M01] * y;
		out.y = m[M10] * x + m[M11] * y;
		
		return out;
	}

	@Override
	public Vec2f transformVector(Vec2f vector) 
	{
		return transformVector( vector, new Vec2f() );
	}

	@Override
	public Matrix<Vec2f> translate(Vec2f by, Matrix<Vec2f> out) 
	{
		return translate( by.x, by.y, out );
	}
	
	public Matrix<Vec2f> translate(float x, float y, Matrix<Vec2f> out) 
	{
		return out.set( multiply( this.value, getTranslation( x, y, temp0 ) ) );
	}
	
	public Matrix<Vec2f> translatei(float x, float y) 
	{
		return set( multiply( this.value, getTranslation( x, y, temp0 ) ) );
	}
	
	@Override
	public Matrix<Vec2f> pretranslate(Vec2f by)
	{
		return pretranslate( by.x, by.y );
	}
	
	public Matrix<Vec2f> pretranslate(float x, float y)
	{
		float[] m = this.value;
		m[M02] += x;
		m[M12] += y;
		return this;
	}

	@Override
	public Vec2f getTranslation(Vec2f out) 
	{
		float[] m = this.value;
		out.x = m[M02];
		out.y = m[M12];
		return out;
	}

	@Override
	public Matrix<Vec2f> setTranslation(Vec2f by) 
	{
		getTranslation( by.x, by.y, this.value );
		return this;
	}

	@Override
	public Matrix<Vec2f> scale(Vec2f by, Matrix<Vec2f> out) 
	{
		return scale( by.x, by.y, out );
	}

	@Override
	public Matrix<Vec2f> scale(float by, Matrix<Vec2f> out) 
	{
		return scale( by, by, out );
	}
	
	public Matrix<Vec2f> scale(float x, float y, Matrix<Vec2f> out) 
	{
		return out.set( multiply( this.value, getScaling( x, y, temp0 ) ) );
	}
	
	public Matrix<Vec2f> scalei(float x, float y) 
	{
		return set( multiply( this.value, getScaling( x, y, temp0 ) ) );
	}
	
	@Override
	public Matrix<Vec2f> prescale(Vec2f by)
	{
		return prescale( by.x, by.y );
	}
	
	@Override
	public Matrix<Vec2f> prescale(float by)
	{
		return prescale( by, by );
	}
	
	public Matrix<Vec2f> prescale(float x, float y)
	{
		float[] m = this.value;
		m[M00] *= x;
		m[M11] *= y;
		return this;
	}

	@Override
	public Vec2f getScaling(Vec2f out) 
	{
		float[] m = this.value;
		out.x = (float)Math.sqrt(m[M00] * m[M00] + m[M01] * m[M01]);
		out.y = (float)Math.sqrt(m[M10] * m[M10] + m[M11] * m[M11]);
		return out;
	}

	@Override
	public Matrix<Vec2f> setScaling(Vec2f by) 
	{
		return setScaling( by.x, by.y );
	}

	@Override
	public Matrix<Vec2f> setScaling(float by) 
	{
		return setScaling( by, by );
	}
	
	public Matrix<Vec2f> setScaling(float x, float y) 
	{
		getScaling( x, y, this.value );
		return this;
	}
	
	public Matrix<Vec2f> rotate(float radians, Matrix<Vec2f> out)
	{
		return rotate( Numbers.cos( radians ), Numbers.sin( radians ), out );
	}

	public Matrix<Vec2f> rotateDegrees(float degrees, Matrix<Vec2f> out)
	{
		return rotate( degrees * Numbers.DEGREE_TO_RADIAN, out );
	}
	
	public Matrix<Vec2f> rotaten(float radians)
	{
		return rotate( radians, create() );
	}
	
	public Matrix<Vec2f> rotaten(float cos, float sin)
	{
		return rotate( cos, sin, create() );
	}
	
	public Matrix<Vec2f> rotatenDegrees(float degrees)
	{
		return rotate( degrees * Numbers.DEGREE_TO_RADIAN, create() );
	}
	
	public Matrix<Vec2f> rotatei(float radians)
	{
		return rotate( radians, this );
	}
	
	public Matrix<Vec2f> rotatei(float cos, float sin)
	{
		return rotate( cos, sin, this );
	}
	
	public Matrix<Vec2f> rotateiDegrees(float degrees)
	{
		return rotate( degrees * Numbers.DEGREE_TO_RADIAN, this );
	}
	
	public Matrix<Vec2f> rotate(float cos, float sin, Matrix<Vec2f> out) 
	{
		return out.set( multiply( this.value, getRotation( cos, sin, temp0 ) ) );
	}

	public float getRotation() 
	{
		float[] m = this.value;
		
		return (float)Math.atan2(m[M10], m[M00]);
	}

	public Matrix<Vec2f> setRotation(float cos, float sin) 
	{
		getRotation( cos, sin, this.value );
		return this;
	}
	
	public Matrix<Vec2f> setRotation(float radians)
	{
		return setRotation( Numbers.cos( radians ), Numbers.sin( radians ) );
	}

	@Override
	public Matrix<Vec2f> setRotation(float cos, float sin, Vec3f axis) 
	{
		getAxisRotation( cos, sin, axis, this.value );
		return this;
	}

	@Override
	public Matrix<Vec2f> rotate(float cos, float sin, Vec3f axis, Matrix<Vec2f> out) 
	{
		return out.set( getAxisRotation( cos, sin, axis, temp0 ) );
	}

	public Matrix<Vec2f> skew(Vec2f by, Matrix<Vec2f> out) 
	{
		return skew( by.x, by.y, out );
	}
	
	public Matrix<Vec2f> skew(float x, float y, Matrix<Vec2f> out) 
	{
		return out.set( multiply( this.value, getSkewing( x, y, temp0 ) ) );
	}

	public Vec2f getSkewing(Vec2f out) 
	{
		return out;
	}

	public Matrix<Vec2f> setSkewing(Vec2f by) 
	{
		return setSkewing( by.x, by.y );
	}
	
	public Matrix<Vec2f> skewn(Vec2f by)
	{
		return skew( by, create() );
	}
	
	public Matrix<Vec2f> skewi(Vec2f by)
	{
		return skew( by, this );
	}
	
	public Vec2f getSkewing()
	{
		return getSkewing( createVector() );
	}

	public Matrix<Vec2f> setSkewing(float x, float y) 
	{
		getSkewing( x, y, this.value );
		return this;
	}

	@Override
	public Matrix<Vec2f> transpose(Matrix<Vec2f> out) 
	{
		float[] val = this.value;
		float v01 = val[M10];
		float v02 = val[M20];
		float v10 = val[M01];
		float v12 = val[M21];
		float v20 = val[M02];
		float v21 = val[M12];
		val[M01] = v01;
		val[M02] = v02;
		val[M10] = v10;
		val[M12] = v12;
		val[M20] = v20;
		val[M21] = v21;
		return this;
	}

	@Override
	public Matrix<Vec2f> invert(Matrix<Vec2f> out) 
	{
		float d = determinant();
		
		if (d == 0) return null;

		float invd = 1.0f / d;
		float[] dst = temp0;
		float[] m = this.value;

		dst[M00] = invd * (m[M11] * m[M22] - m[M21] * m[M12]);
		dst[M10] = invd * (m[M20] * m[M12] - m[M10] * m[M22]);
		dst[M20] = invd * (m[M10] * m[M21] - m[M20] * m[M11]);
		dst[M01] = invd * (m[M21] * m[M02] - m[M01] * m[M22]);
		dst[M11] = invd * (m[M00] * m[M22] - m[M20] * m[M02]);
		dst[M21] = invd * (m[M20] * m[M01] - m[M00] * m[M21]);
		dst[M02] = invd * (m[M01] * m[M12] - m[M11] * m[M02]);
		dst[M12] = invd * (m[M10] * m[M02] - m[M00] * m[M12]);
		dst[M22] = invd * (m[M00] * m[M11] - m[M10] * m[M01]);

		return out.set( dst );
	}

	@Override
	public Matrix<Vec2f> multiply(Matrix<Vec2f> base, Matrix<Vec2f> by, Matrix<Vec2f> out) 
	{
		return out.set( multiply( base.get(), by.get() ) );
	}

	@Override
	public Matrix<Vec2f> set(float[] values) 
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
		return this;
	}

	@Override
	public Matrix<Vec2f> create() 
	{
		return new Matrix2();
	}

	@Override
	public Matrix<Vec2f> create(float[] values) 
	{
		return new Matrix2( values );
	}

	@Override
	public float[] get() 
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		float[] m = this.value;
		
		return (int)(m[M22] + m[M00] * 10 + m[M01] * 100 + m[M10] * 1000 + 
				m[M11] * 10000 + m[M02] * 100000 + m[M20] * 1000000 + 
				m[M12] * 10000000 + m[M21] * 100000000);
	}
	
	private static void setIdentity(float[] out)
	{
		out[M00] = 1;
		out[M10] = 0;
		out[M20] = 0;

		out[M01] = 0;
		out[M11] = 1;
		out[M21] = 0;

		out[M02] = 0;
		out[M12] = 0;
		out[M22] = 1;
	}
	
	private static float[] getTranslation(float x, float y, float[] out)
	{
		setIdentity( out );
		out[M02] = x;
		out[M12] = y;
		return out;
	}
	
	private static float[] getScaling(float x, float y, float[] out)
	{
		setIdentity( out );
		out[M00] = x;
		out[M11] = y;
		return out;
	}
	
	private static float[] getSkewing(float x, float y, float[] out)
	{
		setIdentity( out );
		out[M10] = x;
		out[M01] = y;
		return out;
	}
	
	private static float[] getRotation(float cos, float sin, float[] out)
	{
		setIdentity( out );
		out[M00] = cos;
		out[M10] = sin;
		out[M01] = -sin;
		out[M11] = cos;
		return out;
	}
	
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
