package com.axe.math.calc;

import java.nio.ByteBuffer;

import org.magnos.asset.Assets;

import com.axe.gfx.TextureInfo;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;
import com.axe.tile.Tile;

public final class CalculatorTile extends AbstractCalculator<Tile> 
{
	
	public static final CalculatorTile INSTANCE = new CalculatorTile();

	public static final String NO_VALUE = "NO_VALUE";
	
	@Override
	public Tile copy(Tile out, Tile source) 
	{
		out.s0 = source.s0;
		out.t0 = source.t0;
		out.s1 = source.s1;
		out.t1 = source.t1;
		out.texture = source.texture;
		return out;
	}

	@Override
	public Tile parse(Object input, Tile defaultValue) 
	{
		if (input instanceof Float) 
		{
			return this.createUniform( (float)input );
		}
		
		if (input instanceof float[])
		{
			float[] floats = (float[])input;
			
			return new Tile( floats[0], floats[1], floats[2], floats[3] ); 
		}
		
		if (input instanceof Tile)
		{
			return (Tile)input;
		}
		
		return defaultValue != null ? clone( defaultValue ) : null;
	}
	
	@Override
	public Tile instantiate()
	{
		return new Tile();
	}

	@Override
	public Tile clear(Tile out, float component) 
	{
		out.s0 = out.t0 = out.s1 = out.t1 = component;
		return out;
	}

	@Override
	public Tile unary(Tile out, Tile value, Unary unaryOperation) 
	{
		out.s0 = unaryOperation.unary( value.s0 );
		out.t0 = unaryOperation.unary( value.t0 );
		out.s1 = unaryOperation.unary( value.s1 );
		out.t1 = unaryOperation.unary( value.t1 );
		return out;
	}

	@Override
	public Tile binary(Tile out, Tile a, Tile b, Binary binaryOperation) 
	{
		out.s0 = binaryOperation.binary( a.s0, b.s0 );
		out.t0 = binaryOperation.binary( a.t0, b.t0 );
		out.s1 = binaryOperation.binary( a.s1, b.s1 );
		out.t1 = binaryOperation.binary( a.s1, b.t1 );
		return out;
	}

	@Override
	public Tile adds(Tile out, Tile augend, Tile addend, float scale) 
	{	
		out.s0 = augend.s0 + addend.s0 * scale;
		out.t0 = augend.t0 + addend.t0 * scale;
		out.s1 = augend.s1 + addend.s1 * scale;
		out.t1 = augend.t1 + addend.t1 * scale;
		return out;
	}
	
	@Override
	public Tile mul( Tile out, Tile value, Tile scale )
	{
		out.s0 = value.s0 * scale.s0;
		out.t0 = value.t0 * scale.t0;
		out.s1 = value.s1 * scale.s1;
		out.t1 = value.t1 * scale.t1;
		return out;
	}
	
	@Override
	public Tile div( Tile out, Tile dividend, Tile divisor )
	{
		out.s0 = Numbers.divide( dividend.s0, divisor.s0 );
		out.t0 = Numbers.divide( dividend.t0, divisor.t0 );
		out.s1 = Numbers.divide( dividend.s1, divisor.s1 );
		out.t1 = Numbers.divide( dividend.t1, divisor.t1 );
		return out;
	}

	@Override
	public Tile interpolate( Tile out, Tile start, Tile end, float delta )
	{
		out.s0 = (end.s0 - start.s0) * delta + start.s0;
		out.t0 = (end.t0 - start.t0) * delta + start.t0;
		out.s1 = (end.s1 - start.s1) * delta + start.s1;
		out.t1 = (end.t1 - start.t1) * delta + start.t1;
		return out;
	}
	
	@Override
	public Tile contain( Tile out, Tile min, Tile max )
	{
		out.s0 = Numbers.clamp( out.s0, min.s0, max.s0 );
		out.t0 = Numbers.clamp( out.t0, min.t0, max.t0 );
		out.s1 = Numbers.clamp( out.s1, min.s1, max.s1 );
		out.t1 = Numbers.clamp( out.t1, min.t1, max.t1 );
		return out;
	}
    
	@Override
    public boolean contains( Tile point, Tile min, Tile max )
    {
		return point.s0 >= min.s0 && point.s0 <= max.s0 && 
			   point.t0 >= min.t0 && point.t0 <= max.t0 && 
			   point.s1 >= min.s1 && point.s1 <= max.s1 && 
			   point.t1 >= min.t1 && point.t1 <= max.t1;
    }

    @Override
    public Tile random( Tile out, Tile min, Tile max, Binary randomizer )
    {
    	out.s0 = randomizer.binary( min.s0, max.s0 );
    	out.t0 = randomizer.binary( min.t0, max.t0 );
    	out.s1 = randomizer.binary( min.s1, max.s1 );
    	out.t1 = randomizer.binary( min.t1, max.t1 );
    	return out;
    }
    
    @Override
    public float dot( Tile a, Tile b )
    {
    	return a.s0 * b.s0 + a.t0 * b.t0 + a.s1 * b.s1 + a.t1 * b.t1;
    }
    
    @Override
    public float distanceSq( Tile a, Tile b )
    {
    	return 1;
    }
    
    @Override
    public float distance( Tile a, Tile b )
    {
    	return 1;
    }

    @Override
    public float lengthSq( Tile value ) 
    {
    	return value.s0 * value.s0 + value.t0 * value.t0 + value.s1 * value.s1 + value.t1 * value.t1;
    }

	@Override
	public boolean isValue( Object value )
	{
		return value instanceof Tile;
	}

	@Override
	public boolean isFinite( Tile value )
	{
		return Float.isFinite( value.s0 ) && 
			   Float.isFinite( value.t0 ) && 
			   Float.isFinite( value.s1 ) && 
			   Float.isFinite( value.t1 );
	}
	
	@Override
	public boolean isEqual( Tile a, Tile b, float epsilon )
	{
		return Numbers.equals( a.s0, b.s0, epsilon ) &&
			   Numbers.equals( a.t0, b.t0, epsilon ) &&
			   Numbers.equals( a.s1, b.s1, epsilon ) &&
			   Numbers.equals( a.t1, b.t1, epsilon );
	}
	
	@Override
	public int sizeof( Tile value )
	{
		return 16;
	}

	@Override
	public int write( Tile value, ByteBuffer to )
	{
		to.putFloat( value.s0 );
		to.putFloat( value.t0 );
		to.putFloat( value.s1 );
		to.putFloat( value.t1 );
		return 16;
	}

	@Override
	public Tile read( Tile out, ByteBuffer from )
	{
		out.s0 = from.getFloat();
		out.t0 = from.getFloat();
		out.s1 = from.getFloat();
		out.t1 = from.getFloat();
		return out;
	}
	
	@Override
	public Tile read( Tile out, InputModel input )
	{
		out.s0 = input.readFloat( "s0" );
		out.t0 = input.readFloat( "t0" );
		out.s1 = input.readFloat( "s1" );
		out.t1 = input.readFloat( "t1" );
		
		String texturePath = input.readString( "texture", NO_VALUE );
		
		if (texturePath != NO_VALUE)
		{
			out.texture = Assets.load( texturePath );
		}
		else
		{
			InputModel textureModel = input.readModel( "texture" );
			String textureName = textureModel.readString( "ref" );
			TextureInfo textureInfo = textureModel.readModel( "info", new TextureInfo() ); 
			out.texture = Assets.load( textureName, textureInfo );			
		}
		
		return out;
	}
	
	@Override
	public void write( Tile value, OutputModel output )
	{
		output.write( "s0", value.s0 );
		output.write( "t0", value.t0 );
		output.write( "s1", value.s1 );
		output.write( "t1", value.t1 );
		
		TextureInfo textureInfo = value.texture.getInfo();
		OutputModel textureModel = output.writeModel( "texture" );
		textureModel.write( "ref", textureInfo.getPath() );
		textureModel.writeModel( "info", textureInfo );
	}

	@Override
	public int getComponents()
	{
		return 4;
	}

	@Override
	public float getComponent( Tile value, int index )
	{
		switch (index) {
		case 0: return value.s0;
		case 1: return value.t0;
		case 2: return value.s1;
		case 3: return value.t1;
		}
		
		return 0;
	}

	@Override
	public Tile setComponent( Tile value, int index, float component )
	{
		switch (index) {
		case 0:
			value.s0 = component;
			break;
		case 1:
			value.t0 = component;
			break;
		case 2:
			value.s1 = component;
			break;
		case 3:
			value.t1 = component;
			break;
		}
		return value;
	}

	@Override
	public Tile mods(Tile out, Tile value, float divisor) 
	{
		out.s0 = value.s0 % divisor;
		out.t0 = value.t0 % divisor;
		out.s1 = value.s1 % divisor;
		out.t1 = value.t1 % divisor;
		return out;
	}

	@Override
	public Tile mod(Tile out, Tile value, Tile divisor) 
	{
		out.s0 = value.s0 % divisor.s0;
		out.t0 = value.t0 % divisor.t0;
		out.s1 = value.s1 % divisor.s1;
		out.t1 = value.t1 % divisor.t1;
		return out;
	}

	@Override
	public Tile truncates(Tile out, Tile value, float divisor) 
	{
		out.s0 = value.s0 - value.s0 % divisor;
		out.t0 = value.t0 - value.t0 % divisor;
		out.s1 = value.s1 - value.s1 % divisor;
		out.t1 = value.t1 - value.t1 % divisor;
		return out;
	}

	@Override
	public Tile truncate(Tile out, Tile value, Tile divisor) 
	{
		out.s0 = value.s0 - value.s0 % divisor.s0;
		out.t0 = value.t0 - value.t0 % divisor.t0;
		out.s1 = value.s1 - value.s1 % divisor.s1;
		out.t1 = value.t1 - value.t1 % divisor.t1;
		return out;
	}

	@Override
	public Tile clamp(Tile out, Tile min, Tile max) 
	{
		out.s0 = Numbers.clamp( out.s0, min.s0, max.s0 );
		out.t0 = Numbers.clamp( out.t0, min.t0, max.t0 );
		out.s1 = Numbers.clamp( out.s1, min.s1, max.s1 );
		out.t1 = Numbers.clamp( out.t1, min.t1, max.t1 );
		return out;
	}
	
}
