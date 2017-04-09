package com.axe.math.calc;

import java.nio.ByteBuffer;

import com.axe.io.InputModel;
import com.axe3d.Camera3;

public class CalculatorCamera3 extends CalculatorGeneric<Camera3> 
{
	
	public static final CalculatorCamera3 INSTANCE = new CalculatorCamera3();

	public CalculatorCamera3() 
	{
		super(8);
	}

	@Override
	protected Object getValue(Camera3 out, int index) 
	{
		switch (index) {
		case 0: return out.yaw;
		case 1: return out.pitch;
		case 2: return out.roll;
		case 3: return out.distance;
		case 4: return out.focus;
		case 5: return out.fov;
		case 6: return out.near;
		case 7: return out.far;
		}
		return null;
	}

	@Override
	protected Calculator getCalculator(Camera3 out, int index) 
	{
		switch (index) {
		case 0: return CalculatorScalarf.INSTANCE;
		case 1: return CalculatorScalarf.INSTANCE;
		case 2: return CalculatorScalarf.INSTANCE;
		case 3: return CalculatorScalarf.INSTANCE;
		case 4: return CalculatorVec3f.INSTANCE;
		case 5: return CalculatorScalarf.INSTANCE;
		case 6: return CalculatorScalarf.INSTANCE;
		case 7: return CalculatorScalarf.INSTANCE;
		}
		return null;
	}

	@Override
	protected Camera3 instantiate() 
	{
		return new Camera3();
	}
	
	@Override
	public boolean isValue(Object value)
	{
		return value instanceof Camera3;
	}
	
	@Override
	public float distanceSq(Camera3 a, Camera3 b)
	{
		return a.position.distanceSq( b.position );
	}
	
	@Override
	public Camera3 read(Camera3 out, InputModel input)
	{
		super.read( out, input );
		
		out.update();
		
		return out;
	}
	
	@Override
	public Camera3 read(Camera3 out, ByteBuffer from)
	{
		super.read( out, from );
		
		out.update();
		
		return out;
	}

}
