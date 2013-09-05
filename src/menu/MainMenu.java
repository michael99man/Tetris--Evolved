package menu;

import main.Engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class MainMenu implements Menu {

	private Engine parent;
	private static final String[] stringList = { "Start Game", "High Scores",
			"Quit" };

	private static TrueTypeFont titleFont = Engine.requestFont(50);
	private String selected = stringList[0];

	public MainMenu(Engine e) {
		parent = e;
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		
		
		g.setFont(titleFont);
		int y = 100;
		for (String s : stringList) {

			g.setColor(s.equalsIgnoreCase(selected) ? Color.blue : Color.white);

			g.drawString(s, 200, y);
			y += 40;
		}

	}

	@Override
	public void close(String selection) {
		System.out.println("Closing menu. Selection: " + selected);

		if (selection.equals("Start Game")){
			parent.startGame();
		}
	}

	@Override
	public void processInput(Input input) {
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			close(selected);
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			selected = stringList[findPlace(1)];
		} else if (input.isKeyPressed(Input.KEY_UP)) {
			selected = stringList[findPlace(-1)];
		}
	}

	private int findPlace(int change) {
		for (int i = 0; i < stringList.length; i++) {
			if (stringList[i].equalsIgnoreCase(selected)) {
				int place = i + (change);
				if (place < 0) {
					place = stringList.length - 1;
				}
				if (place > stringList.length - 1) {
					place = 0;
				}
				return place;
			}
		}
		return 0;
	}

}
