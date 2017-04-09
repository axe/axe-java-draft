package com.axe.tile;

import com.axe.gfx.Texture;
import com.axe.io.DataModel;

public interface TileFactory extends DataModel 
{
	public Tile[] create(Texture ... textures);
}
