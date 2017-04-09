package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.gfx.Coord;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

public final class CalculatorCoord extends AbstractCalculator<Coord> 
{
	
	public static final CalculatorCoord INSTANCE = new CalculatorCoord();
	
	@Override
	public Coord copy(Coord out, Coord source) 
	{
		out.s = source.s;
		out.t = source.t;
		return out;
	}

	@Override
	public Coord parse(Object input, Coord defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Coord( floats[0], floats[1] ); 
		}
		
		if (input instanceof Coord)
		{
			return (Coord)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Coord instantiate()
	{
		return new Coord();
	}

	@Override
	public Coord clear(Coord out, float component) 
	{
		out.s = out.t = component;
		return out;
	}

	@Override
	public Coord unary(Coord out, Coord value, Unary unaryOperation) 
	{
		out.s = unaryOperation.unary( value.s );
		out.t = unaryOperation.unary( value.t );
		return out;
	}

	@Override
	public Coord binary(Coord out, Coord a, Coord b, Binary binaryOperation) 
	{
		out.s = binaryOperation.binary( a.s, b.s );
		out.t = binaryOperation.binary( a.t, b.t );
		return out;
	}

	@Override
	public Coord adds(Coord out, Coord augend, Coord addend, float scale) 
	{	
		out.s = augend.s + addend.s * scale;
		out.t = augend.t + addend.t * scale;
		return out;
	}
	
	@Override
	public Coord mul( Coord out, Coord value, Coord scale )
	{
		out.s = value.s * scale.s;
		out.t = value.t * scale.t;
		return out;
	}
	
	@Override
	public Coord div( Coord out, Coord dividend, Coord divisor )
	{
		out.s = Numbers.divide( dividend.s, divisor.s );
		out.t = Numbers.divide( dividend.t, divisor.t );
		return out;
	}

	@Override
	public Coord interpolate( Coord out, Coord start, Coord end, float delta )
	{
		out.s = (end.s - start.s) * delta + start.s;
		out.t = (end.t - start.t) * delta + start.t;
		return out;
	}
	
	@Override
	public Coord contain( Coord out, Coord min, Coord max )
	{
		out.s = Numbers.clamp( out.s, min.s, max.s );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		return out;
	}
    
	@Override
    public boolean contains( Coord point, Coord min, Coord max )
    {
		return point.s >= min.s && point.s <= max.s && 
			   point.t >= min.t && point.t <= max.t;
    }

    @Override
    public Coord random( Coord out, Coord min, Coord max, Binary randomizer )
    {
    	out.s = randomizer.binary( min.s, max.s );
    	out.t = randomizer.binary( min.t, max.t );
    	return out;
    }
    
    @Override
    public float dot( Coord a, Coord b )
    {
    	return a.s * b.s + a.t * b.t;
    }
    
    @Override
    public float distanceSq( Coord a, Coord b )
    {
    	float dx = a.s - b.s;
    	float dy = a.t - b.t;
    	
    	return dx * dx + dy * dy; 
    }

    @Override
    public float lengthSq( Coord value ) 
    {
    	return value.s * value.s + value.t * value.t;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Coord;
	}

	@Override
	public boolean isFinite( Coord value )
	{
		return Float.isFinite( value.s ) && 
			   Float.isFinite( value.t );
	}
	
	@Override
	public boolean isEqual( Coord a, Coord b, float epsilon )
	{
		return Numbers.equals( a.s, b.s, epsilon ) &&
			   Numbers.equals( a.t, b.t, epsilon );
	}
	
	@Override
	public int sizeof( Coord value )
	{
		return 8;
	}

	@Override
	public int write( Coord value, ByteBuffer to )
	{
		to.putFloat( value.s );
		to.putFloat( value.t );
		return 8;
	}

	@Override
	public Coord read( Coord out, ByteBuffer from )
	{
		out.s = from.getFloat();
		out.t = from.getFloat();
		return out;
	}
	
	@Override
	public Coord read( Coord out, InputModel input )
	{
		out.s = input.readFloat( "s" );
		out.t = input.readFloat( "t" );
		return out;
	}
	
	@Override
	public void write( Coord value, OutputModel output )
	{
		output.write( "s", value.s );
		output.write( "t", value.t );
	}

	@Override
	public int getComponents()
	{
		return 2;
	}

	@Override
	public float getComponent( Coord value, int index )
	{
		switch (index) {
		case 0: return value.s;
		case 1: return value.t;
		}
		
		return 0;
	}

	@Override
	public Coord setComponent( Coord value, int index, float component )
	{
		switch (index) {
		case 0:
			value.s = component;
			break;
		case 1:
			value.t = component;
			break;
		}
		return value;
	}

	@Override
	public Coord mods(Coord out, Coord value, float divisor) 
	{
		out.s = value.s % divisor;
		out.t = value.t % divisor;
		return out;
	}

	@Override
	public Coord mod(Coord out, Coord value, Coord divisor) 
	{
		out.s = value.s % divisor.s;
		out.t = value.t % divisor.t;
		return out;
	}

	@Override
	public Coord truncates(Coord out, Coord value, float divisor) 
	{
		out.s = value.s - value.s % divisor;
		out.t = value.t - value.t % divisor;
		return out;
	}

	@Override
	public Coord truncate(Coord out, Coord value, Coord divisor) 
	{
		out.s = value.s - value.s % divisor.s;
		out.t = value.t - value.t % divisor.t;
		return out;
	}

	@Override
	public Coord clamp(Coord out, Coord min, Coord max) 
	{
		out.s = Numbers.clamp( out.s, min.s, max.s );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		return out;
	}
	
}
