package gameObjects;

import gameObjects.Block.Color;

import java.util.LinkedList;

import org.newdawn.slick.Image;
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

	private int cX;
	private int cY;

	public int rotateState;

	public Type type;

	public Block center;

	public Tetromino(Type type) throws SlickException {
		System.out.println("Tetromino created : " + type.name());

		rotateState = 0;
		this.type = type;

		int index = type.ordinal();
		// Color c = Color.values()[index];
		Color c = Color.BLUE;

		// Center X, Center y
		cX = STATE_LIST[index][0][0][0];
		cY = STATE_LIST[index][0][0][1];

		center = new Block(Color.RED, cX, cY);
		blockList.add(center);

		// Each Tetromino has 4 blocks
		for (int i = 1; i < 4; i++) {
			Block b = new Block(c, cX + STATE_LIST[index][0][i][0], cY
					+ STATE_LIST[index][0][i][1]);
			blockList.add(b);
		}
		processList(blockList, this);

		/*
		 * if (type.equals(Type.iBlock)) { // The Long one
		 * 
		 * // Placeholder Color Color c = Block.Color.BLUE;
		 * 
		 * Block b1 = new Block(c, 4, 1); center = new Block(c, 5, 1); Block b3
		 * = new Block(c, 6, 1); Block b4 = new Block(c, 7, 1);
		 * 
		 * blockList.add(b1); blockList.add(center); blockList.add(b3);
		 * blockList.add(b4);
		 * 
		 * processList(blockList, this); }
		 * 
		 * else if (type.equals(Type.lBlock)) { Color c = Block.Color.BLUE;
		 * 
		 * Block b1 = new Block(c, 4, 1); center = new Block(c, 4, 2); Block b3
		 * = new Block(c, 5, 2); Block b4 = new Block(c, 6, 2);
		 * 
		 * blockList.add(b1); blockList.add(center); blockList.add(b3);
		 * blockList.add(b4);
		 * 
		 * processList(blockList, this); } else if
		 * (type.equals(Type.reverselBlock)) { Color c = Block.Color.BLUE;
		 * 
		 * Block b1 = new Block(c, 6, 1); Block b2 = new Block(c, 4, 2); Block
		 * b3 = new Block(c, 5, 2); center = new Block(c, 6, 2);
		 * 
		 * blockList.add(b1); blockList.add(b2); blockList.add(b3);
		 * blockList.add(center);
		 * 
		 * processList(blockList, this); } else if (type.equals(Type.oBlock)) {
		 * Color c = Block.Color.BLUE;
		 * 
		 * Block b1 = new Block(c, 5, 1); Block b2 = new Block(c, 6, 1); Block
		 * b3 = new Block(c, 5, 2); Block b4 = new Block(c, 6, 2);
		 * 
		 * blockList.add(b1); blockList.add(b2); blockList.add(b3);
		 * blockList.add(b4);
		 * 
		 * processList(blockList, this);
		 * 
		 * } else if (type.equals(Type.sBlock)) {
		 * 
		 * Color c = Block.Color.BLUE;
		 * 
		 * Block b1 = new Block(c, 5, 1); Block b2 = new Block(c, 6, 1); Block
		 * b3 = new Block(c, 4, 2); center = new Block(c, 5, 2);
		 * 
		 * blockList.add(b1); blockList.add(b2); blockList.add(b3);
		 * blockList.add(center); processList(blockList, this); } else if
		 * (type.equals(Type.zBlock)) { Color c = Block.Color.BLUE;
		 * 
		 * Block b1 = new Block(c, 4, 1); Block b2 = new Block(c, 5, 1); center
		 * = new Block(c, 5, 2); Block b4 = new Block(c, 6, 2);
		 * 
		 * blockList.add(b1); blockList.add(b2); blockList.add(center);
		 * blockList.add(b4); processList(blockList, this); } else if
		 * (type.equals(Type.tBlock)) { Color c = Block.Color.BLUE;
		 * 
		 * Block b1 = new Block(c, 5, 1); Block b2 = new Block(c, 4, 2); center
		 * = new Block(c, 5, 2); Block b4 = new Block(c, 6, 2);
		 * 
		 * blockList.add(b1); blockList.add(b2); blockList.add(center);
		 * blockList.add(b4); processList(blockList, this); }
		 */

	}

	public static void processList(LinkedList<Block> blockList, Tetromino t) {
		int left = 0, right = 0, bottom = 0;

		for (Block b : blockList) {

			// Taking care of something that should be done on Tetromino

			// initialization
			t.rightList = new LinkedList<Block>();
			t.leftList = new LinkedList<Block>();
			t.bottomList = new LinkedList<Block>();

			b.mark(true);
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

		// System.out.println("Rightmost: " + right + " Leftmost: " + left
		// + " Bottommost: " + bottom);

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

	public static enum Type {
		iBlock, reverselBlock, lBlock, oBlock, sBlock, tBlock, zBlock,

	}

	// True = Rotate right
	// False = Rotate left

	public static final int[][][][] STATE_LIST = {

			// iBlock, reverselBlock, lBlock, oBlock, sBlock, tBlock, zBlock,
			// Array goes: BlockType - Block State - Block Number - X,Y

			// 0,0 means it is the Center block
			{
					// iBlock
					{ { 5, 1 }, { -1, 0 }, { 1, 0 }, { 2, 0 } },
					{ { 5, 1 }, { 0, -1 }, { 0, 1 }, { 0, 2 } },
					{ { 5, 1 }, { 1, 0 }, { -1, 0 }, { -2, 0 } },
					{ { 5, 1 }, { 0, 1 }, { 0, -1 }, { 0, -2 } }, },

			// reverselBlock
			{ { { 6, 2 }, { 0, -1 }, { -1, 0 }, { -2, 0 } },
					{ { 6, 2 }, { 1, 0 }, { 0, -1 }, { 0, -2 } },
					{ { 6, 2 }, { 0, 1 }, { 1, 0 }, { 2, 0 } },
					{ { 6, 2 }, { -1, 0 }, { 0, 1 }, { 0, 2 } } },

			// lBlock
			{ { { 4, 2 }, { 0, -1 }, { 1, 0 }, { 2, 0 } },
					{ { 4, 2 }, { 1, 0 }, { 0, 1 }, { 0, 2 } },
					{ { 4, 2 }, { 0, 1 }, { -1, 0 }, { -2, 0 } },
					{ { 4, 2 }, { -1, 0 }, { 0, -1 }, { 0, -2 } } },

			// oBlock

			{ { { 5, 2 }, { 0, -1 }, { 1, -1 }, { 1, 0 } }, },

			// sBlock
			{ { { 5, 2 }, { -1, 0 }, { 0, -1 }, { 1, -1 } },
					{ { 5, 2 }, { 0, -1 }, { 1, 0 }, { 1, 1 } },
					{ { 5, 2 }, { 1, 0 }, { 0, 1 }, { -1, 1 } },
					{ { 5, 2 }, { 0, 1 }, { -1, 0 }, { -1, -1 } }, },

			// tBlock
			{ { { 5, 2 }, { -1, 0 }, { 0, -1 }, { 1, 0 } },
					{ { 5, 2 }, { 0, -1 }, { 1, 0 }, { 0, 1 } },
					{ { 5, 2 }, { 1, 0 }, { 0, 1 }, { -1, 0 } },
					{ { 5, 2 }, { 0, 1 }, { -1, 0 }, { 0, -1 } }, },

			// zBlock
			{ { { 5, 2 }, { -1, -1 }, { 0, -1 }, { 1, 0 } },
					{ { 5, 2 }, { 1, -1 }, { 1, 0 }, { 0, 1 } },
					{ { 5, 2 }, { 1, 1 }, { 0, 1 }, { -1, 0 } },
					{ { 5, 2 }, { -1, 1 }, { -1, 0 }, { 0, -1 } },

			} };

	public void rotate(boolean right) throws SlickException {

		//4 Possible Rotate States 0 (Original),1,2,3
		if (right) {
			if (rotateState == 3) {
				rotateState = 0;
			} else {
				rotateState++;
			}
		} else if (!right) {
			if (rotateState == 0) {
				rotateState = 3;
			} else {
				rotateState--;
			}
		}

		System.out.println("Rotate State = " + rotateState);

		//Update cX, cY
		cX = center.coordX;
		cY = center.coordY;
		
		for (Block b : blockList){
			b.mark(false);
		}
		
		int i = 0;
		if (!rotateLegal(STATE_LIST[type.ordinal()][rotateState])){
			System.out.println("Rotation failed.");
			
			//Undos Rotation State change
			
			if (right) {
				if (rotateState == 0) {
					rotateState = 3;
				} else {
					rotateState--;
				}
			} else if (!right) {
				if (rotateState == 3) {
					rotateState = 0;
				} else {
					rotateState++;
				}
			}		
			return;
		}
		
		for (Block b : blockList) {
			// If not Center
			if (i != 0) {
				b.mark(false);
				b.coordX = cX + STATE_LIST[type.ordinal()][rotateState][i][0];
				b.coordY = cY + STATE_LIST[type.ordinal()][rotateState][i][1];
				b.refresh();
			}
			b.mark(true);
			i++;
		}
		processList(blockList, this);

	}
	
	
	private boolean rotateLegal(int[][] array){
		
		for (int i = 1; i<4; i++){
			System.out.println("Checking (" + (cX + array[i][0]) + "," + (cY + array[i][1]) + ")");
			if (!Engine.check(cX + array[i][0], cY + array[i][1])){
				return false;
			}
		}
		return true;
	}
}
