package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;

public abstract class CalculatorGeneric<T> extends AbstractCalculator<T> 
{

	protected final int values;
	protected final int size;
	protected final int components;
	protected final int[] componentToValue;
	protected final int[] componentBase;
	protected final Calculator[] calculators;
	
	public CalculatorGeneric(int valueCount)
	{
		Calculator[] calculators = new Calculator[ valueCount ];
		T test = instantiate();
		int size = 0;
		int components = 0;
		
		for (int i = 0; i < valueCount; i++)
		{
			calculators[ i ] = getCalculator( test, i );
			size += calculators[ i ].sizeof( getValue( test, i ) );
			components += calculators[ i ].getComponents();
		}
		
		int[] componentToValue = new int[ components ];
		int[] componentBase = new int[ components ];
		int componentIndex = 0;
		
		for (int i = 0; i < valueCount; i++)
		{
			int valueComponents = calculators[ i ].getComponents();
			
			for (int k = 0; k < valueComponents; k++)
			{
				componentToValue[ componentIndex ] = i;
				componentBase[ componentIndex ] = k;
				componentIndex++;
			}
		}
		
		this.values = valueCount;
		this.size = size;
		this.components = components;
		this.calculators = calculators;
		this.componentToValue = componentToValue;
		this.componentBase = componentBase;
	}
	
	protected abstract Object getValue(T out, int index);
	
	protected Calculator getCalculator(T out, int index)
	{
		return CalculatorRegistry.getFor( getValue( out, index ) );
	}

	@Override
	public T copy(T out, T source) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].copy( getValue( out, i ), getValue( source, i ) );
		}
		
		return out;
	}

	@Override
	public T parse(Object input, T defaultValue) 
	{
		return null;
	}

	@Override
	public T clear(T out, float component) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].clear( getValue( out, i ), component );
		}
		
		return out;
	}

	@Override
	public T unary(T out, T value, Unary unaryOoperation) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].unary( getValue( out, i ), getValue( value, i ), unaryOoperation );
		}
		
		return out;
	}

	@Override
	public T binary(T out, T a, T b, Binary binaryOperation) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].binary( getValue( out, i ), getValue( a, i ), getValue( b, i ), binaryOperation );
		}
		
		return out;
	}

	@Override
	public T adds(T out, T augend, T addend, float scale) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].adds( getValue( out, i ), getValue( augend, i ), getValue( addend, i ), scale );
		}
		
		return out;
	}

	@Override
	public T mul(T out, T value, T scale) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].mul( getValue( out, i ), getValue( value, i ), getValue( scale, i ) );
		}
		
		return out;
	}

	@Override
	public T div(T out, T dividend, T divisor) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].div( getValue( out, i ), getValue( dividend, i ), getValue( divisor, i ) );
		}
		
		return out;
	}

	@Override
	public T contain(T out, T min, T max) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].contain( getValue( out, i ), getValue( min, i ), getValue( max, i ) );
		}
		
		return out;
	}

	@Override
	public boolean contains(T point, T min, T max) 
	{
		for (int i = 0; i < values; i++)
		{
			if ( !calculators[ i ].contains( getValue( point, i ), getValue( min, i ), getValue( max, i ) ) )
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public T random(T out, T min, T max, Binary randomizer) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].random( getValue( out, i ), getValue( min, i ), getValue( max, i ), randomizer );
		}
		
		return out;
	}

	@Override
	public float dot(T a, T b) 
	{
		float dot = 0;
		
		for (int i = 0; i < values; i++)
		{
			dot += calculators[ i ].dot( getValue( a, i ), getValue( b, i ) );
		}
		
		return dot;
	}

	@Override
	public T mods(T out, T value, float divisor) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].mods( getValue( out, i ), getValue( value, i ), divisor );
		}
		
		return out;
	}

	@Override
	public T mod(T out, T value, T divisor) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].mod( getValue( out, i ), getValue( value, i ), getValue( divisor, i ) );
		}
		
		return out;
	}

	@Override
	public T truncates(T out, T value, float divisor) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].truncates( getValue( out, i ), getValue( value, i ), divisor );
		}
		
		return out;
	}

	@Override
	public T truncate(T out, T value, T divisor) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].truncate( getValue( out, i ), getValue( value, i ), getValue( divisor, i ) );
		}
		
		return out;
	}

	@Override
	public T clamp(T out, T min, T max) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].clamp( getValue( out, i ), getValue( min, i ), getValue( max, i ) );
		}
		
		return out;
	}

	@Override
	public boolean isValue(Object value) 
	{
		return false;
	}

	@Override
	public boolean isFinite(T value) 
	{
		for (int i = 0; i < values; i++)
		{
			if ( !calculators[ i ].isFinite( getValue( value, i ) ) )
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isEqual(T a, T b, float epsilon) 
	{		
		for (int i = 0; i < values; i++)
		{
			if ( !calculators[ i ].isEqual( getValue( a, i ), getValue( b, i ) ) )
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public int sizeof(T value) 
	{
		return size;
	}

	@Override
	public int write(T value, ByteBuffer to) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].write( getValue( value, i ), to );
		}
		
		return size;
	}

	@Override
	public T read(T out, ByteBuffer from) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].read( getValue( out, i ), from );
		}
		
		return out;
	}

	@Override
	public void write(T value, OutputModel to) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].write( getValue( value, i ), to );
		}
	}

	@Override
	public T read(T out, InputModel from) 
	{
		for (int i = 0; i < values; i++)
		{
			calculators[ i ].read( getValue( out, i ), from );
		}
		
		return out;
	}

	@Override
	public int getComponents() 
	{
		return components;
	}

	@Override
	public float getComponent(T value, int index) 
	{
		int i = componentToValue[ index ];
		
		return calculators[ i ].getComponent( getValue( value, i ), index - componentBase[ i ] );
	}

	@Override
	public T setComponent(T value, int index, float component) 
	{
		int i = componentToValue[ index ];
		
		calculators[ i ].setComponent( getValue( value, i ), index - componentBase[ i ], component );
		
		return value;
	}

}
