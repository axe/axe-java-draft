package com.axe.gfx;

import com.axe.math.Bound2i;
import com.axe.resource.Resource;
import com.axe.tile.Tile;

public interface Texture extends Resource
{
	
	public TextureInfo getInfo();

	public void bind();

	public void unbind();
	
	default public float s(float x)
	{
		return x * getPixelWidth() + getPixelOffsetX();
	}
	
	default public float t(float y)
	{
		return y * getPixelHeight() + getPixelOffsetY();
	}
	
	default public Coord coord(float x, float y)
	{
		return new Coord( 
			x * getPixelWidth() + getPixelOffsetX(), 
			y * getPixelHeight() + getPixelOffsetY()
		);
	}
	
	default public Tile tileExact(float x, float y, float width, float height)
	{
		return tileExact( x, y, width, height, new Tile() );
	}
	
	default public Tile tileExact(float x, float y, float width, float height, Tile out)
	{
		float pixelW = getPixelWidth();
		float pixelH = getPixelHeight();
		
		float left = x * pixelW;
		float top = y * pixelH;
		float right = (x + width) * pixelW;
		float bottom = (y + height) * pixelH;
		
		return out.set(this, left, right, top, bottom);
	}
	
	default public Tile tile(float x, float y, float width, float height)
	{
		return tile( x, y, width, height, new Tile() );
	}
	
	default public Tile tile(float x, float y, float width, float height, Tile out)
	{
		float pixelW = getPixelWidth();
		float pixelH = getPixelHeight();
		float offW = getPixelOffsetX();
		float offH = getPixelOffsetY();
		
		float left = x * pixelW + offW;
		float top = y * pixelH + offH;
		float right = (x + width) * pixelW - offW;
		float bottom = (y + height) * pixelH - offH;
		
		return out.set( this, left, right, top, bottom );
	}
	
	default public Tile tile()
	{
		return tile(0, 0, getImageWidth(), getImageHeight() );
	}
	
	default public Tile tile(Tile out)
	{
		return tile(0, 0, getImageWidth(), getImageHeight(), out );
	}
	
	default public Tile tileExact()
	{
		return tileExact( 0, 0, getImageWidth(), getImageHeight() );
	}
	
	default public Tile tileExact(Tile out)
	{
		return tileExact( 0, 0, getImageWidth(), getImageHeight(), out );
	}
	
	default public void unpad(Tile tile)
	{
		float offW = getPixelOffsetX();
		float offH = getPixelOffsetY();
		
		tile.s0 -= offW;
		tile.s1 += offW;
		tile.t0 -= offH;
		tile.t1 += offH;
	}
	
	default public void unpad(Tile[] tiles)
	{
		for (int i = 0; i < tiles.length; i++)
		{
			unpad( tiles[i] );
		}
	}
	
	default public void pad(Tile tile)
	{
		float offW = getPixelOffsetX();
		float offH = getPixelOffsetY();
		
		tile.s0 += offW;
		tile.s1 -= offW;
		tile.t0 += offH;
		tile.t1 -= offH;
	}
	
	default public void pad(Tile[] tiles)
	{
		for (int i = 0; i < tiles.length; i++)
		{
			pad( tiles[i] );
		}
	}
	
	default public Bound2i bound(Tile tile)
	{
		return bound( tile, new Bound2i() );
	}
	
	default public Bound2i bound(Tile tile, Bound2i out)
	{
		float offW = getPixelOffsetX();
		float offH = getPixelOffsetY();
		int w = width();
		int h = height();
		
		out.l = (int)((tile.s0 - offW) * w);
		out.t = (int)((tile.t0 - offH) * h);
		out.r = (int)((tile.s1 + offW) * w);
		out.b = (int)((tile.t1 + offH) * h);
		
		return out;
	}
	
	public int width();
	public int height();
	
	public int getImageHeight();
	public int getImageWidth();
	
	public boolean hasAlpha();
	
	public float getPixelWidth();
	public float getPixelOffsetX();
	public float getPixelHeight();
	public float getPixelOffsetY();
	
	public Magnify magnify();
	public void magnify(Magnify mag);
	
	public Minify minify();
	public void minify(Minify min);
	
	public Wrap wrap();
	public void wrap(Wrap wrap);
	
	public int anisotrophy();
	public void anisotrophy(int anisotrophy);
	
}
