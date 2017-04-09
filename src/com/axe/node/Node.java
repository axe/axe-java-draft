package com.axe.node;

import com.axe.View;
import com.axe.collect.List;
import com.axe.game.GameState;
import com.axe.math.Matrix;
import com.axe.math.Scalarf;
import com.axe.math.Vec;
import com.axe.math.calc.Calculator;

/**
 * The life-cycle of a node:
 * 
 * setParent is called when added to node list
 * activate is called at game start OR before the first draw (for nodes added after the game has started)
 * update node if not expired
 * if node expired, remove from parent node list
 * draw node if not expired
 * 
 * @author phil
 *
 * @param <V>
 */
public interface Node<V extends Vec<V>>
{

	public Node<V> getParent();
	
	public void setParent( Node<V> parent );
	
	default public Matrix<V> getLocalMatrix()
	{
		return null;
	}
	
	default public Matrix<V> getMatrix()
	{
		return null;
	}
	
	default public Matrix<V> getParentMatrix()
	{
		Node<V> parent = getParent();
		
		if ( parent != null )
		{
			return parent.getMatrix() != null ? parent.getMatrix() : parent.getParentMatrix();
		}
		
		return null;
	}
	
	default public void updateLocalMatrix()
	{
		
	}
	
	default public void updateMatrix()
	{
		updateLocalMatrix();
		
		Matrix<V> local = getLocalMatrix();
		Matrix<V> global = getMatrix();
		
		if (local != null && global != null)
		{
			Matrix<V> parent = getParentMatrix();
			
			if (parent != null)
			{
				global.set( parent );
				global.multiplyi( local );
			}
			else
			{
				global.set( local );
			}
		}
	}
	
	default public boolean isActivated()
	{
		return true;
	}
	
	default public void activate()
	{
		
	}
	
	public void update(GameState state, View<V> firstView);

	default public void draw(GameState state, View<V> view)
	{
		
	}

	default public boolean getBounds(GameState state, V min, V max)
	{
		final Calculator<V> calc = min.getCalculator();
		V center = calc.create();
		Scalarf radius = new Scalarf();
		boolean exists = false;
		
		if (getSphere(state, center, radius))
		{
			min = calc.clear( min, -radius.v );
			max = calc.clear( max, +radius.v );

			min = calc.addi( min, center );
			max = calc.addi( max, center );
			
			exists = true;
		}
		
		calc.recycle( center );
		
		return exists;
	}
	
	default public boolean getSphere(GameState state, V center, Scalarf radius)
	{
		final Calculator<V> calc = center.getCalculator();
		
		V min = calc.create();
		V max = calc.create();
		boolean exists = false;
		
		if (getBounds(state, min, max))
		{
			center = calc.interpolate( center, min, max, 0.5f );
			radius.v = calc.distance( min, max ) * 0.5f;
			exists = true;
		}
		
		calc.recycle( min );
		calc.recycle( max );
		
		return exists;
	}
	
	default public boolean isExpired()
	{
		return false;
	}
	
	default public void expire()
	{
		
	}
	
	default public void destroy()
	{
		
	}
	
	public static <V extends Vec<V>> void activateNode(Node<V> node)
	{
		if (!node.isExpired() && !node.isActivated())
		{
			node.activate();
		}
	}
	
	public static <V extends Vec<V>> void drawNode(Node<V> node, GameState state, View<V> view)
	{
		if (!node.isExpired())
		{
			if (!node.isActivated())
			{
				node.activate();
			}
			
			node.draw(state, view);
		}
	}
	
	public static <V extends Vec<V>> void removeNode(Node<V> parent, Node<V> child)
	{
		if (child.getParent() == parent)
		{
			child.setParent( null );
		}
	}
	
	public static <V extends Vec<V>, N extends Node<V>> void updateMatrices(List<N> nodes)
	{
		nodes.forAll( n -> n.updateMatrix() );
	}
	
}