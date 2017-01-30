/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - World Class
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 * */

// Imports
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Represents the entire game world. (Designed to be instantiated just once for the whole game).
 */
public class World {
	// Attributes
	/** Testing Flags */
	private static final boolean DISABLE_BLOCKING = false;
	private static final boolean TEST_MODE = false;
	
	/** Array Maximums */
	private static final int MAX_MONSTERS = 130;
	private static final int MAX_VILLAGERS = 4;
	private static final int MAX_ITEMS = 4;

	/** Declare other the objects the World needs to manage */
	// Game Control
	private TiledMap map;
	private Player player;
	private Camera cam;
	private Panel panel;

	// Villagers
	private Villager[] villagers = new Villager[MAX_VILLAGERS];

	// Items
	private Item[] items = new Item[MAX_ITEMS];

	// Monsters
	private Monster[] monsters = new Monster[MAX_MONSTERS];

	// Constructor
	/**
	 * Create a new World object, initialises everything in the world
	 */
	public World() throws SlickException, FileNotFoundException {

		// Game Control
		map = new TiledMap(RPG.ASSETS_PATH + "/map.tmx", RPG.ASSETS_PATH);
		player = new Player(RPG.START_X, RPG.START_Y);
		cam = new Camera(player);
		panel = new Panel(RPG.ASSETS_PATH + "/panel.png");

		// Array index counters
		int vIndex = 0;
		int iIndex = 0;
		int mIndex = 0;

		// Read in location file, contains start location of all Units except Player
		// Data format: unit_name<whitespace>x_location<whitespace>y_location<newline>
		try {
			
			String file = TEST_MODE ? "/test_locations.txt" : "/locations.txt"; 		
			Scanner sc = new Scanner(new FileReader(RPG.ASSETS_PATH + file));
			while (sc.hasNext()) {

				String name = sc.next();

				if (name.equals("Prince")) {
					villagers[vIndex] = new Aldric(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					vIndex += 1;
				} else if (name.equals("Elvira")) {
					villagers[vIndex] = new Elvira(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					vIndex += 1;
				} else if (name.equals("Garth")) {
					villagers[vIndex] = new Garth(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					vIndex += 1;
				} else if (name.equals("Amulet")) {
					items[iIndex] = new Amulet(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					iIndex += 1;
				} else if (name.equals("Sword")) {
					items[iIndex] = new Sword(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					iIndex += 1;
				} else if (name.equals("Tome")) {
					items[iIndex] = new Tome(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					iIndex += 1;
				} else if (name.equals("Elixir")) {
					items[iIndex] = new Elixir(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					iIndex += 1;
				} else if (name.equals("GiantBat")) {
					monsters[mIndex] = new Bat(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					mIndex += 1;
				} else if (name.equals("Zombie")) {
					monsters[mIndex] = new Zombie(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					mIndex += 1;
				} else if (name.equals("Bandit")) {
					monsters[mIndex] = new Bandit(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					mIndex += 1;
				} else if (name.equals("Skeleton")) {
					monsters[mIndex] = new Skeleton(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					mIndex += 1;
				} else if (name.equals("Draelic")) {
					monsters[mIndex] = new Draelic(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
					mIndex += 1;
				}
			}

			sc.close();
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	// Methods
	/**
	 * Locks the player at the edge of the screen. Prevents player from going
	 * off screen
	 */
	public void lockEdge() {
		// Left & right
		if (player.getX() < RPG.TILE_SIZE / 2) {
			player.setX(RPG.TILE_SIZE / 2);

		} else if (player.getX() >= RPG.MAP_SIZE - (RPG.TILE_SIZE / 2)) {
			player.setX(RPG.MAP_SIZE - (RPG.TILE_SIZE / 2));
		}

		// Top & bottom
		if (player.getY() < RPG.TILE_SIZE / 2) {
			player.setY(RPG.TILE_SIZE / 2);

		} else if (player.getY() >= RPG.MAP_SIZE - RPG.PANEL_HEIGHT - (RPG.TILE_SIZE / 2)) {
			player.setY(RPG.MAP_SIZE - RPG.PANEL_HEIGHT - (RPG.TILE_SIZE / 2));
		}
	}
	
	/**
	 * Determines whether a particular map coordinate blocks movement
	 * 
	 * @param x
	 *            map x coordinate (in pixels)
	 * @param y
	 *            map y coordinate (in pixels)
	 * @return true if the coordinate blocks movement
	 */
	public boolean terrainBlocks(double x, double y) {
		if (DISABLE_BLOCKING)
			return false;

		// Check we are within the bounds of the map
		if (x < 0 || y < 0 || x > RPG.MAP_SIZE || y > RPG.MAP_SIZE) {
			return true;
		}

		// Check the tile properties
		int tile_x = (int) x / RPG.TILE_SIZE;
		int tile_y = (int) y / RPG.TILE_SIZE;
		int tileid = map.getTileId(tile_x, tile_y, 0);
		String block = map.getTileProperty(tileid, "block", "0");
		return !block.equals("0");
	}

	/**
	 * Update the game state for a frame
	 * 
	 * @param input
	 *            array of keyboard inputs
	 * @param delta
	 *            time passed since last frame (milliseconds)           
	 */
	public void update(int[] input, int delta) throws SlickException {

		// Update the Player (movement, attacks)
		player.update(this, input, delta);
		
		// Update the Villagers
		for (Villager v : villagers)
			if (v != null)
				v.update(player, input, items, delta);

		// Update the Items
		for (Item i : items)
			if (i != null)
				i.update(player);

		// Update the Monsters (movement, attacks)
		for (Monster m : monsters)
			if (m != null)
				m.update(this, player, delta);
		
		// Lock edges
		lockEdge();
		
		// Update the camera to the new position
		cam.update();
	}

	/**
	 * Render the entire screen, so it reflects the current game state.
	 * 
	 * @param g
	 *            the Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) throws SlickException {

		/* Calculate the section of the map to render */
		int x = (int) -(cam.getMinX() % RPG.TILE_SIZE);
		int y = (int) -(cam.getMinY() % RPG.TILE_SIZE);

		int sx = (int) (cam.getMinX() / RPG.TILE_SIZE);
		int sy = (int) (cam.getMinY() / RPG.TILE_SIZE);

		int w = (int) ((cam.getMaxX() / RPG.TILE_SIZE) - sx) + 1;
		int h = (int) ((cam.getMaxY() / RPG.TILE_SIZE) - sy) + 1;

		/* Render the map using the calculated values */
		map.render(x, y, sx, sy, w, h);
		
		// Translate the Graphics object
		g.translate(-cam.getMinX(), -cam.getMinY());
		
		// Render Items
		for (Item i : items)
			if (!i.isPickedUp())
				i.render();
		
		// Render Villagers
		for (Villager v : villagers)
			if (v != null)
				v.render(g);

		// Render Monsters
		for (Monster m : monsters)
			if (m != null)
				m.render(g);
		
		// Render Player
		player.render(g);
	
		// Translate the Graphics object
		g.translate(cam.getMinX(), cam.getMinY());
		
		// Render Player stats panel 
		panel.renderPanel(g, player, items);	
	}
}
