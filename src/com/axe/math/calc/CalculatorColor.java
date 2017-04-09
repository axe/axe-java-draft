package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.color.Color;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

public final class CalculatorColor extends AbstractCalculator<Color> 
{
	
	public static final CalculatorColor INSTANCE = new CalculatorColor();
	
	@Override
	public Color copy(Color out, Color source) 
	{
		out.r = source.r;
		out.g = source.g;
		out.b = source.b;
		out.a = source.a;
		return out;
	}

	@Override
	public Color parse(Object input, Color defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Color( floats[0], floats[1], floats[2], floats[3] ); 
		}
		
		if (input instanceof Color)
		{
			return (Color)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Color instantiate()
	{
		return new Color();
	}

	@Override
	public Color clear(Color out, float component) 
	{
		out.r = out.g = out.b = out.a = component;
		return out;
	}

	@Override
	public Color unary(Color out, Color value, Unary unaryOperation) 
	{
		out.r = unaryOperation.unary( value.r );
		out.g = unaryOperation.unary( value.g );
		out.b = unaryOperation.unary( value.b );
		out.a = unaryOperation.unary( value.a );
		return out;
	}

	@Override
	public Color binary(Color out, Color a, Color b, Binary binaryOperation) 
	{
		out.r = binaryOperation.binary( a.r, b.r );
		out.g = binaryOperation.binary( a.g, b.g );
		out.b = binaryOperation.binary( a.b, b.b );
		out.a = binaryOperation.binary( a.b, b.a );
		return out;
	}

	@Override
	public Color adds(Color out, Color augend, Color addend, float scale) 
	{	
		out.r = augend.r + addend.r * scale;
		out.g = augend.g + addend.g * scale;
		out.b = augend.b + addend.b * scale;
		out.a = augend.a + addend.a * scale;
		return out;
	}
	
	@Override
	public Color mul( Color out, Color value, Color scale )
	{
		out.r = value.r * scale.r;
		out.g = value.g * scale.g;
		out.b = value.b * scale.b;
		out.a = value.a * scale.a;
		return out;
	}
	
	@Override
	public Color div( Color out, Color dividend, Color divisor )
	{
		out.r = Numbers.divide( dividend.r, divisor.r );
		out.g = Numbers.divide( dividend.g, divisor.g );
		out.b = Numbers.divide( dividend.b, divisor.b );
		out.a = Numbers.divide( dividend.a, divisor.a );
		return out;
	}

	@Override
	public Color interpolate( Color out, Color start, Color end, float delta )
	{
		out.r = (end.r - start.r) * delta + start.r;
		out.g = (end.g - start.g) * delta + start.g;
		out.b = (end.b - start.b) * delta + start.b;
		out.a = (end.a - start.a) * delta + start.a;
		return out;
	}
	
	@Override
	public Color contain( Color out, Color min, Color max )
	{
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.g = Numbers.clamp( out.g, min.g, max.g );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		out.a = Numbers.clamp( out.a, min.a, max.a );
		return out;
	}
    
	@Override
    public boolean contains( Color point, Color min, Color max )
    {
		return point.r >= min.r && point.r <= max.r && 
			   point.g >= min.g && point.g <= max.g && 
			   point.b >= min.b && point.b <= max.b && 
			   point.a >= min.a && point.a <= max.a;
    }

    @Override
    public Color random( Color out, Color min, Color max, Binary randomizer )
    {
    	out.r = randomizer.binary( min.r, max.r );
    	out.g = randomizer.binary( min.g, max.g );
    	out.b = randomizer.binary( min.b, max.b );
    	out.a = randomizer.binary( min.a, max.a );
    	return out;
    }
    
    @Override
    public float dot( Color a, Color b )
    {
    	return a.r * b.r + a.g * b.g + a.b * b.b + a.a * b.a;
    }
    
    @Override
	public float distance( Color a, Color b )
	{
		return Math.abs(a.r - b.r) + 
			   Math.abs(a.g - b.g) + 
			   Math.abs(a.b - b.b) + 
			   Math.abs(a.a - b.a);
	}

    @Override
	public float distanceSq( Color a, Color b )
	{
		float d = this.distance( a, b );
		
		return d * d;
	}

    @Override
    public float lengthSq( Color value ) 
    {
    	return value.r * value.r + value.g * value.g + value.b * value.b + value.a * value.a;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Color;
	}

	@Override
	public boolean isFinite( Color value )
	{
		return Float.isFinite( value.r ) && 
			   Float.isFinite( value.g ) && 
			   Float.isFinite( value.b ) && 
			   Float.isFinite( value.a );
	}
	
	@Override
	public boolean isEqual( Color a, Color b, float epsilon )
	{
		return Numbers.equals( a.r, b.r, epsilon ) &&
			   Numbers.equals( a.g, b.g, epsilon ) &&
			   Numbers.equals( a.b, b.b, epsilon ) &&
			   Numbers.equals( a.a, b.a, epsilon );
	}
	
	@Override
	public int sizeof( Color value )
	{
		return 16;
	}

	@Override
	public int write( Color value, ByteBuffer to )
	{
		to.putFloat( value.r );
		to.putFloat( value.g );
		to.putFloat( value.b );
		to.putFloat( value.a );
		return 16;
	}

	@Override
	public Color read( Color out, ByteBuffer from )
	{
		out.r = from.getFloat();
		out.g = from.getFloat();
		out.b = from.getFloat();
		out.a = from.getFloat();
		return out;
	}
	
	@Override
	public Color read( Color out, InputModel input )
	{
		out.r = input.readFloat( "r" );
		out.g = input.readFloat( "g" );
		out.b = input.readFloat( "b" );
		out.a = input.readFloat( "a" );
		return out;
	}
	
	@Override
	public void write( Color value, OutputModel output )
	{
		output.write( "r", value.r );
		output.write( "g", value.g );
		output.write( "b", value.b );
		output.write( "a", value.a );
	}

	@Override
	public int getComponents()
	{
		return 4;
	}

	@Override
	public float getComponent( Color value, int index )
	{
		switch (index) {
		case 0: return value.r;
		case 1: return value.g;
		case 2: return value.b;
		case 3: return value.a;
		}
		
		return 0;
	}

	@Override
	public Color setComponent( Color value, int index, float component )
	{
		switch (index) {
		case 0:
			value.r = component;
			break;
		case 1:
			value.g = component;
			break;
		case 2:
			value.b = component;
			break;
		case 3:
			value.a = component;
			break;
		}
		return value;
	}

	@Override
	public Color mods(Color out, Color value, float divisor) 
	{
		out.r = value.r % divisor;
		out.g = value.g % divisor;
		out.b = value.b % divisor;
		out.a = value.a % divisor;
		return out;
	}

	@Override
	public Color mod(Color out, Color value, Color divisor) 
	{
		out.r = value.r % divisor.r;
		out.g = value.g % divisor.g;
		out.b = value.b % divisor.b;
		out.a = value.a % divisor.a;
		return out;
	}

	@Override
	public Color truncates(Color out, Color value, float divisor) 
	{
		out.r = value.r - value.r % (int)divisor;
		out.g = value.g - value.g % (int)divisor;
		out.b = value.b - value.b % (int)divisor;
		out.a = value.a - value.a % (int)divisor;
		return out;
	}

	@Override
	public Color truncate(Color out, Color value, Color divisor) 
	{
		out.r = value.r - value.r % divisor.r;
		out.g = value.g - value.g % divisor.g;
		out.b = value.b - value.b % divisor.b;
		out.a = value.a - value.a % divisor.a;
		return out;
	}

	@Override
	public Color clamp(Color out, Color min, Color max) 
	{
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.g = Numbers.clamp( out.g, min.g, max.g );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		out.a = Numbers.clamp( out.a, min.a, max.a );
		return out;
	}
	
}
