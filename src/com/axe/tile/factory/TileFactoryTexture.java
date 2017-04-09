package com.axe.tile.factory;

import com.axe.gfx.Texture;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.tile.Tile;
import com.axe.tile.TileFactory;

/**
 * TODO
 * 
 * @author Philip Diffenderfer
 *
 */
public class TileFactoryTexture implements TileFactory 
{

	/**
	 * TODO
	 */
	public Tile[] create(Texture... textures) 
	{
		int count = textures.length;
		
		Tile[] tiles = new Tile[count];
		
		for (int i = 0; i < count; i++)
		{
			tiles[i] = textures[i].tile();
		}
		
		return tiles;
	}

	@Override
	public void read( InputModel input )
	{
		
	}

	@Override
	public void write( OutputModel output )
	{
		
	}

}
