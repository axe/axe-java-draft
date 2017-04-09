
package com.axe.net;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.magnos.reflect.ReflectFactory;


public class Service<T> implements InvocationHandler
{

	public static <T> Service<T> create(Class<T> serviceClass)
	{
		return new Service<T>( serviceClass );
	}
	
	private Client client;
	private Server server;
	
	private final T service;
	private final ServiceInterface serviceInterface;
	
	public Service(Class<T> serviceClass)
	{
		this.serviceInterface = serviceClass.getAnnotation( ServiceInterface.class );
		this.service = (T)Proxy.newProxyInstance( getClass().getClassLoader(), new Class<?>[] {serviceClass}, this );
	}

	public T get()
	{
		return service;
	}
	
	@Override
	public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
	{
		ServiceCall call = serviceInterface.call().newInstance();
		call.method = ReflectFactory.addMethod( method );
		call.arguments = args;
		call.methodId = method.getAnnotation( ServiceMethod.class ).id();
		
		if (server != null)
		{
			for (Client c : server.clients)
			{
				if (c != client && c.receivesBroadcasts)
				{
					c.out.add( call );	
				}
			}
		}
		else
		{
			client.out.add( call );	
		}

		return null;
	}
	
	public void setTarget( Client client )
	{
		this.server = null;
		this.client = client;
	}
	
	public void setTarget( Server server )
	{
		this.server = server;
		this.client = null;
	}
	
	public void setTarget( Server server, Client skip )
	{
		this.server = server;
		this.client = skip;
	}

}
