package com.axe.node.state;

import com.axe.View;
import com.axe.game.GameState;
import com.axe.math.Vec;
import com.axe.node.Node;

public class StateManagerNode<V extends Vec<V>, S extends Enum<S>> implements Node<V>
{
	
	public Node<V> parent;
	public S currentState;
	public S nextState;
	public StateNode<V, S>[] states;
	public StateManagerState managerState;
	
	public StateManagerNode(Class<S> stateEnum) 
	{
		this.states = new StateNode[ stateEnum.getEnumConstants().length ];
		this.managerState = StateManagerState.Inactive;
	}

	@Override
	public Node<V> getParent()
	{
		return parent;
	}
	
	@Override
	public void setParent(Node<V> parent)
	{
		this.parent = parent;
	}
	
	@Override
	public boolean isActivated()
	{
		StateNode<V, S> current = get( currentState );
		
		return current == null || current.isActivated();
	}
	
	@Override
	public void activate()
	{
		StateNode<V, S> current = get( currentState );
		
		if ( !current.isActivated() )
		{
			current.activate();
		}
	}
	
	@Override 
	public void draw(GameState state, View<V> view)
	{
		StateNode<V, S> current = get( currentState );
		
		if (current != null)
		{
			Node.drawNode( current, state, view );
		}
	}
	
	@Override
	public void update(GameState state, View<V> firstView) 
	{
		StateNode<V, S> current = get( currentState );
		
		switch (managerState) 
		{
		case Inactive:
			break;
			
		case Entering:
			current.entering(state, firstView, this);
			break;
			
		case Exiting:
			current.exiting(state, firstView, this);
			break;
			
		case Active:
			current.update(state, firstView);
			break;
		}
	}
	
	public StateNode<V, S> add(S state, StateNode<V, S> node)
	{
		states[ state.ordinal() ] = node;
		
		node.manager = this;
		
		return node;
	}
	
	public StateNode<V, S> get(S state)
	{
		return state == null ? null : states[ state.ordinal() ];
	}
	
	public void set(S state)
	{
		StateNode<V, S> next = get( state );
		StateNode<V, S> current = get( currentState );
		
		if ( next != null && nextState != state && currentState != state )
		{
			if (current == null)
			{
				currentState = state;
				managerState = StateManagerState.Entering;
				next.onEntering();
				next.listeners( StateNodeEvent.Entering ).onState( next );
			}
			else
			{
				if (managerState != StateManagerState.Active) 
				{
					current.onCancel();
				}
				
				nextState = state;
				managerState = StateManagerState.Exiting;
				current.onExiting();
				current.listeners( StateNodeEvent.Exiting ).onState( current );
			}
		}
	}
	
	public void proceed()
	{
		StateNode<V, S> current = get( currentState );
		StateNode<V, S> next = get( nextState );
		
		switch (managerState)
		{
		case Exiting:
			current.onExit();
			current.listeners( StateNodeEvent.Exit ).onState( current );
			managerState = StateManagerState.Entering;
			currentState = nextState;
			nextState = null;
			next.onEntering();
			next.listeners( StateNodeEvent.Entering ).onState( next );
			break;
			
		case Entering:
			managerState = StateManagerState.Active;
			current.onEnter();
			current.listeners( StateNodeEvent.Enter ).onState( current );
			break;
			
		default: 
			break;
		}
	}
	
}
