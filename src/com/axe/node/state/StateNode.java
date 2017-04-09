package com.axe.node.state;

import com.axe.View;
import com.axe.event.EventMap;
import com.axe.event.HasEvents;
import com.axe.game.GameState;
import com.axe.math.Vec;
import com.axe.node.Node;

public class StateNode<V extends Vec<V>, S extends Enum<S>> implements Node<V>, HasEvents
{
	
	public Node<V> child;
	public EventMap events;
	public StateManagerNode<V, S> manager;
	
	public StateNode(Node<V> child)
	{
		this.child = child;
		this.events = new EventMap();
	}

	@Override
	public Node<V> getParent()
	{
		return manager;
	}
	
	@Override
	public void setParent(Node<V> parent)
	{
		this.manager = (StateManagerNode<V, S>)parent;
	}
	
	@Override
	public void update(GameState state, View<V> firstView)
	{
		if (child != null && !child.isExpired())
		{
			child.update( state, firstView );
		}
		
		updating( state, firstView, manager );
	}
	
	@Override
	public void draw(GameState state, View<V> view)
	{
		if (child != null)
		{
			Node.drawNode( child, state, view );
		}
	}
	
	public void updating(GameState state, View<V> view, StateManagerNode<V, S> manager)
	{
		
	}
	
	public void onExit()
	{
		
	}
	
	public void onExiting()
	{
		
	}
	
	public void exiting(GameState state, View<V> view, StateManagerNode<V, S> manager)
	{
		manager.proceed();
	}
	
	public void onEnter()
	{
		
	}
	
	public void onEntering()
	{
		
	}
	
	public void onCancel()
	{
		
	}
	
	public void entering(GameState state, View<V> view, StateManagerNode<V, S> manager)
	{
		manager.proceed();
	}

	@Override
	public EventMap getEvents() 
	{
		return events;
	}

	
}
