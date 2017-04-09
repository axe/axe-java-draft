
package com.axe.gfx;

import java.util.Arrays;

import com.axe.core.Factory;
import com.axe.util.Array;


public class VertexFormatBuilder implements Factory<VertexFormat>
{

	public Primitive primitive = Primitive.Triangle;
	public int vertexDimension = 3; // 2,3,4
	public DataType vertexType = DataType.Float;
	public int colorComponents = 0; // 0,3,4
	public DataType colorType = DataType.Byte;
	public int secondaryColorComponents = 0; // 0,3,4
	public DataType secondaryColorType = DataType.Byte;
	public int normalDimension = 0; // 0,3
	public DataType normalType = DataType.Float;
	public int textures = 1; // 0,1,2,3..GL_MAX_TEXTURE_COORDS-1
	public int[] textureDimension = { 2 }; // 1,2,3,4
	public DataType[] textureType = { DataType.Float };
	public int attributes = 0;
	public int[] attributeIndex = {};
	public int[] attributeSize = {};
	public DataType[] attributeType = {};
	public boolean[] attributeNormalize = {};
	public boolean aligned = true;

	public boolean isValid()
	{
		return isVertexValid() && isColorValid() && isSecondaryColorValid() && isNormalValid() && isTextureValid() && isAttributesValid();
	}

	public boolean isVertexValid()
	{
		if (vertexDimension != 2 && vertexDimension != 3 && vertexDimension != 4)
		{
			return false;
		}

		if (vertexType == null ||
				(vertexType != DataType.Short && vertexType != DataType.Integer &&
					vertexType != DataType.Float && vertexType != DataType.Double))
		{
			return false;
		}

		return true;
	}

	public boolean isColorValid()
	{
		if (colorComponents != 0 && colorComponents != 3 && colorComponents != 4)
		{
			return false;
		}

		if (colorType == null)
		{
			return false;
		}

		return true;
	}

	public boolean isSecondaryColorValid()
	{
		if (secondaryColorComponents != 0 && secondaryColorComponents != 3 && secondaryColorComponents != 4)
		{
			return false;
		}

		if (secondaryColorType == null)
		{
			return false;
		}

		return true;
	}

	public boolean isNormalValid()
	{
		if (normalDimension != 0 && normalDimension != 3)
		{
			return false;
		}

		if (normalType == null ||
				(normalType != DataType.Short && normalType != DataType.Integer &&
					normalType != DataType.Float && normalType != DataType.Double))
		{
			return false;
		}

		return true;
	}

	public boolean isTextureValid()
	{
		/*
		if (textures < 0 || textures >= glGetInteger( GL_MAX_TEXTURE_COORDS ))
		{
			return false;
		}
		*/

		if (textureDimension.length != textures)
		{
			return false;
		}

		for (int i = 0; i < textures; i++)
		{
			int td = textureDimension[i];

			if (td != 1 && td != 2 && td != 3 && td != 4)
			{
				return false;
			}
		}

		if (textureType.length != textures)
		{
			return false;
		}

		for (int i = 0; i < textures; i++)
		{
			DataType tt = textureType[i];

			if (tt == null || (tt != DataType.Short && tt != DataType.Integer && tt != DataType.Float && tt != DataType.Double))
			{
				return false;
			}
		}

		return true;
	}

	public boolean isAttributesValid()
	{
		for (int i = 0; i < attributes; i++)
		{
			if (attributeSize[i] < 1 || attributeSize[i] > 4)
			{
				return false;
			}

			if (attributeType[i] == null)
			{
				return false;
			}
		}

		return true;
	}

	public int getStride()
	{
		int stride = getSize();

		stride--;
		stride |= (stride >> 1);
		stride |= (stride >> 2);
		stride |= (stride >> 4);
		stride |= (stride >> 8);
		stride |= (stride >> 16);
		stride++;

		return stride;
	}

	public int getPadding()
	{
		return getStride() - getSize();
	}

	public int getSize()
	{
		int size = 0;
		
		size += vertexDimension * vertexType.bytes;
		size += normalDimension * normalType.bytes;
		size += colorComponents * colorType.bytes;
		size += secondaryColorComponents * secondaryColorType.bytes;
		
		for (int i = 0; i < textures; i++)
		{
			size += textureDimension[i] * textureType[i].bytes;	
		}

		for (int i = 0; i < attributes; i++)
		{
			size += attributeSize[i] * attributeType[i].bytes;
		}
		
		return size;
	}

	public boolean isAligned()
	{
		return aligned;
	}

	public VertexFormatBuilder setAligned( boolean aligned )
	{
		this.aligned = aligned;
		return this;
	}

	public int getVertexOffset()
	{
		return 0;
	}

	public int getNormalOffset()
	{
		return (vertexDimension * vertexType.bytes);
	}

	public int getColorOffset()
	{
		return (vertexDimension * vertexType.bytes) +
					(normalDimension * normalType.bytes);
	}

	public int getSecondaryColorOffset()
	{
		return (vertexDimension * vertexType.bytes) +
					(normalDimension * normalType.bytes) +
					(colorComponents * colorType.bytes);
	}

	public int[] getTextureOffsets()
	{
		int start = (vertexDimension * vertexType.bytes) +
						(normalDimension * normalType.bytes) +
						(colorComponents * colorType.bytes) + 
						(secondaryColorComponents * secondaryColorType.bytes);

		int[] offsets = new int[textures];

		for (int i = 0; i < textures; i++)
		{
			offsets[i] = start;

			start += textureDimension[i] * textureType[i].bytes;
		}

		return offsets;
	}

	public int[] getAttributeOffsets()
	{
		int start = (vertexDimension * vertexType.bytes) +
						(normalDimension * normalType.bytes) +
						(colorComponents * colorType.bytes) + 
						(secondaryColorComponents * secondaryColorType.bytes);
		
		for (int i = 0; i < textures; i++)
		{
			start += textureDimension[i] * textureType[i].bytes;
		}

		int[] offsets = new int[attributes];

		for (int i = 0; i < attributes; i++)
		{
			offsets[i] = start;

			start += attributeSize[i] * attributeType[i].bytes;
		}

		return offsets;
	}

	public Primitive getPrimitive()
	{
		return primitive;
	}

	public VertexFormatBuilder setPrimitive( Primitive primitive )
	{
		this.primitive = primitive;
		return this;
	}

	public VertexFormatBuilder setVertex( int vertexDimension, DataType vertexType )
	{
		this.vertexDimension = vertexDimension;
		this.vertexType = vertexType;
		return this;
	}

	public int getVertexDimension()
	{
		return vertexDimension;
	}

	public VertexFormatBuilder setVertexDimension( int vertexDimension )
	{
		this.vertexDimension = vertexDimension;
		return this;
	}

	public DataType getVertexType()
	{
		return vertexType;
	}

	public VertexFormatBuilder setVertexType( DataType vertexType )
	{
		this.vertexType = vertexType;
		return this;
	}

	public VertexFormatBuilder setColor( int colorComponents, DataType colorType )
	{
		this.colorComponents = colorComponents;
		this.colorType = colorType;
		return this;
	}

	public int getColorComponents()
	{
		return colorComponents;
	}

	public VertexFormatBuilder setColorComponents( int colorComponents )
	{
		this.colorComponents = colorComponents;
		return this;
	}

	public DataType getColorType()
	{
		return colorType;
	}

	public VertexFormatBuilder setColorType( DataType colorType )
	{
		this.colorType = colorType;
		return this;
	}
	

	public VertexFormatBuilder setSecondaryColor( int secondaryColorComponents, DataType secondaryColorType )
	{
		this.secondaryColorComponents = secondaryColorComponents;
		this.secondaryColorType = secondaryColorType;
		return this;
	}

	public int getSecondaryColorComponents()
	{
		return secondaryColorComponents;
	}

	public VertexFormatBuilder setSecondaryColorComponents( int secondaryColorComponents )
	{
		this.secondaryColorComponents = secondaryColorComponents;
		return this;
	}

	public DataType getSecondaryColorType()
	{
		return secondaryColorType;
	}

	public VertexFormatBuilder setSecondaryColorType( DataType secondaryColorType )
	{
		this.secondaryColorType = secondaryColorType;
		return this;
	}

	public VertexFormatBuilder setNormal( int normalDimension, DataType normalType )
	{
		this.normalDimension = normalDimension;
		this.normalType = normalType;
		return this;
	}

	public int getNormalDimension()
	{
		return normalDimension;
	}

	public VertexFormatBuilder setNormalDimension( int normalDimension )
	{
		this.normalDimension = normalDimension;
		return this;
	}

	public DataType getNormalType()
	{
		return normalType;
	}

	public VertexFormatBuilder setNormalType( DataType normalType )
	{
		this.normalType = normalType;
		return this;
	}

	public VertexFormatBuilder setTextures( int textures, int[] textureDimension, DataType[] textureType )
	{
		this.textures = textures;
		this.textureDimension = textureDimension;
		this.textureType = textureType;
		return this;
	}
	
	public int getTextures()
	{
		return textures;
	}

	public VertexFormatBuilder setTextures( int textures )
	{
		this.textures = textures;

		if  (textures != textureDimension.length)
		{
			textureDimension = Arrays.copyOf( textureDimension, textures );
		}
		
		if  (textures != textureType.length)
		{
			textureType = Arrays.copyOf( textureType, textures );
		}
		
		return this;
	}
	
	public VertexFormatBuilder addTexture( int dimensions, DataType type )
	{
		textures++;
		
		textureDimension = Arrays.copyOf( textureDimension, textures );
		textureDimension[textures - 1] = dimensions;
		
		textureType = Arrays.copyOf( textureType, textures );
		textureType[textures - 1] = type;
		
		return this;
	}

	public int[] getTextureDimension()
	{
		return textureDimension;
	}

	public VertexFormatBuilder setTextureDimension( int ... textureDimension )
	{
		this.textureDimension = textureDimension;
		return this;
	}

	public DataType[] getTextureType()
	{
		return textureType;
	}

	public VertexFormatBuilder setTextureType( DataType ... textureType )
	{
		this.textureType = textureType;
		return this;
	}

	public VertexFormatBuilder addAttribute( int index, int size, DataType type, boolean normalize )
	{
		attributeIndex = Array.add( index, attributeIndex );
		attributeType = Array.add( type, attributeType );
		attributeSize = Array.add( size, attributeSize );
		attributeNormalize = Array.add( normalize, attributeNormalize );
		attributes++;
		return this;
	}

	public int getAttributes()
	{
		return attributes;
	}

	public VertexFormatBuilder setAttributes( int attributes )
	{
		this.attributes = attributes;
		return this;
	}

	public int[] getAttributeSize()
	{
		return attributeSize;
	}

	public VertexFormatBuilder setAttributeSize( int[] attributeSize )
	{
		this.attributeSize = attributeSize;
		return this;
	}

	public DataType[] getAttributeType()
	{
		return attributeType;
	}

	public VertexFormatBuilder setAttributeType( DataType[] attributeType )
	{
		this.attributeType = attributeType;
		return this;
	}

	public boolean[] getAttributeNormalize()
	{
		return attributeNormalize;
	}

	public VertexFormatBuilder setAttributeNormalize( boolean[] attributeNormalize )
	{
		this.attributeNormalize = attributeNormalize;
		return this;
	}

	public int[] getAttributeIndex()
	{
		return attributeIndex;
	}

	public VertexFormatBuilder setAttributeIndex( int[] attributeIndex )
	{
		this.attributeIndex = attributeIndex;
		return this;
	}

	@Override
	public VertexFormat create()
	{
		return new VertexFormat( this );
	}

}
