
package com.axe.net;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import org.magnos.reflect.ReflectFactory;
import org.magnos.reflect.impl.ReflectMethod;

import com.axe.core.Factory;
import com.axe.util.ReflectionFactory;


public class ServiceMessage implements Message<ServiceCall>
{

	public int id;
	public Class<?> service;
	public ServiceInterface serviceInterface;
	public ReflectMethod[] serviceMethods;
	public Factory<? extends ServiceCall> factory;

	public ServiceMessage( Class<?> service )
	{
		this.service = service;
		this.serviceInterface = service.getAnnotation( ServiceInterface.class );
		this.serviceMethods = new ReflectMethod[256];
		this.factory = ReflectionFactory.fromClass( serviceInterface.call() );
		
		for (Method m : service.getDeclaredMethods())
		{
			ServiceMethod sm = m.getAnnotation( ServiceMethod.class );
			
			if (sm != null)
			{
				serviceMethods[sm.id()] = ReflectFactory.addMethod( m );
			}
		}
	}

	@Override
	public ServiceCall read( ByteBuffer in )
	{
		ServiceCall call = factory.create();
		call.methodId = in.get() & 0xFF;
		call.method = serviceMethods[ call.methodId ];
		call.arguments = call.method.get( in );
		
		return call;
	}

	@Override
	public void write( ByteBuffer out, ServiceCall m )
	{
		out.put( (byte)m.methodId );
		
		m.method.put( out, m.arguments );
	}

	@Override
	public int sizeOf( ServiceCall m )
	{
		return m.method.sizeOf( m.arguments ) + 1;
	}

	@Override
	public int id()
	{
		return serviceInterface.id();
	}

	@Override
	public Class<ServiceCall> type()
	{
		return (Class<ServiceCall>)serviceInterface.call();
	}

}
