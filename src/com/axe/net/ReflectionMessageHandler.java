
package com.axe.net;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class ReflectionMessageHandler<M> extends PermanentMessageHandler<M>
{

	public final Object notify;
	public final Class<M> messageType;
	public final Method notifyMethod;
	public final Type type;

	public ReflectionMessageHandler( Class<M> messageType, String methodName, Object notify )
	{
		this.notify = notify;
		this.messageType = messageType;
		this.type = findType( notify, methodName, messageType );
		this.notifyMethod = type.getMethod( notify, methodName, messageType );
	}

	@Override
	public void handle( Client client, M message, Packet packet )
	{
		try
		{
			type.invoke( notify, notifyMethod, client, message, packet );
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	public static Type findType( Object o, String methodName, Class<?> messageType )
	{
		for (Type t : Type.values())
		{
			if (t.getMethod( o, methodName, messageType ) != null)
			{
				return t;
			}
		}

		return null;
	}

	// Normal Order is: Client, Message, Packet
	public enum Type
	{
		MESSAGE( 1, -1, 0, -1 ),
		CLIENT_MESSAGE( 2, 0, 1, -1 ),
		MESSAGE_CLIENT( 2, 1, 0, -1 ),
		MESSAGE_PACKET( 2, -1, 0, 1 ),
		PACKET_MESSAGE( 2, -1, 1, 0 ),
		CLIENT_MESSAGE_PACKET( 3, 0, 1, 2 ),
		CLIENT_PACKET_MESSAGE( 3, 0, 2, 1 ),
		MESSAGE_CLIENT_PACKET( 3, 1, 0, 2 ),
		MESSAGE_PACKET_CLIENT( 3, 2, 0, 1 ),
		PACKET_MESSAGE_CLIENT( 3, 2, 1, 0 ),
		PACKET_CLIENT_MESSAGE( 3, 1, 2, 0 );

		public final int parameterCount;
		public final int clientIndex;
		public final int messageIndex;
		public final int packetIndex;

		private Type( int parameterCount, int clientIndex, int messageIndex, int packetIndex )
		{
			this.parameterCount = parameterCount;
			this.clientIndex = clientIndex;
			this.messageIndex = messageIndex;
			this.packetIndex = packetIndex;
		}

		public Method getMethod( Object obj, String methodName, Class<?> messageType )
		{
			try
			{
				return obj.getClass().getMethod( methodName, getParameterTypes( messageType ) );
			}
			catch (Exception e)
			{
				return null;
			}
		}

		public Class<?>[] getParameterTypes( Class<?> messageType )
		{
			return build( Client.class, messageType, Packet.class );
		}

		public void invoke( Object obj, Method method, Client client, Object message, Packet packet ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			method.invoke( obj, build( client, message, packet ) );
		}

		public <T> T[] build( T client, T message, T packet, T... out )
		{
			out = Arrays.copyOf( out, parameterCount );
			if (clientIndex != -1)
			{
				out[clientIndex] = client;
			}
			if (messageIndex != -1)
			{
				out[messageIndex] = message;
			}
			if (packetIndex != -1)
			{
				out[packetIndex] = packet;
			}
			return out;
		}
	}

	@SuppressWarnings ("rawtypes" )
	public static void addToRegistry( Object notify, String methodName, Class<?>... messageTypes )
	{
		for (Class<?> mt : messageTypes)
		{
			MessageRegistry.addHandler( mt, new ReflectionMessageHandler( mt, methodName, notify ) );
		}
	}

}
