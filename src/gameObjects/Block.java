package gameObjects;

import org.newdawn.slick.Image;

public class Block {
	
	private Color color;
	public Image img; 
	
	
	public Block(Color color){
		this.color = color;
		
		
	}
	
	
	public static enum Color{
		RED,
		GREEN,
		BLUE,
		YELLOW,
		
		//etc
		
	}
}
