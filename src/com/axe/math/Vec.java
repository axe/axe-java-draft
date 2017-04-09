package com.axe.math;

import com.axe.core.Attribute;

public interface Vec<V extends Vec<V>> extends Attribute<V>
{
	
	/**
     * Rotates this Vector by the given unit V and returns this.
     */
    public V rotate( V cossin );

    /**
     * Sets out to this Vector unit V by the normal and returns out.
     */
    public V rotate( V out, V cossin );

    /**
     * Returns a new Vector that is this Vector rotated by the unit V.
     */
    public V rotaten( V cossin );

    /**
     * Un-Rotates this Vector by the given unit V and returns this.
     */
    public V unrotate( V cossin );

    /**
     * Sets out to this Vector unit V by the normal and returns out.
     */
    public V unrotate( V out, V cossin );

    /**
     * Returns a new Vector that is this Vector unrotated by the unit V.
     */
    public V unrotaten( V cossin );

}
