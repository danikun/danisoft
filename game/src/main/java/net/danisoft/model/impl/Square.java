package net.danisoft.model.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


import net.danisoft.model.BaseElement;

public class Square implements BaseElement{

	private float posX;
	private float posY;
	private float height;
	private float width;
	private float angle;
	private boolean controlled;
	private Texture texture;
	private float[] texCoordinatesX = {0.0f, 0.5f, 0.0f, 0.5f};
	private float[] texCoordinatesY = {0.0f, 0.0f, 0.5f, 0.5f};
	private int frame;
	
	public Square(float height, float width, float posX, float posY, float angle, boolean controlled){
		this.posX = posX;
		this.posY = posY;
		this.angle = angle;
		this.height = height;
		this.width = width;
		this.controlled = controlled;
		try {
			FileInputStream in = new FileInputStream(new File(this.getClass().getClassLoader().getResource("sprites/RYU SPRITES.png").toURI()));
			this.texture = TextureLoader.getTexture("PNG", in, true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		this.frame = 0;
	}
	
	public void render() {
		// center square according to screen size
	    GL11.glPushMatrix();
	      GL11.glTranslatef(posX, posY, 0.0f);
	 
	      // rotate square according to angle
	      GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
	 
	      // render the square
	      Color.white.bind();

	      texture.bind();
	      GL11.glBegin(GL11.GL_QUADS);
	      	  GL11.glTexCoord2f(texCoordinatesX[frame], texCoordinatesY[frame]);
	          GL11.glVertex3f(0.0f, 0.0f, 0.0f);
	          GL11.glTexCoord2f(texCoordinatesX[frame]+0.5f, texCoordinatesY[frame]);
	          GL11.glVertex3f(width, 0.0f, 0.0f);
	          GL11.glTexCoord2f(texCoordinatesX[frame]+0.5f, texCoordinatesY[frame]+0.5f);
	          GL11.glVertex3f(width, height, 0.0f);
	          GL11.glTexCoord2f(texCoordinatesX[frame], texCoordinatesY[frame]+0.5f);
	          GL11.glVertex3f(0.0f, height, 0.0f);
	      GL11.glEnd();
	      
	 
	    GL11.glPopMatrix();

	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void logic(){
		if(controlled){
			
			//Se Controlan las teclas que permiten pulsación continua
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				this.height = this.height + 1;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				this.height = this.height - 1;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				this.width = this.width + 1;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				this.width = this.width - 1;
			}
			
			Keyboard.poll();
			while(Keyboard.next()){
				//Se controlan las teclas que no permiten pulsación continua
				switch(Keyboard.getEventKey()){
					case Keyboard.KEY_F:
						if(Keyboard.getEventKeyState()){
							this.frame = this.frame+1;
							if (this.frame == 4){
								this.frame = 0;
							}
						}
					break;
				}
			}
		}
		
	}
	
}
