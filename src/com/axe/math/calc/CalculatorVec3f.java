package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Vec3f;

public final class CalculatorVec3f extends AbstractCalculator<Vec3f> 
{
	
	public static final CalculatorVec3f INSTANCE = new CalculatorVec3f();
	
	@Override
	public Vec3f copy(Vec3f out, Vec3f source) 
	{
		out.x = source.x;
		out.y = source.y;
		out.z = source.z;
		return out;
	}

	@Override
	public Vec3f parse(Object input, Vec3f defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Vec3f( floats[0], floats[1], floats[2] ); 
		}
		
		if (input instanceof Vec3f)
		{
			return (Vec3f)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Vec3f instantiate()
	{
		return new Vec3f();
	}

	@Override
	public Vec3f clear(Vec3f out, float component) 
	{
		out.x = out.y = out.z = component;
		return out;
	}

	@Override
	public Vec3f unary(Vec3f out, Vec3f value, Unary unaryOperation) 
	{
		out.x = unaryOperation.unary( value.x );
		out.y = unaryOperation.unary( value.y );
		out.z = unaryOperation.unary( value.z );
		return out;
	}

	@Override
	public Vec3f binary(Vec3f out, Vec3f a, Vec3f b, Binary binaryOperation) 
	{
		out.x = binaryOperation.binary( a.x, b.x );
		out.y = binaryOperation.binary( a.y, b.y );
		out.z = binaryOperation.binary( a.z, b.z );
		return out;
	}

	@Override
	public Vec3f adds(Vec3f out, Vec3f augend, Vec3f addend, float scale) 
	{	
		out.x = augend.x + addend.x * scale;
		out.y = augend.y + addend.y * scale;
		out.z = augend.z + addend.z * scale;
		return out;
	}
	
	@Override
	public Vec3f mul( Vec3f out, Vec3f value, Vec3f scale )
	{
		out.x = value.x * scale.x;
		out.y = value.y * scale.y;
		out.z = value.z * scale.z;
		return out;
	}
	
	@Override
	public Vec3f div( Vec3f out, Vec3f dividend, Vec3f divisor )
	{
		out.x = Numbers.divide( dividend.x, divisor.x );
		out.y = Numbers.divide( dividend.y, divisor.y );
		out.z = Numbers.divide( dividend.z, divisor.z );
		return out;
	}

	@Override
	public Vec3f interpolate( Vec3f out, Vec3f start, Vec3f end, float delta )
	{
		out.x = (end.x - start.x) * delta + start.x;
		out.y = (end.y - start.y) * delta + start.y;
		out.z = (end.z - start.z) * delta + start.z;
		return out;
	}
	
	@Override
	public Vec3f contain( Vec3f out, Vec3f min, Vec3f max )
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.z = Numbers.clamp( out.z, min.z, max.z );
		return out;
	}
    
	@Override
    public boolean contains( Vec3f point, Vec3f min, Vec3f max )
    {
		return point.x >= min.x && point.x <= max.x && 
			   point.y >= min.y && point.y <= max.y && 
			   point.z >= min.z && point.z <= max.z;
    }

    @Override
    public Vec3f random( Vec3f out, Vec3f min, Vec3f max, Binary randomizer )
    {
    	out.x = randomizer.binary( min.x, max.x );
    	out.y = randomizer.binary( min.y, max.y );
    	out.z = randomizer.binary( min.z, max.z );
    	return out;
    }
    
    @Override
    public float dot( Vec3f a, Vec3f b )
    {
    	return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    
    @Override
    public float distanceSq( Vec3f a, Vec3f b )
    {
    	float dx = a.x - b.x;
    	float dy = a.y - b.y;
    	float dz = a.z - b.z;
    	
    	return dx * dx + dy * dy + dz * dz; 
    }

    @Override
    public float lengthSq( Vec3f value ) 
    {
    	return value.x * value.x + value.y * value.y + value.z * value.z;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Vec3f;
	}

	@Override
	public boolean isFinite( Vec3f value )
	{
		return Float.isFinite( value.x ) && 
			   Float.isFinite( value.y ) && 
			   Float.isFinite( value.z );
	}
	
	@Override
	public boolean isEqual( Vec3f a, Vec3f b, float epsilon )
	{
		return Numbers.equals( a.x, b.x, epsilon ) &&
			   Numbers.equals( a.y, b.y, epsilon ) &&
			   Numbers.equals( a.z, b.z, epsilon );
	}
	
	@Override
	public int sizeof( Vec3f value )
	{
		return 12;
	}

	@Override
	public int write( Vec3f value, ByteBuffer to )
	{
		to.putFloat( value.x );
		to.putFloat( value.y );
		to.putFloat( value.z );
		return 12;
	}

	@Override
	public Vec3f read( Vec3f out, ByteBuffer from )
	{
		out.x = from.getFloat();
		out.y = from.getFloat();
		out.z = from.getFloat();
		return out;
	}
	
	@Override
	public Vec3f read( Vec3f out, InputModel input )
	{
		out.x = input.readFloat( "x" );
		out.y = input.readFloat( "y" );
		out.z = input.readFloat( "z" );
		return out;
	}
	
	@Override
	public void write( Vec3f value, OutputModel output )
	{
		output.write( "x", value.x );
		output.write( "y", value.y );
		output.write( "z", value.z );
	}

	@Override
	public int getComponents()
	{
		return 3;
	}

	@Override
	public float getComponent( Vec3f value, int index )
	{
		switch (index) {
		case 0: return value.x;
		case 1: return value.y;
		case 2: return value.z;
		}
		
		return 0;
	}

	@Override
	public Vec3f setComponent( Vec3f value, int index, float component )
	{
		switch (index) {
		case 0:
			value.x = component;
			break;
		case 1:
			value.y = component;
			break;
		case 2:
			value.z = component;
			break;
		}
		return value;
	}

	@Override
	public Vec3f mods(Vec3f out, Vec3f value, float divisor) 
	{
		out.x = value.x % divisor;
		out.y = value.y % divisor;
		out.z = value.z % divisor;
		return out;
	}

	@Override
	public Vec3f mod(Vec3f out, Vec3f value, Vec3f divisor) 
	{
		out.x = value.x % divisor.x;
		out.y = value.y % divisor.y;
		out.z = value.z % divisor.z;
		return out;
	}

	@Override
	public Vec3f truncates(Vec3f out, Vec3f value, float divisor) 
	{
		out.x = value.x - value.x % divisor;
		out.y = value.y - value.y % divisor;
		out.z = value.z - value.z % divisor;
		return out;
	}

	@Override
	public Vec3f truncate(Vec3f out, Vec3f value, Vec3f divisor) 
	{
		out.x = value.x - value.x % divisor.x;
		out.y = value.y - value.y % divisor.y;
		out.z = value.z - value.z % divisor.z;
		return out;
	}

	@Override
	public Vec3f clamp(Vec3f out, Vec3f min, Vec3f max) 
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.z = Numbers.clamp( out.z, min.z, max.z );
		return out;
	}
	
}
