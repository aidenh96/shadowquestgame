/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Amulet Class (Extends Item)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

/**
 * Represents the "Amulet of Vitality" item and handles its effects.
 */
public class Amulet extends Item {
	// Attributes

	/** Effect constant */
	private static final int EFFECT = 80;

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
	public Amulet(int x, int y) throws SlickException {
		super(x, y);

		// Set sprite
		Image sprite = new Image(RPG.ITEM_PATH + "amulet.png");
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
			player.setMaxHP(player.getMaxHP() + EFFECT);
			player.setCurHP(player.getCurHP() + EFFECT);
		}

	}
}
