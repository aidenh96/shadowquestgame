/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Sword Class (Extends Item)
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the "Sword of Strength" item and handles its effects.
 */
public class Sword extends Item {
	// Attributes

	/** Effect constant */
	private static final int EFFECT = 30;

	// Constructor
	/**
	 * Create an Item and set its position
	 * 
	 * @param x
	 *            the x position of the Tome
	 * 
	 * @param y
	 *            the y position of the Tome
	 */
	public Sword(int x, int y) throws SlickException {
		super(x, y);

		// Set sprite
		Image sprite = new Image(RPG.ITEM_PATH + "sword.png");
		this.setSprite(sprite);
	}

	// Methods
	/**
	 * Update the game state for a frame
	 * 
	 * @param player
	 *            the Player that is to be attacked
	 */
	@Override
	public void update(Player player) {
		if (checkRadius(player.getX(), player.getY(), RADIUS) && !this.isPickedUp()) {
			this.setPickedUp(true);
			player.setMaxDamage(player.getMaxDamage() + EFFECT);
		}

	}
}
