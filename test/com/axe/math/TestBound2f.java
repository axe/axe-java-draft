package com.axe.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBound2f 
{

	@Test
	public void include()
	{
		Bound2f b = new Bound2f(1, 2);

		assertEquals( 1, b.l, 0 );
		assertEquals( 1, b.r, 0 );
		assertEquals( 2, b.t, 0 );
		assertEquals( 2, b.b, 0 );
		
		b.include( 0.5f, 3 );

		assertEquals( 0.5, b.l, 0 );
		assertEquals( 1, b.r, 0 );
		assertEquals( 3, b.t, 0 );
		assertEquals( 2, b.b, 0 );
		
		b.include( 2, -1 );
		
		assertEquals( b.l, 0.5, 0 );
		assertEquals( b.r, 2, 0 );
		assertEquals( b.t, 3, 0 );
		assertEquals( b.b, -1, 0 );
	}
	
	@Test
	public void setMinMax()
	{
		Bound2f b = new Bound2f();
		
		Vec2f min = new Vec2f( 0.5f, -1 );
		Vec2f max = new Vec2f( 3, 2 );
		
		b.set( min, max );
		
		assertEquals( b.l, 0.5f, 0 );
		assertEquals( b.r, 3, 0 );
		assertEquals( b.t, 2, 0 );
		assertEquals( b.b, -1, 0 );
	}
	
	@Test
	public void setWidthFromLeft()
	{
		Bound2f b = new Bound2f(1, 3, 2, 2);
		
		b.setWidthFromLeft(4);
		
		assertEquals( b.l, 1, 0 );
		assertEquals( b.r, 5, 0 );
		assertEquals( b.t, 3, 0 );
		assertEquals( b.b, 2, 0 );
	}
	
	@Test
	public void setWidthFromRight()
	{
		Bound2f b = new Bound2f(1, 3, 2, 2);
		
		b.setWidthFromRight(4);
		
		assertEquals( b.l, -2, 0 );
		assertEquals( b.r, 2, 0 );
		assertEquals( b.t, 3, 0 );
		assertEquals( b.b, 2, 0 );
	}

	@Test
	public void setHeightFromTop()
	{
		Bound2f b = new Bound2f(1, 3, 2, 2);
		
		b.setHeightFromTop(4);
		
		assertEquals( b.l, 1, 0 );
		assertEquals( b.r, 2, 0 );
		assertEquals( b.t, 3, 0 );
		assertEquals( b.b, -1, 0 );
	}
	
	@Test
	public void setHeightFromBottom()
	{
		Bound2f b = new Bound2f(1, 3, 2, 2);
		
		b.setHeightFromBottom(4);
		
		assertEquals( b.l, 1, 0 );
		assertEquals( b.r, 2, 0 );
		assertEquals( b.t, 6, 0 );
		assertEquals( b.b, 2, 0 );
	}
	
	@Test
	public void expand()
	{
		Bound2f b = new Bound2f(1, 3, 2, 2);
		
		b.expand(1, 2);
		
		assertEquals( b.l, 0, 0 );
		assertEquals( b.r, 3, 0 );
		assertEquals( b.t, 5, 0 );
		assertEquals( b.b, 0, 0 );
	}
	
	@Test
	public void isNegative()
	{
		Bound2f b = new Bound2f(0, 0, 0, 0);
		
		assertFalse( b.isNegative() );
		
		b.l = 1;
		
		assertTrue( b.isNegative() );
		
		b.l = 0;
		
		assertFalse( b.isNegative() );
		
		b.b = 1;
		
		assertTrue( b.isNegative() );
	}
	
	@Test
	public void x()
	{
		Bound2f b = new Bound2f(1, 4, 4, 2);
		
		assertEquals( 1, b.x( 0 ), 0 );
		assertEquals( 2.5f, b.x( 0.5f ), 0 );
		assertEquals( 4, b.x( 1 ), 0 );
	}
	
	@Test
	public void y()
	{
		Bound2f b = new Bound2f(1, 4, 4, 2);
		
		assertEquals( 4, b.y( 0 ), 0 );
		assertEquals( 3, b.y( 0.5f ), 0 );
		assertEquals( 2, b.y( 1 ), 0 );
	}
	
}
