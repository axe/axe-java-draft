package com.axe.color;

import com.axe.core.Alternative;

public class ImmutableColor implements ColorInterface<ImmutableColor>, Alternative<Color> 
{

	private float r, g, b, a;
	
	public ImmutableColor(ColorInterface color)
	{
		this( color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() );
	}
	
	public ImmutableColor(float r, float g, float b)
	{
		this( r, g, b, DEFAULT_ALPHA );
	}
	
	public ImmutableColor(float r, float g, float b, float a ) 
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public ImmutableColor(int r, int g, int b)
	{
		this( r / 255f, g / 255f, b / 255f, DEFAULT_ALPHA );
	}

	public ImmutableColor(int r, int g, int b, int a)
	{
		this( r / 255f, g / 255f, b / 255f, a / 255f );
	}
	
	@Override
	public float getRed() 
	{
		return r;
	}

	@Override
	public float getBlue() 
	{
		return b;
	}

	@Override
	public float getGreen() 
	{
		return g;
	}

	@Override
	public float getAlpha() 
	{
		return a;
	}
	
	@Override
	public ImmutableColor create(float r, float g, float b, float a)
	{
		return new ImmutableColor( r, g, b, a );
	}

	@Override
	public ImmutableColor immutable() 
	{
		return this;
	}

	@Override
	public boolean isImmutable() 
	{
		return true;
	}

	@Override
	public Color alternative()
	{
		return new Color( this );
	}
	
}
