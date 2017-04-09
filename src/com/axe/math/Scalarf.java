package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorScalarf;


public class Scalarf implements Attribute<Scalarf>
{
	
	public static final Scalarf ZERO = new Scalarf( 0f );
	public static final Scalarf ONE = new Scalarf( 1f );

	public float v;
	
	public Scalarf() 
	{
	}
	
	public Scalarf(float v) 
	{
		this.v = v;
	}
	
	public Scalarf(double v) 
	{
		this.v = (float)v;
	}
	
	public Scalarf add(float s) 
	{
		v += s;
		return this;
	}
	
	public Scalarf sub(float s) 
	{
		v -= s;
		return this;
	}
	
	public Scalarf mul(float s) 
	{
		v *= s;
		return this;
	}
	
	public Scalarf div(float s) 
	{
		v = Numbers.divide(v, s);
		return this;
	}
	
	public Scalarf max(float s) 
	{
		v = (v > s ? s : v);
		return this;
	}
	
	public Scalarf min(float s) 
	{
		v = (v < s ? s : v);
		return this;
	}
	
	public Scalarf delta(float start, float end, float delta) 
	{
		v = (end - start) * delta + start;
		return this;
	}
	
	public Scalarf mod(float s) 
	{
		v = v % s;
		return this;
	}
	
	public float cos() 
	{
		return (float)Math.cos(v);
	}
	
	public float sin() 
	{
		return (float)Math.sin(v);
	}
	
	public float degrees() 
	{
		return (float)Math.toDegrees(v);
	}
	
	public float radians() 
	{
		return (float)Math.toRadians(v);
	}
	
	@Override
	public Scalarf get() 
	{
		return this;
	}
	
	@Override
	public Scalarf clone() 
	{
		return new Scalarf( v );
	}
	
	@Override
	public CalculatorScalarf getCalculator()
	{
		return CalculatorScalarf.INSTANCE;
	}
	
	@Override
	public String toString() 
	{
		return Float.toString( v );
	}

	@Override
	public int hashCode()
	{
		return (int)(v * 46340);
	}
	
	public static Scalarf[] array( float ... values )
	{
		Scalarf[] array = new Scalarf[ values.length ];
		
		for ( int i = 0; i < values.length; i++ )
		{
			array[i] = new Scalarf( values[i] );
		}
		
		return array;
	}
	
}