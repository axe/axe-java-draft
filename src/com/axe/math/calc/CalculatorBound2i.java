package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Bound2i;
import com.axe.math.Numbers;

public final class CalculatorBound2i extends AbstractCalculator<Bound2i> 
{
	
	public static final CalculatorBound2i INSTANCE = new CalculatorBound2i();
	
	@Override
	public Bound2i copy(Bound2i out, Bound2i source) 
	{
		out.l = source.l;
		out.t = source.t;
		out.r = source.r;
		out.b = source.b;
		return out;
	}

	@Override
	public Bound2i parse(Object input, Bound2i defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof int[])
		{
			int[] ints = (int[])input;
			
			return new Bound2i( ints[0], ints[1], ints[2], ints[3] ); 
		}
		
		if (input instanceof Bound2i)
		{
			return (Bound2i)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Bound2i instantiate()
	{
		return new Bound2i();
	}

	@Override
	public Bound2i clear(Bound2i out, float component) 
	{
		out.l = out.t = out.r = out.b = (int)component;
		return out;
	}

	@Override
	public Bound2i unary(Bound2i out, Bound2i value, Unary unaryOperation) 
	{
		out.l = (int)unaryOperation.unary( value.l );
		out.t = (int)unaryOperation.unary( value.t );
		out.r = (int)unaryOperation.unary( value.r );
		out.b = (int)unaryOperation.unary( value.b );
		return out;
	}

	@Override
	public Bound2i binary(Bound2i out, Bound2i a, Bound2i b, Binary binaryOperation) 
	{
		out.l = (int)binaryOperation.binary( a.l, b.l );
		out.t = (int)binaryOperation.binary( a.t, b.t );
		out.r = (int)binaryOperation.binary( a.r, b.r );
		out.b = (int)binaryOperation.binary( a.r, b.b );
		return out;
	}

	@Override
	public Bound2i adds(Bound2i out, Bound2i augend, Bound2i addend, float scale) 
	{	
		out.l = (int)(augend.l + addend.l * scale);
		out.t = (int)(augend.t + addend.t * scale);
		out.r = (int)(augend.r + addend.r * scale);
		out.b = (int)(augend.b + addend.b * scale);
		return out;
	}
	
	@Override
	public Bound2i mul( Bound2i out, Bound2i value, Bound2i scale )
	{
		out.l = value.l * scale.l;
		out.t = value.t * scale.t;
		out.r = value.r * scale.r;
		out.b = value.b * scale.b;
		return out;
	}
	
	@Override
	public Bound2i div( Bound2i out, Bound2i dividend, Bound2i divisor )
	{
		out.l = Numbers.divide( dividend.l, divisor.l );
		out.t = Numbers.divide( dividend.t, divisor.t );
		out.r = Numbers.divide( dividend.r, divisor.r );
		out.b = Numbers.divide( dividend.b, divisor.b );
		return out;
	}

	@Override
	public Bound2i interpolate( Bound2i out, Bound2i start, Bound2i end, float delta )
	{
		out.l = (int)((end.l - start.l) * delta + start.l);
		out.t = (int)((end.t - start.t) * delta + start.t);
		out.r = (int)((end.r - start.r) * delta + start.r);
		out.b = (int)((end.b - start.b) * delta + start.b);
		return out;
	}
	
	@Override
	public Bound2i contain( Bound2i out, Bound2i min, Bound2i max )
	{
		out.l = Numbers.clamp( out.l, min.l, max.l );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		return out;
	}
    
	@Override
    public boolean contains( Bound2i point, Bound2i min, Bound2i max )
    {
		return point.l >= min.l && point.l <= max.l && 
			   point.t >= min.t && point.t <= max.t && 
			   point.r >= min.r && point.r <= max.r && 
			   point.b >= min.b && point.b <= max.b;
    }

    @Override
    public Bound2i random( Bound2i out, Bound2i min, Bound2i max, Binary randomizer )
    {
    	out.l = (int)randomizer.binary( min.l, max.l );
    	out.t = (int)randomizer.binary( min.t, max.t );
    	out.r = (int)randomizer.binary( min.r, max.r );
    	out.b = (int)randomizer.binary( min.b, max.b );
    	return out;
    }
    
    @Override
    public float dot( Bound2i a, Bound2i b )
    {
    	return a.l * b.l + a.t * b.t + a.r * b.r + a.b * b.b;
    }
    
    @Override
    public float distanceSq( Bound2i a, Bound2i b )
    {
    	float dr = a.l - b.l;
    	float dg = a.t - b.t;
    	float db = a.r - b.r;
    	float da = a.b - b.b;
    	
    	return dr * dr + dg * dg + db * db + da * da; 
    }

    @Override
    public float lengthSq( Bound2i value ) 
    {
    	return value.l * value.l + value.t * value.t + value.r * value.r + value.b * value.b;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Bound2i;
	}

	@Override
	public boolean isFinite( Bound2i value )
	{
		return Float.isFinite( value.l ) && 
			   Float.isFinite( value.t ) && 
			   Float.isFinite( value.r ) && 
			   Float.isFinite( value.b );
	}
	
	@Override
	public boolean isEqual( Bound2i a, Bound2i b, float epsilon )
	{
		return Numbers.equals( a.l, b.l, epsilon ) &&
			   Numbers.equals( a.t, b.t, epsilon ) &&
			   Numbers.equals( a.r, b.r, epsilon ) &&
			   Numbers.equals( a.b, b.b, epsilon );
	}
	
	@Override
	public int sizeof( Bound2i value )
	{
		return 16;
	}

	@Override
	public int write( Bound2i value, ByteBuffer to )
	{
		to.putFloat( value.l );
		to.putFloat( value.t );
		to.putFloat( value.r );
		to.putFloat( value.b );
		return 16;
	}

	@Override
	public Bound2i read( Bound2i out, ByteBuffer from )
	{
		out.l = from.getInt();
		out.t = from.getInt();
		out.r = from.getInt();
		out.b = from.getInt();
		return out;
	}
	
	@Override
	public Bound2i read( Bound2i out, InputModel input )
	{
		out.l = input.readInt( "l" );
		out.t = input.readInt( "t" );
		out.r = input.readInt( "r" );
		out.b = input.readInt( "b" );
		return out;
	}
	
	@Override
	public void write( Bound2i value, OutputModel output )
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
	public float getComponent( Bound2i value, int index )
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
	public Bound2i setComponent( Bound2i value, int index, float component )
	{
		switch (index) {
		case 0:
			value.l = (int)component;
			break;
		case 1:
			value.t = (int)component;
			break;
		case 2:
			value.r = (int)component;
			break;
		case 3:
			value.b = (int)component;
			break;
		}
		return value;
	}

	@Override
	public Bound2i mods(Bound2i out, Bound2i value, float divisor) 
	{
		out.l = value.l % (int)divisor;
		out.t = value.t % (int)divisor;
		out.r = value.r % (int)divisor;
		out.b = value.b % (int)divisor;
		return out;
	}

	@Override
	public Bound2i mod(Bound2i out, Bound2i value, Bound2i divisor) 
	{
		out.l = value.l % divisor.l;
		out.t = value.t % divisor.t;
		out.r = value.r % divisor.r;
		out.b = value.b % divisor.b;
		return out;
	}

	@Override
	public Bound2i truncates(Bound2i out, Bound2i value, float divisor) 
	{
		out.l = value.l - value.l % (int)divisor;
		out.t = value.t - value.t % (int)divisor;
		out.r = value.r - value.r % (int)divisor;
		out.b = value.b - value.b % (int)divisor;
		return out;
	}

	@Override
	public Bound2i truncate(Bound2i out, Bound2i value, Bound2i divisor) 
	{
		out.l = value.l - value.l % divisor.l;
		out.t = value.t - value.t % divisor.t;
		out.r = value.r - value.r % divisor.r;
		out.b = value.b - value.b % divisor.b;
		return out;
	}

	@Override
	public Bound2i clamp(Bound2i out, Bound2i min, Bound2i max) 
	{
		out.l = Numbers.clamp( out.l, min.l, max.l );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		return out;
	}
	
}
