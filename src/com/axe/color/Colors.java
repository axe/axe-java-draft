
package com.axe.color;

import java.util.LinkedHashMap;
import java.util.Map;

import com.axe.util.Reflections;

public class Colors
{

	public static final ImmutableColor Transparent = new ImmutableColor( 255, 255, 255, 0 );

	public static final ImmutableColor AliceBlue = new ImmutableColor( 240, 248, 255 );
	public static final ImmutableColor AntiqueWhite = new ImmutableColor( 250, 235, 215 );
	public static final ImmutableColor Aqua = new ImmutableColor( 0, 255, 255 );
	public static final ImmutableColor Aquamarine = new ImmutableColor( 127, 255, 212 );
	public static final ImmutableColor Azure = new ImmutableColor( 240, 255, 255 );
	public static final ImmutableColor Beige = new ImmutableColor( 245, 245, 220 );
	public static final ImmutableColor Bisque = new ImmutableColor( 255, 228, 196 );
	public static final ImmutableColor Black = new ImmutableColor( 0, 0, 0 );
	public static final ImmutableColor BlanchedAlmond = new ImmutableColor( 255, 235, 205 );
	public static final ImmutableColor Blue = new ImmutableColor( 0, 0, 255 );
	public static final ImmutableColor BlueViolet = new ImmutableColor( 138, 43, 226 );
	public static final ImmutableColor Brown = new ImmutableColor( 165, 42, 42 );
	public static final ImmutableColor BurlyWood = new ImmutableColor( 222, 184, 135 );
	public static final ImmutableColor CadetBlue = new ImmutableColor( 95, 158, 160 );
	public static final ImmutableColor Chartreuse = new ImmutableColor( 127, 255, 0 );
	public static final ImmutableColor Chocolate = new ImmutableColor( 210, 105, 30 );
	public static final ImmutableColor Coral = new ImmutableColor( 255, 127, 80 );
	public static final ImmutableColor CornflowerBlue = new ImmutableColor( 100, 149, 237 );;
	public static final ImmutableColor Cornsilk = new ImmutableColor( 255, 248, 220 );
	public static final ImmutableColor Crimson = new ImmutableColor( 220, 20, 60 );
	public static final ImmutableColor Cyan = new ImmutableColor( 0, 255, 255 );

	public static final ImmutableColor DarkBlue = new ImmutableColor( 0, 0, 139 );
	public static final ImmutableColor DarkCyan = new ImmutableColor( 0, 139, 139 );
	public static final ImmutableColor DarkGoldenrod = new ImmutableColor( 184, 134, 11 );
	public static final ImmutableColor DarkGray = new ImmutableColor( 169, 169, 169 );
	public static final ImmutableColor DarkGreen = new ImmutableColor( 0, 100, 0 );
	public static final ImmutableColor DarkKhaki = new ImmutableColor( 189, 183, 107 );
	public static final ImmutableColor DarkMagenta = new ImmutableColor( 139, 0, 139 );
	public static final ImmutableColor DarkOliveGreen = new ImmutableColor( 85, 107, 47 );
	public static final ImmutableColor DarkOrange = new ImmutableColor( 255, 140, 0 );
	public static final ImmutableColor DarkOrchid = new ImmutableColor( 153, 50, 204 );
	public static final ImmutableColor DarkRed = new ImmutableColor( 139, 0, 0 );
	public static final ImmutableColor DarkSalmon = new ImmutableColor( 233, 150, 122 );
	public static final ImmutableColor DarkSeaGreen = new ImmutableColor( 143, 188, 139 );
	public static final ImmutableColor DarkSlateBlue = new ImmutableColor( 72, 61, 139 );
	public static final ImmutableColor DarkSlateGray = new ImmutableColor( 47, 79, 79 );
	public static final ImmutableColor DarkTurquoise = new ImmutableColor( 0, 206, 209 );
	public static final ImmutableColor DarkViolet = new ImmutableColor( 148, 0, 211 );

	public static final ImmutableColor DeepPink = new ImmutableColor( 255, 20, 147 );
	public static final ImmutableColor DeepSkyBlue = new ImmutableColor( 0, 191, 255 );
	public static final ImmutableColor DimGray = new ImmutableColor( 105, 105, 105 );
	public static final ImmutableColor DodgerBlue = new ImmutableColor( 30, 144, 255 );
	public static final ImmutableColor Firebrick = new ImmutableColor( 178, 34, 34 );
	public static final ImmutableColor FloralWhite = new ImmutableColor( 255, 250, 240 );
	public static final ImmutableColor ForestGreen = new ImmutableColor( 34, 139, 34 );
	public static final ImmutableColor Fuchsia = new ImmutableColor( 255, 0, 255 );
	public static final ImmutableColor Gainsboro = new ImmutableColor( 220, 220, 220 );
	public static final ImmutableColor GhostWhite = new ImmutableColor( 248, 248, 255 );
	public static final ImmutableColor Gold = new ImmutableColor( 255, 215, 0 );
	public static final ImmutableColor Goldenrod = new ImmutableColor( 218, 165, 32 );
	public static final ImmutableColor Gray = new ImmutableColor( 128, 128, 128 );
	public static final ImmutableColor Green = new ImmutableColor( 0, 128, 0 );
	public static final ImmutableColor GreenYellow = new ImmutableColor( 173, 255, 47 );
	public static final ImmutableColor Honeydew = new ImmutableColor( 240, 255, 240 );
	public static final ImmutableColor HotPink = new ImmutableColor( 255, 105, 180 );
	public static final ImmutableColor IndianRed = new ImmutableColor( 205, 92, 92 );
	public static final ImmutableColor Indigo = new ImmutableColor( 75, 0, 130 );
	public static final ImmutableColor Ivory = new ImmutableColor( 255, 255, 240 );
	public static final ImmutableColor Khaki = new ImmutableColor( 240, 230, 140 );
	public static final ImmutableColor Lavender = new ImmutableColor( 230, 230, 250 );
	public static final ImmutableColor LavenderBlush = new ImmutableColor( 255, 240, 245 );
	public static final ImmutableColor LawnGreen = new ImmutableColor( 124, 252, 0 );
	public static final ImmutableColor LemonChiffon = new ImmutableColor( 255, 250, 205 );

	public static final ImmutableColor LightBlue = new ImmutableColor( 173, 216, 230 );
	public static final ImmutableColor LightCoral = new ImmutableColor( 240, 128, 128 );
	public static final ImmutableColor LightCyan = new ImmutableColor( 224, 255, 255 );
	public static final ImmutableColor LightGoldenrodYellow = new ImmutableColor( 250, 250, 210 );
	public static final ImmutableColor LightGray = new ImmutableColor( 211, 211, 211 );
	public static final ImmutableColor LightGreen = new ImmutableColor( 144, 238, 144 );
	public static final ImmutableColor LightPink = new ImmutableColor( 255, 182, 193 );
	public static final ImmutableColor LightSalmon = new ImmutableColor( 255, 160, 122 );
	public static final ImmutableColor LightSeaGreen = new ImmutableColor( 32, 178, 170 );
	public static final ImmutableColor LightSkyBlue = new ImmutableColor( 135, 206, 250 );
	public static final ImmutableColor LightSlateGray = new ImmutableColor( 119, 136, 153 );
	public static final ImmutableColor LightSteelBlue = new ImmutableColor( 176, 196, 222 );
	public static final ImmutableColor LightYellow = new ImmutableColor( 255, 255, 224 );

	public static final ImmutableColor Lime = new ImmutableColor( 0, 255, 0 );
	public static final ImmutableColor LimeGreen = new ImmutableColor( 50, 205, 50 );
	public static final ImmutableColor Linen = new ImmutableColor( 250, 240, 230 );
	public static final ImmutableColor Magenta = new ImmutableColor( 255, 0, 255 );
	public static final ImmutableColor Maroon = new ImmutableColor( 128, 0, 0 );

	public static final ImmutableColor MediumAquamarine = new ImmutableColor( 102, 205, 170 );
	public static final ImmutableColor MediumBlue = new ImmutableColor( 0, 0, 205 );
	public static final ImmutableColor MediumOrchid = new ImmutableColor( 186, 85, 211 );
	public static final ImmutableColor MediumPurple = new ImmutableColor( 147, 112, 219 );
	public static final ImmutableColor MediumSeaGreen = new ImmutableColor( 60, 179, 113 );
	public static final ImmutableColor MediumSlateBlue = new ImmutableColor( 123, 104, 238 );
	public static final ImmutableColor MediumSpringGreen = new ImmutableColor( 0, 250, 154 );
	public static final ImmutableColor MediumTurquoise = new ImmutableColor( 72, 209, 204 );
	public static final ImmutableColor MediumVioletRed = new ImmutableColor( 199, 21, 133 );

	public static final ImmutableColor MidnightBlue = new ImmutableColor( 25, 25, 112 );
	public static final ImmutableColor MintCream = new ImmutableColor( 245, 255, 250 );
	public static final ImmutableColor MistyRose = new ImmutableColor( 255, 228, 225 );
	public static final ImmutableColor Moccasin = new ImmutableColor( 255, 228, 181 );
	public static final ImmutableColor NavajoWhite = new ImmutableColor( 255, 222, 173 );
	public static final ImmutableColor Navy = new ImmutableColor( 0, 0, 128 );
	public static final ImmutableColor OldLace = new ImmutableColor( 253, 245, 230 );
	public static final ImmutableColor Olive = new ImmutableColor( 128, 128, 0 );
	public static final ImmutableColor OliveDrab = new ImmutableColor( 107, 142, 35 );
	public static final ImmutableColor Orange = new ImmutableColor( 255, 165, 0 );
	public static final ImmutableColor OrangeRed = new ImmutableColor( 255, 69, 0 );
	public static final ImmutableColor Orchid = new ImmutableColor( 218, 112, 214 );

	public static final ImmutableColor PaleGoldenrod = new ImmutableColor( 238, 232, 170 );
	public static final ImmutableColor PaleGreen = new ImmutableColor( 152, 251, 152 );
	public static final ImmutableColor PaleTurquoise = new ImmutableColor( 175, 238, 238 );
	public static final ImmutableColor PaleVioletRed = new ImmutableColor( 219, 112, 147 );

	public static final ImmutableColor PapayaWhip = new ImmutableColor( 255, 239, 213 );
	public static final ImmutableColor PeachPuff = new ImmutableColor( 255, 218, 185 );
	public static final ImmutableColor Peru = new ImmutableColor( 205, 133, 63 );
	public static final ImmutableColor Pink = new ImmutableColor( 255, 192, 203 );
	public static final ImmutableColor Plum = new ImmutableColor( 221, 160, 221 );
	public static final ImmutableColor PowderBlue = new ImmutableColor( 176, 224, 230 );
	public static final ImmutableColor Purple = new ImmutableColor( 128, 0, 128 );
	public static final ImmutableColor Red = new ImmutableColor( 255, 0, 0 );
	public static final ImmutableColor RosyBrown = new ImmutableColor( 188, 143, 143 );
	public static final ImmutableColor RoyalBlue = new ImmutableColor( 65, 105, 225 );
	public static final ImmutableColor SaddleBrown = new ImmutableColor( 139, 69, 19 );
	public static final ImmutableColor Salmon = new ImmutableColor( 250, 128, 114 );
	public static final ImmutableColor SandyBrown = new ImmutableColor( 244, 164, 96 );
	public static final ImmutableColor SeaGreen = new ImmutableColor( 46, 139, 87 );
	public static final ImmutableColor SeaShell = new ImmutableColor( 255, 245, 238 );
	public static final ImmutableColor Sienna = new ImmutableColor( 160, 82, 45 );
	public static final ImmutableColor Silver = new ImmutableColor( 192, 192, 192 );
	public static final ImmutableColor SkyBlue = new ImmutableColor( 135, 206, 235 );
	public static final ImmutableColor SlateBlue = new ImmutableColor( 106, 90, 205 );
	public static final ImmutableColor SlateGray = new ImmutableColor( 112, 128, 144 );
	public static final ImmutableColor Snow = new ImmutableColor( 255, 250, 250 );
	public static final ImmutableColor SpringGreen = new ImmutableColor( 0, 255, 127 );
	public static final ImmutableColor SteelBlue = new ImmutableColor( 70, 130, 180 );
	public static final ImmutableColor Tan = new ImmutableColor( 210, 180, 140 );
	public static final ImmutableColor Teal = new ImmutableColor( 0, 128, 128 );
	public static final ImmutableColor Thistle = new ImmutableColor( 216, 191, 216 );
	public static final ImmutableColor Tomato = new ImmutableColor( 255, 99, 71 );
	public static final ImmutableColor Turquoise = new ImmutableColor( 64, 224, 208 );
	public static final ImmutableColor Violet = new ImmutableColor( 238, 130, 238 );
	public static final ImmutableColor Wheat = new ImmutableColor( 245, 222, 179 );
	public static final ImmutableColor White = new ImmutableColor( 255, 255, 255 );
	public static final ImmutableColor WhiteSmoke = new ImmutableColor( 245, 245, 245 );
	public static final ImmutableColor Yellow = new ImmutableColor( 255, 255, 0 );
	public static final ImmutableColor YellowGreen = new ImmutableColor( 154, 205, 50 );

	public static ImmutableColor[] Colors = 
		Reflections.getStaticFieldArray( ImmutableColor.class, true, Colors.class );
	
	public static Map<String, ImmutableColor> ColorMap = 
		Reflections.getStaticFieldMap( ImmutableColor.class, true, Colors.class, new LinkedHashMap<String, ImmutableColor>() );
	
	public static Map<ImmutableColor, String> ColorMapInversed = 
		Reflections.getStaticFieldMapInversed( ImmutableColor.class, true, Colors.class, new LinkedHashMap<ImmutableColor, String>() );
	
}
