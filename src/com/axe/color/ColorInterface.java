package com.axe.color;

import com.axe.math.Numbers;

public interface ColorInterface<C extends ColorInterface<C>> 
{
	
	public static final float DEFAULT_ALPHA = 1f;
	public static final int DEFAULT_ALPHA_INT = 255;

	public float getRed();
	public float getBlue();
	public float getGreen();
	public float getAlpha();
	
	public C create(float r, float g, float b, float a);

	default public Color mutable()
	{
		return new Color( getRed(), getGreen(), getBlue(), getAlpha() );
	}
	
	default public ImmutableColor immutable()
	{
		return new ImmutableColor( getRed(), getGreen(), getBlue(), getAlpha() );
	}
	
	default public boolean isImmutable()
	{
		return false;
	}
	
	default public int r() 
	{
		return Numbers.clamp( (int)(getRed() * 255), 0, 255 );
	}
	
	default public int g() 
	{
		return Numbers.clamp( (int)(getGreen() * 255), 0, 255 );
	}
	
	default public int b() 
	{
		return Numbers.clamp( (int)(getBlue() * 255), 0, 255 );
	}
	
	default public int a() 
	{
		return Numbers.clamp( (int)(getAlpha() * 255), 0, 255 );
	}
	
	default public int rgb() 
	{
		return hash(16, 8, 0);
	}
	
	default public int bgr() 
	{
		return hash(0, 8, 16);
	}
	
	default public int argb() 
	{
		return hash(16, 8, 0, 24);
	}
	
	default public int abgr() 
	{
		return hash(0, 8, 16, 24);
	}
	
	default public int rgba() 
	{
		return hash(24, 16, 8, 0);
	}
	
	default public int bgra() 
	{
		return hash(8, 16, 24, 0);
	}
	
	default public int hash(int r, int g, int b) 
	{
		return (r() << r) | (g() << g) | (b() << b);
	}
	
	default public int hash(int r, int g, int b, int a) 
	{
		return (a() << a) | (r() << r) | (g() << g) | (b() << b);
	}
	
	/* TODO
	default public C invert() 
	{
		return create( 1 - getRed(), 1 - getGreen(), 1  - getBlue(), getAlpha() );
	}
	
	default public Color invert(Color out) 
	{
		out.r = 1 - getRed();
		out.g = 1 - getGreen();
		out.b = 1 - getBlue();
		return out;
	}
	*/
	
	// 0 = no change, 1 = white
	default public C lighter(float delta)
	{
		return create(
			getRed() + (1 - getRed()) * delta,
			getGreen() + (1 - getGreen()) * delta,
			getBlue() + (1 - getBlue()) * delta,
			getAlpha()
		);
	}
	
	// 0 = no change, 1 = white
	default public Color lighter(float delta, Color out)
	{
		out.r = getRed() + (1 - getRed()) * delta;
		out.g = getGreen() + (1 - getGreen()) * delta;
		out.b = getBlue() + (1 - getBlue()) * delta;
		return out;
	}
	
	// 0 = no change, 1 = black
	default public C darker(float delta)
	{
		return create(
			getRed() - getRed() * delta,
			getGreen() - getGreen() * delta,
			getBlue() - getBlue() * delta,
			getAlpha()
		);
	}
	
	// 0 = no change, 1 = black
	default public Color darker(float delta, Color out)
	{
		out.r = getRed() - getRed() * delta;
		out.g = getGreen() - getGreen() * delta;
		out.b = getBlue()- getBlue() * delta;
		return out;
	}
	
}
