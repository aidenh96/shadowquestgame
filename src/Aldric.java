/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Aldric Class (Extends Villager)
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the villager Prince Aldric, and controls all his effects.
 */
public class Aldric extends Villager {
	// Constructor
	/**
	 * Create Aldric and set his initial position
	 * 
	 * @param startX
	 *            the initial x position of Prince Aldric
	 * 
	 * @param startY
	 *            the initial y position of Prince Aldric
	 */
	public Aldric(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "prince.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Prince Aldric");
	}
	
	// Methods
	/**
	 * Removes the Elixir from the inventory when speaking to Aldric
	 * 
	 * @param player
	 *            the Player object they carries the Elixir
	 * @param talk
	 *            if the T key has been pressed
	 * @param elixir
	 *            the Elixir object
	 */
	public void effect(Player player, int talk, Item elixir) {

		if (checkRadius(player.getX(), player.getY(), Villager.TALK_RADIUS) && talk == 1 && elixir.isPickedUp()) {
			elixir.setRenderItem(false);
		}
	}

	/**
	 * Update Prince Aldric's state for a frame
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

		// Select the Elixir item out of the items array
		Item elixir = null;
		for (Item i : items) {
			if (i instanceof Elixir)
				elixir = i;
		}

		// Select correct dialogue
		if (!elixir.isPickedUp()) {
			this.setDialogue("Please seek out the Elixir of Life to cure the king");
		} else if (elixir.isPickedUp()) {
			this.setDialogue("The elixir! My father is cured! Thank you!");
		}

		// Apply any special effects
		this.effect(player, talk, elixir);

		// Determine if dialogue can be rendered
		this.canRenderDialogue(player, talk, delta);
	}
}
