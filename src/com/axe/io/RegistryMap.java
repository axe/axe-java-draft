package com.axe.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class RegistryMap<A, B> implements Map<A, B>
{
	public static int DEFAULT_CAPACITY = 32;
	
	private Map<A, B> forward;
	private Map<B, A> backward;
	
	public RegistryMap()
	{
		this( DEFAULT_CAPACITY );
	}
	
	public RegistryMap( int initialCapacity )
	{
		forward = new HashMap<A, B>( initialCapacity );
		backward = new HashMap<B, A>( initialCapacity );
	}
	
	public B getForward(A key)
	{
		return forward.get( key );
	}
	
	public A getBackward(B key)
	{
		return backward.get( key );
	}
	
	public void add(A a, B b)
	{
		forward.put( a, b );
		backward.put( b, a );
	}
	
	@Override
	public int size()
	{
		return forward.size();
	}
	
	@Override
	public boolean isEmpty()
	{
		return forward.isEmpty();
	}
	
	@Override
	public boolean containsKey( Object key )
	{
		return forward.containsKey( key );
	}
	
	@Override
	public boolean containsValue( Object value )
	{
		return backward.containsKey( value );
	}
	
	@Override
	public B get( Object key )
	{
		return forward.get( key );
	}
	
	@Override
	public B put( A key, B value )
	{
		backward.put( value, key );
		return forward.put( key, value );
	}
	
	@Override
	public B remove( Object key )
	{
		B data = forward.remove( key );
		backward.remove( data );
		return data;
	}
	
	@Override
	public void putAll( Map<? extends A, ? extends B> m )
	{
		for ( Entry<? extends A, ? extends B> entry : m.entrySet() )
		{
			put( entry.getKey(), entry.getValue() );
		}
	}
	
	
	@Override
	public void clear()
	{
		forward.clear();
		backward.clear();
	}
	
	@Override
	public Set<A> keySet()
	{
		return forward.keySet();
	}
	
	@Override
	public Collection<B> values()
	{
		return backward.keySet();
	}
	
	@Override
	public Set<java.util.Map.Entry<A, B>> entrySet()
	{
		return forward.entrySet();
	}
	
}
