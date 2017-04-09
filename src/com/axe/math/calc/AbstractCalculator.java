package com.axe.math.calc;

import com.axe.math.Numbers;

public abstract class AbstractCalculator<T> implements Calculator<T> 
{
	
	protected T zero;
	protected T one;
	
	protected T temp0;
	protected T temp1;
	
	protected T[] pool;
	protected int poolSize;

	protected AbstractCalculator() 
	{
		this.zero = this.createUniform( 0 );
		this.one = this.createUniform( 1 );
		this.temp0 = this.createUniform( 0 );
		this.temp1 = this.createUniform( 0 );
	}
	
	protected abstract T instantiate();
	
	@Override
	public T ZERO() 
	{
		return zero;
	}

	@Override
	public T ONE() 
	{
		return one;
	}

	@Override
	public T create() 
	{
		return poolSize > 0 ? clear( pool[ --poolSize ], 0 ) : instantiate();
	}

	@Override
	public void setPool(T[] pool) 
	{
		this.pool = pool;
	}

	@Override
	public boolean recycle(T unused) 
	{
		boolean recycled = poolSize < pool.length;
		
		if (recycled)
		{
			pool[ poolSize++ ] = unused;
		}
		
		return recycled;
	}
    
	@Override
    public float distanceSq( T a, T b ) 
    {
        T diff = this.sub( temp0, a, b );
        
        return this.dot( diff, diff );
    }
	
	@Override
	public float delta( T start, T end, T point ) 
	{
	    T p0 = this.sub( temp0, end, start );
	    T p1 = this.sub( temp1, start, point );

	    float dot = this.dot( p0, p1 );
	    float dsq = this.lengthSq( p0 );
	    float delta = dot / dsq;
	    return delta;
	}
	
	@Override
	public float interceptionTime( T shooter, float speed, T targetPosition, T targetVelocity ) 
	{
	    T direct = this.sub( temp0, targetPosition, shooter );
	
	    float a = this.lengthSq( targetVelocity ) - ( speed * speed );
	    float b = 2.0f * this.dot( targetVelocity, direct );
	    float c = this.lengthSq( direct );
	
	    return Numbers.quadraticFormula( a, b, c, -1 );
	}
	
	@Override
	public float distanceFrom( T start, T end, T point, boolean line ) 
	{
	    float delta = this.delta( start, end, point );
	    
	    if (!line) 
	    {
	      delta = Numbers.clamp( delta, 0, 1 );
	    }
	    
	    T projected = this.interpolate( temp0, start, end, delta );
	    
	    return this.distance( point, projected );
	}
	
	public boolean inView( T origin, T direction, float fovCos, T point )
	{
		T diff = this.sub( temp0, origin, point );
		
	    return this.dot( diff, direction ) > fovCos;
	}
	
	public boolean inCircleView( T origin, T direction, float fovTan, float fovCos, T center, float radius, boolean entirely )
	{
		T circleToOrigin = this.sub( temp0, center, origin );

	    float distanceAlongDirection = this.dot( circleToOrigin, direction );
	    float coneRadius = distanceAlongDirection * fovTan;                       
	    float distanceFromAxis = Numbers.sqrt( this.lengthSq( circleToOrigin ) - distanceAlongDirection * distanceAlongDirection );
	    float distanceFromCenterToCone = distanceFromAxis - coneRadius;                              
	    float shortestDistance = distanceFromCenterToCone * fovCos;                          

	    if (entirely) 
	    {
	        shortestDistance += radius;
	    } 
	    else 
	    {
	        shortestDistance -= radius;
	    }

	    return shortestDistance <= 0;
	}
	
	public T cubicCurve( T out, float delta, T p0, T p1, T p2, T p3, float[][] matrix, boolean inverse )
	{
		float d0 = 1.0f;
	    float d1 = delta;
	    float d2 = d1 * d1;
	    float d3 = d2 * d1;

	    float[] ts = {
	      inverse ? d3 : d0,
	      inverse ? d2 : d1,
	      inverse ? d1 : d2,
	      inverse ? d1 : d3
	    };

	    T temp = temp0;
	    out = this.clear( out, 0 );

	    for (int i = 0; i < 4; i++) 
	    {
	      temp = this.clear( temp, 0 );
	      temp = this.addsi( temp, p0, matrix[ i ][ 0 ] );
	      temp = this.addsi( temp, p1, matrix[ i ][ 1 ] );
	      temp = this.addsi( temp, p2, matrix[ i ][ 2 ] );
	      temp = this.addsi( temp, p3, matrix[ i ][ 3 ] );
	      out = this.addsi( out, temp, ts[ i ] );
	    }
	    
	    return out;
	}

	public T parametricCubicCurve( T out, float delta, T[] points, float[][] matrix, float weight, boolean inverse, boolean loop )
	{
		int n = points.length - 1;
	    float a = delta * n;
	    int i = (int)Numbers.clamp( Numbers.floor( a ), 0, n - 1 );
	    float d = a - i;

	    T p0 = (i == 0 ? (loop ? points[n] : points[0]) : points[i - 1]);
	    T p1 = points[i];
	    T p2 = points[i + 1];
	    T p3 = (i == n - 1 ? (loop ? points[0] : points[n]) : points[i + 2]);

	    out = this.cubicCurve( out, d, p0, p1, p2, p3, matrix, inverse );
	    out = this.scalei( out, weight );
	    
	    return out;
	}

}
