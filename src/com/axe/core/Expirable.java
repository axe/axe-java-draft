package com.axe.core;


public interface Expirable
{
	public void expire();
	public boolean hasExpired();
	public void onExpire();
}
