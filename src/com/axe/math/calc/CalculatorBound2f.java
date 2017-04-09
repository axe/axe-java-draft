package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Bound2f;
import com.axe.math.Numbers;

public final class CalculatorBound2f extends AbstractCalculator<Bound2f> 
{
	
	public static final CalculatorBound2f INSTANCE = new CalculatorBound2f();
	
	@Override
	public Bound2f copy(Bound2f out, Bound2f source) 
	{
		out.l = source.l;
		out.t = source.t;
		out.r = source.r;
		out.b = source.b;
		return out;
	}

	@Override
	public Bound2f parse(Object input, Bound2f defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Bound2f( floats[0], floats[1], floats[2], floats[3] ); 
		}
		
		if (input instanceof Bound2f)
		{
			return (Bound2f)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Bound2f instantiate()
	{
		return new Bound2f();
	}

	@Override
	public Bound2f clear(Bound2f out, float component) 
	{
		out.l = out.t = out.r = out.b = component;
		return out;
	}

	@Override
	public Bound2f unary(Bound2f out, Bound2f value, Unary unaryOperation) 
	{
		out.l = unaryOperation.unary( value.l );
		out.t = unaryOperation.unary( value.t );
		out.r = unaryOperation.unary( value.r );
		out.b = unaryOperation.unary( value.b );
		return out;
	}

	@Override
	public Bound2f binary(Bound2f out, Bound2f a, Bound2f b, Binary binaryOperation) 
	{
		out.l = binaryOperation.binary( a.l, b.l );
		out.t = binaryOperation.binary( a.t, b.t );
		out.r = binaryOperation.binary( a.r, b.r );
		out.b = binaryOperation.binary( a.r, b.b );
		return out;
	}

	@Override
	public Bound2f adds(Bound2f out, Bound2f augend, Bound2f addend, float scale) 
	{	
		out.l = augend.l + addend.l * scale;
		out.t = augend.t + addend.t * scale;
		out.r = augend.r + addend.r * scale;
		out.b = augend.b + addend.b * scale;
		return out;
	}
	
	@Override
	public Bound2f mul( Bound2f out, Bound2f value, Bound2f scale )
	{
		out.l = value.l * scale.l;
		out.t = value.t * scale.t;
		out.r = value.r * scale.r;
		out.b = value.b * scale.b;
		return out;
	}
	
	@Override
	public Bound2f div( Bound2f out, Bound2f dividend, Bound2f divisor )
	{
		out.l = Numbers.divide( dividend.l, divisor.l );
		out.t = Numbers.divide( dividend.t, divisor.t );
		out.r = Numbers.divide( dividend.r, divisor.r );
		out.b = Numbers.divide( dividend.b, divisor.b );
		return out;
	}

	@Override
	public Bound2f interpolate( Bound2f out, Bound2f start, Bound2f end, float delta )
	{
		out.l = (end.l - start.l) * delta + start.l;
		out.t = (end.t - start.t) * delta + start.t;
		out.r = (end.r - start.r) * delta + start.r;
		out.b = (end.b - start.b) * delta + start.b;
		return out;
	}
	
	@Override
	public Bound2f contain( Bound2f out, Bound2f min, Bound2f max )
	{
		out.l = Numbers.clamp( out.l, min.l, max.l );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		return out;
	}
    
	@Override
    public boolean contains( Bound2f point, Bound2f min, Bound2f max )
    {
		return point.l >= min.l && point.l <= max.l && 
			   point.t >= min.t && point.t <= max.t && 
			   point.r >= min.r && point.r <= max.r && 
			   point.b >= min.b && point.b <= max.b;
    }

    @Override
    public Bound2f random( Bound2f out, Bound2f min, Bound2f max, Binary randomizer )
    {
    	out.l = randomizer.binary( min.l, max.l );
    	out.t = randomizer.binary( min.t, max.t );
    	out.r = randomizer.binary( min.r, max.r );
    	out.b = randomizer.binary( min.b, max.b );
    	return out;
    }
    
    @Override
    public float dot( Bound2f a, Bound2f b )
    {
    	return a.l * b.l + a.t * b.t + a.r * b.r + a.b * b.b;
    }
    
    @Override
    public float distanceSq( Bound2f a, Bound2f b )
    {
    	float dr = a.l - b.l;
    	float dg = a.t - b.t;
    	float db = a.r - b.r;
    	float da = a.b - b.b;
    	
    	return dr * dr + dg * dg + db * db + da * da; 
    }

    @Override
    public float lengthSq( Bound2f value ) 
    {
    	return value.l * value.l + value.t * value.t + value.r * value.r + value.b * value.b;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Bound2f;
	}

	@Override
	public boolean isFinite( Bound2f value )
	{
		return Float.isFinite( value.l ) && 
			   Float.isFinite( value.t ) && 
			   Float.isFinite( value.r ) && 
			   Float.isFinite( value.b );
	}
	
	@Override
	public boolean isEqual( Bound2f a, Bound2f b, float epsilon )
	{
		return Numbers.equals( a.l, b.l, epsilon ) &&
			   Numbers.equals( a.t, b.t, epsilon ) &&
			   Numbers.equals( a.r, b.r, epsilon ) &&
			   Numbers.equals( a.b, b.b, epsilon );
	}
	
	@Override
	public int sizeof( Bound2f value )
	{
		return 16;
	}

	@Override
	public int write( Bound2f value, ByteBuffer to )
	{
		to.putFloat( value.l );
		to.putFloat( value.t );
		to.putFloat( value.r );
		to.putFloat( value.b );
		return 16;
	}

	@Override
	public Bound2f read( Bound2f out, ByteBuffer from )
	{
		out.l = from.getFloat();
		out.t = from.getFloat();
		out.r = from.getFloat();
		out.b = from.getFloat();
		return out;
	}
	
	@Override
	public Bound2f read( Bound2f out, InputModel input )
	{
		out.l = input.readFloat( "l" );
		out.t = input.readFloat( "t" );
		out.r = input.readFloat( "r" );
		out.b = input.readFloat( "b" );
		return out;
	}
	
	@Override
	public void write( Bound2f value, OutputModel output )
	{
		output.write( "l", value.l );
		output.write( "t", value.t );
		output.write( "r", value.r );
		output.write( "b", value.b );
	}

	@Override
	public int getComponents()
	{
		return 4;
	}

	@Override
	public float getComponent( Bound2f value, int index )
	{
		switch (index) {
		case 0: return value.l;
		case 1: return value.t;
		case 2: return value.r;
		case 3: return value.b;
		}
		
		return 0;
	}

	@Override
	public Bound2f setComponent( Bound2f value, int index, float component )
	{
		switch (index) {
		case 0:
			value.l = component;
			break;
		case 1:
			value.t = component;
			break;
		case 2:
			value.r = component;
			break;
		case 3:
			value.b = component;
			break;
		}
		return value;
	}

	@Override
	public Bound2f mods(Bound2f out, Bound2f value, float divisor) 
	{
		out.l = value.l % divisor;
		out.t = value.t % divisor;
		out.r = value.r % divisor;
		out.b = value.b % divisor;
		return out;
	}

	@Override
	public Bound2f mod(Bound2f out, Bound2f value, Bound2f divisor) 
	{
		out.l = value.l % divisor.l;
		out.t = value.t % divisor.t;
		out.r = value.r % divisor.r;
		out.b = value.b % divisor.b;
		return out;
	}

	@Override
	public Bound2f truncates(Bound2f out, Bound2f value, float divisor) 
	{
		out.l = value.l - value.l % divisor;
		out.t = value.t - value.t % divisor;
		out.r = value.r - value.r % divisor;
		out.b = value.b - value.b % divisor;
		return out;
	}

	@Override
	public Bound2f truncate(Bound2f out, Bound2f value, Bound2f divisor) 
	{
		out.l = value.l - value.l % divisor.l;
		out.t = value.t - value.t % divisor.t;
		out.r = value.r - value.r % divisor.r;
		out.b = value.b - value.b % divisor.b;
		return out;
	}

	@Override
	public Bound2f clamp(Bound2f out, Bound2f min, Bound2f max) 
	{
		out.l = Numbers.clamp( out.l, min.l, max.l );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		return out;
	}
	
}
