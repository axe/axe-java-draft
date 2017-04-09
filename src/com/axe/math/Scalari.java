package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorScalari;


public class Scalari implements Attribute<Scalari> 
{

	public static final Scalari ZERO = new Scalari( 0 );
	public static final Scalari ONE = new Scalari( 1 );
	
	public int v;
	
	public Scalari() 
	{
	}
	
	public Scalari(int v) 
	{
		this.v = v;
	}
	
	public Scalari set(int v) 
	{
		this.v = v;
		return this;
	}
	
	public Scalari add(int s) 
	{
		v += s;
		return this;
	}
	
	public Scalari sub(int s) 
	{
		v -= s;
		return this;
	}
	
	public Scalari mul(int s) 
	{
		v *= s;
		return this;
	}
	
	public Scalari div(int s) 
	{
		v = Numbers.divide(v, s);
		return this;
	}
	
	public Scalari max(int s) 
	{
		v = (v > s ? s : v);
		return this;
	}
	
	public Scalari min(int s) 
	{
		v = (v < s ? s : v);
		return this;
	}
	
	public Scalari clamp(int max, int min) 
	{
		v = (v < min ? min : (v > max ? max : v));
		return this;
	}
	
	public Scalari delta(int start, int end, float delta) 
	{
		v = (int)((end - start) * delta + start);
		return this;
	}
	
	public void mod(int s) 
	{
		v -= v % s;
	}
	
	public float cos() 
	{
		return Numbers.COS[v % 360];
	}
	
	public float sin() 
	{
		return Numbers.SIN[v % 360];
	}
	
	public float degrees() 
	{
		return v;
	}
	
	public float radians() 
	{
		return Numbers.RADIAN[v % 360];
	}
	
	@Override
	public Scalari get() 
	{
		return this;
	}
		
	@Override
	public Scalari clone() 
	{
		return new Scalari( v );
	}
	
	@Override
	public CalculatorScalari getCalculator()
	{
		return CalculatorScalari.INSTANCE;
	}
	
	@Override
	public String toString() 
	{
		return Integer.toString( v );
	}

	@Override
	public int hashCode()
	{
		return (int)(v * 46340);
	}

	public static Scalari[] array( int ... values )
	{
		Scalari[] array = new Scalari[ values.length ];
		
		for ( int i = 0; i < values.length; i++ )
		{
			array[i] = new Scalari( values[i] );
		}
		
		return array;
	}
	
}