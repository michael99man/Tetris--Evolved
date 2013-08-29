package gameObjects;

import gameObjects.Block.Color;

import java.util.LinkedList;

import org.newdawn.slick.SlickException;

import main.Engine;

public class Tetromino {

	// List of the blocks at the very bottom
	public LinkedList<Block> bottomList = new LinkedList<Block>();

	// List of blocks at the very right
	public LinkedList<Block> rightList = new LinkedList<Block>();

	// And left
	public LinkedList<Block> leftList = new LinkedList<Block>();

	public LinkedList<Block> blockList = new LinkedList<Block>();

	public static int startX;
	public static int startY;

	public Tetromino(Type type) throws SlickException {
		if (type.equals(Type.iBlock)) {
			// The Long one

			// Placeholder Color
			Color c = Block.Color.BLUE;

			Block b1 = new Block(c, 4, 1);
			Block b2 = new Block(c, 5, 1);
			Block b3 = new Block(c, 6, 1);
			Block b4 = new Block(c, 7, 1);

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);

			processList(blockList, this);
		}

		else if (type.equals(Type.lBlock)) {
			Color c = Block.Color.BLUE;

			Block b1 = new Block(c, 4, 1);
			Block b2 = new Block(c, 4, 2);
			Block b3 = new Block(c, 5, 2);
			Block b4 = new Block(c, 6, 2);

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);

			processList(blockList, this);
		} else if (type.equals(Type.reverselBlock)) {
			Color c = Block.Color.BLUE;

			Block b1 = new Block(c, 6, 1);
			Block b2 = new Block(c, 4, 2);
			Block b3 = new Block(c, 5, 2);
			Block b4 = new Block(c, 6, 2);

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);

			processList(blockList, this);
		} else if (type.equals(Type.oBlock)) {
			Color c = Block.Color.BLUE;

			Block b1 = new Block(c, 5, 1);
			Block b2 = new Block(c, 6, 1);
			Block b3 = new Block(c, 5, 2);
			Block b4 = new Block(c, 6, 2);

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);

			processList(blockList, this);

		} else if (type.equals(Type.sBlock)) {

			Color c = Block.Color.BLUE;

			Block b1 = new Block(c, 5, 1);
			Block b2 = new Block(c, 6, 1);
			Block b3 = new Block(c, 4, 2);
			Block b4 = new Block(c, 5, 2);

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);
			processList(blockList, this);
		} else if (type.equals(Type.zBlock)) {
			Color c = Block.Color.BLUE;

			Block b1 = new Block(c, 4, 1);
			Block b2 = new Block(c, 5, 1);
			Block b3 = new Block(c, 5, 2);
			Block b4 = new Block(c, 6, 2);

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);
			processList(blockList, this);
		} else if (type.equals(Type.tBlock)) {
			Color c = Block.Color.BLUE;

			Block b1 = new Block(c, 5, 1);
			Block b2 = new Block(c, 4, 2);
			Block b3 = new Block(c, 5, 2);
			Block b4 = new Block(c, 6, 2);

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);
			processList(blockList, this);
		}
	}

	public static void processList(LinkedList<Block> blockList, Tetromino t) {
		int left = 0, right = 0, bottom = 0;

		for (Block b : blockList) {
			if (b.coordX > right) {
				right = b.coordX;
			}

			if (b.coordX < left || left == 0) {
				left = b.coordX;
			}

			if (b.coordY > bottom) {
				bottom = b.coordY;
			}
		}

		System.out.println("Rightmost: " + right + " Leftmost: " + left
				+ " Bottommost: " + bottom);

		for (Block b : blockList) {
			if (b.coordX == right) {
				t.rightList.add(b);
			}

			if (b.coordX == left) {
				t.leftList.add(b);
			}

			if (b.coordY == bottom) {
				t.bottomList.add(b);
			}
		}
	}

	public enum Type {
		iBlock, reverselBlock, lBlock, oBlock, sBlock, tBlock, zBlock,

	}

}
