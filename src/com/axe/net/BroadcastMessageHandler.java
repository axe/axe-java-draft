package com.axe.net;


public class BroadcastMessageHandler<M> extends PermanentMessageHandler<M>
{

	public final boolean echo;
	
	public BroadcastMessageHandler(boolean echo)
	{
		this.echo = echo;
	}
	
	@Override
	public void handle( Client client, M message, Packet packet )
	{
		client.server.broadcast( message, echo ? null : client );
	}
	
	@SuppressWarnings ({ "rawtypes", "unchecked" } )
	public static void addToRegistry(boolean echo, Class<?> ... messageTypes)
	{
		for (Class<?> mt : messageTypes)
		{
			MessageRegistry.addHandler( mt, new BroadcastMessageHandler( echo ) );
		}
	}

}
