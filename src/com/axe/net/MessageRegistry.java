
package com.axe.net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MessageRegistry
{

	private static Map<Class<?>, Message<?>> messageTypeMap = new HashMap<Class<?>, Message<?>>();
	private static Map<Integer, Message<?>> messageIdMap = new HashMap<Integer, Message<?>>();
	private static Map<Integer, Set<MessageHandler<?>>> handlerIdMap = new HashMap<Integer, Set<MessageHandler<?>>>();
	private static Map<Class<?>, Set<MessageHandler<?>>> handlerTypeMap = new HashMap<Class<?>, Set<MessageHandler<?>>>();

	public static <M> Message<M> getMessage( M o )
	{
		assert o != null : "You cannot send a null object as a message.";

		Message<M> message = (Message<M>)messageTypeMap.get( o.getClass() );

		assert message != null : "The object '" + o + "' has no associated message.";

		return message;
	}

	public static Message<?> getMessage( int id )
	{
		Message<?> message = messageIdMap.get( id );

		assert message != null : "The id " + id + " has no associated message.";

		return message;
	}

	public static <M> void addMessage( Message<M> message )
	{
		assert message != null : "You cannot add a null message.";
		assert message.type() != null : "The message type cannot be null for message " + message.getClass() + " with id " + message.id();

		messageTypeMap.put( message.type(), message );
		messageIdMap.put( message.id(), message );
	}

	public static <M> void addHandler( int id, Class<M> type, MessageHandler<M> handler )
	{
		if (messageIdMap.containsKey( id ))
		{
			addToMapSet( id, handler, handlerIdMap );
		}

		if (type != null && messageTypeMap.containsKey( type ))
		{
			addToMapSet( type, handler, handlerTypeMap );
		}
	}
	
	public static <M> void addHandler( int id, MessageHandler<M> handler)
	{
		addToMapSet( id, handler, handlerIdMap );
	}
	
	public static <M> void addHandler( Class<M> type, MessageHandler<M> handler)
	{
		addToMapSet( type, handler, handlerTypeMap );
	}

	private static <K, V> void addToMapSet( K key, V value, Map<K, Set<V>> target )
	{
		Set<V> valueSet = target.get( key );

		if (valueSet == null)
		{
			valueSet = new HashSet<V>();

			target.put( key, valueSet );
		}

		valueSet.add( value );
	}

	public static <M> void handleMessage( Client client, M message, int messageId, Packet packet )
	{
		handleMessageWithSet( client, message, packet, handlerIdMap.get( messageId ) );
		handleMessageWithSet( client, message, packet, handlerTypeMap.get( message.getClass() ) );
	}

	private static <M> void handleMessageWithSet( Client client, M message, Packet packet, Set<MessageHandler<?>> handlerSet )
	{
		if (handlerSet != null)
		{
			Iterator<MessageHandler<?>> iter = handlerSet.iterator();

			while (iter.hasNext())
			{
				MessageHandler<M> handler = (MessageHandler<M>)iter.next();
				
				if (handler.isExpired())
				{
					iter.remove();
				}
				else
				{
					handler.handle( client, message, packet );
				}
			}
		}
	}

}
