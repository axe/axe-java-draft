
package com.axe.easing;

public abstract class AbstractEasingType implements EasingType
{

	private final String name;

	public AbstractEasingType( String name )
	{
		this.name = name;
	}

	public String name()
	{
		return name;
	}
}
