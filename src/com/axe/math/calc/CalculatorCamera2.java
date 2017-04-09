package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe2d.Camera2;

public class CalculatorCamera2 extends CalculatorGeneric<Camera2> 
{
	
	public static final CalculatorCamera2 INSTANCE = new CalculatorCamera2();

	public CalculatorCamera2() 
	{
		super(6);
	}

	@Override
	protected Object getValue(Camera2 out, int index) 
	{
		switch (index) {
		case 0: return out.roll;
		case 1: return out.center;
		case 2: return out.scale;
		case 3: return out.size;
		case 4: return out.near;
		case 5: return out.far;
		}
		return null;
	}

	@Override
	protected Calculator getCalculator(Camera2 out, int index) 
	{
		switch (index) {
		case 0: return CalculatorScalarf.INSTANCE;
		case 1: return CalculatorVec2f.INSTANCE;
		case 2: return CalculatorVec2f.INSTANCE;
		case 3: return CalculatorVec2f.INSTANCE;
		case 4: return CalculatorScalarf.INSTANCE;
		case 5: return CalculatorScalarf.INSTANCE;
		}
		return null;
	}

	@Override
	protected Camera2 instantiate() 
	{
		return new Camera2();
	}
	
	@Override
	public boolean isValue(Object value)
	{
		return value instanceof Camera2;
	}
	
	@Override
	public float distanceSq(Camera2 a, Camera2 b)
	{
		return a.center.distanceSq( b.center );
	}
	
	@Override
	public Camera2 read(Camera2 out, InputModel input)
	{
		super.read( out, input );
		
		out.update();
		
		return out;
	}
	
	@Override
	public Camera2 read(Camera2 out, ByteBuffer from)
	{
		super.read( out, from );
		
		out.update();
		
		return out;
	}
	

}
