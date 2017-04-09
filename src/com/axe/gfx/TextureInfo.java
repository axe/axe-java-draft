
package com.axe.gfx;

import org.magnos.asset.base.BaseAssetInfo;

import com.axe.io.DataModel;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class TextureInfo extends BaseAssetInfo implements DataModel
{

	protected boolean mipmap;
	protected Wrap wrap;
	protected Minify minify;
	protected Magnify magnify;
	protected int anisotrophy = 1;

	public TextureInfo()
	{
		this( defaultMipMap, defaultWrap, defaultMinify, defaultMagnify, defaultAnisotrophy );
	}

	public TextureInfo( boolean mipmap, Wrap wrap )
	{
		this( mipmap, wrap, defaultMinify, defaultMagnify, defaultAnisotrophy );
	}

	public TextureInfo( boolean mipmap, Wrap wrap, Minify minify, Magnify magnify )
	{
		this( mipmap, wrap, minify, magnify, defaultAnisotrophy );
	}

	public TextureInfo( boolean mipmap, Wrap wrap, Minify minify, Magnify magnify, int anisotrophy )
	{
		super( Texture.class );
		
		this.mipmap = mipmap;
		this.wrap = wrap;
		this.minify = minify;
		this.magnify = magnify;
		this.anisotrophy = anisotrophy;
	}

	public boolean isMipMap()
	{
		return mipmap;
	}

	public void setMipMap( boolean mipmap )
	{
		this.mipmap = mipmap;
	}

	public Wrap getWrap()
	{
		return wrap;
	}

	public void setWrap( Wrap wrap )
	{
		this.wrap = wrap;
	}

	public Minify getMinify()
	{
		return minify;
	}

	public void setMinify( Minify minify )
	{
		this.minify = minify;
	}

	public Magnify getMagnify()
	{
		return magnify;
	}

	public void setMagnify( Magnify magnify )
	{
		this.magnify = magnify;
	}

	public int getAnisotrophy()
	{
		return anisotrophy;
	}

	public void setAnisotrophy( int anisotrophy )
	{
		this.anisotrophy = anisotrophy;
	}

	public boolean equals( Object o )
	{
		if (o instanceof TextureInfo)
		{
			TextureInfo other = (TextureInfo)o;
			return (other.anisotrophy == anisotrophy &&
						other.mipmap == mipmap &&
						other.magnify == magnify &&
						other.minify == minify && other.wrap == wrap);
		}
		return false;
	}

	protected static boolean defaultMipMap = true;
	protected static Wrap defaultWrap = Wrap.Edge;
	protected static Minify defaultMinify = Minify.Linear;
	protected static Magnify defaultMagnify = Magnify.Linear;
	protected static int defaultAnisotrophy = 1;

	public static Wrap getDefaultWrap()
	{
		return defaultWrap;
	}

	public static void setDefaultWrap( Wrap wrap )
	{
		defaultWrap = wrap;
	}

	public static Minify getDefaultMinify()
	{
		return defaultMinify;
	}

	public static void setDefaultMinify( Minify minify )
	{
		defaultMinify = minify;
	}

	public static Magnify getDefaultMagnify()
	{
		return defaultMagnify;
	}

	public static void setDefaultMagnify( Magnify magnify )
	{
		defaultMagnify = magnify;
	}

	public static int getDefaultAnisotrophy()
	{
		return defaultAnisotrophy;
	}

	public static void setDefaultAnisotrophy( int anisotrophy )
	{
		defaultAnisotrophy = anisotrophy;
	}

	@Override
	public void read( InputModel input )
	{
		mipmap = input.readBoolean( "mipmap" );
		wrap = input.readEnum( "wrap", Wrap.class );
		minify = input.readEnum( "minify", Minify.class );
		magnify = input.readEnum( "magnify", Magnify.class );
		anisotrophy = input.readInt( "anistrophy" );
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "mipmap", mipmap );
		output.writeEnum( "wrap", wrap );
		output.writeEnum( "minify", minify );
		output.writeEnum( "magnify", magnify );
		output.write( "anistrophy", anisotrophy );
	}

}
