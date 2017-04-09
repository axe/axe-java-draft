
package com.axe.easing;

import com.axe.io.DataModel;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class Easing implements DataModel
{

	private EasingType type;
	private EasingMethod method;
	private float scale;

	public Easing()
	{
		this( Easings.In, Easings.Linear, 1f );
	}

	public Easing( Easing e )
	{
		this( e.type, e.method, e.scale );
	}
	
	public Easing( EasingType type, EasingMethod method )
	{
		this( type, method, 1f );
	}

	public Easing( EasingType type, EasingMethod method, float scale )
	{
		this.type = type;
		this.method = method;
		this.scale = scale;
	}

	public float delta( float delta )
	{
		float d = type.delta( delta, method );
		if (scale != 1f)
		{
			d = scale * d + (1 - scale) * delta;
		}
		return d;
	}

	public EasingType type()
	{
		return type;
	}

	public void type( EasingType type )
	{
		this.type = type;
	}

	public EasingMethod method()
	{
		return method;
	}

	public void method( EasingMethod method )
	{
		this.method = method;
	}

	public float scale()
	{
		return scale;
	}

	public void scale( float scale )
	{
		this.scale = scale;
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "scale", scale );
		output.write( "type", type.name() );
		output.write( "method", method.name() );
	}

	@Override
	public void read( InputModel input )
	{
		scale = input.readFloat( "scale" );
		type = Easings.TypeMap.get( input.readString( "type" ) );
		method = Easings.MethodMap.get( input.readString( "method" ) );
	}

}
