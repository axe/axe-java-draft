package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Scalari;

public final class CalculatorScalari extends AbstractCalculator<Scalari> 
{
	
	public static final CalculatorScalari INSTANCE = new CalculatorScalari();
	
	@Override
	public Scalari copy(Scalari out, Scalari source) 
	{
		out.v = source.v;
		return out;
	}

	@Override
	public Scalari parse(Object input, Scalari defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			return createUniform( ((float[])input)[0] ); 
		}
		
		if (input instanceof Scalari)
		{
			return (Scalari)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Scalari instantiate()
	{
		return new Scalari();
	}

	@Override
	public Scalari clear(Scalari out, float component) 
	{
		out.v = (int)component; 
		return out;
	}

	@Override
	public Scalari unary(Scalari out, Scalari value, Unary unaryOperation) 
	{
		out.v = (int)unaryOperation.unary( value.v );
		return out;
	}

	@Override
	public Scalari binary(Scalari out, Scalari a, Scalari b, Binary binaryOperation) 
	{
		out.v = (int)binaryOperation.binary( a.v, b.v );
		return out;
	}

	@Override
	public Scalari adds(Scalari out, Scalari augend, Scalari addend, float scale) 
	{	
		out.v = (int)(augend.v + addend.v * scale);
		return out;
	}
	
	@Override
	public Scalari mul( Scalari out, Scalari value, Scalari scale )
	{
		out.v = value.v * scale.v;
		return out;
	}
	
	@Override
	public Scalari div( Scalari out, Scalari dividend, Scalari divisor )
	{
		out.v = Numbers.divide( dividend.v, divisor.v );
		return out;
	}

	@Override
	public Scalari interpolate( Scalari out, Scalari start, Scalari end, float delta )
	{
		out.v = (int)((end.v - start.v) * delta + start.v);
		return out;
	}
	
	@Override
	public Scalari contain( Scalari out, Scalari min, Scalari max )
	{
		out.v = Numbers.clamp( out.v, min.v, max.v );
		return out;
	}
    
	@Override
    public boolean contains( Scalari point, Scalari min, Scalari max )
    {
		return point.v >= min.v && point.v <= max.v;
    }

    @Override
    public Scalari random( Scalari out, Scalari min, Scalari max, Binary randomizer )
    {
    	out.v = (int)randomizer.binary( min.v, max.v );
    	return out;
    }
    
    @Override
    public float dot( Scalari a, Scalari b )
    {
    	return a.v * b.v;
    }

    @Override
    public float distance( Scalari a, Scalari b ) 
    {
    	return Math.abs( a.v - b.v );
    }
    
    @Override
    public float distanceSq( Scalari a, Scalari b )
    {
    	float dx = a.v - b.v;
    	
    	return dx * dx; 
    }
    
    @Override
    public float length( Scalari value )
    {
    	return Math.abs( value.v );
    }

    @Override
    public float lengthSq( Scalari value ) 
    {
    	return value.v * value.v;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Scalari;
	}

	@Override
	public boolean isFinite( Scalari value )
	{
		return Float.isFinite( value.v );
	}
	
	@Override
	public boolean isEqual( Scalari a, Scalari b, float epsilon )
	{
		return Numbers.equals( a.v, b.v, epsilon );
	}
	
	@Override
	public int sizeof( Scalari value )
	{
		return 4;
	}

	@Override
	public int write( Scalari value, ByteBuffer to )
	{
		to.putInt( value.v );
		return 4;
	}

	@Override
	public Scalari read( Scalari out, ByteBuffer from )
	{
		out.v = from.getInt();
		return out;
	}
	
	@Override
	public Scalari read( Scalari out, InputModel input )
	{
		out.v = input.readInt( "v" );
		return out;
	}
	
	@Override
	public void write( Scalari value, OutputModel output )
	{
		output.write( "v", value.v );
	}

	@Override
	public int getComponents()
	{
		return 1;
	}

	@Override
	public float getComponent( Scalari value, int index )
	{
		return value.v;
	}

	@Override
	public Scalari setComponent( Scalari value, int index, float component )
	{
		value.v = (int)component;
		return value;
	}

	@Override
	public Scalari mods(Scalari out, Scalari value, float divisor) 
	{
		out.v = value.v % (int)divisor;
		return out;
	}

	@Override
	public Scalari mod(Scalari out, Scalari value, Scalari divisor) 
	{
		out.v = value.v % divisor.v;
		return out;
	}

	@Override
	public Scalari truncates(Scalari out, Scalari value, float divisor) 
	{
		out.v = value.v - value.v % (int)divisor;
		return out;
	}

	@Override
	public Scalari truncate(Scalari out, Scalari value, Scalari divisor) 
	{
		out.v = value.v - value.v % divisor.v;
		return out;
	}

	@Override
	public Scalari clamp(Scalari out, Scalari min, Scalari max) 
	{
		out.v = Numbers.clamp( out.v, min.v, max.v );
		return out;
	}
	
}
