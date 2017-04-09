package com.axe.util;


public class IntMap<K>
{
	
	public static int DEFAULT_TABLE_SIZE = 16;
	public static int DEFAULT_INVALID_VALUE = -1;
	
	private int size;
	private final int invalidValue;
	private final int mod;
	private final Entry[] entries;
	
	public IntMap()
	{
		this( DEFAULT_TABLE_SIZE, DEFAULT_INVALID_VALUE );
	}
	
	public IntMap( int invalidValue )
	{
		this( DEFAULT_TABLE_SIZE, invalidValue );
	}
	
	public IntMap( int tableSize, int invalidValue )
	{
		this.mod = computeTableSize( tableSize );
		this.entries = (Entry[])new Object[ mod + 1 ];
		this.invalidValue = invalidValue;
	}
	
	private int computeTableSize( int requested )
	{
		return (Integer.highestOneBit( requested - 1 ) << 1) - 1;
	}
	
	public int get( K key )
	{
		int hash = key.hashCode() & mod;
		
		Entry e = entries[ hash ];
		
		while ( e != null ) {
			if ( e.key == key || e.key.equals( key ) ) {
				return e.value;
			}
			e = e.next;
		}
		
		return invalidValue;
	}
	
	public boolean exists( K key )
	{
		int hash = key.hashCode() & mod;
		
		Entry e = entries[ hash ];
		
		while ( e != null ) {
			if ( e.key == key || e.key.equals( key ) ) {
				return true;
			}
			e = e.next;
		}
		
		return false;
	}
	
	public int put( K key, int value )
	{
		int hash = key.hashCode() & mod;
		
		Entry e = entries[ hash ];
		
		while ( e != null ) {
			if ( e.key == key || e.key.equals( key ) ) {
				int previous = e.value;
				e.value = value;
				return previous;
			}
			e = e.next;
		}
		
		entries[ hash ] = new Entry( key, value, entries[ hash ] );
		size++;
		
		return invalidValue;
	}
	
	public void add( K key, int value )
	{
		int hash = key.hashCode() & mod;
		
		entries[ hash ] = new Entry( key, value, entries[ hash ] );
		size++;
	}

	public int remove( K key )
	{
		int hash = key.hashCode() & mod;
		
		Entry p = null;
		Entry e = entries[ hash ];
		
		while ( e != null ) {
			if ( e.key == key || e.key.equals( key ) ) {
				if ( p == null ) {
					entries[ hash ] = e.next;
				}
				else {
					p.next = e.next;
				}
				size--;
				return e.value;
			}
			p = e;
			e = e.next;
		}
		
		return invalidValue;
	}
	
	public int size()
	{
		return size;
	}
	
	private class Entry
	{
		private K key;
		private int value;
		private Entry next;
		
		public Entry( K key, int value, Entry next )
		{
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}
	
}
