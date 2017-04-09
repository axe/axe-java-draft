package com.axe.window;

import com.axe.View;
import com.axe.collect.ListArray;
import com.axe.collect.ListItem;
import com.axe.color.Color;
import com.axe.color.ColorInterface;
import com.axe.color.Colors;
import com.axe.event.EventMap;
import com.axe.input.MouseState;
import com.axe.math.Vec;
import com.axe.monitor.Monitor;
import com.axe.ui.Placement;

public abstract class AbstractWindow implements Window 
{

	public ListItem listItem;
	public EventMap events;
	public ListArray<View> views;
	public View hoverView;
	public View focusView;
	public int width;
	public int height;
	public String title;
	public boolean fullscreen;
	public int frameRate;
	public boolean vsync;
	public Placement placement;
	public Monitor monitor;
	public boolean resizable;
	public Color backgroundColor;
	public MouseState mouse;
	public Object data;
	
	public AbstractWindow()
	{
		this.events = new EventMap();
		this.views = ListArray.compacted();
		this.placement = new Placement();
		this.mouse = new MouseState();
		this.backgroundColor = Colors.Black.mutable(); 
		this.title = "Axengine Game";
	}
	
	protected abstract boolean isInitialized();
	protected abstract void applyTitle(String title);
	protected abstract void applyFullscreen(boolean fullscreen);
	protected abstract void applyVsync(boolean vsync);
	protected abstract void applyFrameRate(int frameRate);
	protected abstract void applyResizable(boolean resizable);
	protected abstract void applyPlacement(Placement placement, Monitor monitor);
	
	@Override
	public void updateMouse()
	{
		mouse.updateInside( width, height );
		
		if (mouse.insideChange)
		{
			listeners( mouse.inside ? WindowEvent.MouseEnter : WindowEvent.MouseLeave ).onEvent( this );
		}
	}
	
	@Override
	public EventMap getEvents() 
	{
		return events;
	}
	
	@Override
	public void setListItem(ListItem listItem) 
	{
		this.listItem = listItem;
	}

	@Override
	public ListArray<View> getViews() 
	{
		return views;
	}
	
	@Override
	public MouseState getMouse()
	{
		return mouse;
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

	@Override
	public <V extends Vec<V>, W extends View<V>> W getHoverView() 
	{
		return (W)hoverView;
	}

	@Override
	public <V extends Vec<V>, W extends View<V>> W getFocusView() 
	{
		return (W)focusView;
	}

	@Override
	public void setTitle(String title) 
	{
		if (this.title != title)
		{
			this.title = title;
			
			if (isInitialized())
			{
				applyTitle(title);	
			}
		}
	}
	
	@Override
	public String getTitle() 
	{
		return title;
	}

	@Override
	public void setFullscreen(boolean fullscreen) 
	{
		if (this.fullscreen != fullscreen)
		{
			this.fullscreen = fullscreen;
			
			if (isInitialized())
			{
				applyFullscreen( fullscreen );	
			}
		}
	}

	@Override
	public boolean isFullscreen() 
	{
		return fullscreen;
	}
	
	@Override
	public Color getBackground()
	{
		return backgroundColor;
	}
	
	@Override
	public void setBackground(ColorInterface backgroundColor)
	{
		this.backgroundColor.set( backgroundColor );
	}

	@Override
	public int getWidth() 
	{
		return width;
	}

	@Override
	public int getHeight() 
	{
		return height;
	}

	@Override
	public int getFrameWidth() 
	{
		return width;
	}

	@Override
	public int getFrameHeight() 
	{
		return height;
	}
	
	@Override
	public void setPlacement( Placement placement, Monitor monitor )
	{
		this.placement = placement;
		this.monitor = monitor;
		
		if (isInitialized())
		{
			applyPlacement(placement, monitor);
		}
	}
	
	@Override
	public Monitor getMonitor()
	{
		return monitor;
	}
	
	@Override
	public Placement getPlacement()
	{
		return placement;
	}

	@Override
	public void setVsync(boolean vsync) 
	{
		if (this.vsync != vsync)
		{
			this.vsync = vsync;
			
			if (isInitialized())
			{
				applyVsync( vsync );
			}
		}
	}

	@Override
	public boolean isVsync() 
	{
		return vsync;
	}

	@Override
	public int getFrameRate() 
	{
		return frameRate;
	}

	@Override
	public void setFrameRate(int frameRate) 
	{
		if (this.frameRate != frameRate)
		{
			this.frameRate = frameRate;
			
			if (isInitialized())
			{
				applyFrameRate(frameRate);
			}
		}
	}
	
	@Override
	public void setResizable(boolean resizable)
	{
		if (this.resizable != resizable)
		{
			this.resizable = resizable;
			
			if (isInitialized())
			{
				applyResizable(resizable);
			}
		}
	}
	
	@Override
	public boolean isResizable()
	{
		return resizable;
	}

	@Override
	public float getResolution() 
	{
		return (float)getFrameWidth() / getWidth();
	}
	

}
