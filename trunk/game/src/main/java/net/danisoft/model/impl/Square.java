package net.danisoft.model.impl;

import org.lwjgl.opengl.GL11;

import net.danisoft.model.BaseElement;

public class Square implements BaseElement{

	private float posX;
	private float posY;
	private float height;
	private float width;
	private float angle;
	
	public Square(float height, float width, float posX, float posY, float angle){
		this.posX = posX;
		this.posY = posY;
		this.angle = angle;
		this.height = height;
		this.width = width;
	}
	
	public void render() {
		// center square according to screen size
	    GL11.glPushMatrix();
	      GL11.glTranslatef(posX, posY, 0.0f);
	 
	      // rotate square according to angle
	      GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
	 
	      // render the square
	      GL11.glBegin(GL11.GL_QUADS);
	      	  GL11.glColor3f(1.0f, 0.0f, 0.0f);
	          GL11.glVertex3f(0.0f, 0.0f, 0.0f);
	          GL11.glColor3f(0.0f, 1.0f, 0.0f);
	          GL11.glVertex3f(width, 0.0f, 0.0f);
	          GL11.glColor3f(0.0f, 0.0f, 1.0f);
	          GL11.glVertex3f(width, height, 0.0f);
	          GL11.glColor3f(0.0f, 1.0f, 1.0f);
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

	
}
