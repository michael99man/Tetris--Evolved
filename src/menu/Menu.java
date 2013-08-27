package menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public interface Menu {
	void render(GameContainer arg0, Graphics g) throws SlickException;
	void close(String selection);
	void processInput(Input input);
}
