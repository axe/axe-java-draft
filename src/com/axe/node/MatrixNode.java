package com.axe.node;

import com.axe.View;
import com.axe.game.GameState;
import com.axe.math.Matrix;
import com.axe.math.Vec;

public class MatrixNode<V extends Vec<V>> extends AbstractNode<V> 
{

	public Matrix<V> localMatrix;
	public Matrix<V> matrix;
	
	public MatrixNode(Matrix<V> localMatrix) 
	{
		this.localMatrix = localMatrix;
		this.matrix = localMatrix.create();
	}

	@Override
	public void update(GameState state, View<V> firstView) 
	{
		
	}

}
