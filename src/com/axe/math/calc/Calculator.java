package com.axe.math.calc;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

public interface Calculator<T>
{
	
	public T ZERO();
	
	public T ONE();
	
	public T create();
	
	default public T[] createArray(int count, T ... initial)
	{
		T[] out = Arrays.copyOf( initial, count );
		
		for (int i = 0; i < count; i++)
		{
			out[ i ] = create();
		}
		
		return out;
	}
	
	default public T createUniform(float component) 
	{
		return clear( create(), component );
	}
	
	public void setPool(T[] pool);
	
	public boolean recycle(T unused);
	
	default public T clone(T source)
	{
		return copy( create(), source );
	}
	
	// out.x = source.x
	public T copy( T out, T source );
	
	public T parse( Object input, T defaultValue );
	
	default public T[] parseArray( T[] input, T[] output, T defaultValue ) 
	{
		if (output == null) 
		{
			output = Arrays.copyOf( input, input.length );
		}
		
		for (int i = 0; i < input.length; i++)
		{
			output[ i ] = parse( input[ i ], defaultValue );
 		}
		
		return output;
	}
	
	// out.x = component
	public T clear( T out, float component );
	
	// out.x = op( a.x )
	public T unary( T out, T value, Unary unaryOoperation );
	
	default public T unaryi( T out, Unary unaryOoperation )
	{
		return unary( out, out, unaryOoperation );
	}
	
	// return op( a.x )
	default public T unaryn( T value, Unary unaryOperation )
	{
		return unary( create(), value, unaryOperation );
	}
	
	// out.x = op( a.x, b.x )
	public T binary( T out, T a, T b, Binary binaryOperation );
	
	default public T binaryi( T out, T b, Binary binaryOperation )
	{
		return binary( out, out, b, binaryOperation );
	}
	
	// return op( a.x, b.x )
	default public T binaryn( T a, T b, Binary binaryOperation )
	{
		return binary( create(), a, b, binaryOperation );
	}
	
	// out.x = augend.x + addend.x * scale;
	public T adds( T out, T augend, T addend, float scale );
  
	// return augend.x + addend.x * scale;
	default public T addsn( T augend, T addend, float scale ) 
	{
	    return adds( create(), augend, addend, scale );
	}

	// out.x = out.x + addend.x * scale;
	default public T addsi( T out, T addend, float scale ) 
	{
		return adds( out, out, addend, scale );
	}

	// out.x = value.x * scale
    default public T scale( T out, T value, float scale ) 
    {
    	return adds( out, ZERO(), value, scale );
    }
    
    // return value.x * scale
  	default public T scalen( T value, float scale ) 
  	{
    	return adds( create(), ZERO(), value, scale );
  	}

  	// out.x *= scale
  	default public T scalei( T out, float scale ) 
  	{    
  		return adds( out, ZERO(), out, scale );
  	}
  	
  	// out.x = value.x / divisor
  	default public T divs( T out, T value, float divisor )
  	{
  		return divisor == 0 ? clear( out, 0 ) : scale( out, value, 1.0f / divisor );
  	}
  	
  	// return value.x / divisor
  	default public T divsn( T value, float divisor )
  	{
  		return divs( create(), value, divisor );
  	}
  	
  	// out.x /= divisor
  	default public T divsi( T out, float divisor )
  	{
  		return divs( out, out, divisor );
  	}

  	// out.x = augend.x + addend.x;
  	default public T add( T out, T augend, T addend ) 
  	{
  		return adds( out, augend, addend, 1 );
  	}
  	
  	// return augend.x + addend.x;
  	default public T addn( T augend, T addend ) 
  	{   
  		return this.adds( this.create(), augend, addend, 1 );
  	}
  	
  	// out.x += amount.x;
  	default public T addi( T out, T amount ) 
  	{  	    
  		return this.adds( out, out, amount, 1 );
  	}

  	// out.x = minuend.x - subtrahend.x;
  	default public T sub( T out, T minuend, T subtrahend ) 
  	{  	    
  		return this.adds( out, minuend, subtrahend, -1 );
  	}
  	
  	// return minuend.x - subtrahend.x;
  	default public T subn( T minuend, T subtrahend ) 
  	{  	    
  	    return this.adds( this.create(), minuend, subtrahend, -1 );
  	}
  	
  	// out.x -= subtrahend.x
    default public T subi( T out, T subtrahend ) 
    {        
        return this.adds( out, out, subtrahend, -1 );
    }

    // out.x = value.x * scale.x
    public T mul( T out, T value, T scale );
    
    // return value.x * scale.x
    default public T muln( T value, T scale ) 
    {
        return this.mul( this.create(), value, scale );
    }
 
    // out.x *= scale.x
    default public T muli( T out, T scale ) 
    {
        return this.mul( out, out, scale );
    }

    // out.x = Numbers.divide( dividend.x, divisor.x )
    public T div( T out, T dividend, T divisor );
    
    // return Numbers.divide( out.x, denominator.x )
    default public T divn( T dividend, T divisor ) 
    {
        return this.div( this.create(), dividend, divisor );
    }
    
    // out.x = Numbers.divide( out.x, denominator.x )
    default public T divi( T out, T divisor ) 
    {
    	return this.div( out, out, divisor );
    }
    
    // out.x = (end.x - start.x) * delta + start.x
    default public T interpolate( T out, T start, T end, float delta ) 
    {    
        out = this.adds( out, this.ZERO(), start, 1 - delta );
        out = this.adds( out, out, end, delta );
        return out;
    }
    
    // out.x = (end.x - start.x) * delta + start.x
    default public T interpolaten( T start, T end, float delta ) 
    {
    	return this.interpolate( this.create(), start, end, delta );
    }
    
    default public T reflect( T out, T vector, T normal )
    {
    	float scole = -2 * dot( vector, normal );
    	return adds( out, vector, normal, scole );
    }
    
    default public T reflectn( T vector, T normal )
    {
    	return reflect( create(), vector, normal );
    }
    
    default public T reflecti( T out, T normal )
    {
    	return reflect( out, out, normal );
    }
    
    default public T refract( T out, T vector, T normal )
    {
    	return this.negi( this.reflect( out, vector, normal ) );
    }
    
    default public T refractn( T vector, T normal )
    {
    	return refract( create(), vector, normal );
    }
    
    default public T refracti( T out, T normal )
    {
    	return refract( out, out, normal );
    }
    
    default public boolean isParallel( T a, T b, float epsilon )
    {
    	return Math.abs( length( a ) * length( b ) - dot( a, b ) ) < epsilon;
    }

    default public boolean isParallel( T a, T b )
    {
    	return isParallel( a, b, Numbers.EPSILON );
    }
    
    default public boolean isPerpendicular( T a, T b, float epsilon )
    {
    	return Math.abs( dot( a, b ) ) < epsilon;
    }

    default public boolean isPerpendicular( T a, T b )
    {
    	return isPerpendicular( a, b, Numbers.EPSILON );
    }
    
    // out.x = OpenMath.clamp( out.x, min.x, max.x )
    public T contain( T out, T min, T max );
    
    // return point >= min && point <= max
    public boolean contains( T point, T min, T max );

    // out.x = randomizer( min.x, max.x )
    public T random( T out, T min, T max, Binary randomizer );

    // return a.x * b.x + a.y * b.y + ...
    public float dot( T a, T b );

    default public float distance( T a, T b ) 
    {
        return Numbers.sqrt( this.distanceSq( a, b ) );
    }
    
    public float distanceSq( T a, T b );
    
    default public float length( T value )
    {
        return Numbers.sqrt( this.lengthSq( value ) );
    }

    default public float lengthSq( T value ) 
    {
    	return this.dot( value, value );
    }
    
    default public float normali( T out )
    {
    	return this.normal( out, out );
    }
    
    default public float normal( T out, T vector ) 
    {
        float lsq = this.lengthSq( vector );
        
        out = this.copy( out, vector );
        
        if (lsq != 1.0 && lsq != 0.0)
        {
          lsq = Numbers.sqrt( lsq );
          
          this.scalei( out, 1 / lsq );
        }
        
        return lsq;
	}
	
	default public float normaln( T vector ) 
	{
	    return this.normal( this.create(), vector );
	}
	
	default public T negi( T out )
	{
		return this.neg( out, out );
	}
	
	default public T negn( T value )
	{
		return this.neg( this.create(), value );
	}
	
	default public T neg( T out, T value )
	{
		return this.scale( out, value, -1 );
	}
	
	default public T absi( T out )
	{
		return this.abs( out, out );
	}
	
	default public T absn( T value )
	{
		return this.abs( this.create(), value );
	}
	
	default public T abs( T out, T value )
	{
		return this.unary( out, value, Math::abs );
	}
	
	default public T modsi( T out, float divisor )
	{
		return this.mods( out, out, divisor );
	}
	
	default public T modsn( T value, float divisor )
	{
		return this.mods( this.create(), value, divisor );
	}
	
	public T mods( T out, T value, float divisor );

	
	default public T modi( T out, T divisor )
	{
		return this.mod( out, out, divisor );
	}
	
	default public T modn( T value, T divisor )
	{
		return this.mod( this.create(), value, divisor );
	}
	
	public T mod( T out, T value, T divisor );
	
	default public T truncatesi( T out, float divisor )
	{
		return truncates( out, out, divisor );
	}
	
	default public T truncatesn( T value, float divisor )
	{
		return truncates( create(), value, divisor );
	}
	
	public T truncates( T out, T value, float divisor );
	
	default public T truncatei( T out, T divisor )
	{
		return truncate( out, out, divisor );
	}
	
	default public T truncaten( T value, T divisor )
	{
		return truncate( create(), value, divisor );
	}
	
	public T truncate( T out, T value, T divisor );
	
	default public T floori( T out )
	{
		return this.floor( out, out );
	}
	
	default public T floorn( T value )
	{
		return this.floor( this.create(), value );
	}
	
	default public T floor( T out, T value )
	{
		return this.unary( out, value, Numbers::floor );
	}
	
	default public T ceili( T out )
	{
		return this.ceil( out, out );
	}
	
	default public T ceiln( T value )
	{
		return this.ceil( this.create(), value );
	}
	
	default public T ceil( T out, T value )
	{
		return this.unary( out, value, Numbers::ceil );
	}
	
	default public T mini( T out, T other )
	{
		return this.min( out, out, other );
	}
	
	default public T minn( T a, T b )
	{
		return this.min( this.create(), a, b );
	}
	
	default public T min( T out, T a, T b )
	{		
		return this.binary( out, a, b, Math::min );
	}
	
	default public T maxi( T out, T other )
	{
		return this.max( out, out, other );
	}
	
	default public T maxn( T a, T b )
	{
		return this.max( this.create(), a, b );
	}
	
	default public T max( T out, T a, T b )
	{		
		return this.binary( out, a, b, Math::max );
	}
	
	public T clamp( T out, T min, T max );
	
	public boolean isValue( Object value );

	public boolean isFinite( T value );
    
	default public boolean isZero( T value, float epsilon ) 
	{
		return this.isEqual( value, this.ZERO(), epsilon );
	}
    
	default public boolean isZero( T value ) 
	{
		return this.isEqual( value, this.ZERO(), Numbers.EPSILON );
	}
	
	default public boolean isEqual( T a, T b )
	{
		return isEqual( a, b, Numbers.EPSILON );
	}
	
	// return OpenMath.equals( a.x, b.x, epsilon ) && ...
	public boolean isEqual( T a, T b, float epsilon );

    default public boolean isUnit( T vector )
    {
    	return Numbers.equals( this.lengthSq( vector ), 1 );
    }
    
    default public boolean isUnit( T vector, float epsilon )
    {
    	return Numbers.equals( this.lengthSq( vector ), 1, epsilon );
    }
    
    default public boolean isLength( T vector, float length )
    {
    	return Numbers.equals( this.lengthSq( vector ), length * length );
    }
    
    default public boolean isLength( T vector, float length, float epsilon )
    {
    	return Numbers.equals( this.lengthSq( vector ), length * length, epsilon );
    }
    
	default public T lengthen( T out, T value, float length ) 
	{
	    return this.clamp( out, value, length, length );
	}
	
	default public T lengthenn( T value, float length ) 
	{
	    return this.clampn( value, length, length );
	}
	
	default public T lengtheni( T out, float length ) 
	{
	    return this.clampi( out, length, length );
	}

	default public T clamp( T out, T value, float min, float max )
	{
	    float valueSq = this.lengthSq( value );
	    
	    if (valueSq != 0.0) 
	    {
	      if (valueSq < min * min) 
	      {
	        return this.scale( out, value, min / Numbers.sqrt( valueSq ) );
	      }
	      else if (valueSq > max * max) 
	      {
	        return this.scale( out, value, max / Numbers.sqrt( valueSq ) );
	      }
	    }
	    
	    return this.copy( out, value );
	}

	default public T clampn( T value, float min, float max ) 
	{
	    return this.clamp( this.create(), value, min, max );
	}
	
	default public T clampi( T out, float min, float max ) 
	{
	    return this.clamp( out, out, min, max );
	}
	
	default public T inverti( T out )
	{
		return this.invert( out, out );
	}
	
	default public T invertn( T value )
	{
		return this.invert( this.create(), value );
	}
	
	default public T invert( T out, T value )
	{
		return this.div( out, ONE(), value );
	}
	
	public int sizeof( T value );
	
	public int write( T value, ByteBuffer to );
	
	public T read( T out, ByteBuffer from );
	
	public void write( T value, OutputModel to );
	
	public T read( T out, InputModel from );
	
	public int getComponents();
	
	public float getComponent( T value, int index );
	
	public T setComponent( T value, int index, float component );
	
	default public int getDimensions() 
	{
	    return this.getComponents();
	}
	
	default public float getDimension( T value, int index ) 
	{
	    return this.getComponent( value, index );
	}
	
	default public T setDimension( T value, int index, float dimension ) 
	{
	    return this.setComponent( value, index, dimension );
	}
	
	default public T slerpDirect( T out, T start, T end, float delta ) 
	{
	    float startLength = this.length( start );
	    float endLength = this.length( end );
	    float lengthSq = startLength * endLength;
	    float dot = this.dot( start, end );
	    float angle = Numbers.acos( dot / lengthSq );
	    
	    return this.slerp( out, start, end, delta, angle );
	}

	default public T slerpNormal( T out, T start, T end, float delta )
	{
	    float dot = this.dot( start, end );
	    float angle = Numbers.acos( dot );
	    
	    return this.slerp( out, start, end, delta, angle );
	}

	default public T slerp( T out, T start, T end, float delta, float angle ) 
	{
	    float invDenom = Numbers.divide( 1, Numbers.sin( angle ) );
	    float d0 = Numbers.sin( (1 - delta) * angle ) * invDenom;
	    float d1 = Numbers.cos( delta * angle ) * invDenom;

	    out = this.scale( out, end, d1 );
	    out = this.addsi( out, start, d0 );
	    
	    return out;
	}
	
	public float delta( T start, T end, T point );
	
	default public T closest( T out, T start, T end, T point )
	{
		return closest( out, start, end, point, false );
	}
	
	default public T closest( T out, T start, T end, T point, boolean line ) 
	{
	    float delta = this.delta( start, end, point );
	    
	    if (!line) 
	    {
	      delta = Numbers.clamp( delta, 0, 1 );
	    }
	    
	    return this.interpolate( out, start, end, delta );
	}
	
	public float interceptionTime( T shooter, float speed, T targetPosition, T targetVelocity );
	
	default public float distanceFrom( T start, T end, T point )
	{
		return distanceFrom( start, end, point, false );
	}
	
	public float distanceFrom( T start, T end, T point, boolean line );
	
	public boolean inView( T origin, T direction, float fovCos, T point );
	
	public boolean inCircleView( T origin, T direction, float fovTan, float fovCos, T center, float radius, boolean entirely );
	
	default public T cubicCurve( T out, float delta, T p0, T p1, T p2, T p3, float[][] matrix)
	{
		return cubicCurve( out, delta, p0, p1, p2, p3, matrix, false );
	}
	
	public T cubicCurve( T out, float delta, T p0, T p1, T p2, T p3, float[][] matrix, boolean inverse );
	
	default public T parametricCubicCurve( T out, float delta, T[] points, float[][] matrix, float weight )
	{
		return parametricCubicCurve( out, delta, points, matrix, weight, false, false );
	}
	
	public T parametricCubicCurve( T out, float delta, T[] points, float[][] matrix, float weight, boolean inverse, boolean loop );
	

	
	public static <T> T createLike( T value )
	{
		return CalculatorRegistry.getFor( value ).create();
	}
	
	public static <T> T cloneLike( T value )
	{
		return CalculatorRegistry.getFor( value ).clone( value );
	}
	
}
