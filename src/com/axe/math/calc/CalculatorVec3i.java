package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.math.Vec3i;

public final class CalculatorVec3i extends AbstractCalculator<Vec3i> 
{
	
	public static final CalculatorVec3i INSTANCE = new CalculatorVec3i();
	
	@Override
	public Vec3i copy(Vec3i out, Vec3i source) 
	{
		out.x = source.x;
		out.y = source.y;
		out.z = source.z;
		return out;
	}

	@Override
	public Vec3i parse(Object input, Vec3i defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof int[])
		{
			int[] ints = (int[])input;
			
			return new Vec3i( ints[0], ints[1], ints[2] ); 
		}
		
		if (input instanceof Vec3i)
		{
			return (Vec3i)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Vec3i instantiate()
	{
		return new Vec3i();
	}

	@Override
	public Vec3i clear(Vec3i out, float component) 
	{
		out.x = out.y = out.z = (int)component;
		return out;
	}

	@Override
	public Vec3i unary(Vec3i out, Vec3i value, Unary unaryOperation) 
	{
		out.x = (int)unaryOperation.unary( value.x );
		out.y = (int)unaryOperation.unary( value.y );
		out.z = (int)unaryOperation.unary( value.z );
		return out;
	}

	@Override
	public Vec3i binary(Vec3i out, Vec3i a, Vec3i b, Binary binaryOperation) 
	{
		out.x = (int)binaryOperation.binary( a.x, b.x );
		out.y = (int)binaryOperation.binary( a.y, b.y );
		out.z = (int)binaryOperation.binary( a.z, b.z );
		return out;
	}

	@Override
	public Vec3i adds(Vec3i out, Vec3i augend, Vec3i addend, float scale) 
	{	
		out.x = (int)(augend.x + addend.x * scale);
		out.y = (int)(augend.y + addend.y * scale);
		out.z = (int)(augend.z + addend.z * scale);
		return out;
	}
	
	@Override
	public Vec3i mul( Vec3i out, Vec3i value, Vec3i scale )
	{
		out.x = value.x * scale.x;
		out.y = value.y * scale.y;
		out.z = value.z * scale.z;
		return out;
	}
	
	@Override
	public Vec3i div( Vec3i out, Vec3i dividend, Vec3i divisor )
	{
		out.x = Numbers.divide( dividend.x, divisor.x );
		out.y = Numbers.divide( dividend.y, divisor.y );
		out.z = Numbers.divide( dividend.z, divisor.z );
		return out;
	}

	@Override
	public Vec3i interpolate( Vec3i out, Vec3i start, Vec3i end, float delta )
	{
		out.x = (int)((end.x - start.x) * delta + start.x);
		out.y = (int)((end.y - start.y) * delta + start.y);
		out.z = (int)((end.z - start.z) * delta + start.z);
		return out;
	}
	
	@Override
	public Vec3i contain( Vec3i out, Vec3i min, Vec3i max )
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.z = Numbers.clamp( out.z, min.z, max.z );
		return out;
	}
    
	@Override
    public boolean contains( Vec3i point, Vec3i min, Vec3i max )
    {
		return point.x >= min.x && point.x <= max.x && 
			   point.y >= min.y && point.y <= max.y && 
			   point.z >= min.z && point.z <= max.z;
    }

    @Override
    public Vec3i random( Vec3i out, Vec3i min, Vec3i max, Binary randomizer )
    {
    	out.x = (int)randomizer.binary( min.x, max.x );
    	out.y = (int)randomizer.binary( min.y, max.y );
    	out.z = (int)randomizer.binary( min.z, max.z );
    	return out;
    }
    
    @Override
    public float dot( Vec3i a, Vec3i b )
    {
    	return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    
    @Override
    public float distanceSq( Vec3i a, Vec3i b )
    {
    	float dx = a.x - b.x;
    	float dy = a.y - b.y;
    	float dz = a.z - b.z;
    	
    	return dx * dx + dy * dy + dz * dz; 
    }

    @Override
    public float lengthSq( Vec3i value ) 
    {
    	return value.x * value.x + value.y * value.y + value.z * value.z;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Vec3i;
	}

	@Override
	public boolean isFinite( Vec3i value )
	{
		return Float.isFinite( value.x ) && 
			   Float.isFinite( value.y ) && 
			   Float.isFinite( value.z );
	}
	
	@Override
	public boolean isEqual( Vec3i a, Vec3i b, float epsilon )
	{
		return Numbers.equals( a.x, b.x, epsilon ) &&
			   Numbers.equals( a.y, b.y, epsilon ) &&
			   Numbers.equals( a.z, b.z, epsilon );
	}
	
	@Override
	public int sizeof( Vec3i value )
	{
		return 12;
	}

	@Override
	public int write( Vec3i value, ByteBuffer to )
	{
		to.putFloat( value.x );
		to.putFloat( value.y );
		to.putFloat( value.z );
		return 12;
	}

	@Override
	public Vec3i read( Vec3i out, ByteBuffer from )
	{
		out.x = from.getInt();
		out.y = from.getInt();
		out.z = from.getInt();
		return out;
	}
	
	@Override
	public Vec3i read( Vec3i out, InputModel input )
	{
		out.x = input.readInt( "x" );
		out.y = input.readInt( "y" );
		out.z = input.readInt( "z" );
		return out;
	}
	
	@Override
	public void write( Vec3i value, OutputModel output )
	{
		output.write( "x", value.x );
		output.write( "y", value.y );
		output.write( "z", value.z );
	}

	@Override
	public int getComponents()
	{
		return 3;
	}

	@Override
	public float getComponent( Vec3i value, int index )
	{
		switch (index) {
		case 0: return value.x;
		case 1: return value.y;
		case 2: return value.z;
		}
		
		return 0;
	}

	@Override
	public Vec3i setComponent( Vec3i value, int index, float component )
	{
		switch (index) {
		case 0:
			value.x = (int)component;
			break;
		case 1:
			value.y = (int)component;
			break;
		case 2:
			value.z = (int)component;
			break;
		}
		return value;
	}

	@Override
	public Vec3i mods(Vec3i out, Vec3i value, float divisor) 
	{
		out.x = value.x % (int)divisor;
		out.y = value.y % (int)divisor;
		out.z = value.z % (int)divisor;
		return out;
	}

	@Override
	public Vec3i mod(Vec3i out, Vec3i value, Vec3i divisor) 
	{
		out.x = value.x % divisor.x;
		out.y = value.y % divisor.y;
		out.z = value.z % divisor.z;
		return out;
	}

	@Override
	public Vec3i truncates(Vec3i out, Vec3i value, float divisor) 
	{
		out.x = value.x - value.x % (int)divisor;
		out.y = value.y - value.y % (int)divisor;
		out.z = value.z - value.z % (int)divisor;
		return out;
	}

	@Override
	public Vec3i truncate(Vec3i out, Vec3i value, Vec3i divisor) 
	{
		out.x = value.x - value.x % divisor.x;
		out.y = value.y - value.y % divisor.y;
		out.z = value.z - value.z % divisor.z;
		return out;
	}

	@Override
	public Vec3i clamp(Vec3i out, Vec3i min, Vec3i max) 
	{
		out.x = Numbers.clamp( out.x, min.x, max.x );
		out.y = Numbers.clamp( out.y, min.y, max.y );
		out.z = Numbers.clamp( out.z, min.z, max.z );
		return out;
	}
	
}
