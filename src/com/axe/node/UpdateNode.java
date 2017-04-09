package com.axe.node;

import com.axe.View;
import com.axe.game.GameState;
import com.axe.math.Scalarf;
import com.axe.math.Vec;

public class UpdateNode<V extends Vec<V>> extends AbstractNode<V> 
{
	
	public static float DEFAULT_RATE = 0;
	public static boolean DEFAULT_EXACT = false;
	public static boolean DEFAULT_ENABLED = true;
	public static float DEFAULT_TIME_SCALE = 1.0f;

	public Node<V> child;
	public float rate;
	public boolean exact;
	public boolean enabled;
	public float timeScale;
	
	protected float time;
	
	public UpdateNode(Node<V> child)
	{
		this( child, DEFAULT_RATE, DEFAULT_EXACT, DEFAULT_ENABLED, DEFAULT_TIME_SCALE );
	}
	
	public UpdateNode(Node<V> child, float timeScale) 
	{
		this( child, DEFAULT_RATE, DEFAULT_EXACT, DEFAULT_ENABLED, timeScale );
	}
	
	public UpdateNode(Node<V> child, float rate, boolean exact)
	{
		this( child, rate, exact, DEFAULT_ENABLED, DEFAULT_TIME_SCALE );
	}
	
	public UpdateNode(Node<V> child, float rate, boolean exact, boolean enabled, float timeScale)
	{
		this.child = child;
		this.rate = rate;
		this.exact = exact;
		this.enabled = enabled;
		this.timeScale = timeScale;
	}
	
	@Override
	public boolean isActivated()
	{
		return child.isActivated();
	}
	
	@Override
	public void activate()
	{
		child.activate();
	}
	
	@Override
	public void update(GameState state, View<V> firstView) 
	{
		if ( !enabled )
		{
			return;
		}
		
		int updates = 1;
		
		if (rate > 0)
		{
			time += state.seconds;
			
			updates = (int)(time / rate);
			
			time -= updates * rate;
			
			if (!exact && updates > 0)
			{
				updates = 1;
			}
		}
		
		long nanos = state.nanos;
		float scale = timeScale;
		
		if (scale != 1.0f)
		{
			state.setElapsed( (long)(nanos * timeScale) );
		}

		while (--updates >= 0)
		{
			child.update( state, firstView );	
		}
		
		if (scale != 1.0f)
		{
			state.setElapsed( nanos );
		}
	}
	
	@Override
	public void draw(GameState state, View<V> view)
	{
		Node.drawNode( child, state, view );
	}
	
	@Override
	public boolean getBounds(GameState state, V min, V max)
	{
		return child.getBounds( state, min, max );
	}
	
	@Override
	public boolean getSphere(GameState state, V center, Scalarf radius)
	{
		return child.getSphere( state, center, radius );
	}

	@Override
	public void expire()
	{
		child.expire();
	}
	
	@Override
	public boolean isExpired()
	{
		return child.isExpired();
	}
	
	@Override
	public void destroy()
	{
		child.destroy();
	}

}
