package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Rect2f;

public final class CalculatorRect2f extends AbstractCalculator<Rect2f> 
{
	
	public static final CalculatorRect2f INSTANCE = new CalculatorRect2f();
	
	@Override
	public Rect2f copy(Rect2f out, Rect2f source) 
	{
		out.x = source.x;
		out.y = source.y;
		out.w = source.w;
		out.h = source.h;
		return out;
	}

	@Override
	public Rect2f parse(Object input, Rect2f defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Rect2f( floats[0], floats[1], floats[2], floats[3] ); 
		}
		
		if (input instanceof Rect2f)
		{
			return (Rect2f)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Rect2f instantiate()
	{
		return new Rect2f();
	}

	@Override
	public Rect2f clear(Rect2f out, float component) 
	{
		out.x = out.y = out.w = out.h = component;
		return out;
	}

	@Override
	public Rect2f unary(Rect2f out, Rect2f value, Unary unaryOperation) 
	{
		out.x = unaryOperation.unary( value.x );
		out.y = unaryOperation.unary( value.y );
		out.w = unaryOperation.unary( value.w );
		out.h = unaryOperation.unary( value.h );
		return out;
	}

	@Override
	public Rect2f binary(Rect2f out, Rect2f a, Rect2f b, Binary binaryOperation) 
	{
		out.x = binaryOperation.binary( a.x, b.x );
		out.y = binaryOperation.binary( a.y, b.y );
		out.w = binaryOperation.binary( a.w, b.w );
		out.h = binaryOperation.binary( a.w, b.h );
		return out;
	}

	@Override
	public Rect2f adds(Rect2f out, Rect2f augend, Rect2f addend, float scale) 
	{	
		out.x = augend.x + addend.x * scale;
		out.y = augend.y + addend.y * scale;
		out.w = augend.w + addend.w * scale;
		out.h = augend.h + addend.h * scale;
		return out;
	}
	
	@Override
	public Rect2f mul( Rect2f out, Rect2f value, Rect2f scale )
	{
		out.x = value.x * scale.x;
		out.y = value.y * scale.y;
		out.w = value.w * scale.w;
		out.h = value.h * scale.h;
		return out;
	}
	
	@Override
	public Rect2f div( Rect2f out, Rect2f dividend, Rect2f divisor )
	{
		out.x = Numbers.divide( dividend.x, divisor.x );
		out.y = Numbers.divide( dividend.y, divisor.y );
		out.w = Numbers.divide( dividend.w, divisor.w );
		out.h = Numbers.divide( dividend.h, divisor.h );
		return out;
	}

	@Override
	public Rect2f interpolate( Rect2f out, Rect2f start, Rect2f end, float delta )
	{
		out.x = (end.x - start.x) * delta + start.x;
		out.y = (end.y - start.y) * delta + start.y;
		out.w = (end.w - start.w) * delta + start.w;
		out.h = (end.h - start.h) * delta + start.h;
		return out;
	}
	
	@Override
	public Rect2f contain( Rect2f out, Rect2f min, Rect2f max )
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.w = Numbers.clamp( out.w, min.w, max.w );
		out.h = Numbers.clamp( out.h, min.h, max.h );
		return out;
	}
    
	@Override
    public boolean contains( Rect2f point, Rect2f min, Rect2f max )
    {
		return point.x >= min.x && point.x <= max.x && 
			   point.y >= min.y && point.y <= max.y && 
			   point.w >= min.w && point.w <= max.w && 
			   point.h >= min.h && point.h <= max.h;
    }

    @Override
    public Rect2f random( Rect2f out, Rect2f min, Rect2f max, Binary randomizer )
    {
    	out.x = randomizer.binary( min.x, max.x );
    	out.y = randomizer.binary( min.y, max.y );
    	out.w = randomizer.binary( min.w, max.w );
    	out.h = randomizer.binary( min.h, max.h );
    	return out;
    }
    
    @Override
    public float dot( Rect2f a, Rect2f b )
    {
    	return a.x * b.x + a.y * b.y + a.w * b.w + a.h * b.h;
    }
    
    @Override
    public float distanceSq( Rect2f a, Rect2f b )
    {
    	float dr = a.x - b.x;
    	float dg = a.y - b.y;
    	float db = a.w - b.w;
    	float da = a.h - b.h;
    	
    	return dr * dr + dg * dg + db * db + da * da; 
    }

    @Override
    public float lengthSq( Rect2f value ) 
    {
    	return value.x * value.x + value.y * value.y + value.w * value.w + value.h * value.h;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Rect2f;
	}

	@Override
	public boolean isFinite( Rect2f value )
	{
		return Float.isFinite( value.x ) && 
			   Float.isFinite( value.y ) && 
			   Float.isFinite( value.w ) && 
			   Float.isFinite( value.h );
	}
	
	@Override
	public boolean isEqual( Rect2f a, Rect2f b, float epsilon )
	{
		return Numbers.equals( a.x, b.x, epsilon ) &&
			   Numbers.equals( a.y, b.y, epsilon ) &&
			   Numbers.equals( a.w, b.w, epsilon ) &&
			   Numbers.equals( a.h, b.h, epsilon );
	}
	
	@Override
	public int sizeof( Rect2f value )
	{
		return 16;
	}

	@Override
	public int write( Rect2f value, ByteBuffer to )
	{
		to.putFloat( value.x );
		to.putFloat( value.y );
		to.putFloat( value.w );
		to.putFloat( value.h );
		return 16;
	}

	@Override
	public Rect2f read( Rect2f out, ByteBuffer from )
	{
		out.x = from.getFloat();
		out.y = from.getFloat();
		out.w = from.getFloat();
		out.h = from.getFloat();
		return out;
	}
	
	@Override
	public Rect2f read( Rect2f out, InputModel input )
	{
		out.x = input.readFloat( "x" );
		out.y = input.readFloat( "y" );
		out.w = input.readFloat( "w" );
		out.h = input.readFloat( "h" );
		return out;
	}
	
	@Override
	public void write( Rect2f value, OutputModel output )
	{
		output.write( "x", value.x );
		output.write( "y", value.y );
		output.write( "w", value.w );
		output.write( "h", value.h );
	}

	@Override
	public int getComponents()
	{
		return 4;
	}

	@Override
	public float getComponent( Rect2f value, int index )
	{
		switch (index) {
		case 0: return value.x;
		case 1: return value.y;
		case 2: return value.w;
		case 3: return value.h;
		}
		
		return 0;
	}

	@Override
	public Rect2f setComponent( Rect2f value, int index, float component )
	{
		switch (index) {
		case 0:
			value.x = component;
			break;
		case 1:
			value.y = component;
			break;
		case 2:
			value.w = component;
			break;
		case 3:
			value.h = component;
			break;
		}
		return value;
	}

	@Override
	public Rect2f mods(Rect2f out, Rect2f value, float divisor) 
	{
		out.x = value.x % divisor;
		out.y = value.y % divisor;
		out.w = value.w % divisor;
		out.h = value.h % divisor;
		return out;
	}

	@Override
	public Rect2f mod(Rect2f out, Rect2f value, Rect2f divisor) 
	{
		out.x = value.x % divisor.x;
		out.y = value.y % divisor.y;
		out.w = value.w % divisor.w;
		out.h = value.h % divisor.h;
		return out;
	}

	@Override
	public Rect2f truncates(Rect2f out, Rect2f value, float divisor) 
	{
		out.x = value.x - value.x % divisor;
		out.y = value.y - value.y % divisor;
		out.w = value.w - value.w % divisor;
		out.h = value.h - value.h % divisor;
		return out;
	}

	@Override
	public Rect2f truncate(Rect2f out, Rect2f value, Rect2f divisor) 
	{
		out.x = value.x - value.x % divisor.x;
		out.y = value.y - value.y % divisor.y;
		out.w = value.w - value.w % divisor.w;
		out.h = value.h - value.h % divisor.h;
		return out;
	}

	@Override
	public Rect2f clamp(Rect2f out, Rect2f min, Rect2f max) 
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.w = Numbers.clamp( out.w, min.w, max.w );
		out.h = Numbers.clamp( out.h, min.h, max.h );
		return out;
	}
	
}
