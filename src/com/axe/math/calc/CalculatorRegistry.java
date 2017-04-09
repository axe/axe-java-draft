package com.axe.math.calc;

import java.util.HashMap;
import java.util.Map;

import com.axe.core.Attribute;
import com.axe.util.Array;

public class CalculatorRegistry 
{

	private static Calculator[] calculators = {};
	private static Map<Object, Calculator> calculatorMap = new HashMap<>();
	
	public static <T> Calculator<T> getFor( T value ) 
	{
		if ( value == null )
		{
			throw new RuntimeException( "No calculator can be determined with null" );
		}
		
		if ( value instanceof Attribute )
		{
			return ((Attribute)value).getCalculator();
		}
		
		Calculator calc = calculatorMap.get( value.getClass() );
		
		if ( calc != null )
		{
			return calc;
		}
		
		for (int i = 0; i < calculators.length; i++)
		{
			calc = calculators[ i ];
			
			if ( calc.isValue( value ) )
			{
				return calc;
			}
		}

	    throw new RuntimeException( "No calculator exists for the following value: " + value );
	}

	public static <T> Calculator<T> get( Object nameOrType ) 
	{
	    Calculator calc = calculatorMap.get( nameOrType );
	    
	    if (calc != null) 
	    {
	      return calc;
	    }
	    
	    throw new RuntimeException( "No calculator exists for the following type: " + nameOrType );
	}

	public static <T> void register( Calculator<T> calculator, Object ... namesOrTypes ) 
	{
		for (int i = 0; i < namesOrTypes.length; i++)
		{
			calculatorMap.put( namesOrTypes[ i ], calculator );
		}
		
		calculators = Array.add( calculator, calculators );
	}
	
}
