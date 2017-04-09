package com.axe.gfx;

import org.magnos.asset.AssetInfo;

import com.axe.resource.Resource;

public interface ShaderObject extends Resource
{
	
	public CharSequence getCode();
	
	public ShaderObjectType getType();
	
	public AssetInfo getInfo();
	
}
