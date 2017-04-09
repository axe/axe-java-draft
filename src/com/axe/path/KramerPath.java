package com.axe.path;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

/**
 * http://www.reddit.com/r/gamedev/comments/1ei88i/very_fast_2d_interpolation/
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 */
public class KramerPath<T> extends AbstractPath<T> 
{
	
	public static final float DEFAULT_LOOSENESS = 0.0575f;
	
	protected T[] points;
	protected T temp0;
	protected T temp1;
	protected int depth;
	protected float looseness;
	protected boolean loops;
	protected float roughness;
	
	public KramerPath()
	{
	}
	
	// TODO alternatives
	
	public KramerPath( int depth, boolean loops, T ... points )
	{
		this( depth, loops, DEFAULT_LOOSENESS, 0.0f, points );
	}
	
	public KramerPath( int depth, boolean loops, float looseness, T ... points ) 
	{
		this( depth, loops, looseness, 0.0f, points );
	}
	
	public KramerPath( float roughness, boolean loops, T ... points )
	{
		this( 0, loops, DEFAULT_LOOSENESS, roughness, points );
	}
	
	public KramerPath( float roughness, boolean loops, float looseness, T ... points ) 
	{
		this( 0, loops, looseness, roughness, points );
	}
	
	protected KramerPath( int depth, boolean loops, float looseness, float roughness, T ... points ) 
	{
		super( points[0] );
		
		this.depth = depth;
		this.loops = loops;
		this.looseness = looseness;
		this.roughness = roughness;
		this.points = points;
		this.temp0 = calc.create();
		this.temp1 = calc.create();
	}
	
	@Override
	public T set(T subject, float delta) 
	{
		final int n = points.length;
		final float a = delta * n;
		final int i = Numbers.clamp( (int)a, 0, n - 1 );
		float d = a - i;
		
		if (depth != 0)
		{
			subject = getPointWithExactDepth( i, d, subject );
		}
		else
		{
			subject = getPointWithRoughness( i, d, subject );
		}
		
		return subject;
	}
	
	public T getPointWithExactDepth( int i, float d, T subject )
	{
		// v0 and v5 are used to calculate the next v1 or v4, at the next level.
		T v0 = points[ getActualIndex( i - 2 ) ];
		T v1 = points[ getActualIndex( i - 1 ) ];
		T v2 = points[ getActualIndex( i ) ];
		T v3 = points[ getActualIndex( i + 1 ) ];
		T v4 = points[ getActualIndex( i + 2 ) ];
		T v5 = points[ getActualIndex( i + 3 ) ];
		
		int k = depth;

		while (--k >= 0)
		{
			// Get mid point
			T mid = getPoint( v1, v2, v3, v4 );
			
			// If the desired point is closer to v2...
			if (d < 0.5f)
			{
				// shift all surrounding points one-level closer to v2
				if (k == 0)
				{
					v3 = mid;
				}
				else
				{
					T newEnd = v1;
					v5 = v4;
					v4 = v3;
					v3 = mid;
					v1 = getPoint( v0, v1, v2, v3 );
					v0 = newEnd;
				}
				// adjust d so it's between 0.0 an 1.0
				d = d * 2.0f;
			}
			// else, the desired point is closer to v3...
			else
			{
				// shift all surrounding points one-level closer to v3
				if (k == 0)
				{
					v2 = mid;
				}
				else
				{
					T newEnd = v4;
					v0 = v1;
					v1 = v2;
					v2 = mid;
					v4 = getPoint( v2, v3, v4, v5 );
					v5 = newEnd;
				}
				// adjust d so it's between 0.0 an 1.0
				d = (d - 0.5f) * 2.0f;
			}
		}
		
		// subject = (v3 - v2) * d + v2
		return calc.interpolate( subject, v2, v3, d );
	}
	
	public T getPointWithRoughness( int i, float d, T subject )
	{
		// v0 and v5 are used to calculate the next v1 or v4, at the next level.
		T v0 = points[ getActualIndex( i - 2 ) ];
		T v1 = points[ getActualIndex( i - 1 ) ];
		T v2 = points[ getActualIndex( i ) ];
		T v3 = points[ getActualIndex( i + 1 ) ];
		T v4 = points[ getActualIndex( i + 2 ) ];
		T v5 = points[ getActualIndex( i + 3 ) ];

		for (;;)
		{
			// Get mid point
			T mid = getPoint( v1, v2, v3, v4 );
			
			// if distance from mid to (v2->v3) is <= roughness, break
			// calculate the distance between all three points to form a triangle,
			// with the distances determine the perimeter then area. Use the
			//	area = 0.5 * b * h formula to calculate height.
			float height = calc.distanceFrom( v2, v3, mid );
			
			if (height <= roughness)
			{
				break;
			}
			
			// If the desired point is closer to v2...
			if (d < 0.5f)
			{
				// shift all surrounding points one-level closer to v2
				T newEnd = v1;
				v5 = v4;
				v4 = v3;
				v3 = mid;
				v1 = getPoint( v0, v1, v2, v3 );
				v0 = newEnd;
				// adjust d so it's between 0.0 an 1.0
				d = d * 2.0f;
			}
			// else, the desired point is closer to v3...
			else
			{
				// shift all surrounding points one-level closer to v3
				T newEnd = v4;
				v0 = v1;
				v1 = v2;
				v2 = mid;
				v4 = getPoint( v2, v3, v4, v5 );
				v5 = newEnd;
				// adjust d so it's between 0.0 an 1.0
				d = (d - 0.5f) * 2.0f;
			}
		}
		
		// subject = (v3 - v2) * d + v2
		return calc.interpolate( subject, v2, v3, d );
	}
		
	public T getPoint( T v1, T v2, T v3, T v4 )
	{
		// p = (0.5f + looseness) * (v2 + v3) - looseness * (v1 + v4)
		
		temp0 = calc.copy( temp0, v2 );
		temp0 = calc.addi( temp0, v3 );
		temp0 = calc.scalei( temp0, 0.5f + looseness );
				
		temp1 = calc.copy( temp1, v1 );
		temp1 = calc.addi( temp1, v4 );
		
		// TODO recycle
		return calc.addsn( temp0, temp1, -looseness );
	}
	
	public int getActualIndex( int index )
	{
		final int n = points.length;
		
		return ( loops ? (index + n) % n : Numbers.clamp( index, 0, n - 1 ) );
	}

	@Override
	public int getPointCount()
	{
		return points.length;
	}

	@Override
	public T getPoint(int index)
	{
		return points[ index ];
	}
	
	public T[] points() 
	{
		return points;
	}

	@Override
	public void read( InputModel input )
	{
		// TODO calc
		depth = input.readInt( "depth" );
		loops = input.readBoolean( "loops" );
		looseness = input.readFloat( "looseness" );
		points = input.readModelArray( "point", "point-type" );
		temp0 = calc.create();
		temp1 = calc.create();
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "depth", depth );
		output.write( "loops", loops );
		output.write( "looseness", looseness );
		output.writeModelArray( "point", points, "point-type", calc );
	}
	
}
