package com.axe.core;


public enum Match
{
	Exact() {
		public final boolean isMatch( long set, long input ) {
			return ( set == input );
		}
	},
	All() {
		public final boolean isMatch( long set, long input ) {
			return ( set & input ) == input;
		}
	},
	AnyOf() {
		public final boolean isMatch( long set, long input ) {
			return ( set & input ) != 0;
		}
	},
	None() {
		public final boolean isMatch( long set, long input ) {
			return ( set & input ) == 0;
		}
	},
	NotAll() {
		public final boolean isMatch( long set, long input ) {
			return ( set & input ) != input;
		}
	};
	
	public abstract boolean isMatch( long set, long input );
	
}
