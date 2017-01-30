/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Elixir Class (Extends Item)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the "Elixir of Life" item and handles its effects.
 */
public class Elixir extends Item {

	// Constructor
	/**
	 * Create an Item and set its position
	 * 
	 * @param x
	 *            the x position of the Elixir
	 * 
	 * @param y
	 *            the y position of the Elixir
	 */
	public Elixir(int x, int y) throws SlickException {
		super(x, y);

		// Set sprite
		Image sprite = new Image(RPG.ITEM_PATH + "elixir.png");
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
			this.setRenderItem(true);
			this.setPickedUp(true);
		}

	}

}
