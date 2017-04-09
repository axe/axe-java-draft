package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class HermitePath<T> extends AbstractPath<T>
{

	public T start;
	public T startTangent;
	public T end;
	public T endTangent;
	
	public HermitePath()
	{
	}
	
	public HermitePath(Alternative<T> start, Alternative<T> startTangent, Alternative<T> end, Alternative<T> endTangent)
	{
		this( start.alternative(), startTangent.alternative(), end.alternative(), endTangent.alternative() );
	}
	
	public HermitePath(T start, T startTangent, T end, T endTangent)
	{
		super( start );
		
		this.start = start;
		this.startTangent = startTangent;
		this.end = end;
		this.endTangent = endTangent;
	}
	
	@Override
	public T set( T subject, float d )
	{
		float d2 = d * d;
		float d3 = d2 * d;
		
		subject = calc.clear( subject, 0 );
		subject = calc.addsi( subject, start, 2 * d3 - 3 * d2 + 1 );
		subject = calc.addsi( subject, end, -2 * d3 + 3 * d2 );
		subject = calc.addsi( subject, startTangent, d3 - 2 * d2 + d );
		subject = calc.addsi( subject, endTangent, d3 - d2 );
				
		return subject;
	}

	@Override
	public int getPointCount()
	{
		return 4;
	}

	@Override
	public T getPoint( int index )
	{
		switch(index) {
		case 0: return start;
		case 1: return startTangent;
		case 2: return end;
		case 3: return endTangent;
		}
		return null;
	}

	@Override
	public void read( InputModel input )
	{
		// TODO calc
		// Attribute<T> factory = input.readInstance( "type" );
		
		start = calc.create();
		startTangent = calc.create();
		end = calc.create();
		endTangent = calc.create();
		
		input.readModel( "start", start, calc );
		input.readModel( "start-tangent", startTangent, calc );
		input.readModel( "end", end, calc );
		input.readModel( "end-tangent", endTangent, calc );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeInstance( "type", start );
		output.writeModel( "start", start, calc );
		output.writeModel( "start-tangent", startTangent, calc );
		output.writeModel( "end", end, calc );
		output.writeModel( "end-tangent", endTangent, calc );
	}

}
