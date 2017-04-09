package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Rangei;

public final class CalculatorRangei extends AbstractCalculator<Rangei> 
{
	
	public static final CalculatorRangei INSTANCE = new CalculatorRangei();
	
	@Override
	public Rangei copy(Rangei out, Rangei source) 
	{
		out.min = source.min;
		out.max = source.max;
		return out;
	}

	@Override
	public Rangei parse(Object input, Rangei defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof int[])
		{
			int[] ints = (int[])input;
			
			return new Rangei( ints[0], ints[1] ); 
		}
		
		if (input instanceof Rangei)
		{
			return (Rangei)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Rangei instantiate()
	{
		return new Rangei();
	}

	@Override
	public Rangei clear(Rangei out, float component) 
	{
		out.min = out.max = (int)component;
		return out;
	}

	@Override
	public Rangei unary(Rangei out, Rangei value, Unary unaryOperation) 
	{
		out.min = (int)unaryOperation.unary( value.min );
		out.max = (int)unaryOperation.unary( value.max );
		return out;
	}

	@Override
	public Rangei binary(Rangei out, Rangei a, Rangei b, Binary binaryOperation) 
	{
		out.min = (int)binaryOperation.binary( a.min, b.min );
		out.max = (int)binaryOperation.binary( a.max, b.max );
		return out;
	}

	@Override
	public Rangei adds(Rangei out, Rangei augend, Rangei addend, float scale) 
	{	
		out.min = (int)(augend.min + addend.min * scale);
		out.max = (int)(augend.max + addend.max * scale);
		return out;
	}
	
	@Override
	public Rangei mul( Rangei out, Rangei value, Rangei scale )
	{
		out.min = value.min * scale.min;
		out.max = value.max * scale.max;
		return out;
	}
	
	@Override
	public Rangei div( Rangei out, Rangei dividend, Rangei divisor )
	{
		out.min = Numbers.divide( dividend.min, divisor.min );
		out.max = Numbers.divide( dividend.max, divisor.max );
		return out;
	}

	@Override
	public Rangei interpolate( Rangei out, Rangei start, Rangei end, float delta )
	{
		out.min = (int)((end.min - start.min) * delta + start.min);
		out.max = (int)((end.max - start.max) * delta + start.max);
		return out;
	}
	
	@Override
	public Rangei contain( Rangei out, Rangei min, Rangei max )
	{
		out.min = Numbers.clamp( out.min, min.min, max.min );
		out.max = Numbers.clamp( out.max, min.max, max.max );
		return out;
	}
    
	@Override
    public boolean contains( Rangei point, Rangei min, Rangei max )
    {
		return point.min >= min.min && point.min <= max.min && 
			   point.max >= min.max && point.max <= max.max;
    }

    @Override
    public Rangei random( Rangei out, Rangei min, Rangei max, Binary randomizer )
    {
    	out.min = (int)randomizer.binary( min.min, max.min );
    	out.max = (int)randomizer.binary( min.max, max.max );
    	return out;
    }
    
    @Override
    public float dot( Rangei a, Rangei b )
    {
    	return a.min * b.min + a.max * b.max;
    }
    
    @Override
    public float distanceSq( Rangei a, Rangei b )
    {
    	float d = this.distance( a, b );
    	
    	return d * d; 
    }
    
    @Override
    public float distance( Rangei a, Rangei b )
    {
    	return Math.abs(a.min - b.min) + Math.abs(a.max - b.max);
    }

    @Override
    public float lengthSq( Rangei value ) 
    {
    	return value.min * value.min + value.max * value.max;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Rangei;
	}

	@Override
	public boolean isFinite( Rangei value )
	{
		return Float.isFinite( value.min ) && 
			   Float.isFinite( value.max );
	}
	
	@Override
	public boolean isEqual( Rangei a, Rangei b, float epsilon )
	{
		return Numbers.equals( a.min, b.min, epsilon ) &&
			   Numbers.equals( a.max, b.max, epsilon );
	}
	
	@Override
	public int sizeof( Rangei value )
	{
		return 8;
	}

	@Override
	public int write( Rangei value, ByteBuffer to )
	{
		to.putInt( value.min );
		to.putInt( value.max );
		return 8;
	}

	@Override
	public Rangei read( Rangei out, ByteBuffer from )
	{
		out.min = from.getInt();
		out.max = from.getInt();
		return out;
	}
	
	@Override
	public Rangei read( Rangei out, InputModel input )
	{
		out.min = input.readInt( "x" );
		out.max = input.readInt( "y" );
		return out;
	}
	
	@Override
	public void write( Rangei value, OutputModel output )
	{
		output.write( "x", value.min );
		output.write( "y", value.max );
	}

	@Override
	public int getComponents()
	{
		return 2;
	}

	@Override
	public float getComponent( Rangei value, int index )
	{
		switch (index) {
		case 0: return value.min;
		case 1: return value.max;
		}
		
		return 0;
	}

	@Override
	public Rangei setComponent( Rangei value, int index, float component )
	{
		switch (index) {
		case 0:
			value.min = (int)component;
			break;
		case 1:
			value.max = (int)component;
			break;
		}
		return value;
	}

	@Override
	public Rangei mods(Rangei out, Rangei value, float divisor) 
	{
		out.min = value.min % (int)divisor;
		out.max = value.max % (int)divisor;
		return out;
	}

	@Override
	public Rangei mod(Rangei out, Rangei value, Rangei divisor) 
	{
		out.min = value.min % divisor.min;
		out.max = value.max % divisor.max;
		return out;
	}

	@Override
	public Rangei truncates(Rangei out, Rangei value, float divisor) 
	{
		out.min = value.min - value.min % (int)divisor;
		out.max = value.max - value.max % (int)divisor;
		return out;
	}

	@Override
	public Rangei truncate(Rangei out, Rangei value, Rangei divisor) 
	{
		out.min = value.min - value.min % divisor.min;
		out.max = value.max - value.max % divisor.max;
		return out;
	}

	@Override
	public Rangei clamp(Rangei out, Rangei min, Rangei max) 
	{
		out.min = Numbers.clamp( out.min, min.min, max.min );
		out.max = Numbers.clamp( out.max, min.max, max.max );
		return out;
	}
	
}
