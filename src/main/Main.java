package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import org.newdawn.slick.TrueTypeFont;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	//Different Font classes
	private static Font f;
	
	public static void main(String[] args) {
		try {
			Engine engine = new Engine("Tetris");
			
			AppGameContainer app = new AppGameContainer(engine);
			app.setDisplayMode(750,800, false);
			
			engine.setContainer(app);
			
			
			//Basic setup
			app.setShowFPS(false);
			


			//app.setFullscreen(true);
			app.start();
			

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
