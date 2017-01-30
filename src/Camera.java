
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Camera Class
 * Author: Aiden H
 * */

// Imports
import org.newdawn.slick.SlickException;

/**
 * Represents the camera that controls our viewpoint.
 */
public class Camera {
	// Attributes
	/** The unit this camera is following */
	private Player unitFollow;

	/** X position of the camera (pixels) */
	private int cameraX;

	/** Y position of the camera (pixels) */
	private int cameraY;

	// Getter & Setters
	public int getMinX() {
		return cameraX;
	}

	public int getMaxX() {
		return cameraX + RPG.SCR_WIDTH;
	}

	public int getMinY() {
		return cameraY;
	}

	public int getMaxY() {
		return cameraY + RPG.SCR_HEIGHT;
	}

	// Constructor
	/**
	 * Create a new Camera object, sets object to follow
	 * 
	 * @param player
	 *            the player to follow
	 */
	public Camera(Player player) throws SlickException {
		// Set the player that is to be followed
		followUnit(player);
	}

	// Methods
	/**
	 * Tells the camera to follow a given unit
	 * 
	 * @param unit
	 *            the unit to be followed
	 */
	public void followUnit(Object unit) throws SlickException {

		if (unit instanceof Player) {
			unitFollow = (Player) unit; // Casting unit to a Player object
		}
	}

	/**
	 * Update the game camera to re-centre it's viewpoint around the player
	 * 
	 */
	public void update() throws SlickException {
		cameraX = (int) unitFollow.getX() - (RPG.SCR_WIDTH / 2);
		cameraY = (int) unitFollow.getY() - ((RPG.SCR_HEIGHT - RPG.PANEL_HEIGHT) / 2);

		// Lock camera at edges of map (resets the camera position to 'lock' it)
		// Left
		if (cameraX < 0) {
			cameraX = 0;
		}
		// Right
		if (cameraX + RPG.SCR_WIDTH > RPG.MAP_SIZE) {
			cameraX = RPG.MAP_SIZE - RPG.SCR_WIDTH;
		}
		// Top
		if (cameraY < 0) {
			cameraY = 0;
		}
		// Bottom
		if (cameraY + RPG.SCR_HEIGHT > RPG.MAP_SIZE) {
			cameraY = RPG.MAP_SIZE - RPG.SCR_HEIGHT;
		}
	}
}