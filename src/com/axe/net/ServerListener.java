package com.axe.net;


public interface ServerListener
{
	public void onInit(Server server);
	public void onUpdateBegin(Server server);
	public void onUpdateEnd(Server server);
	public void onClientConnect(Server server, Client c);
	public void onClientFailedConnect(Server server, Client c, Exception e);
	public void onClientUpdate(Server server, Client c);
	public void onClientClose(Server server, Client c, Exception e);
	public void onAcceptError(Server server, Exception e);
}
