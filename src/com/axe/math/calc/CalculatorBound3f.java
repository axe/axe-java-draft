package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Bound3f;
import com.axe.math.Numbers;

public final class CalculatorBound3f extends AbstractCalculator<Bound3f> 
{
	
	public static final CalculatorBound3f INSTANCE = new CalculatorBound3f();
	
	@Override
	public Bound3f copy(Bound3f out, Bound3f source) 
	{
		out.l = source.l;
		out.t = source.t;
		out.r = source.r;
		out.b = source.b;
		out.n = source.n;
		out.f = source.f;
		return out;
	}

	@Override
	public Bound3f parse(Object input, Bound3f defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Bound3f( floats[0], floats[1], floats[2], floats[3], floats[4], floats[5] ); 
		}
		
		if (input instanceof Bound3f)
		{
			return (Bound3f)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Bound3f instantiate()
	{
		return new Bound3f();
	}

	@Override
	public Bound3f clear(Bound3f out, float component) 
	{
		out.l = out.t = out.r = out.b = out.n = out.f = component;
		return out;
	}

	@Override
	public Bound3f unary(Bound3f out, Bound3f value, Unary unaryOperation) 
	{
		out.l = unaryOperation.unary( value.l );
		out.t = unaryOperation.unary( value.t );
		out.r = unaryOperation.unary( value.r );
		out.b = unaryOperation.unary( value.b );
		out.n = unaryOperation.unary( value.n );
		out.f = unaryOperation.unary( value.f );
		return out;
	}

	@Override
	public Bound3f binary(Bound3f out, Bound3f a, Bound3f b, Binary binaryOperation) 
	{
		out.l = binaryOperation.binary( a.l, b.l );
		out.t = binaryOperation.binary( a.t, b.t );
		out.r = binaryOperation.binary( a.r, b.r );
		out.b = binaryOperation.binary( a.r, b.b );
		out.n = binaryOperation.binary( a.n, b.n );
		out.f = binaryOperation.binary( a.f, b.f );
		return out;
	}

	@Override
	public Bound3f adds(Bound3f out, Bound3f augend, Bound3f addend, float scale) 
	{	
		out.l = augend.l + addend.l * scale;
		out.t = augend.t + addend.t * scale;
		out.r = augend.r + addend.r * scale;
		out.b = augend.b + addend.b * scale;
		out.n = augend.n + addend.n * scale;
		out.f = augend.f + addend.f * scale;
		return out;
	}
	
	@Override
	public Bound3f mul( Bound3f out, Bound3f value, Bound3f scale )
	{
		out.l = value.l * scale.l;
		out.t = value.t * scale.t;
		out.r = value.r * scale.r;
		out.b = value.b * scale.b;
		out.n = value.n * scale.n;
		out.f = value.f * scale.f;
		return out;
	}
	
	@Override
	public Bound3f div( Bound3f out, Bound3f dividend, Bound3f divisor )
	{
		out.l = Numbers.divide( dividend.l, divisor.l );
		out.t = Numbers.divide( dividend.t, divisor.t );
		out.r = Numbers.divide( dividend.r, divisor.r );
		out.b = Numbers.divide( dividend.b, divisor.b );
		out.n = Numbers.divide( dividend.n, divisor.n );
		out.f = Numbers.divide( dividend.f, divisor.f );
		return out;
	}

	@Override
	public Bound3f interpolate( Bound3f out, Bound3f start, Bound3f end, float delta )
	{
		out.l = (end.l - start.l) * delta + start.l;
		out.t = (end.t - start.t) * delta + start.t;
		out.r = (end.r - start.r) * delta + start.r;
		out.b = (end.b - start.b) * delta + start.b;
		out.n = (end.n - start.n) * delta + start.n;
		out.f = (end.f - start.f) * delta + start.f;
		return out;
	}
	
	@Override
	public Bound3f contain( Bound3f out, Bound3f min, Bound3f max )
	{
		out.l = Numbers.clamp( out.l, min.l, max.l );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		out.n = Numbers.clamp( out.n, min.n, max.n );
		out.f = Numbers.clamp( out.f, min.f, max.f );
		return out;
	}
    
	@Override
    public boolean contains( Bound3f point, Bound3f min, Bound3f max )
    {
		return point.l >= min.l && point.l <= max.l && 
			   point.t >= min.t && point.t <= max.t && 
			   point.r >= min.r && point.r <= max.r && 
			   point.b >= min.b && point.b <= max.b && 
			   point.n >= min.n && point.n <= max.n && 
			   point.f >= min.f && point.f <= max.f;
    }

    @Override
    public Bound3f random( Bound3f out, Bound3f min, Bound3f max, Binary randomizer )
    {
    	out.l = randomizer.binary( min.l, max.l );
    	out.t = randomizer.binary( min.t, max.t );
    	out.r = randomizer.binary( min.r, max.r );
    	out.b = randomizer.binary( min.b, max.b );
    	out.n = randomizer.binary( min.n, max.n );
    	out.f = randomizer.binary( min.f, max.f );
    	return out;
    }
    
    @Override
    public float dot( Bound3f a, Bound3f b )
    {
    	return a.l * b.l + a.t * b.t + a.r * b.r + a.b * b.b + a.n * b.n + a.f * b.f;
    }
    
    @Override
    public float distanceSq( Bound3f a, Bound3f b )
    {
    	float dl = a.l - b.l;
    	float dt = a.t - b.t;
    	float dr = a.r - b.r;
    	float db = a.b - b.b;
    	float dn = a.n - b.n;
    	float df = a.f - b.f;
    	
    	return dl * dl + dt * dt + dr * dr + db * db + dn * dn + df * df; 
    }

    @Override
    public float lengthSq( Bound3f value ) 
    {
    	return value.l * value.l + value.t * value.t + value.r * value.r + value.b * value.b + value.n * value.n + value.f * value.f;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Bound3f;
	}

	@Override
	public boolean isFinite( Bound3f value )
	{
		return Float.isFinite( value.l ) && 
			   Float.isFinite( value.t ) && 
			   Float.isFinite( value.r ) && 
			   Float.isFinite( value.b ) && 
			   Float.isFinite( value.n ) && 
			   Float.isFinite( value.f );
	}
	
	@Override
	public boolean isEqual( Bound3f a, Bound3f b, float epsilon )
	{
		return Numbers.equals( a.l, b.l, epsilon ) &&
			   Numbers.equals( a.t, b.t, epsilon ) &&
			   Numbers.equals( a.r, b.r, epsilon ) &&
			   Numbers.equals( a.b, b.b, epsilon ) &&
			   Numbers.equals( a.n, b.n, epsilon ) &&
			   Numbers.equals( a.f, b.f, epsilon );
	}
	
	@Override
	public int sizeof( Bound3f value )
	{
		return 24;
	}

	@Override
	public int write( Bound3f value, ByteBuffer to )
	{
		to.putFloat( value.l );
		to.putFloat( value.t );
		to.putFloat( value.r );
		to.putFloat( value.b );
		to.putFloat( value.n );
		to.putFloat( value.f );
		return 24;
	}

	@Override
	public Bound3f read( Bound3f out, ByteBuffer from )
	{
		out.l = from.getFloat();
		out.t = from.getFloat();
		out.r = from.getFloat();
		out.b = from.getFloat();
		out.n = from.getFloat();
		out.f = from.getFloat();
		return out;
	}
	
	@Override
	public Bound3f read( Bound3f out, InputModel input )
	{
		out.l = input.readFloat( "l" );
		out.t = input.readFloat( "t" );
		out.r = input.readFloat( "r" );
		out.b = input.readFloat( "b" );
		out.n = input.readFloat( "n" );
		out.f = input.readFloat( "f" );
		return out;
	}
	
	@Override
	public void write( Bound3f value, OutputModel output )
	{
		output.write( "l", value.l );
		output.write( "t", value.t );
		output.write( "r", value.r );
		output.write( "b", value.b );
		output.write( "n", value.n );
		output.write( "f", value.f );
	}

	@Override
	public int getComponents()
	{
		return 6;
	}

	@Override
	public float getComponent( Bound3f value, int index )
	{
		switch (index) {
		case 0: return value.l;
		case 1: return value.t;
		case 2: return value.r;
		case 3: return value.b;
		case 4: return value.n;
		case 5: return value.f;
		}
		
		return 0;
	}

	@Override
	public Bound3f setComponent( Bound3f value, int index, float component )
	{
		switch (index) {
		case 0:
			value.l = component;
			break;
		case 1:
			value.t = component;
			break;
		case 2:
			value.r = component;
			break;
		case 3:
			value.b = component;
			break;
		case 4:
			value.n = component;
			break;
		case 5:
			value.f = component;
			break;
		}
		return value;
	}

	@Override
	public Bound3f mods(Bound3f out, Bound3f value, float divisor) 
	{
		out.l = value.l % divisor;
		out.t = value.t % divisor;
		out.r = value.r % divisor;
		out.b = value.b % divisor;
		out.n = value.n % divisor;
		out.f = value.f % divisor;
		return out;
	}

	@Override
	public Bound3f mod(Bound3f out, Bound3f value, Bound3f divisor) 
	{
		out.l = value.l % divisor.l;
		out.t = value.t % divisor.t;
		out.r = value.r % divisor.r;
		out.b = value.b % divisor.b;
		out.n = value.n % divisor.n;
		out.f = value.f % divisor.f;
		return out;
	}

	@Override
	public Bound3f truncates(Bound3f out, Bound3f value, float divisor) 
	{
		out.l = value.l - value.l % divisor;
		out.t = value.t - value.t % divisor;
		out.r = value.r - value.r % divisor;
		out.b = value.b - value.b % divisor;
		out.n = value.n - value.n % divisor;
		out.f = value.f - value.f % divisor;
		return out;
	}

	@Override
	public Bound3f truncate(Bound3f out, Bound3f value, Bound3f divisor) 
	{
		out.l = value.l - value.l % divisor.l;
		out.t = value.t - value.t % divisor.t;
		out.r = value.r - value.r % divisor.r;
		out.b = value.b - value.b % divisor.b;
		out.n = value.n - value.n % divisor.n;
		out.f = value.f - value.f % divisor.f;
		return out;
	}

	@Override
	public Bound3f clamp(Bound3f out, Bound3f min, Bound3f max) 
	{
		out.l = Numbers.clamp( out.l, min.l, max.l );
		out.t = Numbers.clamp( out.t, min.t, max.t );
		out.r = Numbers.clamp( out.r, min.r, max.r );
		out.b = Numbers.clamp( out.b, min.b, max.b );
		out.n = Numbers.clamp( out.n, min.n, max.n );
		out.f = Numbers.clamp( out.f, min.f, max.f );
		return out;
	}
	
}
