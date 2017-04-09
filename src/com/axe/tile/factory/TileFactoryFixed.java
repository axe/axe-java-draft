package com.axe.tile.factory;

import java.util.ArrayList;
import java.util.List;

import com.axe.gfx.Texture;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Vec2i;
import com.axe.tile.Tile;
import com.axe.tile.TileFactory;


/**
 * TODO
 * 
 * @author Philip Diffenderfer
 *
 */
public class TileFactoryFixed implements TileFactory 
{
	
	/**
	 * The width of a tile on the texture in pixels.
	 */
	public int frameWidth;
	
	/**
	 * The height of a tile on the texture in pixels.
	 */
	public int frameHeight;
	
	// The list of points that make up the offsets of the created frames.
	private List<Vec2i> offsets = new ArrayList<Vec2i>();

	/**
	 * Instantiates a new TileFactoryFixed.
	 */
	public TileFactoryFixed() 
	{
		
	}

	/**
	 * Instantiates a new TileFactoryFixed.
	 * 
	 * @param frameWidth
	 * 		The width of a tile on the texture in pixels.
	 * @param frameHeight
	 * 		The height of a tile on the texture in pixels.
	 */
	public TileFactoryFixed(int frameWidth, int frameHeight)
	{
		set(frameWidth, frameHeight);
	}
	
	/**
	 * Sets the factories parameters.
	 * 
	 * @param frameWidth
	 * 		The width of a tile on the texture in pixels.
	 * @param frameHeight
	 * 		The height of a tile on the texture in pixels.
	 */
	public void set(int frameWidth, int frameHeight)
	{
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}
	
	/**
	 * Creates a new array of tiles given the source texture. If the number of 
	 * offsets or the frame dimensions is less then one then null is returned.
	 * The tiles returned will all have the same dimensions but different
	 * locations according the the offsets given (in the same order added).
	 * For example:
	 * <pre>
	 * TileFactory f = new TileFactorySizeFixed();
	 * f.frameWidth = 4;
	 * f.frameHeight = 3;
	 * f.addFrame(0, 0);
	 * f.addFrame(6, 0);
	 * f.addFrame(2, 5);
	 * f.addFrame(5, 3);
	 * 
	 * Texture:
	 * x--------------x
	 * |+--+  +--+    |
	 * ||0 |  |1 |    |
	 * |+--+  +--+    |
	 * |     +--+     |
	 * |     |3 |     |
	 * |  +--+--+     |
	 * |  |2 |        |
	 * |  +--+        |
	 * x--------------x
	 * 
	 * </pre>
	 */
	public Tile[] create(Texture ... textures) 
	{
		// If the number of offsets or the frame dimensions is less then 1
		// then this factory does not have sufficient information.
		if (offsets.size() < 1 || frameWidth < 1 || frameHeight  < 1)
			return null;
		
		int frames = offsets.size();

		// A tile for each offset
		Tile[] tiles = new Tile[frames];
		
		// Start at the 0th frame.
		int frame = 0;
		Vec2i offset;
		
		while (frame < frames)
		{
			offset = offsets.get(frame);
			// Create the tile based on the current offset
			tiles[frame] = textures[0].tile(offset.x, offset.y, frameWidth, frameHeight);
			
			// Increment the number of frames.
			frame++;
		}
		
		return tiles;
	}
	
	/**
	 * Clears all existing frame offsets from the factory.
	 */
	public void clear()
	{
		offsets.clear();
	}
	
	/**
	 * Adds the offset of the frame to the factory list.
	 */
	public void addFrame(Vec2i offset)
	{
		offsets.add(offset);
	}
	
	/**
	 * Adds the offset of the frame to the factory list.
	 */
	public void addFrame(int x, int y)
	{
		offsets.add(new Vec2i(x, y));
	}

	@Override
	public void read( InputModel input )
	{
		frameWidth = input.readInt( "frame-width" );
		frameHeight = input.readInt( "frame-height" );
		offsets = input.readModelList( "offset", Vec2i.class );
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "frame-width", frameWidth );
		output.write( "frame-height", frameHeight );
		output.writeModelList( "offset", offsets );
	}
	
}
