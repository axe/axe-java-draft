package com.axe;

import com.axe.collect.ListItem;
import com.axe.math.Vec;
import com.axe.ui.Placement;
import com.axe.window.Window;

public abstract class AbstractView<V extends Vec<V>> implements View<V> 
{

	public ListItem listItem;
	public Window window;
	public Placement placement;
	public Object data;
	
	public AbstractView(Window window)
	{
		this.window = window;
		this.placement = Placement.maximized();
	}
	
	@Override
	public void setListItem(ListItem listItem) 
	{
		this.listItem = listItem;
	}

	@Override
	public Placement getPlacement() 
	{
		return placement;
	}

	@Override
	public Window getWindow() 
	{
		return window;
	}

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
