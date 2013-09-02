package gameObjects;

import main.Engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Block {

	public Color color;
	public Image img;

	// X,Y positions of the IMG
	public int x;
	public int y;

	public Point point;

	public Tetromino parent = null;

	// public int coordX;
	// public int coordY;

	// CYAN, ORANGE, BLUE, YELLOW, GREEN, PURPlE, RED
	private static final String[] linkList = { "CYAN", "ORANGE",
			"resources/BLUE_BLOCK.png", "YELLOW", "GREEN", "PURPLE",
			"resources/RED_BLOCK.png" };

	public Block(Color color, int x, int y, Tetromino t) throws SlickException {
		this.color = color;
		// System.out.println("Block created at Coords: " + x + "," + y);
		point = new Point(x, y);
		refresh();

		img = new Image(linkList[color.ordinal()]);
		parent = t;
	}

	// iBlock, reverselBlock, lBlock, oBlock, sBlock, tBlock, zBlock,
	public static enum Color {
		CYAN, ORANGE, BLUE, YELLOW, GREEN, PURPlE, RED
	}

	public boolean willIntersect(String direction) {

		try {
			if (direction.equalsIgnoreCase("down")) {
				// Catching maximum triggers (20, 1, 10)
			
				if (point.y >= 20) {
					System.out.println("REACHED THE BOTTOM");
					return true;
				}

				if (Engine.blockArray[point.y - 1 + 1][point.x - 1]) {
					if (!parent.equals(null)) {
						if (parent.includes(point.x,point.y+1)){
							return false;
						}
					}
					return true;
				}
			} else if (direction.equalsIgnoreCase("left")) {

				if (point.x == 1) {
					return true;
				}

				if (Engine.blockArray[point.y - 1][point.x - 1 - 1]) {
					if (!parent.equals(null)) {
						if (parent.includes(point.x-1,point.y)){
							return false;
						}
					}
					return true;
				}
			} else if (direction.equalsIgnoreCase("right")) {
				if (point.x == 10) {
					return true;
				}

				if (Engine.blockArray[point.y - 1][point.x - 1 + 1]) {
					if (!parent.equals(null)) {
						if (parent.includes(point.x+1,point.y)){
							return false;
						}
					}
					return true;
					
				}
			}

		} catch (Exception e) {
			//System.out.println("EXCEPTION: x - " + point.x + " y - " + point.y);
		}

		return false;

	}

	// Methods to get the X/Y positions based on Coordinates.
	// Essentially refreshes the block based on coordX, coordY
	public void refresh() {
		x = 165 + ((point.x - 1) * Engine.BLOCKSIZE);
		y = 125 + ((point.y - 1) * Engine.BLOCKSIZE);
	}

	public void mark(boolean b) {
		if (point.y <= 0 || point.y > Engine.BOARD_HEIGHT) {
			//System.out.println("ERROR: Y");
			return;
		}
		if (point.x <= 0 || point.x > Engine.BOARD_WIDTH) {
			//System.out.println("ERROR: X");
			return;
		}
		// 2D array is [Y VALUE][X VALUE]
		Engine.blockArray[point.y - 1][point.x - 1] = b;
	}

}
