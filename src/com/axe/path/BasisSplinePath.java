package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;

//TODO fix BasisSplinePath where 
//	P(t) = Ti * Mbs * Gbs
//		Ti = [(t - ti)^3, (t - ti)^2, (t - ti), 1]
//		Gbs = [P(i-3), P(i-2), P(i-1), P(i)]
//		Mbs = BasisSplinePath.MATRIX * BasisSplinePath.WEIGHT
public class BasisSplinePath<T> extends AbstractPath<T>
{

	public static final float WEIGHT = 1.0f / 6.0f;
	public static final float[][] MATRIX = {
		{-1, 3,-3, 1},
		{ 3,-6, 3, 0},
		{-3, 0, 3, 0},
		{ 1, 4, 1, 0}
	};
	
	public T[] points;
	
	public BasisSplinePath()
	{
	}
	
	public BasisSplinePath( Alternative<T> ... points ) 
	{
		this( PathUtility.alternatives( points ) );
	}
	
	public BasisSplinePath( T ... points )
	{
		super( points[0] );
		
		this.points = points;
	}

	@Override
	public T set( T subject, float delta )
	{
		return calc.parametricCubicCurve( subject, delta, points, MATRIX, WEIGHT );
	}

	@Override
	public int getPointCount()
	{
		return points.length;
	}

	@Override
	public T getPoint( int index )
	{
		return points[index];
	}
	
	@Override
	public void read( InputModel input )
	{
		// TODO calc
		points = input.readModelArray( "points", "point-type" );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModelArray( "points", points, "point-type", calc );
	}

}
