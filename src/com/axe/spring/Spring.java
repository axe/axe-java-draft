package com.axe.spring;

import com.axe.io.DataModel;
import com.axe.math.Vec;
import com.axe.math.calc.Calculator;
import com.axe.node.Node;

public interface Spring<V extends Vec<V>, T> extends Node<V>, DataModel
{
	public T rest(); 
	public T velocity();
	public T position();
	public Calculator<T> getCalculator();
}
