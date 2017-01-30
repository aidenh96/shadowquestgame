/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - RPG Class
 * Author: Matt Giuca
 * Updated By: Aiden Hussein <hussein>
 * Student ID: 756647
 * */

// Imports
import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main class for the Role-Playing Game engine. Handles initialisation, input and rendering.
 */
public class RPG extends BasicGame {
	// Attributes
	/** Screen width (pixels) */
	public static final int SCR_WIDTH = 800;

	/** Screen height (pixels) */
	public static final int SCR_HEIGHT = 600;

	/** Initial camera position (pixels) */
	public static final int START_X = 756;
	public static final int START_Y = 684;

	/** Width/height of the map (pixels) */
	public static final int MAP_SIZE = 6912;

	/** Width/height of one tile (pixels) */
	public static final int TILE_SIZE = 72;

	/** Panel values */
	public static final int PANEL_HEIGHT = 70;
	public static final int PANEL_OFFSET = 20;

	/** Define a font */
	public static Font font;

	/** Assets paths */
	public static final String ASSETS_PATH = "assets";
	public static final String UNIT_PATH = ASSETS_PATH + "/units/";
	public static final String ITEM_PATH = ASSETS_PATH + "/items/";

	/** Define a World */
	private World world;

	// Constructor
	/** Create a new RPG object. */
	public RPG() {
		super("RPG Game Engine");
	}

	/**
	 * Initialise the game state
	 * 
	 * @param gc
	 *            the Slick game container object
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {

		// Load World
		try {
			world = new World();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Load font
		font = FontLoader.loadFont(ASSETS_PATH + "/DejaVuSans-Bold.ttf", 15);
	}

	/**
	 * Update the game state for a frame.
	 * 
	 * @param gc
	 *            the Slick game container object
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// Get data about the current input (keyboard state).
		Input input = gc.getInput();

		// Update the player's movement direction based on keyboard presses.
		int dirX = 0;
		int dirY = 0;
		int attack = 0;
		int talk = 0;
		if (input.isKeyDown(Input.KEY_DOWN))
			dirY += 1;
		if (input.isKeyDown(Input.KEY_UP))
			dirY -= 1;
		if (input.isKeyDown(Input.KEY_LEFT))
			dirX -= 1;
		if (input.isKeyDown(Input.KEY_RIGHT))
			dirX += 1;
		if (input.isKeyDown(Input.KEY_A))
			attack = 1;
		if (input.isKeyDown(Input.KEY_T))
			talk = 1;

		// Initialise an array for the input states to be recorded
		int[] inputState = { dirX, dirY, attack, talk };

		// Let World.update decide what to do with this data
		world.update(inputState, delta);
	}

	/**
	 * Render the entire screen, so it reflects the current game state
	 * 
	 * @param gc
	 *            the Slick game container object
	 * @param g
	 *            the Slick graphics object, used for drawing
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {

		// Set font to be used
		g.setFont(font);

		// Let World.render handle the rendering
		world.render(g);
	}

	/**
	 * Start-up method. Creates the game and runs it
	 * 
	 * @param args
	 *            command-line arguments (ignored)
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new RPG());

		app.setShowFPS(false);
		app.setDisplayMode(SCR_WIDTH, SCR_HEIGHT, false);
		app.start();
	}
}
