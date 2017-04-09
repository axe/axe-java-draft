
package com.axe.render;

import com.axe.Axe;
import com.axe.View;
import com.axe.game.GameState;

public class RendererHandle<T>
{

	public Renderer<T> renderer;
	public final T entity;

	public RendererHandle( T entity, int view )
	{
		this.entity = entity;
		this.set( view );
	}

	public RendererHandle( T entity )
	{
		this.entity = entity;
	}

	public void set( int view )
	{
		Renderer<T> renderer = Axe.renderers.get( view );

		set( renderer );
	}

	public void refresh()
	{
		set( renderer );
	}

	public void set( Renderer<T> newRenderer )
	{
		if (renderer != null)
		{
			renderer.destroy( entity );
			renderer = null;
		}

		if (newRenderer != null)
		{
			renderer = newRenderer.create( entity );
		}
	}

	public void begin( GameState state, View view )
	{
		if (renderer != null)
		{
			renderer.begin( entity, state, view );
		}
	}

	public void end( GameState state, View view )
	{
		if (renderer != null)
		{
			renderer.end( entity, state, view );
		}
	}

	public void draw( GameState state, View view )
	{
		if (renderer != null)
		{
			renderer.begin( entity, state, view );
			renderer.end( entity, state, view );
		}
	}

	public void destroy()
	{
		if (renderer != null)
		{
			renderer.destroy( entity );
			renderer = null;
		}
	}

	public boolean hasRenderer()
	{
		return renderer != null;
	}

	public static <T, R extends Renderer<T>> R take( T entity, int view )
	{
		R renderer = Axe.renderers.get( view );

		return (renderer == null ? null : (R)renderer.create( entity ));
	}

}
