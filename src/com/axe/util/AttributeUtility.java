package com.axe.util;

import com.axe.core.Attribute;
import com.axe.math.Numbers;
import com.axe.math.Vec2f;
import com.axe.math.Vec3f;


public class AttributeUtility
{
	
	public static <A extends Attribute<A>> A bilinear( Vec2f delta, A tl, A tr, A br, A bl, A temp, A out )
	{
		return bilinear( delta.x, delta.y, tl, tr, br, bl, temp, out );
	}
	
	public static <A extends Attribute<A>> A bilinear( Vec2f delta, A[] corners, A temp, A out )
	{
		return bilinear( delta.x, delta.y, corners[0], corners[1], corners[2], corners[3], temp, out );
	}
	
	public static <A extends Attribute<A>> A bilinear( float x, float y, A[] corners, A temp, A out )
	{
		return bilinear( x, y, corners[0], corners[1], corners[2], corners[3], temp, out );
	}
	
	public static <A extends Attribute<A>> A bilinear( float x, float y, A tl, A tr, A br, A bl, A temp, A out )
	{
		temp.interpolate( tl, tr, x );
		out.interpolate( bl, br, x );
		out.interpolate( temp, out, y );

		return out;
	}
	
	public static <A extends Attribute<A>> A trilinear( Vec3f delta, A ftl, A ftr, A fbl, A fbr, A btl, A btr, A bbl, A bbr, A temp0, A temp1, A temp2, A out )
	{
		return trilinear( delta.x, delta.y, delta.z, ftl, ftr, fbl, fbr, btl, btr, bbl, bbr, temp0, temp1, temp2, out );
	}
	
	public static <A extends Attribute<A>> A trilinear( Vec3f delta, A[] corners, A[] temp3, A out )
	{
		return trilinear( delta.x, delta.y, delta.z, corners[0], corners[1], corners[2], corners[3], corners[4], corners[5], corners[6], corners[7], temp3[0], temp3[1], temp3[2], out );
	}
	
	public static <A extends Attribute<A>> A trilinear( float x, float y, float z, A[] corners, A[] temp3, A out )
	{
		return trilinear( x, y, z, corners[0], corners[1], corners[2], corners[3], corners[4], corners[5], corners[6], corners[7], temp3[0], temp3[1], temp3[2], out );
	}
	
	public static <A extends Attribute<A>> A trilinear( float x, float y, float z, A ftl, A ftr, A fbl, A fbr, A btl, A btr, A bbl, A bbr, A temp0, A temp1, A temp2, A out )
	{
		temp0.interpolate( fbl, bbl, z );
		temp1.interpolate( fbr, bbr, z );
		temp2.interpolate( ftl, btl, z );
		out.interpolate( ftr, btr, z );
		temp0.interpolate( temp0, temp1, x );
		temp2.interpolate( temp2, out, x );
		out.interpolate( temp0, temp2, y );

		return out;
	}
	
	public static <T extends Attribute<T>> float getDistanceFromLine(T point, T start, T end)
	{
		float lineLength = start.distance( end );
		float startToPoint = point.distance( start );
		float endToPoint = point.distance( end );

		return getTriangleHeight( lineLength, startToPoint, endToPoint );
	}
	
	public static <T, V extends Attribute<T>> float getDistanceFromLine(T point, T start, T end, V temp)
	{
		temp.set( start );
		float lineLength = temp.distance( end );
		float startToPoint = temp.distance( point );
		temp.set( end );
		float endToPoint = temp.distance( point );
		
		return getTriangleHeight( lineLength, startToPoint, endToPoint );
	}
	
	public static float getTriangleHeight(float base, float side1, float side2)
	{
		float p = (base + side1 + side2) * 0.5f;
		float area = Numbers.sqrt( p * (p - base) * (p - side1) * (p - side2) );
		float height = area * 2.0f / base;
		
		return height;
	}
	
	public static <T> void euler(Attribute<T> position, Attribute<T> velocity, T acceleration, float time) 
	{
		velocity.adds( acceleration, time );
		position.adds( velocity.get(), time );
	}
	
}
