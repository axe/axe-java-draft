package com.axe.path;

import com.axe.io.DataModel;
import com.axe.math.calc.Calculator;

public interface Path<T> extends DataModel
{
	
	public T set(T subject, float delta);
	
	public T get(float delta);
	
	public int getPointCount();
	
	public T getPoint(int index);
	
	public Calculator<T> getCalculator();
	
}
