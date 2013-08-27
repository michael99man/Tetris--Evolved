package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import menu.MainMenu;
import menu.Menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Engine extends BasicGame {

	private static Image img;
	private static int x, y;

	// FONTS:
	
	//Base font
	private static Font f;
	public TrueTypeFont title;
	public TrueTypeFont text;

	public static State state;

	// Parent
	private AppGameContainer parent;
	private Menu menu;

	public Engine(String title) {
		super(title);
	}

	private enum State {

		Created, MainMenu, Main, Paused,
		// etc

	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {

		if (state.equals(State.MainMenu)) {
			menu.render(arg0, g);
		}

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		state = State.Created;
		img = new Image("resources/Test Image.jpg");

		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("resources/FONT.TTF"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		title = requestFont(50);
		text = requestFont(20);
		
		parent.setDefaultFont(text);

		
		menu = new MainMenu(this);
		state = State.MainMenu;
	
	}

	
	public static TrueTypeFont requestFont(float size){
		return new TrueTypeFont(f.deriveFont(size), true);
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		Input input = arg0.getInput();

		if (state.equals(State.Main)) {
			if (input.isKeyPressed(Input.KEY_UP)) {
				y--;
			} else if (input.isKeyPressed(Input.KEY_DOWN)) {
				y++;
			} else if (input.isKeyPressed(Input.KEY_LEFT)) {
				x--;
			} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
				x++;
			}
		} else if (state.equals(State.MainMenu)) {
			menu.processInput(input);
		}

		// Toggles Fullscreen
		if (input.isKeyDown(Input.KEY_F)) {
			parent.setFullscreen(parent.isFullscreen() ? false : true);
		}
	}

	public void setContainer(AppGameContainer app) {
		parent = app;
	}

}