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

	private static final String[] linkList = { "REDDD", "GREEN",
			"resources/BLUE_BLOCK.png", "4", "5", "6" };

	public Block(Color color, int x, int y) throws SlickException {
		this.color = color;

		coordX = x;
		coordY = y;

		this.x = getCX(x);
		this.y = getCY(y);
				
		img = new Image(linkList[color.ordinal()]);

	}

	public static enum Color {
		RED, GREEN, BLUE, YELLOW,

		// etc

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

	private static int getCX(int x){
		return 165 + ((x-1) * Engine.BLOCKSIZE); 
	}

	private static int getCY(int y){
		return 125 + ((y-1) * Engine.BLOCKSIZE);
	}
	
	public void mark(boolean b) {

		// 2D array is [Y VALUE][X VALUE]
		Engine.blockArray[coordY - 1][coordX - 1] = b;
	}
}
