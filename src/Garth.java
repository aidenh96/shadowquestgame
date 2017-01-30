/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Garth Class (Extends Villager)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the villager Garth, and controls all his effects.
 */
public class Garth extends Villager {

	// Constructor
	/**
	 * Create Garth and set his initial position
	 * 
	 * @param startX
	 *            the initial x position of Garth
	 * @param startY
	 *            the initial y position of Garth
	 */
	public Garth(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "peasant.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Garth		");
	}

	// Methods
	/**
	 * Update Garth's state for a frame
	 * 
	 * @param player
	 *            the Player object thats is interacting with Garth
	 * @param input
	 *            array of keyboard inputs
	 * @param items
	 *            array of all items loaded into the World
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	@Override
	public void update(Player player, int[] input, Item[] items, int delta) {
		// Load required input
		int talk = input[3];

		// Set all items
		Item amulet = null;
		Item sword = null;
		Item tome = null;
		for (Item i : items) {
			if (i instanceof Amulet)
				amulet = i;
			if (i instanceof Sword)
				sword = i;
			if (i instanceof Tome)
				tome = i;
		}

		// Select correct dialogue
		if (!amulet.isPickedUp()) {
			this.setDialogue("Find the Amulet of Vitality, across the river to the west");
		} else if (!sword.isPickedUp()) {
			this.setDialogue("Find the Sword of Strength - cross the bridge to the east, then head south");
		} else if (!tome.isPickedUp()) {
			this.setDialogue("Find the Tome of Agility, in the Land of Shadows");
		} else if (amulet.isPickedUp() && sword.isPickedUp() && tome.isPickedUp()) {
			this.setDialogue("You have found all the treasure I know of");
		}

		// Determine if dialogue can be rendered
		this.canRenderDialogue(player, talk, delta);
	}
}
