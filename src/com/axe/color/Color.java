package com.axe.color;

import java.io.Serializable;

import com.axe.core.Alternative;
import com.axe.core.Attribute;
import com.axe.core.Factory;
import com.axe.math.Numbers;
import com.axe.math.calc.CalculatorColor;

public class Color implements ColorInterface<Color>, Attribute<Color>, Alternative<Color>, Serializable
{
	
	private static final long serialVersionUID = -8256268873078605411L;

	public static final Factory<Color> FACTORY = new Factory<Color>() {
		public Color create() {
			return new Color();
		}
	};
	
	public float r, b, g, a;

	public Color()
	{
		set(1f, 1f, 1f, 1f);
	}

	public Color(float red, float green, float blue)
	{
		set(red, green, blue, 1f);
	}

	public Color(float red, float green, float blue, float alpha)
	{
		set(red, green, blue, alpha);
	}

	public Color(int red, int green, int blue)
	{
		set(red / 255f, green / 255f, blue / 255f, 1f);
	}

	public Color(int red, int green, int blue, int alpha)
	{
		set(red / 255f, green / 255f, blue / 255f, alpha / 255f);
	}

	public Color(ColorInterface c) 
	{
		set(c);
	}
	
	public Color(ColorInterface c, float alpha) 
	{
		set(c); a = alpha;
	}

	public void set(ColorInterface c)
	{
		set(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public void set(float red, float green, float blue)
	{
		set(red, green, blue, 1f);
	}
	
	public void set(float red, float green, float blue, float alpha)
	{
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}

	public void set(int red, int green, int blue)
	{
		set(red, green, blue, 255);
	}

	public void set(int red, int green, int blue, int alpha)
	{
		r = red / 255f;
		g = green / 255f;
		b = blue / 255f;
		a = alpha / 255f;
	}
	
	@Override
	public Color alternative()
	{
		return this;
	}
	
	public void mul(float s)
	{
		r = clamp(r * s);
		g = clamp(g * s);
		b = clamp(b * s);
	}

	public void set(Color c, float scale)
	{
		r = clamp(c.r * scale);
		g = clamp(c.g * scale);
		b = clamp(c.b * scale);
		a = clamp(c.a * scale);
	}

	private float clamp(float r)
	{
		return Numbers.clamp(r, 0, 1);
	}
	
	public Color invert() 
	{
		r = 1 - r;
		g = 1 - g;
		b = 1 - b;
		return this;
	}

	public int r() 
	{
		return Numbers.clamp( (int)(r * 255), 0, 255 );
	}
	
	public int g() 
	{
		return Numbers.clamp( (int)(g * 255), 0, 255 );
	}
	
	public int b() 
	{
		return Numbers.clamp( (int)(b * 255), 0, 255 );
	}
	
	public int a() 
	{
		return Numbers.clamp( (int)(a * 255), 0, 255 );
	}
	
	public void rgb(int rgb) 
	{
		unhash( rgb, 16, 8, 0 );
	}
	
	public void bgr(int bgr) 
	{
		unhash( bgr, 0, 8, 16 );
	}
	
	public void argb(int argb) 
	{
		unhash( argb, 16, 8, 0, 24 );
	}
	
	public void abgr(int abgr) 
	{
		unhash( abgr, 0, 8, 16, 24 );
	}
	
	public void rgba(int rgba) 
	{
		unhash( rgba, 24, 16, 8, 0 );
	}
	
	public void bgra(int bgra) 
	{
		unhash( bgra, 8, 16, 24, 0 );
	}
	
	public void unhash(int hash, int r, int g, int b, int a) 
	{
		set( (hash >> r) & 0xFF, (hash >> g) & 0xFF, (hash >> b) & 0xFF, (hash >> a) & 0xFF );
	}
	
	public void unhash(int hash, int r, int g, int b) 
	{
		set( (hash >> r) & 0xFF, (hash >> g) & 0xFF, (hash >> b) & 0xFF );
	}

	public void clamp()
	{
		r = clamp(r);
		g = clamp(g);
		b = clamp(b);
		a = clamp(a);
	}
	
	public Color lighten( float delta ) 
	{
		r += (1 - r) * delta;
		g += (1 - g) * delta;
		b += (1 - b) * delta;
		return this;
	}
	
	public Color darken( float delta ) 
	{
		r -= r * delta;
		g -= g * delta;
		b -= b * delta;
		return this;
	}

	@Override
	public Color clone() 
	{
		return new Color(r, g, b, a);
	}

	// ARGB
	public Color fromHex( String hex, int alphaIndex, int redIndex, int greenIndex, int blueIndex )
	{
		a = getComponentFromHex( hex, alphaIndex );
		r = getComponentFromHex( hex, redIndex );
		g = getComponentFromHex( hex, greenIndex );
		b = getComponentFromHex( hex, blueIndex );
		
		return this;
	}
	
	private float getComponentFromHex( String hex, int index )
	{
		return ( index == -1 ? 1.0f : Integer.valueOf( hex.substring( index << 1, ( index + 1 ) << 1 ), 16 ) / 255.0f );
	}
	
	public void scaleShade( float d ) 
	{
		r *= d;
		g *= d;
		b *= d;
	}
	
	@Override
	public Color mutable()
	{
		return this;
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
	public Color create(float r, float g, float b, float a)
	{
		return new Color( r, g, b, a );
	}
	
	@Override
	public Color get()
	{
		return this;
	}
	
	@Override
	public CalculatorColor getCalculator()
	{
		return CalculatorColor.INSTANCE;
	}
	
	@Override
	public String toString()
	{
		return String.format( "(%d, %d, %d, %d)", r(), g(), b(), a() );
	}

	@Override
	public int hashCode()
	{
		return bgra();
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == this ) 
		{
			return true;
		}
		
		if ( obj == null || obj.getClass() != getClass() ) 
		{
			return false;
		}
		
		return isEqual( (Color)obj );
	}

}