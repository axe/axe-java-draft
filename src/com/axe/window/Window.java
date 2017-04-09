package com.axe.window;

import com.axe.Axe;
import com.axe.Scene;
import com.axe.View;
import com.axe.collect.HasListItem;
import com.axe.collect.ListArray;
import com.axe.color.Color;
import com.axe.color.ColorInterface;
import com.axe.core.HasData;
import com.axe.event.HasEvents;
import com.axe.game.Game;
import com.axe.game.GameState;
import com.axe.input.MouseState;
import com.axe.math.Vec;
import com.axe.monitor.Monitor;
import com.axe.ui.Placement;

public interface Window extends HasEvents, HasListItem, HasData
{
	
	public Game getGame();
	
	public void update();
	
	public void draw(GameState state);
	
	public boolean isFocused();
	
	public boolean isHidden();
	
	public boolean isCloseRequest();
	
	public MouseState getMouse();
	
	public void updateMouse();
	
	public ListArray<View> getViews();
	
	public <V extends Vec<V>, W extends View<V>> W newView(Scene<V> scene);
	
	public <V extends Vec<V>, W extends View<V>> W getHoverView();
	
	public <V extends Vec<V>, W extends View<V>> W getFocusView();

	public void setTitle(String title);
	
	public String getTitle();

	public void setFullscreen(boolean fullscreen);
	
	public boolean isFullscreen();
	
	public Color getBackground();
	
	public void setBackground(ColorInterface backgroundColor);
	
	public int getWidth();
	
	public int getHeight();
	
	public int getFrameWidth();
	
	public int getFrameHeight();
	
	public void setVsync(boolean vsync);
	
	public boolean isVsync();
	
	public void setResizable(boolean resizable);
	
	public boolean isResizable();

	default public void setPlacement( Placement placement )
	{
		setPlacement( placement, Axe.monitors.getPrimary() );
	}
	
	public void setPlacement( Placement placement, Monitor monitor );
	
	public Monitor getMonitor();
	
	public Placement getPlacement();
	
	public int getFrameRate();
	
	public void setFrameRate(int frameRate);
	
	public float getResolution();
	
	public void close();
	
	public void hide();
	
	public void show();
	
	public void minimize();
	
	public void maximize();
	
	public void focus();
	
}