package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Rangef;

public final class CalculatorRangef extends AbstractCalculator<Rangef> 
{
	
	public static final CalculatorRangef INSTANCE = new CalculatorRangef();
	
	@Override
	public Rangef copy(Rangef out, Rangef source) 
	{
		out.min = source.min;
		out.max = source.max;
		return out;
	}

	@Override
	public Rangef parse(Object input, Rangef defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Rangef( floats[0], floats[1] ); 
		}
		
		if (input instanceof Rangef)
		{
			return (Rangef)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Rangef instantiate()
	{
		return new Rangef();
	}

	@Override
	public Rangef clear(Rangef out, float component) 
	{
		out.min = out.max = component;
		return out;
	}

	@Override
	public Rangef unary(Rangef out, Rangef value, Unary unaryOperation) 
	{
		out.min = unaryOperation.unary( value.min );
		out.max = unaryOperation.unary( value.max );
		return out;
	}

	@Override
	public Rangef binary(Rangef out, Rangef a, Rangef b, Binary binaryOperation) 
	{
		out.min = binaryOperation.binary( a.min, b.min );
		out.max = binaryOperation.binary( a.max, b.max );
		return out;
	}

	@Override
	public Rangef adds(Rangef out, Rangef augend, Rangef addend, float scale) 
	{	
		out.min = augend.min + addend.min * scale;
		out.max = augend.max + addend.max * scale;
		return out;
	}
	
	@Override
	public Rangef mul( Rangef out, Rangef value, Rangef scale )
	{
		out.min = value.min * scale.min;
		out.max = value.max * scale.max;
		return out;
	}
	
	@Override
	public Rangef div( Rangef out, Rangef dividend, Rangef divisor )
	{
		out.min = Numbers.divide( dividend.min, divisor.min );
		out.max = Numbers.divide( dividend.max, divisor.max );
		return out;
	}

	@Override
	public Rangef interpolate( Rangef out, Rangef start, Rangef end, float delta )
	{
		out.min = (end.min - start.min) * delta + start.min;
		out.max = (end.max - start.max) * delta + start.max;
		return out;
	}
	
	@Override
	public Rangef contain( Rangef out, Rangef min, Rangef max )
	{
		out.min = Numbers.clamp( out.min, min.min, max.min );
		out.max = Numbers.clamp( out.max, min.max, max.max );
		return out;
	}
    
	@Override
    public boolean contains( Rangef point, Rangef min, Rangef max )
    {
		return point.min >= min.min && point.min <= max.min && 
			   point.max >= min.max && point.max <= max.max;
    }

    @Override
    public Rangef random( Rangef out, Rangef min, Rangef max, Binary randomizer )
    {
    	out.min = randomizer.binary( min.min, max.min );
    	out.max = randomizer.binary( min.max, max.max );
    	return out;
    }
    
    @Override
    public float dot( Rangef a, Rangef b )
    {
    	return a.min * b.min + a.max * b.max;
    }
    
    @Override
    public float distanceSq( Rangef a, Rangef b )
    {
    	float d = this.distance( a, b );
    	
    	return d * d; 
    }
    
    @Override
    public float distance( Rangef a, Rangef b )
    {
    	return Math.abs(a.min - b.min) + Math.abs(a.max - b.max);
    }

    @Override
    public float lengthSq( Rangef value ) 
    {
    	return value.min * value.min + value.max * value.max;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Rangef;
	}

	@Override
	public boolean isFinite( Rangef value )
	{
		return Float.isFinite( value.min ) && 
			   Float.isFinite( value.max );
	}
	
	@Override
	public boolean isEqual( Rangef a, Rangef b, float epsilon )
	{
		return Numbers.equals( a.min, b.min, epsilon ) &&
			   Numbers.equals( a.max, b.max, epsilon );
	}
	
	@Override
	public int sizeof( Rangef value )
	{
		return 8;
	}

	@Override
	public int write( Rangef value, ByteBuffer to )
	{
		to.putFloat( value.min );
		to.putFloat( value.max );
		return 8;
	}

	@Override
	public Rangef read( Rangef out, ByteBuffer from )
	{
		out.min = from.getFloat();
		out.max = from.getFloat();
		return out;
	}
	
	@Override
	public Rangef read( Rangef out, InputModel input )
	{
		out.min = input.readFloat( "min" );
		out.max = input.readFloat( "max" );
		return out;
	}
	
	@Override
	public void write( Rangef value, OutputModel output )
	{
		output.write( "min", value.min );
		output.write( "max", value.max );
	}

	@Override
	public int getComponents()
	{
		return 2;
	}

	@Override
	public float getComponent( Rangef value, int index )
	{
		switch (index) {
		case 0: return value.min;
		case 1: return value.max;
		}
		
		return 0;
	}

	@Override
	public Rangef setComponent( Rangef value, int index, float component )
	{
		switch (index) {
		case 0:
			value.min = component;
			break;
		case 1:
			value.max = component;
			break;
		}
		return value;
	}

	@Override
	public Rangef mods(Rangef out, Rangef value, float divisor) 
	{
		out.min = value.min % divisor;
		out.max = value.max % divisor;
		return out;
	}

	@Override
	public Rangef mod(Rangef out, Rangef value, Rangef divisor) 
	{
		out.min = value.min % divisor.min;
		out.max = value.max % divisor.max;
		return out;
	}

	@Override
	public Rangef truncates(Rangef out, Rangef value, float divisor) 
	{
		out.min = value.min - value.min % divisor;
		out.max = value.max - value.max % divisor;
		return out;
	}

	@Override
	public Rangef truncate(Rangef out, Rangef value, Rangef divisor) 
	{
		out.min = value.min - value.min % divisor.min;
		out.max = value.max - value.max % divisor.max;
		return out;
	}

	@Override
	public Rangef clamp(Rangef out, Rangef min, Rangef max) 
	{
		out.min = Numbers.clamp( out.min, min.min, max.min );
		out.max = Numbers.clamp( out.max, min.max, max.max );
		return out;
	}
	
}
