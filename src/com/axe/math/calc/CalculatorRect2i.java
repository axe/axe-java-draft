package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Rect2i;

public final class CalculatorRect2i extends AbstractCalculator<Rect2i> 
{
	
	public static final CalculatorRect2i INSTANCE = new CalculatorRect2i();
	
	@Override
	public Rect2i copy(Rect2i out, Rect2i source) 
	{
		out.x = source.x;
		out.y = source.y;
		out.w = source.w;
		out.h = source.h;
		return out;
	}

	@Override
	public Rect2i parse(Object input, Rect2i defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof int[])
		{
			int[] ints = (int[])input;
			
			return new Rect2i( ints[0], ints[1], ints[2], ints[3] ); 
		}
		
		if (input instanceof Rect2i)
		{
			return (Rect2i)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Rect2i instantiate()
	{
		return new Rect2i();
	}

	@Override
	public Rect2i clear(Rect2i out, float component) 
	{
		out.x = out.y = out.w = out.h = (int)component;
		return out;
	}

	@Override
	public Rect2i unary(Rect2i out, Rect2i value, Unary unaryOperation) 
	{
		out.x = (int)unaryOperation.unary( value.x );
		out.y = (int)unaryOperation.unary( value.y );
		out.w = (int)unaryOperation.unary( value.w );
		out.h = (int)unaryOperation.unary( value.h );
		return out;
	}

	@Override
	public Rect2i binary(Rect2i out, Rect2i a, Rect2i b, Binary binaryOperation) 
	{
		out.x = (int)binaryOperation.binary( a.x, b.x );
		out.y = (int)binaryOperation.binary( a.y, b.y );
		out.w = (int)binaryOperation.binary( a.w, b.w );
		out.h = (int)binaryOperation.binary( a.w, b.h );
		return out;
	}

	@Override
	public Rect2i adds(Rect2i out, Rect2i augend, Rect2i addend, float scale) 
	{	
		out.x = (int)(augend.x + addend.x * scale);
		out.y = (int)(augend.y + addend.y * scale);
		out.w = (int)(augend.w + addend.w * scale);
		out.h = (int)(augend.h + addend.h * scale);
		return out;
	}
	
	@Override
	public Rect2i mul( Rect2i out, Rect2i value, Rect2i scale )
	{
		out.x = value.x * scale.x;
		out.y = value.y * scale.y;
		out.w = value.w * scale.w;
		out.h = value.h * scale.h;
		return out;
	}
	
	@Override
	public Rect2i div( Rect2i out, Rect2i dividend, Rect2i divisor )
	{
		out.x = Numbers.divide( dividend.x, divisor.x );
		out.y = Numbers.divide( dividend.y, divisor.y );
		out.w = Numbers.divide( dividend.w, divisor.w );
		out.h = Numbers.divide( dividend.h, divisor.h );
		return out;
	}

	@Override
	public Rect2i interpolate( Rect2i out, Rect2i start, Rect2i end, float delta )
	{
		out.x = (int)((end.x - start.x) * delta + start.x);
		out.y = (int)((end.y - start.y) * delta + start.y);
		out.w = (int)((end.w - start.w) * delta + start.w);
		out.h = (int)((end.h - start.h) * delta + start.h);
		return out;
	}
	
	@Override
	public Rect2i contain( Rect2i out, Rect2i min, Rect2i max )
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.w = Numbers.clamp( out.w, min.w, max.w );
		out.h = Numbers.clamp( out.h, min.h, max.h );
		return out;
	}
    
	@Override
    public boolean contains( Rect2i point, Rect2i min, Rect2i max )
    {
		return point.x >= min.x && point.x <= max.x && 
			   point.y >= min.y && point.y <= max.y && 
			   point.w >= min.w && point.w <= max.w && 
			   point.h >= min.h && point.h <= max.h;
    }

    @Override
    public Rect2i random( Rect2i out, Rect2i min, Rect2i max, Binary randomizer )
    {
    	out.x = (int)randomizer.binary( min.x, max.x );
    	out.y = (int)randomizer.binary( min.y, max.y );
    	out.w = (int)randomizer.binary( min.w, max.w );
    	out.h = (int)randomizer.binary( min.h, max.h );
    	return out;
    }
    
    @Override
    public float dot( Rect2i a, Rect2i b )
    {
    	return a.x * b.x + a.y * b.y + a.w * b.w + a.h * b.h;
    }
    
    @Override
    public float distanceSq( Rect2i a, Rect2i b )
    {
    	float dr = a.x - b.x;
    	float dg = a.y - b.y;
    	float db = a.w - b.w;
    	float da = a.h - b.h;
    	
    	return dr * dr + dg * dg + db * db + da * da; 
    }

    @Override
    public float lengthSq( Rect2i value ) 
    {
    	return value.x * value.x + value.y * value.y + value.w * value.w + value.h * value.h;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Rect2i;
	}

	@Override
	public boolean isFinite( Rect2i value )
	{
		return Float.isFinite( value.x ) && 
			   Float.isFinite( value.y ) && 
			   Float.isFinite( value.w ) && 
			   Float.isFinite( value.h );
	}
	
	@Override
	public boolean isEqual( Rect2i a, Rect2i b, float epsilon )
	{
		return Numbers.equals( a.x, b.x, epsilon ) &&
			   Numbers.equals( a.y, b.y, epsilon ) &&
			   Numbers.equals( a.w, b.w, epsilon ) &&
			   Numbers.equals( a.h, b.h, epsilon );
	}
	
	@Override
	public int sizeof( Rect2i value )
	{
		return 16;
	}

	@Override
	public int write( Rect2i value, ByteBuffer to )
	{
		to.putFloat( value.x );
		to.putFloat( value.y );
		to.putFloat( value.w );
		to.putFloat( value.h );
		return 16;
	}

	@Override
	public Rect2i read( Rect2i out, ByteBuffer from )
	{
		out.x = from.getInt();
		out.y = from.getInt();
		out.w = from.getInt();
		out.h = from.getInt();
		return out;
	}
	
	@Override
	public Rect2i read( Rect2i out, InputModel input )
	{
		out.x = input.readInt( "x" );
		out.y = input.readInt( "y" );
		out.w = input.readInt( "w" );
		out.h = input.readInt( "h" );
		return out;
	}
	
	@Override
	public void write( Rect2i value, OutputModel output )
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
	public float getComponent( Rect2i value, int index )
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
	public Rect2i setComponent( Rect2i value, int index, float component )
	{
		switch (index) {
		case 0:
			value.x = (int)component;
			break;
		case 1:
			value.y = (int)component;
			break;
		case 2:
			value.w = (int)component;
			break;
		case 3:
			value.h = (int)component;
			break;
		}
		return value;
	}

	@Override
	public Rect2i mods(Rect2i out, Rect2i value, float divisor) 
	{
		out.x = value.x % (int)divisor;
		out.y = value.y % (int)divisor;
		out.w = value.w % (int)divisor;
		out.h = value.h % (int)divisor;
		return out;
	}

	@Override
	public Rect2i mod(Rect2i out, Rect2i value, Rect2i divisor) 
	{
		out.x = value.x % divisor.x;
		out.y = value.y % divisor.y;
		out.w = value.w % divisor.w;
		out.h = value.h % divisor.h;
		return out;
	}

	@Override
	public Rect2i truncates(Rect2i out, Rect2i value, float divisor) 
	{
		out.x = value.x - value.x % (int)divisor;
		out.y = value.y - value.y % (int)divisor;
		out.w = value.w - value.w % (int)divisor;
		out.h = value.h - value.h % (int)divisor;
		return out;
	}

	@Override
	public Rect2i truncate(Rect2i out, Rect2i value, Rect2i divisor) 
	{
		out.x = value.x - value.x % divisor.x;
		out.y = value.y - value.y % divisor.y;
		out.w = value.w - value.w % divisor.w;
		out.h = value.h - value.h % divisor.h;
		return out;
	}

	@Override
	public Rect2i clamp(Rect2i out, Rect2i min, Rect2i max) 
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.w = Numbers.clamp( out.w, min.w, max.w );
		out.h = Numbers.clamp( out.h, min.h, max.h );
		return out;
	}
	
}
