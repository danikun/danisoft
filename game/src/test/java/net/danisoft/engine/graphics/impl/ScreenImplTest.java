package net.danisoft.engine.graphics.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lwjgl.opengl.Display;

import net.danisoft.engine.graphics.Screen;
import net.danisoft.model.impl.Square;

public class ScreenImplTest {
	
	@Test
	public void testInit(){
		
		Screen screen = new ScreenImpl(0, 0, 0, false);
		Square square = new Square(50, 50, 0.0f, 0.0f, 0.0f);
		Square square2 = new Square(100, 100, 400, 400, 0);
		
		
		screen.init();
		
		boolean gameRunning = true;
		
		while(gameRunning){
			screen.print();
			
			square.render();
			square2.render();
			//square.setAngle(square.getAngle()+1);
			if(Display.isCloseRequested()){
				Display.destroy();
				gameRunning = false;
				
			}
			Display.sync(60);
		}
	}

}
