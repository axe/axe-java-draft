package com.axe;

import com.axe.collect.HasListItem;
import com.axe.core.HasData;
import com.axe.game.GameState;
import com.axe.math.Vec;
import com.axe.node.Node;

public interface Scene<V extends Vec<V>> extends HasListItem, HasData
{
	
	public Node<V> getRoot();

	public void setRoot(Node<V> root);
	
	public View<V> getView();
	
	public void setView(View<V> view);
	
	default public void update(GameState state)
	{
		Node<V> root = getRoot();
		
		if (root != null)
		{
			root.update( state, getView() );
		}
	}

	default public void draw(GameState state, View<V> view)
	{
		Node<V> root = getRoot();
		
		if (root != null)
		{
			Node.drawNode( root, state, view );
		}
	}
	
	default public void activate()
	{
		Node<V> root = getRoot();
		
		if (root != null)
		{
			Node.activateNode( root );
		}
	}
	
}
