package gameObjects;

import gameObjects.Block.Color;

import java.util.LinkedList;

import org.newdawn.slick.SlickException;

import main.Engine;

public class Tetromino {


	public LinkedList<Block> blockList = new LinkedList<Block>();

	public LinkedList<Point> pointList = new LinkedList<Point>();
	
	public static int startX;
	public static int startY;

	
	
	private int cX;
	private int cY;

	public int rotateState;

	public Type type;

	public Block center;

	public Tetromino(Type type) throws SlickException {
		System.out.println("Tetromino created : " + type.name());
		pointList = new LinkedList<Point>();
		
		rotateState = 0;
		this.type = type;

		int index = type.ordinal();
		// Color c = Color.values()[index];
		Color c = Color.BLUE;

		// Center X, Center y
		cX = STATE_LIST[index][0][0][0];
		cY = STATE_LIST[index][0][0][1];

		center = new Block(Color.RED, cX, cY, this);
		pointList.add(center.point);
		blockList.add(center);

		// Each Tetromino has 4 blocks
		for (int i = 1; i < 4; i++) {
			Block b = new Block(c, cX + STATE_LIST[index][0][i][0], cY
					+ STATE_LIST[index][0][i][1], this);
			blockList.add(b);
			pointList.add(b.point);
		}
		processList(blockList, this);

	}

	public static void processList(LinkedList<Block> blockList, Tetromino t) {
		
		/*
		int left = 0, right = 0, bottom = 0;


		// initialization
		t.rightList = new LinkedList<Block>();
		t.leftList = new LinkedList<Block>();
		t.bottomList = new LinkedList<Block>();
		

		for (Block b : blockList) {

			
		
			// Taking care of something that should be done on Tetromino
			
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
			if (b.Point.coordX == right) {
				t.rightList.add(b);
			}

			if (b.coordX == left) {
				t.leftList.add(b);
			}

			if (b.coordY == bottom) {
				t.bottomList.add(b);
			}
		}
		*/
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

		if (type.equals(Type.oBlock)){
			return;
		}
		
		// 4 Possible Rotate States 0 (Original),1,2,3
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
		
		// Update cX, cY
		cX = center.point.x;
		cY = center.point.y;

		
		if (!rotateLegal(STATE_LIST[type.ordinal()][rotateState])) {
			System.out.println("Rotation failed.");
			// Undos Rotation State change

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

		pointList = new LinkedList<Point>();
		pointList.add(center.point);
		
		System.out.println("Rotate State = " + rotateState);
		
		int i = 0;
		
		for (Block b : blockList) {
			// If not Center
			if (i != 0) {
				b.mark(false);
				b.point = new Point(cX + STATE_LIST[type.ordinal()][rotateState][i][0], cY + STATE_LIST[type.ordinal()][rotateState][i][1]);
				b.refresh();
				pointList.add(b.point);
				b.mark(true);
			}
			i++;
		}
		
		processList(blockList, this);

	}

	private boolean rotateLegal(int[][] array) {

		for (int i = 1; i < 4; i++) {
			// System.out.println("Checking (" + (cX + array[i][0]) + "," + (cY
			// + array[i][1]) + ")");
			if (!(Engine.check(cX + array[i][0], cY + array[i][1]))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean includes(int x, int y){
		//System.out.println("CHECKING (" + x + "," + y + ")");
		for (Point p: pointList){
			//System.out.println("(" + p.x + "," + p.y + ")");
			if (p.x == x && p.y == y){
				return true;
			}
		}
		return false;
	}
}
