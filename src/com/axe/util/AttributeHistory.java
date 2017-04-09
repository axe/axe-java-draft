
package com.axe.util;

import com.axe.core.Attribute;
import com.axe.easing.Easing;
import com.axe.easing.Easings;
import com.axe.math.calc.Calculator;


/**
 * A circular queue of attributes added at a specific time. New attributes can
 * be added given a time, where time is expected to be non-zero, increasing, and
 * will never overflow. An attribute value can be calculated any point in time
 * within the history using an interpolation function with the Easing class (by
 * default {@link Easings#Default}). Attributes can be inserted at a specific
 * time however this is not recommended.
 * 
 * @author Philip Diffenderfer
 * 
 * @param <T>
 *        The attribute type.
 */
public class AttributeHistory<T>
{

	private final Calculator<T> calculator;
	private final int capacity;
	private T[] history;
	private long[] times;
	private int index;

	public AttributeHistory( Calculator<T> calculator, int historySize, T ... initial )
	{
		this.calculator = calculator;
		this.capacity = historySize;
		this.history = calculator.createArray( historySize, initial );
		this.times = new long[ capacity ];
	}

	public void clear( long time, T value )
	{
		for (int i = 0; i < capacity; i++)
		{
			calculator.copy( history[i], value );
			times[i] = time;
		}
	}

	public void insertOrAdd( long time, T value )
	{
		if (!insert( time, value ))
		{
			add( time, value );
		}
	}

	public boolean insert( long time, T value )
	{
		int j = getBeforeIndex( time );

		if (j == -1)
		{
			return false;
		}

		int k = previous( index );
		int e = index;

		while (k != j)
		{
			calculator.copy( history[k], history[e] );
			times[e] = times[k];

			e = k;
			k = previous( k );
		}

		calculator.copy( history[e], value );
		times[e] = time;

		return true;
	}

	public void add( long time, T value )
	{
		calculator.copy( history[index], value );
		times[index] = time;

		index = next( index );
	}

	public T get( long time, T attribute )
	{
		return get( time, attribute, Easings.Default );
	}

	public T get( long time, T attribute, Easing easing )
	{
		int j = getBeforeIndex( time );

		if (j == -1)
		{
			return null;
		}

		int i = next( j );

		float d0 = (float)(time - times[j]) / (float)(times[i] - times[j]);
		float d1 = easing.delta( d0 );

		calculator.interpolate( attribute, history[j], history[i], d1 );

		return attribute;
	}

	public T getNearest( long time, Attribute<T> attribute )
	{
		int nearest = getNearestIndex( time );

		if (nearest == -1)
		{
			return null;
		}

		attribute.set( history[nearest] );

		return attribute.get();
	}

	public long getNearestTime( long time, long empty )
	{
		int nearest = getNearestIndex( time );

		if (nearest == -1)
		{
			return empty;
		}

		return times[nearest];
	}

	public boolean exists( long time )
	{
		return (time >= firstTime() && time <= lastTime());
	}

	public int capacity()
	{
		return capacity;
	}

	public int size()
	{
		int size = 0;

		for (int i = 0; i < capacity; i++)
		{
			if (times[i] != 0)
			{
				size++;
			}
		}

		return size;
	}

	public long firstTime()
	{
		return times[index];
	}

	public T first()
	{
		return history[index];
	}

	public long lastTime()
	{
		return times[previous( index )];
	}

	public T last()
	{
		return history[previous( index )];
	}

	public long minTime()
	{
		return times[minIndex()];
	}

	public T min()
	{
		return history[minIndex()];
	}

	public long maxTime()
	{
		return times[maxIndex()];
	}

	public T max()
	{
		return history[maxIndex()];
	}

	private int getBeforeIndex( long time )
	{
		final int last = previous( index );
		int i = index;
		int j = index;

		while (i != last && time < times[i])
		{
			j = i;
			i = next( i );
		}

		if (i == j || time > times[i] || time < times[j])
		{
			return -1;
		}

		return j;
	}

	private int getNearestIndex( long time )
	{
		long nearest = Long.MAX_VALUE;
		int nearestIndex = -1;

		for (int i = 0; i < capacity; i++)
		{
			if (times[i] != 0)
			{
				long dif = Math.abs( time - times[i] );

				if (dif < nearest)
				{
					nearest = dif;
					nearestIndex = i;
				}
			}
		}

		return nearestIndex;
	}

	private int minIndex()
	{
		long min = Long.MAX_VALUE;
		int minIndex = -1;

		for (int i = 0; i < capacity; i++)
		{
			if (times[i] < min)
			{
				min = times[i];
				minIndex = i;
			}
		}

		return minIndex;
	}

	private int maxIndex()
	{
		long max = Long.MIN_VALUE;
		int maxIndex = -1;

		for (int i = 0; i < capacity; i++)
		{
			if (times[i] > max)
			{
				max = times[i];
				maxIndex = i;
			}
		}

		return maxIndex;
	}

	private final int previous( int i )
	{
		return (i + capacity - 1) % capacity;
	}

	private final int next( int i )
	{
		return (i + 1) % capacity;
	}

}
