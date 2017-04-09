package com.axe.plot;

public interface Plot<T> {
	public boolean next();
	public void reset();
	public void end();
	public T get();
}
