package net.danisoft.engine.graphics.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;
import org.lwjgl.opengl.Display;

import net.danisoft.engine.graphics.Screen;
import net.danisoft.model.BaseElement;
import net.danisoft.model.impl.Square;

public class ScreenImplTest {
	
	private ArrayList<BaseElement> elements;
	private Screen screen;
	
	@Test
	public void testInit(){
		
		this.init();
		
		boolean gameRunning = true;
		
		while(gameRunning){
			screen.print();
			
			this.render();
			this.logic();
			
			if(Display.isCloseRequested()){
				Display.destroy();
				gameRunning = false;
				
			}
			Display.sync(60);
		}
	}
	
	private void init(){
		elements = new ArrayList<BaseElement>();
		screen = new ScreenImpl(0, 0, 0, false);
		screen.init();
		
		Square square = new Square(50, 50, 0.0f, 0.0f, 0.0f, true);
		Square square2 = new Square(100, 100, 400, 400, 0, false);
		
		elements.add(square);
		elements.add(square2);
	}
	
	private void render(){
		Iterator<BaseElement> it = elements.iterator();
		
		while(it.hasNext()){
			BaseElement element = it.next();
			element.render();
		}
	}
	
	private void logic(){
		Iterator<BaseElement> it = elements.iterator();
		
		while(it.hasNext()){
			BaseElement element = it.next();
			element.logic();
		}
	}


}
