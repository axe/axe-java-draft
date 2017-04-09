package com.axe.render;

import com.axe.View;
import com.axe.game.GameState;

public interface Renderer<T> 
{
	
	public static final int NONE = -1;

	default public Renderer<T> create(T entity) 
	{
		return this;
	}
	
	default public boolean isActivated(T entity)
	{
		return true;
	}
	
	default public void activate(T entity)
	{
		
	}

	public void begin(T entity, GameState state, View view);

	default public void end(T entity, GameState state, View view) 
	{
		// nothing
	}

	default public void update(T entity) 
	{
		// nothing
	}

	default public void destroy(T entity) 
	{
		// nothing
	}
	
}