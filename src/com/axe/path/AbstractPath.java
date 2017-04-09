package com.axe.path;

import com.axe.math.calc.Calculator;
import com.axe.math.calc.CalculatorRegistry;

public abstract class AbstractPath<T> implements Path<T> 
{
	
	public Calculator<T> calc;
	
	public AbstractPath()
	{
		
	}
	
	public AbstractPath(T value) 
	{
		this.initialize( value );
	}
	
	public AbstractPath( Calculator<T> calc )
	{
		this.calc = calc;
	}
	
	protected void initialize(T value)
	{
		this.calc = CalculatorRegistry.getFor( value );
	}

	@Override
	public T get(float delta) 
	{
		return set( getCalculator().create(), delta );
	}

	@Override
	public Calculator<T> getCalculator() 
	{
		return calc;
	}

}
