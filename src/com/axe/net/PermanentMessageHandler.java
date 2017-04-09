
package com.axe.net;

public abstract class PermanentMessageHandler<M> implements MessageHandler<M>
{

	public boolean isExpired()
	{
		return false;
	}

}
