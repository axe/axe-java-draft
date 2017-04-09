package com.axe.gfx;

import com.axe.color.Color;
import com.axe.math.Scalarf;
import com.axe.math.Scalari;
import com.axe.math.Vec2f;
import com.axe.math.Vec2i;
import com.axe.math.Vec3f;
import com.axe.math.Vec3i;
import com.axe.math.Vec4f;
import com.axe.resource.Resource;

public interface Shader extends Resource
{
	
	public ShaderObject[] getObjects();

	public void bind();

	public void unbind();

	// Direct Location
	
	public void set( int location, boolean x );
	
	public void set( int location, float x );

	public void set( int location, float[] x );

	public void set( int location, Scalarf v );

	public void set( int location, float x, float y );

	public void set( int location, Vec2f v );

	public void set( int location, Vec2f[] v );

	public void set( int location, float x, float y, float z );

	public void set( int location, Vec3f v );

	public void set( int location, Vec3f[] v );

	public void set( int location, float x, float y, float z, float w );

	public void set( int location, Vec4f v );

	public void set( int location, Color color );

	public void set( int location, Vec4f[] v );
	
	public void set( int location, Color[] v );

	public void set( int location, int x );

	public void set( int location, int[] x );

	public void set( int location, Scalari v );

	public void set( int location, int x, int y );

	public void set( int location, Vec2i v );

	public void set( int location, Vec2i[] v );

	public void set( int location, int x, int y, int z );

	public void set( int location, Vec3i v );

	public void set( int location, Vec3i[] v );

	public void set( int location, int x, int y, int z, int w );

	public int getLocation( Enum<?> enumConstant );
	
	public int getLocation( String name );
	
	public int getAttributeLocation( String name );
	
	public <E extends Enum<E>> int getAttributeLocation( E enumConstant );

	
	// Enumerated Variables
	
	default public void set( Enum<?> enumConstant, boolean x )
	{
		set( getLocation( enumConstant ), x );
	}
	
	default public void set( Enum<?> enumConstant, float x )
	{
		set( getLocation( enumConstant ), x );
	}

	default public void set( Enum<?> enumConstant, float[] x )
	{
		set( getLocation( enumConstant ), x );
	}

	default public void set( Enum<?> enumConstant, Scalarf v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, float x, float y )
	{
		set( getLocation( enumConstant ), x, y );
	}

	default public void set( Enum<?> enumConstant, Vec2f v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, Vec2f[] v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, float x, float y, float z )
	{
		set( getLocation( enumConstant ), x, y, z );
	}

	default public void set( Enum<?> enumConstant, Vec3f v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, Vec3f[] v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, float x, float y, float z, float w )
	{
		set( getLocation( enumConstant ), x, y, z, w );
	}

	default public void set( Enum<?> enumConstant, Vec4f v )
	{
		set( getLocation( enumConstant ), v.x, v.y, v.z, v.w );
	}

	default public void set( Enum<?> enumConstant, Color color )
	{
		set( getLocation( enumConstant ), color );
	}

	default public void set( Enum<?> enumConstant, Vec4f[] v )
	{
		set( getLocation( enumConstant ), v );
	}
	
	default public void set( Enum<?> enumConstant, Color[] v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, int x )
	{
		set( getLocation( enumConstant ), x );
	}

	default public void set( Enum<?> enumConstant, int[] x )
	{
		set( getLocation( enumConstant ), x );
	}

	default public void set( Enum<?> enumConstant, Scalari v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, int x, int y )
	{
		set( getLocation( enumConstant ), x, y );
	}

	default public void set( Enum<?> enumConstant, Vec2i v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, Vec2i[] v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, int x, int y, int z )
	{
		set( getLocation( enumConstant ), x, y, z );
	}

	default public void set( Enum<?> enumConstant, Vec3i v )
	{
		set( getLocation( enumConstant ), v.x, v.y, v.z );
	}

	default public void set( Enum<?> enumConstant, Vec3i[] v )
	{
		set( getLocation( enumConstant ), v );
	}

	default public void set( Enum<?> enumConstant, int x, int y, int z, int w )
	{
		set( getLocation( enumConstant ), x, y, z, w );
	}
	
	// Dynamic Variables
	
	default public void set( String name, boolean x )
	{
		set( getLocation( name ), x );
	}
	
	default public void set( String name, float x )
	{
		set( getLocation( name ), x );
	}

	default public void set( String name, float[] x )
	{
		set( getLocation( name ), x );
	}

	default public void set( String name, Scalarf v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, float x, float y )
	{
		set( getLocation( name ), x, y );
	}

	default public void set( String name, Vec2f v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, Vec2f[] v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, float x, float y, float z )
	{
		set( getLocation( name ), x, y, z );
	}

	default public void set( String name, Vec3f v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, Vec3f[] v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, float x, float y, float z, float w )
	{
		set( getLocation( name ), x, y, z, w );
	}

	default public void set( String name, Vec4f v )
	{
		set( getLocation( name ), v.x, v.y, v.z, v.w );
	}

	default public void set( String name, Color color )
	{
		set( getLocation( name ), color );
	}

	default public void set( String name, Vec4f[] v )
	{
		set( getLocation( name ), v );
	}
	
	default public void set( String name, Color[] v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, int x )
	{
		set( getLocation( name ), x );
	}

	default public void set( String name, int[] x )
	{
		set( getLocation( name ), x );
	}

	default public void set( String name, Scalari v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, int x, int y )
	{
		set( getLocation( name ), x, y );
	}

	default public void set( String name, Vec2i v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, Vec2i[] v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, int x, int y, int z )
	{
		set( getLocation( name ), x, y, z );
	}

	default public void set( String name, Vec3i v )
	{
		set( getLocation( name ), v.x, v.y, v.z );
	}

	default public void set( String name, Vec3i[] v )
	{
		set( getLocation( name ), v );
	}

	default public void set( String name, int x, int y, int z, int w )
	{
		set( getLocation( name ), x, y, z, w );
	}
	
}
