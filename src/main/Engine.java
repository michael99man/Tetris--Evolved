package main;

import gameObjects.Block;
import gameObjects.Player;
import gameObjects.Point;
import gameObjects.Tetromino;
import gameObjects.Tetromino.Type;

import java.awt.Font;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import menu.MainMenu;
import menu.Menu;
import menu.PauseMenu;

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

	// FONTS:

	// Base font
	private static Font f;
	public TrueTypeFont title;
	public TrueTypeFont text;

	// Parent
	private AppGameContainer parent;
	private Menu menu;

	private int renderTicks = 0;
	private boolean secondPassed = false;

	// GAME RELATED STUFF
	// ----------------------------------------------------------
	public static State state;
	public Player player1;

	private Random rand = new Random();

	protected LinkedList<Block> stationaryBlocks = new LinkedList<Block>();

	public static boolean[][] blockArray = new boolean[20][10];

	// If a block is falling
	private boolean falling = false;
	private static Tetromino tetromino;

	private static int streak = 0;

	private static final int SCORE_VALUE = 100;

	// Temporary Borders and Background

	private static Image BACKGROUND;
	private static final int BG_WIDTH = 270;
	private static final int BG_HEIGHT = 540;

	public static final int BLOCKSIZE = 27;

	private static final int STARTX = 150;
	private static final int STARTY = 110;

	private static final int BORDER_SIZE = 15;

	public static final int BOARD_HEIGHT = 20;
	public static final int BOARD_WIDTH = 10;

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

	public enum State {

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
			drawMain(g);
		} else if (state.equals(State.Paused)) {
			drawMain(g);
			menu.render(arg0, g);
		}

	}

	public void drawMain(Graphics g) {
		g.setColor(Color.red);
		g.draw(botBox);
		g.draw(topBox);
		g.draw(leftBox);
		g.draw(rightBox);
		g.setColor(Color.white);

		g.drawImage(BACKGROUND, 165, 126);

		g.drawString(player1.name + " SCORE: " + player1.score, 100, 40);
		for (Block b : stationaryBlocks) {
			b.refresh();
			g.drawImage(b.img, b.x, b.y);
			b.mark(true);
		}

		for (Block b : tetromino.blockList) {
			b.refresh();
			g.drawImage(b.img, b.x, b.y);
			b.mark(true);
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

	// Checks coordinates
	public static boolean check(int x, int y) {
		// Returns false if illegal
		if (x > BOARD_WIDTH || y > BOARD_HEIGHT || x < 1 || y < 1) {
			System.out.println("Illegal point: (" + x + "," + y + ")");
			return false;
		}

		boolean invalid = blockArray[y - 1][x - 1];

		if (invalid && !tetromino.includes(x, y)) {
			System.out.println("(" + x + "," + y + ") occupied.");
			return false;
		}
		return true;
	}

	public static TrueTypeFont requestFont(float size) {
		return new TrueTypeFont(f.deriveFont(size), true);
	}

	private void clearRow(int y) {

		LinkedList<Block> tempList = new LinkedList<Block>();
		blockArray[y - 1] = new boolean[10];

		for (int i = 0; i < 10; i++) {
			blockArray[y - 1][i] = false;
		}

		for (Block b : stationaryBlocks) {
			if (b.point.y == y) {
				tempList.add(b);
				// System.out.println("Cleared a block");
			} else if (b.point.y < y) {
				b.mark(false);
				b.point.y++;
				b.refresh();
				b.mark(true);
			}
		}

		for (Block b : tempList) {
			stationaryBlocks.remove(b);
		}
	}

	public void checkRows() {

		LinkedList<Integer> clearList = new LinkedList<Integer>();

		int i = 1;
		for (boolean[] bA : blockArray) {
			boolean found = true;

			for (boolean b : bA) {
				if (!b) {
					found = false;
				}
			}

			if (found) {
				clearList.add(i);
			}
			i++;
		}

		if (clearList.size() == 0 && streak != 0) {
			System.out.println("STREAK RESET");
			streak = 0;
		}

		
		if (clearList.size()!=0){
			System.out.println("\n");
			for (int j :clearList){
				System.out.println("ROW: " + j);
			}
		}
		for (int y : clearList) {
			System.out.println("CLEAR");
			
			if (streak == 0) {
				streak += 1;
			} else {
				streak += 1;
				player1.score += 200 + 75 * streak;
			}
			clearRow(y);
		}

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
				for (Block b : tetromino.blockList) {
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
						b.point.x--;
						b.mark(true);
					}
				}
			} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
				boolean stop = false;
				for (Block b : tetromino.blockList) {
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
						b.point.x++;
						b.mark(true);
					}
				}
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				// Full drop

				boolean stop = false;

				while (!stop) {
					for (Block b : tetromino.blockList) {
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
							b.point.y++;
							b.mark(true);

						}
					}
				}
				
				for (Block b : tetromino.blockList) {
					System.out.println("ADDED TO STATIONARYBLOCKS");
					stationaryBlocks.add(b);
				}
				checkRows();
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

				String str = "";
				for (Point p : tetromino.pointList) {
					str += ("(" + p.x + "," + p.y + "), ");
				}
				System.out.println(str);

			} else if (input.isKeyPressed(Input.KEY_Z)) {
				tetromino.rotate(false);
			} else if (input.isKeyPressed(Input.KEY_X)) {
				tetromino.rotate(true);
			} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
				state = State.Paused;
				menu = new PauseMenu();
				System.out.println("PAUSED");
			}
		} else if (state.equals(State.MainMenu) || state.equals(State.Paused)) {
			menu.processInput(input);
		}

		// Toggles Fullscreen
		if (input.isKeyDown(Input.KEY_F)) {
			parent.setFullscreen(parent.isFullscreen() ? false : true);
		}

		// Main processing

		if (state.equals(State.Main)) {
			if (!falling) {
				falling = true;
				tetromino = new Tetromino(Type.iBlock);
				//tetromino = new Tetromino(
					//	Tetromino.Type.values()[rand.nextInt(6)]);
			} else if (falling) {
				if (secondPassed) {

					boolean stop = false;

					for (Block b : tetromino.blockList) {
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
							b.point.y++;
							b.refresh();
							b.mark(true);
						}
					} else if (stop) {
						for (Block b : tetromino.blockList) {
							stationaryBlocks.add(b);
						}
					}

				}
				checkRows();
			}
		}

	}

	public void setContainer(AppGameContainer app) {
		parent = app;
	}

	public void startGame() {
		state = State.Main;

		player1 = new Player("Michael");
		// START MAIN GAMEPLAY

	}

}