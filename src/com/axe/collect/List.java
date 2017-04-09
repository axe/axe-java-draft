package com.axe.collect;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.axe.util.Ref;

public interface List<T> 
{
	
	public static final BiPredicate EQUALS_STRICT = (a, b) -> (a == b);
	public static final BiPredicate EQUALS = (a, b) -> (a == b || a.equals(b));
	
	public ListItem add(T value);
	
	default public void add(T[] values)
	{
		for (int i = 0; i < values.length; i++)
		{
			add( values[ i ] );
		}
	}
	
	default public void add(List<T> list)
	{
		list.forAll((value) -> add(value));
	}
		
	public ListItem find(T value, BiPredicate<T, T> equals);

	default public ListItem find(T value)
	{
		return find( value, EQUALS_STRICT );
	}
	
	default public boolean remove(T value)
	{
		ListItem link = find(value);
		boolean removed = link != null;
		
		if (removed)
		{
			link.remove( value );
		}
		
		return removed;
	}
	
	public void clear();
	
	public int size();
	
	default public void update()
	{
		
	}
	
	public int forEach(Predicate<T> predicate);
	
	public int forEach(BiPredicate<T, ListItem> predicate);
	
	default public void forAll(Consumer<T> consumer)
	{
		forEach((value) -> {
			consumer.accept(value);
			return true;
		});
	}
	
	default public void forAllItem(Consumer<ListItem> consumer)
	{
		forEach((value, item) -> {
			consumer.accept(item);
			return true;
		});
	}
	
	default public void where(Predicate<T> predicate, Consumer<T> consumer)
	{
		forAll((value) -> {
			if (predicate.test(value)) {
				consumer.accept(value);
			}
		});
	}
	
	default public int count(Predicate<T> predicate)
	{
		return forEach( predicate );
	}
	
	default public T first(Predicate<T> predicate)
	{
		Ref<T> ref = new Ref<>();
		
		forEach((item) -> {
			if (ref.value == null && predicate.test(item)) {
				ref.value = item;
			}
			return ref.value == null;
		});
		
		return ref.value;
	}
	
	default public boolean exists(Predicate<T> predicate)
	{
		AtomicBoolean exists = new AtomicBoolean();
		
		forEach((item) -> {
			if (predicate.test(item)) {
				exists.set(true);
				return false;
			}
			return true;
		});
		
		return exists.get();
	}
	
}
