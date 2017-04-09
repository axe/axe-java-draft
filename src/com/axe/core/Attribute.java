package com.axe.core;

import java.nio.ByteBuffer;

import com.axe.io.DataModel;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.calc.Binary;
import com.axe.math.calc.Calculator;
import com.axe.math.calc.Unary;


/**
 * Any class subject to animation. A subject has some value that can be
 * set, returned, modulated between a start and end value by some delta,
 * and can determine its distance from another value of the same type.
 *
 * @author Philip Diffenderfer
 *
 * @param <T>
 */
public interface Attribute<T> extends Cloned<T>, Bufferable, DataModel, Factory<T>
{
	
	public T get();
	
	public Calculator<T> getCalculator();
	
	default public T create()
	{
		return getCalculator().create();
	}
	
	default public boolean recycle()
	{
		return getCalculator().recycle( get() );
	}
	
	default public T clone()
	{
		return getCalculator().clone( get() );
	}
	
	default public T set( T value )
	{
		return getCalculator().copy( get(), value );
	}
	
	default public T set( Alternative<T> alternative )
	{
		return getCalculator().copy( get(), alternative.alternative() );
	}
	
	default public T copy( T target )
	{
		return getCalculator().copy( target, get() );
	}
	
	default public T clear()
	{
		return getCalculator().clear( get(), 0 );
	}
	
	default public T set( float component )
	{
		return getCalculator().clear( get(), component );
	}
	

	default public T unaryn( Unary unaryOperation )
	{
		return getCalculator().unaryn( get(), unaryOperation );
	}
	
	default public T unary( Unary unaryOperation )
	{
		return getCalculator().unaryi( get(), unaryOperation );
	}
	
	default public T unary( T value, Unary unaryOperation )
	{
		return getCalculator().unary( get(), value, unaryOperation );
	}

	
	default public T binaryn( T b, Binary binaryOperation )
	{
		return getCalculator().binaryn( get(), b, binaryOperation );
	}
	
	default public T binary( T b, Binary binaryOperation )
	{
		return getCalculator().binaryi( get(), b, binaryOperation );
	}
	
	default public T binary( T a, T b, Binary binaryOperation )
	{
		return getCalculator().binary( get(), a, b, binaryOperation );
	}
	
	
	default public T addsn( T addend, float scale )
	{
		return getCalculator().addsn( get(), addend, scale );
	}
	
	default public T adds( T addend, float scale )
	{
		return getCalculator().addsi( get(), addend, scale );
	}
	
	default public T adds( T augend, T addend, float scale )
	{
		return getCalculator().adds( get(), augend, addend, scale );
	}
	
	
	default public T scalen( float scale )
	{
		return getCalculator().scalen( get(), scale );
	}
	 
	default public T scale( float scale )
	{
		return getCalculator().scalei( get(), scale );
	}
	 
	default public T scale( T value, float scale )
	{
		return getCalculator().scale( get(), value, scale );
	}
	
	
	default public T divsn( float scale )
	{
		return getCalculator().divsn( get(), scale );
	}
	 
	default public T divs( float scale )
	{
		return getCalculator().divsi( get(), scale );
	}
	 
	default public T divs( T value, float scale )
	{
		return getCalculator().divs( get(), value, scale );
	}
	
	
	default public T addn( T addend )
	{
		return getCalculator().addn( get(), addend );
	}
	
	default public T add( T augend, T addend )
	{
		return getCalculator().add( get(), augend, addend );
	}
	 
	default public T add( T amount )
	{
		return getCalculator().addi( get(), amount );
	}
	 
	
	default public T subn( T subtrahend )
	{
		return getCalculator().subn( get(), subtrahend );
	}
	 
	default public T sub( T subtrahend )
	{
		return getCalculator().subi( get(), subtrahend );
	}
	
	default public T sub( T minuend, T subtrahend )
	{
		return getCalculator().sub( get(), minuend, subtrahend );
	}
	 
	
	default public T muln( T scale )
	{
		return getCalculator().muln( get(), scale );
	}
	 
	default public T mul( T scale )
	{
		return getCalculator().muli( get(), scale );
	}
	 
	default public T mul( T value, T scale )
	{
		return getCalculator().mul( get(), value, scale );
	}
	
	
	default public T divn( T divisor )
	{
		return getCalculator().divn( get(), divisor );
	}
	 
	default public T div( T divisor )
	{
		return getCalculator().divi( get(), divisor );
	}

	default public T div( T dividend, T divisor )
	{
		return getCalculator().div( get(), dividend, divisor );
	}
	 
	
	default public T interpolate( T start, T end, float delta )
	{
		return getCalculator().interpolate( get(), start, end, delta );
	}
	
	
	default public T reflectn( T normal )
	{
		return getCalculator().reflectn( get(), normal );
	}
	
	default public T reflect( T normal ) 
	{
		return getCalculator().reflecti( get(), normal );
	}
	
	default public T reflect( T vector, T normal )
	{
		return getCalculator().reflect( get(), vector, normal );
	}
	
	
	default public T refractn( T normal )
	{
		return getCalculator().refractn( get(), normal );
	}
	
	default public T refract( T normal ) 
	{
		return getCalculator().refracti( get(), normal );
	}
	
	default public T refract( T vector, T normal )
	{
		return getCalculator().refract( get(), vector, normal );
	}

	
	default public boolean isParallel( T value, float epsilon )
	{
		return getCalculator().isParallel( get(), value, epsilon );
	}
	
	default public boolean isParallel( T value )
	{
		return getCalculator().isParallel( get(), value );
	}

	
	default public boolean isPerpendicular( T value, float epsilon )
	{
		return getCalculator().isPerpendicular( get(), value, epsilon );
	}
	
	default public boolean isPerpendicular( T value )
	{
		return getCalculator().isPerpendicular( get(), value );
	}
	
	
	default public T contain( T min, T max )
	{
		return getCalculator().contain( get(), min, max );
	}
	
	
	default public boolean contains( T min, T max )
	{
		return getCalculator().contains( get(), min, max );
	}
	

	default public T random( T min, T max, Binary randomizer )
	{
		return getCalculator().random( get(), min, max, randomizer );
	}
	
	default public T random( T min, T max )
	{
		return getCalculator().random( get(), min, max, Numbers::randomFloat );
	}
	

	default public float dot( T b )
	{
		return getCalculator().dot( get(), b );
	}
	
	default public float distanceSq( T b )
	{
		return getCalculator().distanceSq( get(), b );
	}
	
	default public float distance( T b )
	{
		return getCalculator().distance( get(), b );
	}
	 
	
	default public float length()
	{
		return getCalculator().length( get() );
	}
	
	default public float lengthSq()
	{
		return getCalculator().lengthSq( get() );
	}
	
	 
	default public float normaln()
	{
		return getCalculator().normaln( get() );
	}

	default public float normal( T vector )
	{
		return getCalculator().normal( get(), vector );
	}

	default public float normal()
	{
		return getCalculator().normali( get() );
	}
	
	
	default public T negn()
	{
		return getCalculator().negn( get() );
	}
	
	default public T neg()
	{
		return getCalculator().negi( get() );
	}
	
	default public T neg( T value )
	{
		return getCalculator().neg( get(), value );
	}
	
	
	default public T absn()
	{
		return getCalculator().absn( get() );
	}
	
	default public T abs()
	{
		return getCalculator().absi( get() );
	}
	
	default public T abs( T value )
	{
		return getCalculator().abs( get(), value );
	}
	
	
	default public T modsn( float divisor )
	{
		return getCalculator().modsn( get(), divisor );
	}
	
	default public T mods( float divisor )
	{
		return getCalculator().modsi( get(), divisor );
	}
	
	default public T mods( T value, float divisor )
	{
		return getCalculator().mods( get(), value, divisor );
	}
	
	
	default public T modn( T divisor )
	{
		return getCalculator().modn( get(), divisor );
	}
	
	default public T mod( T divisor )
	{
		return getCalculator().modi( get(), divisor );
	}
	
	default public T mod( T value, T divisor )
	{
		return getCalculator().mod( get(), value, divisor );
	}
	
	
	default public T invertn()
	{
		return getCalculator().invertn( get() );
	}
	
	default public T invert()
	{
		return getCalculator().inverti( get() );
	}
	
	default public T invert( T value )
	{
		return getCalculator().invert( get(), value );
	}
	
	
	default public T floorn()
	{
		return getCalculator().floorn( get() );
	}
	
	default public T floor()
	{
		return getCalculator().floori( get() );
	}
	
	default public T floor( T value )
	{
		return getCalculator().floor( get(), value );
	}
	
	
	default public T ceiln()
	{
		return getCalculator().ceiln( get() );
	}
	
	default public T ceil()
	{
		return getCalculator().ceili( get() );
	}
	
	default public T ceil( T value )
	{
		return getCalculator().ceil( get(), value );
	}
	
	
	default public T minn( T b )
	{
		return getCalculator().minn( get(), b );
	}
	
	default public T min( T other )
	{
		return getCalculator().mini( get(), other );
	}
	
	default public T min( T a, T b )
	{
		return getCalculator().min( get(), a, b );
	}
	
	
	default public T maxn( T b )
	{
		return getCalculator().maxn( get(), b );
	}
	
	default public T max( T other )
	{
		return getCalculator().maxi( get(), other );
	}
	
	default public T max( T a, T b )
	{
		return getCalculator().max( get(), a, b );
	}
	
	
	default public T clamp( T min, T max )
	{
		return getCalculator().clamp( get(), min, max );
	}
	
	
	default public boolean isFinite()
	{
		return getCalculator().isFinite( get() );
	}
	
	default public boolean isZero( float epsilon )
	{
		return getCalculator().isZero( get(), epsilon );
	}
	 
	default public boolean isZero()
	{
		return getCalculator().isZero( get() );
	}
	 
	default public boolean isEqual( T b )
	{
		return getCalculator().isEqual( get(), b );
	}
	
	default public boolean isEqual( T b, float epsilon )
	{
		return getCalculator().isEqual( get(), b, epsilon );
	}
	
	
	default public boolean isUnit()
	{
		return getCalculator().isUnit( get() );
	}
	
	default public boolean isUnit( float epsilon )
	{
		return getCalculator().isUnit( get(), epsilon );
	}
	
	default public boolean isLength( float length )
	{
		return getCalculator().isLength( get(), length );
	}
	
	default public boolean isLength( float length, float epsilon )
	{
		return getCalculator().isLength( get(), length, epsilon );
	}
	
	
	default public T lengthenn( float length )
	{
		return getCalculator().lengthenn( get(), length );
	}
	 
	default public T lengthen( float length )
	{
		return getCalculator().lengtheni( get(), length );
	}
	
	default public T lengthen( T value, float length )
	{
		return getCalculator().lengthen( get(), value, length );
	}
	 
	
	default public T clampn( float min, float max )
	{
		return getCalculator().clampn( get(), min, max );
	}
	
	default public T clamp( float min, float max )
	{
		return getCalculator().clampi( get(), min, max );
	}
	
	default public T clamp( T value, float min, float max )
	{
		return getCalculator().clamp( get(), value, min, max );
	}
	
	
	default public int bytes()
	{
		return getCalculator().sizeof( get() );
	}
	
	default public void write( ByteBuffer to )
	{
		getCalculator().write( get(), to );
	}
	
	default public void read( ByteBuffer from )
	{
		getCalculator().read( get(), from );
	}
	
	default public void write( OutputModel to )
	{
		getCalculator().write( get(), to );
	}
	
	default public void read( InputModel from )
	{
		getCalculator().read( get(), from );
	}
	
	
	default public int getComponents()
	{
		return getCalculator().getComponents();
	}
	
	default public float getComponent( int index )
	{
		return getCalculator().getComponent( get(), index );
	}
	
	default public T setComponent( int index, float component )
	{
		return getCalculator().setComponent( get(), index, component );
	}
	
	
	default public int getDimensions()
	{
		return getCalculator().getDimensions();
	}
	 
	default public float getDimension( int index )
	{
		return getCalculator().getDimension( get(), index );
	}
	
	default public T setDimension( int index, float dimension )
	{
		return getCalculator().setDimension( get(), index, dimension );
	}
	
	
	default public T slerpDirect( T start, T end, float delta )
	{
		return getCalculator().slerpDirect( get(), start, end, delta );
	}
	
	default public T slerpNormal( T start, T end, float delta )
	{
		return getCalculator().slerpNormal( get(), start, end, delta );
	}
	
	default public T slerp( T start, T end, float delta, float angle )
	{
		return getCalculator().slerp( get(), start, end, delta, angle );
	}
	
	
	default public float delta( T start, T end )
	{
		return getCalculator().delta( start, end, get() );
	}
	
	
	default public T closest( T start, T end, T point )
	{
		return getCalculator().closest( get(), start, end, point );
	}
	
	default public T closest( T start, T end, T point, boolean line )
	{
		return getCalculator().closest( get(), start, end, point, line );
	}
	
	
	default public float distanceFrom( T start, T end )
	{
		return getCalculator().distanceFrom( start, end, get() );
	}
	
	default public float distanceFrom( T start, T end, boolean line )
	{
		return getCalculator().distanceFrom( start, end, get(), line );
	}
	
	
	default public boolean inView( T origin, T direction, float fovCos )
	{
		return getCalculator().inView( origin, direction, fovCos, get() );
	}
	
	default public boolean inCircleView( T origin, T direction, float fovTan, float fovCos, float radius, boolean entirely )
	{
		return getCalculator().inCircleView( origin, direction, fovTan, fovCos, get(), radius, entirely );
	}
	
	
	default public T cubicCurve( float delta, T p0, T p1, T p2, T p3, float[][] matrix)
	{
		return getCalculator().cubicCurve( get(), delta, p0, p1, p2, p3, matrix );
	}
	
	default public T cubicCurve( float delta, T p0, T p1, T p2, T p3, float[][] matrix, boolean inverse )
	{
		return getCalculator().cubicCurve( get(), delta, p0, p1, p2, p3, matrix, inverse );
	}
	
	default public T parametricCubicCurve( float delta, T[] points, float[][] matrix, float weight )
	{
		return getCalculator().parametricCubicCurve( get(), delta, points, matrix, weight );
	}
	
	default public T parametricCubicCurve( float delta, T[] points, float[][] matrix, float weight, boolean inverse, boolean loop )
	{
		return getCalculator().parametricCubicCurve( get(), delta, points, matrix, weight, inverse, loop );
	}
	
}
