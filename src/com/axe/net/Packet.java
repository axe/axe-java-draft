package com.axe.net;

import java.util.ArrayList;
import java.util.List;

public class Packet
{

	public List<Object> messages = new ArrayList<Object>();
	public List<Integer> ids = new ArrayList<Integer>();
	public long packetTime;
	public int packetIndex;
	public int packetSize;
	public long receivedTime;
	
}
