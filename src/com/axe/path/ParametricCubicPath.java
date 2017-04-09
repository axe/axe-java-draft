package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class ParametricCubicPath<T> extends AbstractPath<T>
{

	public float[][] matrix;
	public float weight;
	public T[] points;

	public ParametricCubicPath()
	{
	}
	
	public ParametricCubicPath( float weight, float[][] matrix, Alternative<T> ... points ) 
	{
		this( weight, matrix, PathUtility.alternatives( points ) );
	}
	
	public ParametricCubicPath(float weight, float[][] matrix, T ... points)
	{
		super( points[0] );
		
		this.weight = weight;
		this.matrix = matrix;
		this.points = points;
	}
	
	@Override
	public T set( T subject, float delta )
	{
		return calc.parametricCubicCurve( subject, delta, points, matrix, delta );
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
		weight = input.readFloat( "weight" );
		points = input.readModelArray( "points", "point-type" );
		matrix = getMatrixFromString( input.readString( "matrix" ) );
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "weight", weight );
		output.writeModelArray( "points", points, "point-type", calc );
		output.write( "matrix", getStringFromMatrix( matrix ) );
	}

	public static float[][] getMatrixFromString(String x)
	{
		String[] parts = x.split( "," );
		
		float[][] m = new float[4][4];
		
		for (int i = 0; i < 16; i++)
		{
			m[i / 4][i % 4] = Float.parseFloat( parts[i] );
		}
		
		return m;
	}
	
	public static String getStringFromMatrix(float[][] matrix)
	{
		StringBuilder s = new StringBuilder( 64 );
		
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				if (s.length() > 0)
				{
					s.append( ',' );
				}
				
				s.append( matrix[y][x] );
			}
		}
		
		return s.toString();
	}

}
