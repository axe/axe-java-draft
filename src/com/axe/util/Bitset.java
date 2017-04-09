package com.axe.util;

import com.axe.core.Match;

public class Bitset
{
	public long x = 0;
	
	public void clear() 
	{
		x = 0;
	}
	
	public void setIndex(int i, boolean on) 
	{
		if (on) 
		{
			x |= (1L << i);
		}
		else 
		{
			x &= ~(1L << i);
		}
	}
	
	public <E extends Enum<E>> void set(E enumConstant, boolean on)
	{
		if (on) 
		{
			x |= (1L << enumConstant.ordinal());
		}
		else 
		{
			x &= ~(1L << enumConstant.ordinal());
		}
	}
	
	public void add(long y) 
	{
		x |= y;
	}
	
	public void addIf(long y, boolean condition) 
	{
		if (condition) 
		{
			x |= y;
		}
	}
	
	public <E extends Enum<E>> void add(E enumConstant) 
	{
		x |= (1L << enumConstant.ordinal());
	}
	
	public <E extends Enum<E>> void addIf(E enumConstant, boolean condition) 
	{
		if (condition) 
		{
			x |= (1L << enumConstant.ordinal());
		}
	}
	
	public void addIndex(int i) 
	{
		x |= (1L << i);
	}
	
	public void addIndexIf(int i, boolean condition) 
	{
		if (condition) 
		{
			x |= (1L << i);
		}
	}
	
	public void remove(long y) 
	{
		x &= ~y;
	}
	
	public void removeIf(long y, boolean condition) 
	{
		if (condition) 
		{
			x &= ~y;
		}
	}
	
	public <E extends Enum<E>> void remove(E enumConstant) 
	{
		x &= ~(1L << enumConstant.ordinal());
	}
	
	public <E extends Enum<E>> void removeIf(E enumConstant, boolean condition) 
	{
		if (condition) 
		{
			x &= ~(1L << enumConstant.ordinal());
		}
	}
	
	public void removeIndex(int i) 
	{
		x &= ~(1L << i);
	}
	
	public void removeIndexIf(int i, boolean condition) 
	{
		if (condition) 
		{
			x &= ~(1L << i);
		}
	}
	
	public void toggle(long y) 
	{
		x ^= y;
	}
	
	public void toggleIf(long y, boolean condition) 
	{
		if (condition) 
		{
			x ^= y;
		}
	}
	
	public <E extends Enum<E>> void toggle(E enumConstant) 
	{
		x ^= (1L << enumConstant.ordinal());
	}
	
	public <E extends Enum<E>> void toggleIf(E enumConstant, boolean condition) 
	{
		if (condition) 
		{
			x ^= (1L << enumConstant.ordinal());
		}
	}
	
	public void toggleIndex(int i) 
	{
		x ^= (1L << i);
	}
	
	public void toggleIndexIf(int i, boolean condition) 
	{
		if (condition) 
		{
			x ^= (1L << i);
		}
	}
	
	public boolean hasIndex(int i, Match match) 
	{
		return has(1L << i, match);
	}
	
	public boolean matchesIndex(int i) 
	{
		return has(1L << i, Match.All);
	}
	
	public boolean existsIndex(int i) 
	{
		return has(1L << i, Match.AnyOf);
	}
	
	public boolean equalsIndex(int i) 
	{
		return has(1L << i, Match.Exact);
	}
	
	public <E extends Enum<E>> boolean has(E enumConstant, Match match) 
	{
		return match.isMatch(x, 1L << enumConstant.ordinal());
	}
	
	public <E extends Enum<E>> boolean matches(E enumConstant) 
	{
		return has(1L << enumConstant.ordinal(), Match.All);
	}
	
	public <E extends Enum<E>> boolean exists(E enumConstant) 
	{
		return has(1L << enumConstant.ordinal(), Match.AnyOf);
	}
	
	public <E extends Enum<E>> boolean equals(E enumConstant) 
	{
		return has(1L << enumConstant.ordinal(), Match.Exact);
	}
	
	public boolean has(long y, Match match) 
	{
		return match.isMatch(x, y);
	}
	
	public boolean matches(long y) 
	{
		return has(y, Match.All);
	}
	
	public boolean exists(long y) 
	{
		return has(y, Match.AnyOf);
	}
	
	public boolean equals(long y) 
	{
		return has(y, Match.Exact);
	}
	
}