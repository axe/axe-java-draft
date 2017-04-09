package com.axe.net;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServiceMessageHandler implements MessageHandler<ServiceCall>
{

	public ServiceBase service;
	public AtomicBoolean expired = new AtomicBoolean( false );
	
	public ServiceMessageHandler(ServiceBase service)
	{
		this.service = service;
	}
	
	public ServiceMessageHandler(ServiceBase service, AtomicBoolean expired)
	{
		this.service = service;
		this.expired = expired;
	}
	
	@Override
	public void handle( Client client, ServiceCall m, Packet packet )
	{
		service.setCallSource( client, packet );
		
		try
		{
			m.method.getMethod().invoke( service, m.arguments );
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	@Override
	public boolean isExpired()
	{
		return expired.get();
	}

}
