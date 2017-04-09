package com.axe.node;

import com.axe.Axe;
import com.axe.View;
import com.axe.game.GameState;
import com.axe.math.Vec;
import com.axe.render.Renderer;

public class RenderedNode<V extends Vec<V>> extends AbstractNode<V>
{

	public Renderer<RenderedNode<V>> renderer;

	public RenderedNode() 
	{	
	}
	
	public RenderedNode(int id)
	{
		this.setRenderer( id );
	}
	
	@Override
	public boolean isActivated()
	{
		return renderer == null || renderer.isActivated( this );
	}
	
	@Override
	public void activate()
	{
		renderer.activate( this );
	}
	
	@Override
	public void update(GameState state, View<V> firstView)
	{
		if (renderer != null)
		{
			renderer.update( this );	
		}
	}
	
	@Override
	public void draw(GameState state, View<V> view)
	{
		if (renderer != null)
		{
			renderer.begin( this, state, view );
			
			indraw( state, view );
			
			renderer.end( this, state, view );
		}
	}
	
	protected void indraw(GameState state, View<V> view)
	{
		
	}
	
	public void setRenderer(int id)
	{
		destroyRenderer();
		
		renderer = Axe.renderers.get( id );
		
		if (renderer != null)
		{
			renderer = renderer.create( this );
		}
	}
	
	@Override
	public void destroy()
	{
		destroyRenderer();
	}
	
	protected void destroyRenderer()
	{
		if (renderer != null)
		{
			renderer.destroy( this );
			renderer = null;
		}
	}
	
}
