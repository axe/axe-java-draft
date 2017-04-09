package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Vec2i;

public final class CalculatorVec2i extends AbstractCalculator<Vec2i> 
{
	
	public static final CalculatorVec2i INSTANCE = new CalculatorVec2i();
	
	@Override
	public Vec2i copy(Vec2i out, Vec2i source) 
	{
		out.x = source.x;
		out.y = source.y;
		return out;
	}

	@Override
	public Vec2i parse(Object input, Vec2i defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof int[])
		{
			int[] ints = (int[])input;
			
			return new Vec2i( ints[0], ints[1] ); 
		}
		
		if (input instanceof Vec2i)
		{
			return (Vec2i)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Vec2i instantiate()
	{
		return new Vec2i();
	}

	@Override
	public Vec2i clear(Vec2i out, float component) 
	{
		out.x = out.y = (int)component;
		return out;
	}

	@Override
	public Vec2i unary(Vec2i out, Vec2i value, Unary unaryOperation) 
	{
		out.x = (int)unaryOperation.unary( value.x );
		out.y = (int)unaryOperation.unary( value.y );
		return out;
	}

	@Override
	public Vec2i binary(Vec2i out, Vec2i a, Vec2i b, Binary binaryOperation) 
	{
		out.x = (int)binaryOperation.binary( a.x, b.x );
		out.y = (int)binaryOperation.binary( a.y, b.y );
		return out;
	}

	@Override
	public Vec2i adds(Vec2i out, Vec2i augend, Vec2i addend, float scale) 
	{	
		out.x = (int)(augend.x + addend.x * scale);
		out.y = (int)(augend.y + addend.y * scale);
		return out;
	}
	
	@Override
	public Vec2i mul( Vec2i out, Vec2i value, Vec2i scale )
	{
		out.x = value.x * scale.x;
		out.y = value.y * scale.y;
		return out;
	}
	
	@Override
	public Vec2i div( Vec2i out, Vec2i dividend, Vec2i divisor )
	{
		out.x = Numbers.divide( dividend.x, divisor.x );
		out.y = Numbers.divide( dividend.y, divisor.y );
		return out;
	}

	@Override
	public Vec2i interpolate( Vec2i out, Vec2i start, Vec2i end, float delta )
	{
		out.x = (int)((end.x - start.x) * delta + start.x);
		out.y = (int)((end.y - start.y) * delta + start.y);
		return out;
	}
	
	@Override
	public Vec2i contain( Vec2i out, Vec2i min, Vec2i max )
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		return out;
	}
    
	@Override
    public boolean contains( Vec2i point, Vec2i min, Vec2i max )
    {
		return point.x >= min.x && point.x <= max.x && 
			   point.y >= min.y && point.y <= max.y;
    }

    @Override
    public Vec2i random( Vec2i out, Vec2i min, Vec2i max, Binary randomizer )
    {
    	out.x = (int)randomizer.binary( min.x, max.x );
    	out.y = (int)randomizer.binary( min.y, max.y );
    	return out;
    }
    
    @Override
    public float dot( Vec2i a, Vec2i b )
    {
    	return a.x * b.x + a.y * b.y;
    }
    
    @Override
    public float distanceSq( Vec2i a, Vec2i b )
    {
    	float dx = a.x - b.x;
    	float dy = a.y - b.y;
    	
    	return dx * dx + dy * dy; 
    }

    @Override
    public float lengthSq( Vec2i value ) 
    {
    	return value.x * value.x + value.y * value.y;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Vec2i;
	}

	@Override
	public boolean isFinite( Vec2i value )
	{
		return Float.isFinite( value.x ) && 
			   Float.isFinite( value.y );
	}
	
	@Override
	public boolean isEqual( Vec2i a, Vec2i b, float epsilon )
	{
		return Numbers.equals( a.x, b.x, epsilon ) &&
			   Numbers.equals( a.y, b.y, epsilon );
	}
	
	@Override
	public int sizeof( Vec2i value )
	{
		return 8;
	}

	@Override
	public int write( Vec2i value, ByteBuffer to )
	{
		to.putInt( value.x );
		to.putInt( value.y );
		return 8;
	}

	@Override
	public Vec2i read( Vec2i out, ByteBuffer from )
	{
		out.x = from.getInt();
		out.y = from.getInt();
		return out;
	}
	
	@Override
	public Vec2i read( Vec2i out, InputModel input )
	{
		out.x = input.readInt( "x" );
		out.y = input.readInt( "y" );
		return out;
	}
	
	@Override
	public void write( Vec2i value, OutputModel output )
	{
		output.write( "x", value.x );
		output.write( "y", value.y );
	}

	@Override
	public int getComponents()
	{
		return 2;
	}

	@Override
	public float getComponent( Vec2i value, int index )
	{
		switch (index) {
		case 0: return value.x;
		case 1: return value.y;
		}
		
		return 0;
	}

	@Override
	public Vec2i setComponent( Vec2i value, int index, float component )
	{
		switch (index) {
		case 0:
			value.x = (int)component;
			break;
		case 1:
			value.y = (int)component;
			break;
		}
		return value;
	}

	@Override
	public Vec2i mods(Vec2i out, Vec2i value, float divisor) 
	{
		out.x = value.x % (int)divisor;
		out.y = value.y % (int)divisor;
		return out;
	}

	@Override
	public Vec2i mod(Vec2i out, Vec2i value, Vec2i divisor) 
	{
		out.x = value.x % divisor.x;
		out.y = value.y % divisor.y;
		return out;
	}

	@Override
	public Vec2i truncates(Vec2i out, Vec2i value, float divisor) 
	{
		out.x = value.x - value.x % (int)divisor;
		out.y = value.y - value.y % (int)divisor;
		return out;
	}

	@Override
	public Vec2i truncate(Vec2i out, Vec2i value, Vec2i divisor) 
	{
		out.x = value.x - value.x % divisor.x;
		out.y = value.y - value.y % divisor.y;
		return out;
	}

	@Override
	public Vec2i clamp(Vec2i out, Vec2i min, Vec2i max) 
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		return out;
	}
	
}
