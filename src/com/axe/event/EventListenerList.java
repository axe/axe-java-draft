package com.axe.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.axe.collect.ListArray;

public class EventListenerList<L> extends ListArray<L> implements InvocationHandler 
{
	
	public final L proxy;

	public EventListenerList(Class<L> listenerClass, L ... initial ) 
	{
		super( DEFAULT_INITIAL_CAPACITY, DEFAULT_INCREASE, false, initial );
		
		final ClassLoader loader = getClass().getClassLoader();
		final Class[] interfaces = { listenerClass };
		
		this.proxy = (L) Proxy.newProxyInstance( loader, interfaces, this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
	{
		Object result = null;
		
		for (int i = 0; i < size; i++)
		{
			L listener = values[ i ];
			
			if (listener != null)
			{
				result = method.invoke(listener, args);
			}
		}
		
		return result;
	}
	
}