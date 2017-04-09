
package com.axe.net;

public interface MessageHandler<M>
{

	public void handle( Client client, M message, Packet packet );

	public boolean isExpired();

}
