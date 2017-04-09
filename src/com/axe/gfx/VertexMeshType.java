package com.axe.gfx;

public enum VertexMeshType
{
	/* Static = set once, draw many times */
	/* Dynamic = set each frame draw many times */
	/* Stream = one time use */
	
	Static,
	
	StaticRead,
	
	StaticCopy,
	
	Dynamic,
	
	DynamicRead,
	
	DynamicCopy,
	
	Stream,
	
	StreamRead,
	
	StreamCopy
	
}
