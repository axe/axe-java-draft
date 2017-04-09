package com.axe.path;


public class CompiledPath<T> extends JumpPath<T>
{
		
	public CompiledPath()
	{
	}
	
	public CompiledPath( Path<T> path, T[] allocated )
	{
		super( compile( path, allocated ) );
	}
	
	public CompiledPath( Path<T> path, int pointCount )
	{
		super( compile( path, path.getCalculator().createArray( pointCount ) ) );
	}

	public static <T> T[] compile( Path<T> path, T[] allocated)
	{
		int n = allocated.length - 1;
		
		for ( int i = 0; i <= n; i++ )
		{
			path.set( allocated[i], (float)i / n );
		}
		
		return allocated;
	}
	
}
