package com.axe.resource;

import org.magnos.asset.AssetInfo;
import org.magnos.asset.FutureAssetFactory;
import org.magnos.asset.base.BaseFutureAsset;

public class ResourceFutureAsset extends BaseFutureAsset<Resource> 
{
	
	public static final FutureAssetFactory<Resource> FACTORY = 
		(assetInfo) -> new ResourceFutureAsset( assetInfo );  
			
	public ResourceFutureAsset(AssetInfo info) 
	{
		super(info);
	}

	@Override
	public void loaded()
	{
		Resource resource = get();
		
		if (resource != null && !resource.isActivated())
		{
			resource.activate();
		}
	}
	
}
