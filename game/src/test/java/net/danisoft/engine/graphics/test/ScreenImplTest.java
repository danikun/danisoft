package net.danisoft.engine.graphics.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;

import net.danidoft.engine.controllers.test.KeyboardSquareController;
import net.danisoft.engine.controllers.impl.NullController;
import net.danisoft.engine.graphics.Screen;
import net.danisoft.engine.graphics.impl.ScreenImpl;
import net.danisoft.model.BaseElement;
import net.danisoft.model.test.Square;


public class ScreenImplTest {
	
	private ArrayList<BaseElement> elements;
	private Screen screen;
	
	@Test
	public void testInit(){
		
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
	
	@Before
	public void init(){
		elements = new ArrayList<BaseElement>();
		screen = new ScreenImpl(0, false);
		
		Square square = new Square(50, 50, 0.0f, 0.0f, 0.0f, new KeyboardSquareController());
		Square square2 = new Square(100, 100, 400, 400, 0, new NullController());
		
		elements.add(square);
		elements.add(square2);
	}
	
	private void render(){
		for(BaseElement element : elements){
			element.render();
		}
	}
	
	private void logic(){
		for(BaseElement element : elements){
			element.logic();
		}
	}


}
