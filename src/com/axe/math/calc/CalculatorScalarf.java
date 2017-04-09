package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Scalarf;

public final class CalculatorScalarf extends AbstractCalculator<Scalarf> 
{
	
	public static final CalculatorScalarf INSTANCE = new CalculatorScalarf();
	
	@Override
	public Scalarf copy(Scalarf out, Scalarf source) 
	{
		out.v = source.v;
		return out;
	}

	@Override
	public Scalarf parse(Object input, Scalarf defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			return createUniform( ((float[])input)[0] ); 
		}
		
		if (input instanceof Scalarf)
		{
			return (Scalarf)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Scalarf instantiate()
	{
		return new Scalarf();
	}

	@Override
	public Scalarf clear(Scalarf out, float component) 
	{
		out.v = component; 
		return out;
	}

	@Override
	public Scalarf unary(Scalarf out, Scalarf value, Unary unaryOperation) 
	{
		out.v = unaryOperation.unary( value.v );
		return out;
	}

	@Override
	public Scalarf binary(Scalarf out, Scalarf a, Scalarf b, Binary binaryOperation) 
	{
		out.v = binaryOperation.binary( a.v, b.v );
		return out;
	}

	@Override
	public Scalarf adds(Scalarf out, Scalarf augend, Scalarf addend, float scale) 
	{	
		out.v = augend.v + addend.v * scale;
		return out;
	}
	
	@Override
	public Scalarf mul( Scalarf out, Scalarf value, Scalarf scale )
	{
		out.v = value.v * scale.v;
		return out;
	}
	
	@Override
	public Scalarf div( Scalarf out, Scalarf dividend, Scalarf divisor )
	{
		out.v = Numbers.divide( dividend.v, divisor.v );
		return out;
	}

	@Override
	public Scalarf interpolate( Scalarf out, Scalarf start, Scalarf end, float delta )
	{
		out.v = (end.v - start.v) * delta + start.v;
		return out;
	}
	
	@Override
	public Scalarf contain( Scalarf out, Scalarf min, Scalarf max )
	{
		out.v = Numbers.clamp( out.v, min.v, max.v );
		return out;
	}
    
	@Override
    public boolean contains( Scalarf point, Scalarf min, Scalarf max )
    {
		return point.v >= min.v && point.v <= max.v;
    }

    @Override
    public Scalarf random( Scalarf out, Scalarf min, Scalarf max, Binary randomizer )
    {
    	out.v = randomizer.binary( min.v, max.v );
    	return out;
    }
    
    @Override
    public float dot( Scalarf a, Scalarf b )
    {
    	return a.v * b.v;
    }

    @Override
    public float distance( Scalarf a, Scalarf b ) 
    {
    	return Math.abs( a.v - b.v );
    }
    
    @Override
    public float distanceSq( Scalarf a, Scalarf b )
    {
    	float dx = a.v - b.v;
    	
    	return dx * dx; 
    }
    
    @Override
    public float length( Scalarf value )
    {
    	return Math.abs( value.v );
    }

    @Override
    public float lengthSq( Scalarf value ) 
    {
    	return value.v * value.v;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Scalarf;
	}

	@Override
	public boolean isFinite( Scalarf value )
	{
		return Float.isFinite( value.v );
	}
	
	@Override
	public boolean isEqual( Scalarf a, Scalarf b, float epsilon )
	{
		return Numbers.equals( a.v, b.v, epsilon );
	}
	
	@Override
	public int sizeof( Scalarf value )
	{
		return 4;
	}

	@Override
	public int write( Scalarf value, ByteBuffer to )
	{
		to.putFloat( value.v );
		return 4;
	}

	@Override
	public Scalarf read( Scalarf out, ByteBuffer from )
	{
		out.v = from.getFloat();
		return out;
	}
	
	@Override
	public Scalarf read( Scalarf out, InputModel input )
	{
		out.v = input.readFloat( "v" );
		return out;
	}
	
	@Override
	public void write( Scalarf value, OutputModel output )
	{
		output.write( "v", value.v );
	}

	@Override
	public int getComponents()
	{
		return 1;
	}

	@Override
	public float getComponent( Scalarf value, int index )
	{
		return value.v;
	}

	@Override
	public Scalarf setComponent( Scalarf value, int index, float component )
	{
		value.v = component;
		return value;
	}

	@Override
	public Scalarf mods(Scalarf out, Scalarf value, float divisor) 
	{
		out.v = value.v % divisor;
		return out;
	}

	@Override
	public Scalarf mod(Scalarf out, Scalarf value, Scalarf divisor) 
	{
		out.v = value.v % divisor.v;
		return out;
	}

	@Override
	public Scalarf truncates(Scalarf out, Scalarf value, float divisor) 
	{
		out.v = value.v - value.v % divisor;
		return out;
	}

	@Override
	public Scalarf truncate(Scalarf out, Scalarf value, Scalarf divisor) 
	{
		out.v = value.v - value.v % divisor.v;
		return out;
	}

	@Override
	public Scalarf clamp(Scalarf out, Scalarf min, Scalarf max) 
	{
		out.v = Numbers.clamp( out.v, min.v, max.v );
		return out;
	}
	
}
