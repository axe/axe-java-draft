
package com.axe.easing;

public abstract class AbstractEasingMethod implements EasingMethod
{

	private final String name;

	public AbstractEasingMethod( String name )
	{
		this.name = name;
	}

	public String name()
	{
		return name;
	}
}
