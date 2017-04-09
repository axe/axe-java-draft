package com.axe.tile;

import java.util.Arrays;

import com.axe.gfx.Texture;
import com.axe.tile.factory.TileFactoryGrid;
import com.axe.tile.factory.TileFactoryTexture;


public class TileSet 
{

	private int tileCount;
	private Tile[] tiles;
	
	public TileSet(int capacity) 
	{
		tiles = new Tile[capacity];
	}
	
	public void add(Tile[] t) 
	{
		for (int i = 0; i < t.length; i++) {
			tiles[tileCount++] = t[i];
		}
	}
	
	public void add(Tile[] t, int start, int end ) 
	{
		int dir = Integer.signum( end - start );
		
		for (int i = start; i != end; i += dir) 
		{
			tiles[ tileCount++ ] = t[ i ];
		}
		
		tiles[ tileCount++ ] = t[ end ];
	}
	
	public void add(Tile[] t, int ... indices) 
	{
		for (int i = 0; i < indices.length; i++) {
			tiles[tileCount++] = t[indices[i]];
		}
	}
	
	public void add(Texture tex, int x, int y, int columns, int frames, int frameWidth, int frameHeight) 
	{
		add( new TileFactoryGrid(x, y, columns, frames, frameWidth, frameHeight).create(new Texture[] {tex}) );
	}
	
	public void add(Texture ... texs)
	{
		add( new TileFactoryTexture().create(texs) );
	}
	
	public Tile get(int index) 
	{
		return tiles[index];
	}
	
	public int size() 
	{
		return tileCount;
	}
	
	public Tile[] tiles() 
	{
		return (tileCount == tiles.length ?
			tiles : 	Arrays.copyOf( tiles, tileCount ));
	}
	
}
