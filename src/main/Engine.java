package main;

import gameObjects.Block;
import gameObjects.Tetromino;

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
	// ----------------------------------------------------------
	private LinkedList<Block> stationaryBlocks = new LinkedList<Block>();

	public static boolean[][] blockArray = new boolean[20][10];

	// If a block is falling
	private boolean falling = false;
	private Block tempBlock;
	private Tetromino tetromino;

	// Temporary Borders and Background

	private static Image BACKGROUND;
	private static final int BG_WIDTH = 270;
	private static final int BG_HEIGHT = 540;

	public static final int BLOCKSIZE = 27;

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

				tetromino = new Tetromino(Tetromino.Type.reverselBlock);
			} else if (falling) {
				if (secondPassed) {

					boolean stop = false;

					for (Block b : tetromino.bottomList) {
						if (b.willIntersect("down")) {
							stop = true;
							falling = false;
							System.out
									.println("Tetromino has reached the bottom!");
						}
					}

					if (!stop) {
						for (Block b : tetromino.blockList) {
							b.mark(false);
							b.y += BLOCKSIZE;
							b.coordY++;
							// Marking done at the end.

						}
					} else {
						for (Block b : tetromino.blockList) {
							stationaryBlocks.add(b);
						}
					}
				}
			}

			for (Block b : stationaryBlocks) {
				g.drawImage(b.img, b.x, b.y);
			}

			for (Block b : tetromino.blockList) {
				g.drawImage(b.img, b.x, b.y);
				b.mark(true);
			}

		}

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		state = State.Created;

		Tetromino.startX = STARTX + BORDER_SIZE + 135;
		Tetromino.startY = STARTY + BORDER_SIZE + 1;

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

				boolean stop = false;
				for (Block b : tetromino.leftList) {
					if (b.willIntersect("left")) {
						stop = true;
						System.out.println("STOP!");
						break;
					}
				}

				if (!stop) {
					for (Block b : tetromino.blockList) {
						b.mark(false);
						b.x -= BLOCKSIZE;
						b.coordX--;
						b.mark(true);
					}
				}

			} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
				boolean stop = false;
				for (Block b : tetromino.rightList) {
					if (b.willIntersect("right")) {
						stop = true;
						System.out.println("STOP!");
						break;
					}
				}

				if (!stop) {
					for (Block b : tetromino.blockList) {
						b.mark(false);
						b.x += BLOCKSIZE;
						b.coordX++;
						b.mark(true);
					}

				}
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				// Full drop

				boolean stop = false;

				while (!stop) {
					for (Block b : tetromino.bottomList) {
						if (b.willIntersect("down")) {
							stop = true;
							System.out.println("STOP!");
							break;
						}
					}

					if (!stop) {
						for (Block b : tetromino.blockList) {
							b.mark(false);
							b.y += BLOCKSIZE;
							b.coordY++;
							b.mark(true);
							stationaryBlocks.add(b);
						}

					}
				}
				
				falling = false;

			} else if (input.isKeyPressed(Input.KEY_P)) {
				// Prints blockArray

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