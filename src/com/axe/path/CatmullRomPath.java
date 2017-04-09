package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class CatmullRomPath<T> extends AbstractPath<T>
{

	public static final float WEIGHT = 0.5f;
	public static final float[][] MATRIX = {
		{ 0, 2, 0, 0},
		{-1, 0, 1, 0},
		{ 2,-5, 4,-1},
		{-1, 3,-3, 1}
	};
	
	public T[] points;
	
	public CatmullRomPath()
	{
	}
	
	public CatmullRomPath( Alternative<T> ... points ) 
	{
		this( PathUtility.alternatives( points ) );
	}
	
	public CatmullRomPath(T ... points)
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
