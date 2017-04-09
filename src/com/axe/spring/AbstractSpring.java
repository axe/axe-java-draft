package com.axe.spring;

import com.axe.math.Vec;
import com.axe.math.calc.Calculator;
import com.axe.math.calc.CalculatorRegistry;
import com.axe.node.Node;

public abstract class AbstractSpring<V extends Vec<V>, T> implements Spring<V, T> 
{
	
	public Node<V> parent;
	protected Calculator<T> calc;
	protected T rest;
	protected T position;
	protected T velocity;
	
	public AbstractSpring()
	{
		
	}
	
	public AbstractSpring(T rest, T position, T velocity)
	{
		this.calc = CalculatorRegistry.getFor( rest );
		this.rest = rest;
		this.position = position;
		this.velocity = velocity;
	}

	@Override
	public Node<V> getParent() 
	{
		return parent;
	}

	@Override
	public void setParent(Node<V> parent) 
	{
		this.parent = parent;
	}

	@Override
	public T rest() 
	{
		return rest;
	}

	@Override
	public T velocity() 
	{
		return velocity;
	}

	@Override
	public T position() 
	{
		return position;
	}
	
	@Override
	public Calculator<T> getCalculator()
	{
		return calc;
	}

}
