package com.axe3d.sprite;

import com.axe.Axe;
import com.axe.math.Vec3f;
import com.axe.sprite.PhysicsSprite;

public class PhysicsSprite3 extends PhysicsSprite<Vec3f> 
{
	
	public static final int VIEW = Axe.renderers.create();
	
	public PhysicsSprite3()
	{
		super( VIEW, Vec3f.FACTORY );
	}
		
}
