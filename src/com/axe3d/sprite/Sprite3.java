package com.axe3d.sprite;

import com.axe.Axe;
import com.axe.math.Vec3f;
import com.axe.sprite.Sprite;

public class Sprite3 extends Sprite<Vec3f> 
{
	
	public static final int VIEW = Axe.renderers.create();
	
	public Sprite3()
	{
		super( VIEW, Vec3f.FACTORY );
	}
		
}
