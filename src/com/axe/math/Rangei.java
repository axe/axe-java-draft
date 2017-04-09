package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorRangei;

public class Rangei implements Attribute<Rangei>
{
	
	public int min, max;
	
	public Rangei() 
	{
		this(0, 0);
	}
	
	public Rangei(Rangei r) 
	{
		this(r.min, r.max);
	}
	
	public Rangei(int x) 
	{
		this(x, x);
	}
	
	public Rangei(int min, int max) 
	{
		this.min = min;
		this.max = max;
	}
	
	public int rnd() 
	{
		return Numbers.randomInt( min, max );
	}
	
	@Override
	public Rangei get() 
	{
		return this;
	}

	public void set( int x )
	{
		min = max = x;
	}
	
	public void set( int min, int max )
	{
		this.min = min;
		this.max = max;
	}
	
	@Override
	public Rangei clone() 
	{
		return new Rangei( min, max );
	}
	
	@Override
	public CalculatorRangei getCalculator()
	{
		return CalculatorRangei.INSTANCE;
	}

	@Override
	public int hashCode()
	{
		return (int)(min + max * 46340);
	}
	
}