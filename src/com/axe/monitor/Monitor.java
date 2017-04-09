package com.axe.monitor;

import com.axe.core.HasData;

public interface Monitor extends HasData
{

	public int getWidth();
	
	public int getHeight();
	
	public int getRefreshRate();
	
	public boolean isPrimary();
	
	public int getX();
	
	public int getY();
	
	public String getName();
	
	public boolean isValid();
	
}
