
package com.axe.gfx;

import com.axe.core.Attribute;
import com.axe.core.Factory;
import com.axe.math.calc.CalculatorCoord;


public class Coord implements Attribute<Coord>
{
	
	public static Factory<Coord> Factory = new Factory<Coord>() {
		public Coord create()
		{
			return new Coord();
		}
	};

	public float s, t;

	public Coord()
	{
	}

	public Coord( float s, float t )
	{
		set( s, t );
	}

	public void set( float s, float t )
	{
		this.s = s;
		this.t = t;
	}

	@Override
	public Coord get()
	{
		return this;
	}

	@Override
	public Coord clone()
	{
		return new Coord( s, t );
	}

	@Override
	public CalculatorCoord getCalculator()
	{
		return CalculatorCoord.INSTANCE;
	}

}
