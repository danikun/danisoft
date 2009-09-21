package net.danisoft.engine.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

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
	   */
	  public static Texture loadTexture(String name, boolean flip) {
	    Texture texture = null;
	    ByteBuffer imageData = null;
	    int ilImageHandle;
	    int oglImageHandle;
	    IntBuffer scratch = BufferUtils.createIntBuffer(1);

	    // create image in DevIL and bind it
	    IL.ilGenImages(scratch);
	    IL.ilBindImage(scratch.get(0));
	    ilImageHandle = scratch.get(0);
	   
	    // load the image
	    if(!IL.ilLoadFromURL(IL.class.getClassLoader().getResource(name))) {
	      return null;
	    }
	   
	    // convert image to RGBA
	    IL.ilConvertImage(IL.IL_RGBA, IL.IL_BYTE);
	   
	    // flip if needed
	    if(flip) {
	      ILU.iluFlipImage();
	    }
	   
	    // get image attributes
	    int width = IL.ilGetInteger(IL.IL_IMAGE_WIDTH);
	    int height = IL.ilGetInteger(IL.IL_IMAGE_HEIGHT);
	    int textureWidthSize = getNextPowerOfTwo(width);
	    int textureHeightSize = getNextPowerOfTwo(height);
	   
	    // resize image according to poweroftwo
	    if (textureWidthSize != width || textureHeightSize != height) {
	      imageData = BufferUtils.createByteBuffer(textureWidthSize * textureHeightSize * 4);
	      IL.ilCopyPixels(0, 0, 0, textureWidthSize, textureHeightSize, 1, IL.IL_RGBA, IL.IL_BYTE, imageData);
	    } else {
	      imageData = IL.ilGetData();
	    }
	   
	    // create OpenGL counterpart
	    GL11.glGenTextures(scratch);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, scratch.get(0));
	    oglImageHandle = scratch.get(0);
	   
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureWidthSize, textureHeightSize, 
	                      0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
	   
	    // Create image (either resized by copying, else directly from IL)
	    if (textureWidthSize != width || textureHeightSize != height) {
	      texture = new Texture(oglImageHandle, width, height, (width / (float) textureWidthSize), 
	                           (height / (float) textureHeightSize), textureWidthSize, textureHeightSize);
	    } else {
	      texture = new Texture(oglImageHandle, width, height);
	    }
	   
	    // delete Image in DevIL
	    scratch.put(0, ilImageHandle);
	    IL.ilDeleteImages(scratch);
	   
	    // revert the gl state back to the default so that accidental texture binding doesn't occur
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	   
	    // return OpenGL texture handle
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