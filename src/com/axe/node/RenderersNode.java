package com.axe.node;

import java.util.Arrays;

import com.axe.Axe;
import com.axe.View;
import com.axe.game.GameState;
import com.axe.math.Scalarf;
import com.axe.math.Vec;
import com.axe.render.Renderer;

public class RenderersNode<V extends Vec<V>> extends AbstractNode<V> 
{

	public Node<V> parent;
	public Renderer<Node<V>>[] renderers;
	public Node<V> model;
	public boolean callUpdate;
	
	public RenderersNode(Node<V> model, boolean callUpdate, int ... ids) 
	{
		this.model = model;
		this.callUpdate = callUpdate;
		this.renderers = new Renderer[ ids.length ];
		
		for (int i = 0; i < ids.length; i++)
		{
			Renderer<Node<V>> template = Axe.renderers.get( ids[ i ] );
			
			this.renderers[ i ] = template.create( model );
		}
	}
	
	@Override
	public boolean isActivated()
	{
		if (!model.isActivated())
		{
			return false;
		}
		
		for (int i = 0; i < renderers.length; i++)
		{
			if (!renderers[i].isActivated(this))
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void activate()
	{
		if (!model.isActivated())
		{
			model.activate();
		}
		
		for (int i = 0; i < renderers.length; i++)
		{
			if (!renderers[i].isActivated(this))
			{
				renderers[i].activate(this);
			}
		}
	}
	
	@Override
	public void update(GameState state, View<V> firstView) 
	{
		model.update( state, firstView );
		
		if ( callUpdate && !model.isExpired() )
		{
			for (int i = 0; i < renderers.length; i++)
			{
				renderers[ i ].update( model );
			}
		}
	}
	
	@Override
	public void draw(GameState state, View<V> view)
	{
		if ( view.getCamera().intersects( state, this ) )
		{
			for (int i = 0; i < renderers.length; i++)
			{
				renderers[ i ].begin( model, state, view );
			}
			
			Node.drawNode( model, state, view );

			for (int i = renderers.length - 1; i >= 0; i--)
			{
				renderers[ i ].end( model, state, view );
			}
		}
	}
		
	@Override
	public boolean getBounds(GameState state, V min, V max)
	{
		return model.getBounds( state, min, max );
	}
	
	@Override
	public boolean getSphere(GameState state, V center, Scalarf radius)
	{
		return model.getSphere( state, center, radius );
	}
	
	@Override
	public void expire()
	{
		model.expire();
	}
	
	@Override
	public boolean isExpired()
	{
		return model.isExpired();
	}
	
	@Override
	public void destroy()
	{
		for (int i = 0; i < renderers.length; i++)
		{
			renderers[ i ].destroy( model );
		}
		
		renderers = Arrays.copyOf( renderers, 0 );
		
		model.destroy();
	}

}
