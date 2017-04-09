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
public class TileFactoryStatic implements TileFactory 
{
	
	/**
	 * The offset (on the x-axis) of the single tile on the texture in pixels
	 */
	public int x;
	
	/**
	 * The offset (on the y-axis) of the single tile on the texture in pixels
	 */
	public int y;

	/**
	 * The width of a tile on the texture in pixels.
	 */
	public int width;

	/**
	 * The height of a tile on the texture in pixels.
	 */
	public int height;

	
	/**
	 * Instantiates a new TileFactoryStatic.
	 */
	public TileFactoryStatic()
	{
		
	}
	
	/**
	 * Instantiates a new TileFactoryStatic.
	 * 
	 * @param x
	 * 		The offset (on the x-axis) of the grid on the texture in pixels
	 * @param y
	 * 		The offset (on the y-axis) of the grid on the texture in pixels
	 * @param frameWidth
	 * 		The width of a tile on the grid in pixels.
	 * @param frameHeight
	 * 		The height of a tile on the grid in pixels.
	 */
	public TileFactoryStatic(int x, int y, int frameWidth, int frameHeight)
	{
		set(x, y, frameWidth, frameHeight);
	}
	
	/**
	 * Sets the factories parameters.
	 * 
	 * @param x
	 * 		The offset (on the x-axis) of the grid on the texture in pixels
	 * @param y
	 * 		The offset (on the y-axis) of the grid on the texture in pixels
	 * @param frameWidth
	 * 		The width of a tile on the grid in pixels.
	 * @param frameHeight
	 * 		The height of a tile on the grid in pixels.
	 */
	public void set(int x, int y, int frameWidth, int frameHeight)
	{
		this.x = x;
		this.y = y;
		this.width = frameWidth;
		this.height = frameHeight;
	}
	
	/**
	 * Creates a new array with a single tile given the source texture. If the 
	 * frame dimensions are less then one then null is returned. For example:
	 * <pre>
	 * TileFactory f = new TileFactoryStatic();
	 * f.x = 4;
	 * f.y = 1;
	 * f.width = 4;
	 * f.height = 3;
	 * 
	 * Texture:
	 * x---------------x
	 * |               |
	 * |    +--+       |
	 * |    |0 |       |
	 * |    +--+       |
	 * |               |
	 * x---------------x
	 * </pre>
	 */
	public Tile[] create(Texture ... textures) 
	{
		// If the frame dimensions are less then 1 then this factory does not
		// have sufficient information.
		if (width < 1 || height < 1)
			return null;
		
		return new Tile[] { textures[0].tile(x, y, width, height) };
	}

	@Override
	public void read( InputModel input )
	{
		x = input.readInt( "x" );
		y = input.readInt( "y" );
		width = input.readInt( "width" );
		height = input.readInt( "height" );
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "x", x );
		output.write( "y", y );
		output.write( "width", width );
		output.write( "height", height );
	}

}
