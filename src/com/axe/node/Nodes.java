package com.axe.node;

import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.axe.View;
import com.axe.collect.List;
import com.axe.collect.ListItem;
import com.axe.collect.ListItemIgnored;
import com.axe.game.GameState;
import com.axe.math.Matrix;
import com.axe.math.Scalarf;
import com.axe.math.Scalari;
import com.axe.math.Vec;
import com.axe.math.calc.Calculator;
import com.axe.util.Array;
import com.axe.util.Ref;

public class Nodes<V extends Vec<V>, N extends Node<V>> extends RenderedNode<V> implements List<N>
{

	public static int DEFAULT_CAPACITY = 32;
	public static int DEFAULT_INCREASE = 16;
	public static boolean DEFAULT_EXPIRE = false;
	
	public N[] nodes;
	public int size;
	public int increase;
	public boolean hasSphere;
	public boolean hasBounds;
	public boolean expireOnEmpty;
	public boolean activated;
	public Matrix<V> localMatrix;
	public Matrix<V> matrix;

	public Nodes(N ... initial)
	{
		this( DEFAULT_CAPACITY, DEFAULT_INCREASE, DEFAULT_EXPIRE, initial );
	}
	
	public Nodes(int capacity, N ... initial)
	{
		this( capacity, DEFAULT_INCREASE, DEFAULT_EXPIRE, initial );
	}
	
	public Nodes(int capacity, boolean expireOnEmpty, N ... initial)
	{
		this( capacity, DEFAULT_INCREASE, expireOnEmpty, initial );
	}
	
	public Nodes(boolean expireOnEmpty, N ... initial)
	{
		this( DEFAULT_CAPACITY, DEFAULT_INCREASE, expireOnEmpty, initial );
	}
	
	public Nodes(int capacity, int increase, boolean expireOnEmpty, N ... initial)
	{
		this.nodes = Arrays.copyOf( initial, Math.max( capacity, initial.length ) );
		this.size = initial.length;
		this.increase = increase;
		this.expireOnEmpty = expireOnEmpty;
		this.activated = false;
	}
	
	public Nodes<V, N> setBoundary(boolean hasSphere, boolean hasBounds)
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
		for (int i = 0; i < size; i++)
		{
			Node.activateNode( nodes[ i ] );
		}
		
		activated = true;
	}

	@Override
	public void update(GameState state, View<V> firstView) 
	{
		int live = 0;
		
		for (int i = 0; i < size; i++)
		{
			N node = nodes[ i ];
			
			node.update( state, firstView );
			
			if ( node.isExpired() )
			{
				destroyNode( node );
			}
			else
			{
				nodes[ live++ ] = node;
			}
		}
		
		while (live < size)
		{
			nodes[ live++ ] = null;
		}
		
		size = live;
	}
	
	protected void destroyNode(N node)
	{
		node.destroy();
		
		Node.removeNode( this, node );
	}

	@Override
	public void draw(GameState state, View<V> view) 
	{
		for (int i = 0; i < size; i++)
		{
			Node.drawNode( nodes[ i ], state, view );
		}
	}
	
	@Override
	public boolean getBounds(GameState state, V min, V max)
	{
		if ( !hasBounds )
		{
			return false;
		}
		
		return Nodes.getBounds( state, this, min, max );
	}
	
	@Override
	public boolean getSphere(GameState state, V center, Scalarf radius)
	{
		if ( !hasSphere )
		{
			return false;
		}
		
		return Nodes.getSphere( state, this, center, radius );
	}

	@Override
	public boolean isExpired()
	{
		if ( !expireOnEmpty )
		{
			return false;
		}
		
		for (int i = 0; i < size; i++)
		{
			if ( !nodes[ i ].isExpired() )
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void expire()
	{
		for (int i = 0; i < size; i++)
		{
			nodes[ i ].expire();
		}
	}
	
	@Override
	public void destroy()
	{
		clear( true );
	}
	
	@Override
	public ListItem add(N value) 
	{
		nodes = Array.put( nodes, size++, value, increase );
		value.setParent( this );
		
		return ListItemIgnored.INSTANCE;
	}

	@Override
	public ListItem find(N value, BiPredicate<N, N> equals) 
	{
		return null;
	}

	@Override
	public void clear() 
	{
		clear( false );
	}
	
	public void clear(boolean destroy)
	{
		while (--size >= 0)
		{
			if ( destroy )
			{
				destroyNode( nodes[ size ] );
			}
			
			nodes[ size ] = null;
		}
		
		size = 0;
	}

	@Override
	public int size() 
	{
		return size;
	}

	@Override
	public int forEach(Predicate<N> predicate) 
	{
		int iterated = 0;
		
		while (iterated < size)
		{
			N node = nodes[ iterated++ ];
			
			if ( !predicate.test( node ) )
			{
				break;
			}
		}
		
		return iterated;
	}
	
	@Override
	public void forAll(Consumer<N> consumer) 
	{
		for (int i = 0; i < size; i++)
		{
			consumer.accept( nodes[ i ] );
		}
	}

	@Override
	public int forEach(BiPredicate<N, ListItem> predicate) 
	{
		int iterated = 0;
		
		while (iterated < size)
		{
			N node = nodes[ iterated++ ];
			
			if ( !predicate.test( node, ListItemIgnored.INSTANCE ) )
			{
				break;
			}
		}
		
		return iterated;
	}
	
	public static <V extends Vec<V>, N extends Node<V>> boolean getSphere(GameState state, List<N> nodes, V center, Scalarf radius)
	{
		final V centerRef = center;
		final Calculator<V> calc = center.getCalculator();
		
		// Set center and radius to the first node which has a bounding sphere.
		int searched = nodes.forEach((n) -> 
		{
			return !n.getSphere(state, centerRef, radius);
		});
		
		// If the forEach returned the number of nodes, none of the nodes have a bounding sphere.
		if ( searched >= nodes.size() )
		{
			return false;
		}
		
		// Calculate the sphere with the farthest boundary from the first sphere. 
		N far1 = getFurthestCenter( state, nodes, center );
		
		Scalarf radius1 = radius.clone();
		V center1 = calc.clone( center );
		far1.getSphere( state, center1, radius1 );
		
		// Calculate the sphere with the farthest boundary from the second sphere.
		N for2 = getFurthestCenter( state, nodes, center1 );
		
		Scalarf radius2 = radius.clone();
		V center2 = calc.clone( center );
		for2.getSphere( state, center2, radius2 );
		
		// The center of the bounding sphere is the center of the two previous spheres
		// while taking into account their radius.
		float distance = calc.distance( center1, center2 );
		float total = distance + radius1.v + radius2.v;
		float delta1 = radius1.v / total;
		float delta2 = (radius1.v + distance) / total;
		float relative1 = delta2 - delta1;
		float relative2 = 0.5f - delta1;
		float delta = relative2 / relative1;
		
		center = calc.interpolate( center, center1, center2, delta );
		radius.v = Math.max( distance * 0.5f, Math.max( radius1.v, radius2.v ) );
		
		final V centerNewRef = center;
		
		// The bounding sphere is then expanded to fit all nodes.
		nodes.forAll((n) ->
		{
			if (n.getSphere( state, center1, radius1 ))
			{
				float ndist = calc.distance( center1, centerNewRef ) + radius1.v;
				
				radius.v = Math.max( radius.v, ndist );
			}
		});

		calc.recycle( center1 );
		calc.recycle( center2 );
		
		return false;
	}
	
	public static <V extends Vec<V>, N extends Node<V>> N getFurthestCenter(GameState state, List<N> nodes, V center)
	{
		final Calculator<V> calc = center.getCalculator();
		final Ref<N> furthest = new Ref<>();
		final V tempCenter = calc.clone( center );
		final Scalarf tempRadius = new Scalarf();
		final Scalarf max = new Scalarf();
		
		nodes.forAll((n) -> 
		{
			if (n.getSphere(state, tempCenter, tempRadius))
			{
				float distance = calc.distance( center, tempCenter ) + tempRadius.v;
				
				if ( furthest.value == null || distance > max.v )
				{
					furthest.value = n;
					max.v = distance;
				}
			}
		});
		
		calc.recycle( tempCenter );
		
		return furthest.value;
	}
	
	public static <V extends Vec<V>, N extends Node<V>> boolean getBounds(GameState state, List<N> nodes, V min, V max)
	{		
		final Calculator<V> calc = min.getCalculator();
		Scalari set = new Scalari();
		V minTemp = calc.clone( min );
		V maxTemp = calc.clone( max );
		
		nodes.forAll((n) ->
		{
			if (n.getBounds(state, minTemp, maxTemp))
			{
				if (set.v == 0)
				{
					calc.copy( min, minTemp );
					calc.copy( max, maxTemp );
				}
				else
				{
					calc.binary( min, min, minTemp, Math::min );
					calc.binary( max, max, maxTemp, Math::max );
				}
				
				set.v++;
			}
		});
		
		calc.recycle( minTemp );
		calc.recycle( maxTemp );
		
		return set.v > 0;
	}
	
}