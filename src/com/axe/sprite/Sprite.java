package com.axe.sprite;

import com.axe.color.Color;
import com.axe.color.ColorInterface;
import com.axe.core.Factory;
import com.axe.gfx.Blend;
import com.axe.math.Scalarf;
import com.axe.math.Vec;
import com.axe.math.Vec2f;

public class Sprite<V extends Vec<V>> extends SimpleSprite<V> 
{
	
	public final Vec2f scale;
	public final Vec2f anchor;
	public final Color color;
	public final Scalarf rotation;
	public Blend blend;
	
	public Sprite(int id, Factory<V> factory)
	{
		super( id, factory );
		
		this.scale = new Vec2f( 1f );
		this.anchor = new Vec2f( 0.5f );
		this.color = new Color();
		this.rotation = new Scalarf();
	}
	
	public Sprite<V> setScale(Vec2f scale)
	{
		this.scale.set( scale );
		
		return this;
	}
	
	public Sprite<V> setScale(float scale)
	{
		this.scale.set( scale );
		
		return this;
	}
	
	public Sprite<V> setScale(float x, float y)
	{
		this.scale.set( x, y );
		
		return this;
	}
	
	public Sprite<V> setAnchor(Vec2f anchor)
	{
		this.anchor.set( anchor );
		
		return this;
	}
	
	public Sprite<V> setAnchor(float anchor)
	{
		this.anchor.set( anchor );
		
		return this;
	}
	
	public Sprite<V> setAnchor(float x, float y)
	{
		this.anchor.set( x, y );
		
		return this;
	}
	
	public Sprite<V> setRotation(Scalarf rotation)
	{
		this.rotation.set( rotation );
		
		return this;
	}
	
	public Sprite<V> setRotation(float rotation)
	{
		this.rotation.set( rotation );
		
		return this;
	}
	
	public Sprite<V> setColor(ColorInterface color)
	{
		this.color.set( color );
		
		return this;
	}
	
	public Sprite<V> setBlend(Blend blend)
	{
		this.blend = blend;
		
		return this;
	}
	
	@Override
	public int hashCode()
	{
		return position.hashCode() ^ size.hashCode() ^ tile.hashCode() ^ 
				scale.hashCode() ^ anchor.hashCode() ^ color.hashCode() ^ 
				rotation.hashCode() ^ (blend != null ? blend.hashCode() : 0);
	}
	
}
