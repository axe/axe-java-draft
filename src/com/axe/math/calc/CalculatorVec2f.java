package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Vec2f;

public final class CalculatorVec2f extends AbstractCalculator<Vec2f> 
{
	
	public static final CalculatorVec2f INSTANCE = new CalculatorVec2f();
	
	@Override
	public Vec2f copy(Vec2f out, Vec2f source) 
	{
		out.x = source.x;
		out.y = source.y;
		return out;
	}

	@Override
	public Vec2f parse(Object input, Vec2f defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Vec2f( floats[0], floats[1] ); 
		}
		
		if (input instanceof Vec2f)
		{
			return (Vec2f)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Vec2f instantiate()
	{
		return new Vec2f();
	}

	@Override
	public Vec2f clear(Vec2f out, float component) 
	{
		out.x = out.y = component;
		return out;
	}

	@Override
	public Vec2f unary(Vec2f out, Vec2f value, Unary unaryOperation) 
	{
		out.x = unaryOperation.unary( value.x );
		out.y = unaryOperation.unary( value.y );
		return out;
	}

	@Override
	public Vec2f binary(Vec2f out, Vec2f a, Vec2f b, Binary binaryOperation) 
	{
		out.x = binaryOperation.binary( a.x, b.x );
		out.y = binaryOperation.binary( a.y, b.y );
		return out;
	}

	@Override
	public Vec2f adds(Vec2f out, Vec2f augend, Vec2f addend, float scale) 
	{	
		out.x = augend.x + addend.x * scale;
		out.y = augend.y + addend.y * scale;
		return out;
	}
	
	@Override
	public Vec2f mul( Vec2f out, Vec2f value, Vec2f scale )
	{
		out.x = value.x * scale.x;
		out.y = value.y * scale.y;
		return out;
	}
	
	@Override
	public Vec2f div( Vec2f out, Vec2f dividend, Vec2f divisor )
	{
		out.x = Numbers.divide( dividend.x, divisor.x );
		out.y = Numbers.divide( dividend.y, divisor.y );
		return out;
	}

	@Override
	public Vec2f interpolate( Vec2f out, Vec2f start, Vec2f end, float delta )
	{
		out.x = (end.x - start.x) * delta + start.x;
		out.y = (end.y - start.y) * delta + start.y;
		return out;
	}
	
	@Override
	public boolean isParallel( Vec2f a, Vec2f b, float epsilon )
	{
		return Math.abs( (a.y * b.x) - (a.x * b.y) ) < epsilon;
	}
	
	@Override
	public Vec2f contain( Vec2f out, Vec2f min, Vec2f max )
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		return out;
	}
    
	@Override
    public boolean contains( Vec2f point, Vec2f min, Vec2f max )
    {
		return point.x >= min.x && point.x <= max.x && 
			   point.y >= min.y && point.y <= max.y;
    }

    @Override
    public Vec2f random( Vec2f out, Vec2f min, Vec2f max, Binary randomizer )
    {
    	out.x = randomizer.binary( min.x, max.x );
    	out.y = randomizer.binary( min.y, max.y );
    	return out;
    }
    
    @Override
    public float dot( Vec2f a, Vec2f b )
    {
    	return a.x * b.x + a.y * b.y;
    }
    
    @Override
    public float distanceSq( Vec2f a, Vec2f b )
    {
    	float dx = a.x - b.x;
    	float dy = a.y - b.y;
    	
    	return dx * dx + dy * dy; 
    }

    @Override
    public float lengthSq( Vec2f value ) 
    {
    	return value.x * value.x + value.y * value.y;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Vec2f;
	}

	@Override
	public boolean isFinite( Vec2f value )
	{
		return Float.isFinite( value.x ) && 
			   Float.isFinite( value.y );
	}
	
	@Override
	public boolean isEqual( Vec2f a, Vec2f b, float epsilon )
	{
		return Numbers.equals( a.x, b.x, epsilon ) &&
			   Numbers.equals( a.y, b.y, epsilon );
	}
	
	@Override
	public int sizeof( Vec2f value )
	{
		return 8;
	}

	@Override
	public int write( Vec2f value, ByteBuffer to )
	{
		to.putFloat( value.x );
		to.putFloat( value.y );
		return 8;
	}

	@Override
	public Vec2f read( Vec2f out, ByteBuffer from )
	{
		out.x = from.getFloat();
		out.y = from.getFloat();
		return out;
	}
	
	@Override
	public Vec2f read( Vec2f out, InputModel input )
	{
		out.x = input.readFloat( "x" );
		out.y = input.readFloat( "y" );
		return out;
	}
	
	@Override
	public void write( Vec2f value, OutputModel output )
	{
		output.write( "x", value.x );
		output.write( "y", value.y );
	}

	@Override
	public int getComponents()
	{
		return 2;
	}

	@Override
	public float getComponent( Vec2f value, int index )
	{
		switch (index) {
		case 0: return value.x;
		case 1: return value.y;
		}
		
		return 0;
	}

	@Override
	public Vec2f setComponent( Vec2f value, int index, float component )
	{
		switch (index) {
		case 0:
			value.x = component;
			break;
		case 1:
			value.y = component;
			break;
		}
		return value;
	}

	@Override
	public Vec2f mods(Vec2f out, Vec2f value, float divisor) 
	{
		out.x = value.x % divisor;
		out.y = value.y % divisor;
		return out;
	}

	@Override
	public Vec2f mod(Vec2f out, Vec2f value, Vec2f divisor) 
	{
		out.x = value.x % divisor.x;
		out.y = value.y % divisor.y;
		return out;
	}

	@Override
	public Vec2f truncates(Vec2f out, Vec2f value, float divisor) 
	{
		out.x = value.x - value.x % divisor;
		out.y = value.y - value.y % divisor;
		return out;
	}

	@Override
	public Vec2f truncate(Vec2f out, Vec2f value, Vec2f divisor) 
	{
		out.x = value.x - value.x % divisor.x;
		out.y = value.y - value.y % divisor.y;
		return out;
	}

	@Override
	public Vec2f clamp(Vec2f out, Vec2f min, Vec2f max) 
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		return out;
	}
	
}
