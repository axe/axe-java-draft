package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorRangef;


public class Rangef implements Attribute<Rangef>
{
	
	public float min, max;
	
	public Rangef() 
	{
		this(0, 0);
	}
	
	public Rangef(Rangef r) 
	{
		this(r.min, r.max);
	}
	
	public Rangef(float x) 
	{
		this(x, x);
	}
	
	public Rangef(float min, float max) 
	{
		this.min = min;
		this.max = max;
	}

	public void set( float min, float max ) 
	{
		this.min = min;
		this.max = max;
	}
	
	public float rnd() 
	{
		return Numbers.randomFloat( min, max );
	}

	@Override
	public Rangef get() 
	{
		return this;
	}
	
	@Override
	public Rangef clone()
	{
		return new Rangef( this );
	}
	
	@Override
	public CalculatorRangef getCalculator()
	{
		return CalculatorRangef.INSTANCE;
	}

	@Override
	public int hashCode()
	{
		return (int)(min + max * 46340);
	}
	
}