package com.axe.math;

import java.nio.FloatBuffer;

public interface Matrix<V extends Vec<V>> 
{

	public Matrix<V> identity();
	
	public float determinant();
	
	public Matrix<V> ortho(V min, V max);
	
	public V transform(V point, V out);
	
	public V transform(V point);
	
	public V transformVector(V vector, V out);
	
	public V transformVector(V vector);
	
	public Matrix<V> translate(V by, Matrix<V> out);
	
	default public Matrix<V> translaten(V by)
	{
		return translate( by, create() );
	}
	
	default public Matrix<V> translatei(V by)
	{
		return translate( by, this );
	}
	
	public Matrix<V> pretranslate(V by);
	
	public V getTranslation(V out);
	
	default public V getTranslation()
	{
		return getTranslation( createVector() );
	}
	
	public Matrix<V> setTranslation(V by);
	
	public Matrix<V> scale(V by, Matrix<V> out);
	
	default public Matrix<V> scalen(V by)
	{
		return scale( by, create() );
	}
	
	default public Matrix<V> scalei(V by)
	{
		return scale( by, this );
	}
	
	public Matrix<V> scale(float by, Matrix<V> out);
	
	default public Matrix<V> scalen(float by)
	{
		return scale( by, create() );
	}
	
	default public Matrix<V> scalei(float by)
	{
		return scale( by, this );
	}
	
	public Matrix<V> prescale(V by);
	
	public Matrix<V> prescale(float by);
	
	public V getScaling(V out);
	
	default public V getScaling()
	{
		return getScaling( createVector() );
	}
	
	public Matrix<V> setScaling(V by);
	
	public Matrix<V> setScaling(float by);
	
	public Matrix<V> setRotation(float cos, float sin, Vec3f axis);
	
	default public Matrix<V> setRotation(float radians, Vec3f axis)
	{
		return setRotation( Numbers.cos( radians ), Numbers.sin( radians ), axis );
	}
	
	public Matrix<V> rotate(float cos, float sin, Vec3f axis, Matrix<V> out);
	
	default public Matrix<V> rotate(float radians, Vec3f axis, Matrix<V> out)
	{
		return rotate( Numbers.cos( radians ), Numbers.sin( radians ), axis, out );
	}
	
	default public Matrix<V> rotateDegrees(float degrees, Vec3f axis, Matrix<V> out)
	{
		return rotate( degrees * Numbers.DEGREE_TO_RADIAN, axis, out );
	}

	default public Matrix<V> rotaten(float radians, Vec3f axis)
	{
		return rotate( radians, axis, create() );
	}

	default public Matrix<V> rotatenDegrees(float degrees, Vec3f axis)
	{
		return rotate( degrees * Numbers.DEGREE_TO_RADIAN, axis, create() );
	}
	
	default public Matrix<V> rotaten(float cos, float sin, Vec3f axis)
	{
		return rotate( cos, sin, axis, create() );
	}
	
	default public Matrix<V> rotatei(float radians, Vec3f axis)
	{
		return rotate( radians, axis, this );
	}
	
	default public Matrix<V> rotateiDegrees(float degrees, Vec3f axis)
	{
		return rotate( degrees * Numbers.DEGREE_TO_RADIAN, axis, this );
	}
	
	default public Matrix<V> rotatei(float cos, float sin, Vec3f axis)
	{
		return rotate( cos, sin, axis, this );
	}
	
	public Matrix<V> transpose(Matrix<V> out);
	
	default Matrix<V> transposen()
	{
		return transpose( create() );
	}
	
	default Matrix<V> transposei()
	{
		return transpose( this );
	}
	
	public Matrix<V> invert(Matrix<V> out);
	
	default public Matrix<V> invertn()
	{
		return invert( create() ); 
	}
	
	default public Matrix<V> inverti()
	{
		return invert( this );
	}
	
	public Matrix<V> multiply(Matrix<V> base, Matrix<V> by, Matrix<V> out);
	
	default public Matrix<V> multiply(Matrix<V> by, Matrix<V> out)
	{
		return multiply( this, by, out );
	}
	
	default Matrix<V> multiplyn(Matrix<V> by)
	{
		return multiply( this, by, create() );
	}

	default Matrix<V> multiplyi(Matrix<V> by)
	{
		return multiply( this, by, this );
	}
	
	default public Matrix<V> premultiply(Matrix<V> by, Matrix<V> out)
	{
		return multiply( by, this, out );
	}
	
	default Matrix<V> premultiplyn(Matrix<V> by)
	{
		return multiply( by, this, create() );
	}

	default Matrix<V> premultiplyi(Matrix<V> by)
	{
		return multiply( by, this, this );
	}
	
	public Matrix<V> set(float[] values);
	
	default public Matrix<V> set(Matrix<V> matrix)
	{
		return set( matrix.get() );
	}
	
	public Matrix<V> create();
	
	public V createVector();
	
	public Matrix<V> create(float[] values);
		
	public float[] get();
	
	default public void get(FloatBuffer out)
	{
		out.put( get() );
	}
	
}
