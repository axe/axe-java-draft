package com.axe.io.xml;

import java.io.InputStream;

import org.magnos.asset.AssetInfo;
import org.magnos.asset.base.BaseAssetFormat;

import com.axe.core.Factory;
import com.axe.io.DataFormat;
import com.axe.io.DataModel;
import com.axe.io.InputModel;


public class XmlDataModelFormat<T extends DataModel> extends BaseAssetFormat
{
	
	public final Factory<T> factory;
	
	public XmlDataModelFormat( Factory<T> factory, String ... extensions )
	{
		super( extensions, DataModel.class );
		
		this.factory = factory;
	}

	@Override
	public T loadAsset(InputStream input, AssetInfo assetInfo) throws Exception 
	{
		DataFormat xml = XmlDataFormat.get();
		
		InputModel in = xml.read( input );
		
		T model = factory.create();
		
		model.read( in );
		
		return model;
	}

}
