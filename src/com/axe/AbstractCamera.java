package com.axe;

import com.axe.core.HasData;
import com.axe.math.Vec;

public abstract class AbstractCamera<V extends Vec<V>> implements Camera<V>, HasData
{
	
	public Object data;

	@Override
	public <T> T getData() 
	{
		return (T) data;
	}

	@Override
	public void setData(Object data) 
	{
		this.data = data;
	}
	
}