package com.axe;

import com.axe.collect.HasListItem;
import com.axe.core.HasData;
import com.axe.game.Game;
import com.axe.game.GameState;
import com.axe.gfx.ProjectionOutside;
import com.axe.math.Matrix;
import com.axe.math.Vec2i;
import com.axe.math.Vec;
import com.axe.ui.Placement;
import com.axe.window.Window;

public interface View<V extends Vec<V>> extends HasData, HasListItem
{
	
	public <S extends Scene<V>> S getScene();

	public <C extends Camera<V>> C getCamera();

	public Placement getPlacement();

	public Window getWindow();

	default public Game getGame()
	{
		return getWindow().getGame();
	}
	
	public Matrix<V> getProjectionMatrix();
	
	public Matrix<V> getViewMatrix();
	
	public Matrix<V> getCombinedMatrix();
	
	public void update();
	
	public V project(Vec2i mouse, ProjectionOutside outside, V out);
	
	default public V project(ProjectionOutside outside, V out)
	{
		Window window = getWindow();
		
		if (!window.isFocused())
		{
			return null;
		}
		
		return project( window.getMouse().position, outside, out );
	}
	
	default public V project(V out)
	{
		Window window = getWindow();
		
		if (!window.isFocused())
		{
			return null;
		}
		
		return project( window.getMouse().position, ProjectionOutside.Ignore, out );
	}
	
	public V unproject(V point, ProjectionOutside outside, V mouseOut);
	
	default public V unproject(V point, V mouseOut)
	{
		return unproject( point, ProjectionOutside.Ignore, mouseOut );
	}

	default public void draw(GameState state) 
	{
		getScene().draw( state, this );
	}
	
}