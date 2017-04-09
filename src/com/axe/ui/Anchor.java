package com.axe.ui;

public class Anchor {
	public float base;
	public float anchor;

	public void set(float base, float anchor) {
		this.base = base;
		this.anchor = anchor;
	}

	public void set(float value, boolean relative) {
		this.base = relative ? 0 : value;
		this.anchor = relative ? value : 0;
	}

	public float get(float total) 
	{
		return base + total * anchor;
	}
	
}