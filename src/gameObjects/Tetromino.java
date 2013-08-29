package gameObjects;

import java.util.LinkedList;

import org.newdawn.slick.SlickException;

import main.Engine;

public class Tetromino {
	
	
	

	//List of the blocks at the very bottom
	public LinkedList<Block> bottomList = new LinkedList<Block>();
	
	
	//List of blocks at the very right
	public LinkedList<Block> rightList = new LinkedList<Block>();

	//And left
	public LinkedList<Block> leftList = new LinkedList<Block>();

	
	public LinkedList<Block> blockList = new LinkedList<Block>();
	
	public static int startX;
	public static int startY;
	
	public Tetromino(Type type) throws SlickException{
		if (type.equals(Type.reverselBlock)){
			
			Block b1 = new Block(Block.Color.BLUE);
			b1.coordX = 5;
			b1.coordY = 1;
			b1.x = startX - Engine.BLOCKSIZE;
			b1.y = startY;
			
			Block b2 = new Block(Block.Color.BLUE);
			b2.coordX = 5;
			b2.coordY = 2;
			b2.x = startX - Engine.BLOCKSIZE;
			b2.y = startY + Engine.BLOCKSIZE;
			
			Block b3 = new Block(Block.Color.BLUE);
			b3.coordX = 6;
			b3.coordY = 2;
			b3.x = startX;
			b3.y = startY + Engine.BLOCKSIZE;
			
			Block b4 = new Block(Block.Color.BLUE);
			b4.coordX = 7;
			b4.coordY = 2;
			b4.x = startX + Engine.BLOCKSIZE;
			b4.y = startY + Engine.BLOCKSIZE;

			blockList.add(b1);
			blockList.add(b2);
			blockList.add(b3);
			blockList.add(b4);
			
			bottomList.add(b2);
			bottomList.add(b3);
			bottomList.add(b4);
			
			
			rightList.add(b4);
			
			leftList.add(b1);
			leftList.add(b2);
		}
	}
	
	public enum Type{
		iBlock,
		reverselBlock,
		lBlock,
		oBlock,
		sBlock,
		tBlock,
		zBlock,
		
	}
	
	
}
