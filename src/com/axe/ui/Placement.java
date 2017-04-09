package com.axe.ui;

import com.axe.math.Bound2f;
import com.axe.math.Bound2i;

public class Placement 
{
	public Anchor left = new Anchor();
	public Anchor right = new Anchor();
	public Anchor top = new Anchor();
	public Anchor bottom = new Anchor();

	public Placement() 
	{
		maximize();
	}
	
	public void maximize() 
	{
		relative(0, 0, 1, 1);
	}

	public void center(float width, float height) 
	{
		attach(0.5f, 0.5f, width, height);
	}

	public void attach(float dx, float dy, float width, float height) 
	{
		left.set(width * -dx, dx);
		right.set(width * (1 - dx), dx);
		bottom.set(height * -dy, dy);
		top.set(height * (1 - dy), dy);
	}

	public void relative(float leftAnchor, float topAnchor, float rightAnchor, float bottomAnchor) 
	{
		left.set(0, leftAnchor);
		top.set(0, topAnchor);
		right.set(0, rightAnchor);
		bottom.set(0, bottomAnchor);
	}

	public Bound2f getBounds(float parentWidth, float parentHeight, Bound2f out) 
	{
		out.l = left.get(parentWidth);
		out.t = top.get(parentHeight);
		out.r = right.get(parentWidth);
		out.b = bottom.get(parentHeight);
		return out;
	}

	public Bound2i getBounds(float parentWidth, float parentHeight, Bound2i out) 
	{
		out.l = (int)left.get(parentWidth);
		out.t = (int)top.get(parentHeight);
		out.r = (int)right.get(parentWidth);
		out.b = (int)bottom.get(parentHeight);
		return out;
	}
	
	public boolean contains(float x, float y, float parentWidth, float parentHeight)
	{
		return !(
			x < left.get( parentWidth ) ||
			y > top.get( parentHeight ) ||
			x > right.get( parentWidth ) || 
			y < bottom.get( parentHeight )
		);
	}

	public float getWidth(float parentWidth)
	{
		return getWidth(parentWidth, 0);
	}
	
	public float getWidth(float parentWidth, float minWidth)
	{
		return Math.max(minWidth, right.get(parentWidth) - left.get(parentWidth));
	}
	
	public float getHeight(float parentHeight)
	{
		return getHeight(parentHeight, 0);
	}
	
	public float getHeight(float parentHeight, float minHeight)
	{
		return Math.max(minHeight, top.get(parentHeight) - bottom.get(parentHeight));
	}
	

	public int getWidth(int parentWidth)
	{
		return getWidth(parentWidth, 0);
	}
	
	public int getWidth(int parentWidth, int minWidth)
	{
		return (int)Math.max(minWidth, right.get(parentWidth) - left.get(parentWidth));
	}
	
	public int getHeight(int parentHeight)
	{
		return getHeight(parentHeight, 0);
	}
	
	public int getHeight(int parentHeight,int minHeight)
	{
		return (int)Math.max(minHeight, top.get(parentHeight) - bottom.get(parentHeight));
	}
	
	public float getLeft(float parentWidth)
	{
		return left.get(parentWidth);
	}
	
	public int getLeft(int parentWidth)
	{
		return (int)left.get(parentWidth);
	}
	
	public float getTop(float parentHeight)
	{
		return top.get(parentHeight);
	}
	
	public int getTop(int parentHeight)
	{
		return (int)top.get(parentHeight);
	}
	
	public float getRight(float parentWidth)
	{
		return right.get(parentWidth);
	}
	
	public int getRight(int parentWidth)
	{
		return (int)right.get(parentWidth);
	}
	
	public float getBottom(float parentHeight)
	{
		return bottom.get(parentHeight);
	}
	
	public int getBottom(int parentHeight)
	{
		return (int)bottom.get(parentHeight);
	}
	
	public static Placement maximized()
	{
		Placement p = new Placement();
		p.maximize();
		return p;
	}
	
	public static Placement centered(float width, float height)
	{
		Placement p = new Placement();
		p.center(width, height);
		return p;
	}
	
	public static Placement attached(float dx, float dy, float width, float height)
	{
		Placement p = new Placement();
		p.attach(dx, dy, width, height);
		return p;
	}
	
}