package com.axe;

import com.axe.core.HasData;
import com.axe.game.GameState;
import com.axe.math.Vec;
import com.axe.node.Node;

public interface Camera<V extends Vec<V>> extends HasData
{

	public void update();
	
	public boolean intersects(GameState state, Node<V> node);
	
}