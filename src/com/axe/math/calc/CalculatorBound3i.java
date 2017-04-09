package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Bound3i;
import com.axe.math.Numbers;

public final class CalculatorBound3i extends AbstractCalculator<Bound3i> 
{
	
	public static final CalculatorBound3i INSTANCE = new CalculatorBound3i();
	
	@Override
	public Bound3i copy(Bound3i out, Bound3i source) 
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
	public Bound3i parse(Object input, Bound3i defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof int[])
		{
			int[] ints = (int[])input;
			
			return new Bound3i( ints[0], ints[1], ints[2], ints[3], ints[4], ints[5] ); 
		}
		
		if (input instanceof Bound3i)
		{
			return (Bound3i)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Bound3i instantiate()
	{
		return new Bound3i();
	}

	@Override
	public Bound3i clear(Bound3i out, float component) 
	{
		out.l = out.t = out.r = out.b = out.n = out.f = (int)component;
		return out;
	}

	@Override
	public Bound3i unary(Bound3i out, Bound3i value, Unary unaryOperation) 
	{
		out.l = (int)unaryOperation.unary( value.l );
		out.t = (int)unaryOperation.unary( value.t );
		out.r = (int)unaryOperation.unary( value.r );
		out.b = (int)unaryOperation.unary( value.b );
		out.n = (int)unaryOperation.unary( value.n );
		out.f = (int)unaryOperation.unary( value.f );
		return out;
	}

	@Override
	public Bound3i binary(Bound3i out, Bound3i a, Bound3i b, Binary binaryOperation) 
	{
		out.l = (int)binaryOperation.binary( a.l, b.l );
		out.t = (int)binaryOperation.binary( a.t, b.t );
		out.r = (int)binaryOperation.binary( a.r, b.r );
		out.b = (int)binaryOperation.binary( a.r, b.b );
		out.n = (int)binaryOperation.binary( a.n, b.n );
		out.f = (int)binaryOperation.binary( a.f, b.f );
		return out;
	}

	@Override
	public Bound3i adds(Bound3i out, Bound3i augend, Bound3i addend, float scale) 
	{	
		out.l = (int)(augend.l + addend.l * scale);
		out.t = (int)(augend.t + addend.t * scale);
		out.r = (int)(augend.r + addend.r * scale);
		out.b = (int)(augend.b + addend.b * scale);
		out.n = (int)(augend.n + addend.n * scale);
		out.f = (int)(augend.f + addend.f * scale);
		return out;
	}
	
	@Override
	public Bound3i mul( Bound3i out, Bound3i value, Bound3i scale )
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
	public Bound3i div( Bound3i out, Bound3i dividend, Bound3i divisor )
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
	public Bound3i interpolate( Bound3i out, Bound3i start, Bound3i end, float delta )
	{
		out.l = (int)((end.l - start.l) * delta + start.l);
		out.t = (int)((end.t - start.t) * delta + start.t);
		out.r = (int)((end.r - start.r) * delta + start.r);
		out.b = (int)((end.b - start.b) * delta + start.b);
		out.n = (int)((end.n - start.n) * delta + start.n);
		out.f = (int)((end.f - start.f) * delta + start.f);
		return out;
	}
	
	@Override
	public Bound3i contain( Bound3i out, Bound3i min, Bound3i max )
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
    public boolean contains( Bound3i point, Bound3i min, Bound3i max )
    {
		return point.l >= min.l && point.l <= max.l && 
			   point.t >= min.t && point.t <= max.t && 
			   point.r >= min.r && point.r <= max.r && 
			   point.b >= min.b && point.b <= max.b && 
			   point.n >= min.n && point.n <= max.n && 
			   point.f >= min.f && point.f <= max.f;
    }

    @Override
    public Bound3i random( Bound3i out, Bound3i min, Bound3i max, Binary randomizer )
    {
    	out.l = (int)randomizer.binary( min.l, max.l );
    	out.t = (int)randomizer.binary( min.t, max.t );
    	out.r = (int)randomizer.binary( min.r, max.r );
    	out.b = (int)randomizer.binary( min.b, max.b );
    	out.n = (int)randomizer.binary( min.n, max.n );
    	out.f = (int)randomizer.binary( min.f, max.f );
    	return out;
    }
    
    @Override
    public float dot( Bound3i a, Bound3i b )
    {
    	return a.l * b.l + a.t * b.t + a.r * b.r + a.b * b.b + a.n * b.n + a.f * b.f;
    }
    
    @Override
    public float distanceSq( Bound3i a, Bound3i b )
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
    public float lengthSq( Bound3i value ) 
    {
    	return value.l * value.l + value.t * value.t + value.r * value.r + value.b * value.b + value.n * value.n + value.f * value.f;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Bound3i;
	}

	@Override
	public boolean isFinite( Bound3i value )
	{
		return Float.isFinite( value.l ) && 
			   Float.isFinite( value.t ) && 
			   Float.isFinite( value.r ) && 
			   Float.isFinite( value.b ) && 
			   Float.isFinite( value.n ) && 
			   Float.isFinite( value.f );
	}
	
	@Override
	public boolean isEqual( Bound3i a, Bound3i b, float epsilon )
	{
		return Numbers.equals( a.l, b.l, epsilon ) &&
			   Numbers.equals( a.t, b.t, epsilon ) &&
			   Numbers.equals( a.r, b.r, epsilon ) &&
			   Numbers.equals( a.b, b.b, epsilon ) &&
			   Numbers.equals( a.n, b.n, epsilon ) &&
			   Numbers.equals( a.f, b.f, epsilon );
	}
	
	@Override
	public int sizeof( Bound3i value )
	{
		return 24;
	}

	@Override
	public int write( Bound3i value, ByteBuffer to )
	{
		to.putInt( value.l );
		to.putInt( value.t );
		to.putInt( value.r );
		to.putInt( value.b );
		to.putInt( value.n );
		to.putInt( value.f );
		return 24;
	}

	@Override
	public Bound3i read( Bound3i out, ByteBuffer from )
	{
		out.l = from.getInt();
		out.t = from.getInt();
		out.r = from.getInt();
		out.b = from.getInt();
		out.n = from.getInt();
		out.f = from.getInt();
		return out;
	}
	
	@Override
	public Bound3i read( Bound3i out, InputModel input )
	{
		out.l = input.readInt( "l" );
		out.t = input.readInt( "t" );
		out.r = input.readInt( "r" );
		out.b = input.readInt( "b" );
		out.n = input.readInt( "n" );
		out.f = input.readInt( "f" );
		return out;
	}
	
	@Override
	public void write( Bound3i value, OutputModel output )
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
	public float getComponent( Bound3i value, int index )
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
	public Bound3i setComponent( Bound3i value, int index, float component )
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
		case 4:
			value.n = (int)component;
			break;
		case 5:
			value.f = (int)component;
			break;
		}
		return value;
	}

	@Override
	public Bound3i mods(Bound3i out, Bound3i value, float divisor) 
	{
		out.l = value.l % (int)divisor;
		out.t = value.t % (int)divisor;
		out.r = value.r % (int)divisor;
		out.b = value.b % (int)divisor;
		out.n = value.n % (int)divisor;
		out.f = value.f % (int)divisor;
		return out;
	}

	@Override
	public Bound3i mod(Bound3i out, Bound3i value, Bound3i divisor) 
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
	public Bound3i truncates(Bound3i out, Bound3i value, float divisor) 
	{
		out.l = value.l - value.l % (int)divisor;
		out.t = value.t - value.t % (int)divisor;
		out.r = value.r - value.r % (int)divisor;
		out.b = value.b - value.b % (int)divisor;
		out.n = value.n - value.n % (int)divisor;
		out.f = value.f - value.f % (int)divisor;
		return out;
	}

	@Override
	public Bound3i truncate(Bound3i out, Bound3i value, Bound3i divisor) 
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
	public Bound3i clamp(Bound3i out, Bound3i min, Bound3i max) 
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
