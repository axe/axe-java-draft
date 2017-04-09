package com.axe.gfx;


public abstract class AbstractTexture implements Texture 
{
	
	public TextureInfo info;
	
	// original width of the image in pixels
	public int imageWidth;
	// original height of the image in pixels
	public int imageHeight;

	// computed image width in pixels
	public int	width;
	// computed image height in pixels
	public int	height;

	// the normalized width of a pixel (1 / width)
	public float pixelW;
	// half of the width of a pixel (used for coordinate correction).
	public float offW;

	// the normalized height of a pixel (1 / height)
	public float pixelH;
	// half of the height of a pixel (used for coordinate correction).
	public float offH;

	// The texture wrapping function
	public Wrap wrap = Wrap.Default;
	// The texture minification function
	public Minify min = Minify.Linear;
	// The texture magnification function
	public Magnify mag = Magnify.Linear;

	// The anisotropic filter
	public int anisotrophy = 1;
	
	
	public AbstractTexture(TextureInfo info, int imageWidth, int imageHeight, int textureWidth, int textureHeight)
	{	
		this.info = info;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.width = textureWidth;
		this.pixelW = 1f / textureWidth;
		this.offW = pixelW * 0.5f;
		this.height = textureHeight;
		this.pixelH = 1f / textureHeight;
		this.offH = pixelH * 0.5f;
	}

	@Override
	public TextureInfo getInfo() 
	{
		return info;
	}

	@Override
	public int width() 
	{
		return width;
	}

	@Override
	public int height() 
	{
		return height;
	}

	@Override
	public int getImageHeight() 
	{
		return imageHeight;
	}

	@Override
	public int getImageWidth() 
	{
		return imageWidth;
	}

	@Override
	public float getPixelWidth() 
	{
		return pixelW;
	}

	@Override
	public float getPixelOffsetX() 
	{
		return offW;
	}

	@Override
	public float getPixelHeight() 
	{
		return pixelH;
	}

	@Override
	public float getPixelOffsetY() 
	{
		return offH;
	}

	@Override
	public Magnify magnify() 
	{
		return mag;
	}

	@Override
	public void magnify(Magnify mag) 
	{
		this.mag = mag;
	}

	@Override
	public Minify minify() 
	{
		return min;
	}

	@Override
	public void minify(Minify min) 
	{
		this.min = min;
	}

	@Override
	public Wrap wrap() 
	{
		return wrap;
	}

	@Override
	public void wrap(Wrap wrap) 
	{
		this.wrap = wrap;
	}

	@Override
	public int anisotrophy() 
	{
		return anisotrophy;
	}

	@Override
	public void anisotrophy(int anisotrophy) 
	{
		this.anisotrophy = anisotrophy;
	}

}
