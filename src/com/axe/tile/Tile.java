package com.axe.tile;

import com.axe.core.Attribute;
import com.axe.gfx.Coord;
import com.axe.gfx.Texture;
import com.axe.math.Bound2i;
import com.axe.math.Vec2f;
import com.axe.math.Vec2i;
import com.axe.math.calc.CalculatorTile;

public class Tile implements Attribute<Tile>
{
	
	public Texture texture;
	public float s0, s1, t0, t1;

	public Tile() 
	{
	}

	public Tile(Texture texture, float s0, float t0, float s1, float t1)
	{
		this.set( texture, s0, s1, t0, t1 );
	}

	public Tile(float s0, float t0, float s1, float t1)
	{
		this.set( null, s0, s1, t0, t1 );
	}
		
	public void set(float s0, float s1, float t0, float t1)
	{
		this.s0 = s0;
		this.s1 = s1;
		this.t0 = t0;
		this.t1 = t1;
	}
	
	public Tile set(Texture texture, float s0, float s1, float t0, float t1)
	{
		this.texture = texture;
		this.set( s0, s1, t0, t1 );
		
		return this;
	}
	
	public void bind()
	{
		texture.bind();
	}

	public void shift(Coord src)
	{
		shift(src.s, src.t);
	}

	public void shift(float s, float t)
	{
		s0 += s;
		t0 += t;
		s1 += s;
		t1 += t;
	}

	public void get(Coord[] srcs)
	{
		srcs[0].set(s0, t0);
		srcs[1].set(s0, t1);
		srcs[2].set(s1, t1);
		srcs[3].set(s1, t0);
	}

	public float cs()
	{
		return (s0 + s1) * 0.5f;
	}

	public float ct()
	{
		return (t0 + t1) * 0.5f;
	}

	public float ds(float d)
	{
		return (s1 - s0) * d + s0;
	}

	public float dt(float d)
	{
		return (t1 - t0) * d + t0;
	}
	
	public float sspan()
	{
		return (s1 - s0);
	}
	
	public float tspan()
	{
		return (t1 - t0);
	}

	public Vec2f getSizef()
	{
		return getSizef( new Vec2f() );
	}
	
	public Vec2f getSizef( Vec2f size )
	{
		size.x = getWidthf();
		size.y = getHeightf();
		return size;
	}
	
	public float getWidthf()
	{
		return (texture.width() + 1) * (s1 - s0);
	}
	
	public float getHeightf()
	{
		return (texture.height() + 1) * (t1 - t0);
	}

	public Vec2i getSizei()
	{
		return getSizei( new Vec2i() );
	}
	
	public Vec2i getSizei( Vec2i size )
	{
		size.x = (int)getWidthf();
		size.y = (int)getHeightf();
		return size;
	}
	
	public int getWidthi()
	{
		return (int)getWidthf();
	}
	
	public int getHeighti()
	{
		return (int)getHeightf();
	}

	public Bound2i getBounds()
	{
		return texture.bound(this);
	}

	public void flips()
	{
		float s = s0; s0 = s1; s1 = s;
	}
	
	public void flipt()
	{
		float t = t0; t0 = t1; t1 = t;
	}
	
	@Override
	public Tile get()
	{
		return this;
	}

	@Override
	public Tile clone() 
	{
		return new Tile(texture, s0, t0, s1, t1);
	}
	
	@Override
	public CalculatorTile getCalculator()
	{
		return CalculatorTile.INSTANCE;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(s0 + t0 * 73 + s1 * 5404 + t1 * 397336 + (texture != null ? texture.hashCode() : 0) * 29210829);
	}

}
