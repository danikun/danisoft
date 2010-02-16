package net.danisoft.engine.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.eclipse.swt.graphics.ImageData;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * Class to instantiate textures, and load to the OpenGL engine.
 * 
 * @author danielg.pino
 *
 */
public class Texture {
	 
	  /** Texture id for this image (OpenGL) */
	  private int textureID;
	 
	  /** Width of this image */
	  private int width;
	 
	  /** Height of this image */
	  private int height;
	 
	  /** Width ratio */
	  private float	widthRatio;
	 
	  /** Height ratio */
	  private float	heightRatio;
	 
	  /** Texture width */
	  private int textureWidth;
	 
	  /** Texture heigth */
	  private int textureHeigth;
	 
	  /**
	   * Creates a new Texture
	   * 
	   * @param textureID Texture ID
	   * @param width Width of image
	   * @param height Height of image
	   */
	  public Texture(int textureID, int width, int height) {
	    this(textureID, width, height, 1.0f, 1.0f, width, height);
	  }
	 
	  /**
	   * Creates a new Texture
	   * 
	   * @param textureID Texture ID
	   * @param width Width of image
	   * @param height Height of image
	   * @param widthRatio Ratio of texture width
	   * @param heightRatio Ratio of texture height
	   * @param textureWidth Actual width of texture
	   * @param textureHeight Actual height of texture
	   */
	  public Texture(int textureID, int width, int height, float widthRatio, float heightRatio, int textureWidth, int textureHeight) {
	    this.textureID = textureID;
	    this.width = width;
	    this.height = height;
	    this.widthRatio = widthRatio;
	    this.heightRatio = heightRatio;
	    this.textureWidth = textureWidth;
	    this.textureHeigth = textureHeight;
	  }
	 
	  /**
	   * Destroys this Texture, reclaiming all resources
	   */
	  public void destroy() {
	    IntBuffer scratch = BufferUtils.createIntBuffer(1);
	    scratch.put(0, textureID);
	    GL11.glDeleteTextures(scratch);
	  }
	 
	  /**
	   * @return Texture ID for this image
	   */
	  public int getTextureID() {
	    return textureID;
	  }
	  /**
	   * @return height of image
	   */
	  public int getHeight() {
	    return height;
	  }
	  /**
	   * @return width of image
	   */
	  public int getWidth() {
	    return width;
	  }
	  /**
	   * Binds this image
	   */
	  public void bind() {
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	  }
	  /**
	   * Renders this image to a quad. The image will bind itself first.
	   */
	  public void render() {
	    bind();
	    GL11.glBegin(GL11.GL_QUADS);
	    {
	      GL11.glTexCoord2f(0.0f, 0.0f);              GL11.glVertex2i(0, 0);
	      GL11.glTexCoord2f(widthRatio, 0.0f);        GL11.glVertex2i(width, 0);
	      GL11.glTexCoord2f(widthRatio, heightRatio); GL11.glVertex2i(width, height);
	      GL11.glTexCoord2f(0.0f, heightRatio);       GL11.glVertex2i(0, height);
	    }
	    GL11.glEnd();
	  }
	  /**
	   * Returns a string representation of the image
	   */
	  public String toString() {
	    return "Texture [" + textureID + ", " + width + ", " + height + ", " + 
	    textureWidth + ", " + textureHeigth + ", " + widthRatio + ", " + heightRatio + "]";
	  }
	  
	  /**
	   * Loads the named texture from the classpath
	   * 
	   * @param name Name of texture to load
	   * @param flip Whether to flip image
	   * @return Loaded texture or null
	 * @throws FileNotFoundException 
	   */
	  public static Texture loadTexture(URI imgPath) throws FileNotFoundException {
		  	Texture    texture  = null;
		  	FileInputStream file = new FileInputStream(new File(imgPath));
			ImageData  img      = new ImageData(file);
			int        width    = img.width;
			int        height   = img.height;
			byte[]     data     = new byte[img.data.length];
			
			for(int i=0; i<img.data.length; i++) 
				data[i] = img.data[img.data.length-(i+0x1)];
			
			ByteBuffer scratch  = ByteBuffer.wrap(data);
			IntBuffer  buffer   = ByteBuffer.allocateDirect(0x4).order(ByteOrder.nativeOrder()).asIntBuffer();
			GL11.glGenTextures(buffer);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, buffer.get(0x0));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0x0, GL11.GL_RGB, width, height, 0x0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, scratch);
			
			texture = new Texture(buffer.get(0x0),width, height);
			return texture;
		}
	  
	  /**
	   * Get the closest greater power of 2 to the fold number
	   * 
	   * @param fold The target number
	   * @return The power of 2
	   */
	  public static int getNextPowerOfTwo(int fold) {
	    int pow = 2;
	    while (pow < fold) {
	      pow *= 2;
	    }
	    return pow;
	  }
}