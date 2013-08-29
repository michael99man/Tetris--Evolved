package main;

import gameObjects.Block;

import java.awt.Font;
import java.io.File;
import java.util.LinkedList;

import menu.MainMenu;
import menu.Menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class Engine extends BasicGame {

	private static Image img;
	private static int x, y;

	// FONTS:

	// Base font
	private static Font f;
	public TrueTypeFont title;
	public TrueTypeFont text;

	public static State state;

	// Parent
	private AppGameContainer parent;
	private Menu menu;

	private int renderTicks = 0;
	private boolean secondPassed = false;

	// GAME RELATED STUFF
	// -------------------
	private LinkedList<Block> stationaryBlocks = new LinkedList<Block>();

	public static boolean[][] blockArray = new boolean[20][10];

	// If a block is falling
	private boolean falling = false;
	private Block tempBlock;

	// Temporary Borders and Background

	private static Image BACKGROUND;
	private static final int BG_WIDTH = 270;
	private static final int BG_HEIGHT = 540;

	private static final int BLOCKSIZE = 27;

	private static final int STARTX = 150;
	private static final int STARTY = 110;

	private static final int BORDER_SIZE = 15;

	private Rectangle leftBox = new Rectangle(STARTX, STARTY, BORDER_SIZE,
			BG_HEIGHT + BORDER_SIZE * 2 + 1);
	private Rectangle rightBox = new Rectangle(STARTX + BORDER_SIZE + BG_WIDTH
			+ 1, STARTY, BORDER_SIZE, BG_HEIGHT + BORDER_SIZE * 2 + 1);

	private Rectangle topBox = new Rectangle(STARTX, STARTY, BG_WIDTH
			+ BORDER_SIZE * 2, BORDER_SIZE);
	private Rectangle botBox = new Rectangle(STARTX, STARTY + BG_HEIGHT
			+ BORDER_SIZE + 1, BG_WIDTH + BORDER_SIZE * 2 + 1, BORDER_SIZE);

	public Engine(String title) {
		super(title);
	}

	private enum State {

		Created, MainMenu, Main, Paused,
		// etc

	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// NOTE: Running Engine.requestFont() will throw the FPS to around 14.
		// DO NOT USE THE FUNCTION IN ANY RENDERING.

		if (state.equals(State.MainMenu)) {
			menu.render(arg0, g);
		} else if (state.equals(State.Main)) {
			// MAIN GAMEPLAY

			// Temp graphics

			// Temporary dimensions

			g.setColor(Color.red);
			g.draw(botBox);
			g.draw(topBox);
			g.draw(leftBox);
			g.draw(rightBox);
			g.setColor(Color.white);

			g.drawImage(BACKGROUND, 165, 126);

			if (!falling) {
				falling = true;

				tempBlock = new Block(Block.Color.BLUE);
				tempBlock.x = STARTX + BORDER_SIZE + 135;
				tempBlock.y = STARTY + BORDER_SIZE + 1;

				tempBlock.coordX = 6;
				tempBlock.coordY = 1;

			} else if (falling) {
				if (secondPassed) {
					tempBlock.y += BLOCKSIZE;

					if (tempBlock.willIntersect("down")) {
						System.out.println("Reached the bottom!");
						stationaryBlocks.add(tempBlock);
						falling = false;
					}
					
					tempBlock.mark(false);
					tempBlock.coordY++;
					tempBlock.mark(true);
				}
			}

			for (Block b : stationaryBlocks) {
				g.drawImage(b.img, b.x, b.y);
			}

			g.drawImage(tempBlock.img, tempBlock.x, tempBlock.y);

		}

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		state = State.Created;

		BACKGROUND = new Image("resources/BACKGROUND.png");

		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(
					"resources/FONT.TTF"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		title = requestFont(50);
		text = requestFont(20);

		parent.setDefaultFont(text);

		menu = new MainMenu(this);
		state = State.MainMenu;

	}

	public static TrueTypeFont requestFont(float size) {
		return new TrueTypeFont(f.deriveFont(size), true);
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		secondPassed = false;

		// Times game
		renderTicks++;

		if (renderTicks >= 60) {
			// System.out.println("A second has passed");
			renderTicks = 0;
			secondPassed = true;
		}

		// CHECK INPUT
		Input input = arg0.getInput();

		if (state.equals(State.Main)) {
			if (input.isKeyPressed(Input.KEY_LEFT)) {
				
				if (!tempBlock.willIntersect("left")) {
					tempBlock.mark(false);
					
					tempBlock.x -= BLOCKSIZE;
					tempBlock.coordX--;
				
					tempBlock.mark(true);
				}
				
			} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
				if (!tempBlock.willIntersect("right")) {
					tempBlock.mark(false);

					tempBlock.x += BLOCKSIZE;
					tempBlock.coordX++;
				}

				tempBlock.mark(true);
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				//Full drop
				
				tempBlock.mark(false);
				
				while (!(tempBlock.willIntersect("down"))) {
					tempBlock.y += BLOCKSIZE;
					tempBlock.coordY++;
				}
				
				falling = false;
				stationaryBlocks.add(tempBlock);
				
				tempBlock.mark(true);

			} else if (input.isKeyPressed(Input.KEY_P)) {
				// Prints array

				String s = "";
				for (boolean[] bA : blockArray) {
					String str = "";

					for (boolean b : bA) {
						str += " " + (b ? "1" : "0");
					}

					s += str;
					s += "\n";
				}
				System.out.println(s);
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

	public void startGame() {
		state = State.Main;
		// START MAIN GAMEPLAY

	}

}