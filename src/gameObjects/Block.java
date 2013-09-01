package gameObjects;

import main.Engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Block {

	public Color color;
	public Image img;

	public int x;
	public int y;

	public int coordX;
	public int coordY;

	// CYAN, ORANGE, BLUE, YELLOW, GREEN, PURPlE, RED
	private static final String[] linkList = { "CYAN", "ORANGE",
			"resources/BLUE_BLOCK.png", "YELLOW", "GREEN", "PURPLE",
			"resources/RED_BLOCK.png" };

	public Block(Color color, int x, int y) throws SlickException {
		this.color = color;

		//System.out.println("Block created at Coords: " + x + "," + y);
		coordX = x;
		coordY = y;

		refresh();

		img = new Image(linkList[color.ordinal()]);

	}

	// iBlock, reverselBlock, lBlock, oBlock, sBlock, tBlock, zBlock,
	public static enum Color {
		CYAN, ORANGE, BLUE, YELLOW, GREEN, PURPlE, RED
	}

	public boolean willIntersect(String direction) {

		try {

			if (direction.equalsIgnoreCase("down")) {
				// Catching maximum triggers (20, 1, 10)
				if (coordY == 20) {
					return true;
				}

				if (Engine.blockArray[coordY - 1 + 1][coordX - 1]) {
					return true;
				}
			} else if (direction.equalsIgnoreCase("left")) {

				if (coordX == 1) {
					return true;
				}

				if (Engine.blockArray[coordY - 1][coordX - 1 - 1]) {
					return true;
				}
			} else if (direction.equalsIgnoreCase("right")) {
				if (coordX == 10) {
					return true;
				}

				if (Engine.blockArray[coordY - 1][coordX - 1 + 1]) {
					return true;
				}
			}

		} catch (Exception e) {
			System.out.println("EXCEPTION: x - " + coordX + " y - " + coordY);
		}

		return false;

	}

	// Methods to get the X/Y positions based on Coordinates.
	// Essentially refreshes the block based on coordX, coordY
	public void refresh() {
		x = 165 + ((coordX - 1) * Engine.BLOCKSIZE);
		y = 125 + ((coordY - 1) * Engine.BLOCKSIZE);
	}

	public void mark(boolean b) {
		if (coordY <= 0) {
			//System.out.println("ERROR: Y");
			return;
		}
		if (coordX <= 0) {
			//System.out.println("ERROR: X");
			return;
		}
		// 2D array is [Y VALUE][X VALUE]
		Engine.blockArray[coordY - 1][coordX - 1] = b;
	}
	

}
