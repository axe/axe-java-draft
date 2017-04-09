package com.axe.node;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.axe.View;
import com.axe.collect.List;
import com.axe.collect.ListArray;
import com.axe.collect.ListItem;
import com.axe.collect.ListLinked;
import com.axe.collect.ListSorted;
import com.axe.game.GameState;
import com.axe.math.Matrix;
import com.axe.math.Scalarf;
import com.axe.math.Vec;


public class NodeList<V extends Vec<V>, N extends Node<V>> extends AbstractNode<V> implements List<N>
{
	
	public static <V extends Vec<V>, T extends Node<V>> NodeList<V, T> create(List<T> nodes)
	{
		return new NodeList<V, T>( nodes );
	}
	
	public static <V extends Vec<V>, T extends Node<V>> NodeList<V, T> linked()
	{
		return new NodeList<V, T>( ListLinked.create() );
	}
	
	public static <V extends Vec<V>, T extends Node<V>> NodeList<V, T> array(T ... nodes)
	{
		return new NodeList<V, T>( ListArray.create(nodes) );
	}
	
	public static <V extends Vec<V>, T extends Node<V>> NodeList<V, T> sorted(Comparator<T> comparator, T ... nodes)
	{
		return new NodeList<V, T>( ListSorted.create(comparator, nodes) );
	}
	
	public final List<N> nodes;
	public boolean hasSphere;
	public boolean hasBounds;
	public boolean activated;
	public Matrix<V> localMatrix;
	public Matrix<V> matrix;
	
	protected NodeList(List<N> nodes)
	{
		this.nodes = nodes;
		this.activated = false;
	}
	
	public NodeList<V, N> setBoundary(boolean hasSphere, boolean hasBounds)
	{
		this.hasSphere = hasSphere;
		this.hasBounds = hasBounds;
		
		return this;
	}
	
	public void setLocalMatrix(Matrix<V> matrix)
	{
		this.localMatrix = matrix;
		this.matrix = matrix != null ? matrix.create() : null;
		this.updateMatrix();
		
		Node.updateMatrices( this );
	}
	
	@Override
	public boolean isActivated()
	{
		return activated;
	}
	
	@Override
	public void activate()
	{
		nodes.forAll( Node::activateNode );
		activated = true;
	}
	
	@Override
	public void update(GameState state, View<V> firstView)
	{		
		nodes.forEach((n, item) -> 
		{
			n.update( state, firstView );
			
			if (n.isExpired()) 
			{
				item.remove( n );
				
				n.destroy();
			}
			
			if (!item.isLinked())
			{
				Node.removeNode( this, n );
			}
			
			return true;
		});
		
		nodes.update();
	}
	
	@Override
	public void draw(GameState state, View<V> view)
	{
		if ( view.getCamera().intersects( state, this ) )
		{			
			nodes.forAll((n) -> Node.drawNode(n, state, view));
		}
	}
	
	@Override
	public boolean getBounds(GameState state, V min, V max)
	{
		if ( !hasBounds )
		{
			return false;
		}
		
		return Nodes.getBounds( state, nodes, min, max );
	}
	
	@Override
	public boolean getSphere(GameState state, V center, Scalarf radius)
	{
		if ( !hasSphere )
		{
			return false;
		}
		
		return Nodes.getSphere( state, nodes, center, radius );
	}
	
	@Override
	public ListItem add(N value) 
	{
		value.setParent( this );
		
		return nodes.add( value );
	}
	
	@Override
	public void add(N[] values)
	{
		for (int i = 0; i < values.length; i++)
		{
			values[ i ].setParent( this );
		}
		
		nodes.add( values );
	}

	@Override
	public ListItem find(N value, BiPredicate<N, N> equals) 
	{
		return nodes.find( value, equals );
	}
	
	@Override
	public boolean remove(N value)
	{
		boolean removed = nodes.remove( value );
		
		if (removed)
		{
			Node.removeNode( this, value );
		}
		
		return removed;
	}

	@Override
	public void clear() 
	{
		nodes.clear();
	}

	@Override
	public int size() 
	{
		return nodes.size();
	}

	@Override
	public void forAll(Consumer<N> consume) 
	{
		nodes.forAll( consume );
	}

	@Override
	public int forEach(Predicate<N> predicate) 
	{
		return nodes.forEach( predicate );
	}

	@Override
	public int forEach(BiPredicate<N, ListItem> predicate) 
	{
		return nodes.forEach( predicate );
	}
	
	@Override
	public void where(Predicate<N> predicate, Consumer<N> consumer)
	{
		nodes.where( predicate, consumer );
	}
	
	@Override
	public int count(Predicate<N> predicate)
	{
		return nodes.count( predicate );
	}
	
	@Override
	public N first(Predicate<N> predicate)
	{
		return nodes.first( predicate );
	}
	
}