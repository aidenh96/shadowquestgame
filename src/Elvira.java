/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Elvira Class (Extends Villager)
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the villager Elvira, and controls all her effects.
 */
public class Elvira extends Villager {

	// Constructor
	/**
	 * Create Elvira and set her initial position
	 * 
	 * @param startX
	 *            the initial x position of Elvira
	 * @param startY
	 *            the initial y position of Elvira
	 */
	public Elvira(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "shaman.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Elvira");
	}

	// Methods
	/**
	 * Fully heals the Player when speaking to Elvira
	 * 
	 * @param player
	 *            the Player object that need to be healed
	 * @param talk
	 *            if the T key has been pressed
	 */
	public void effect(Player player, int talk) {

		if (checkRadius(player.getX(), player.getY(), Villager.TALK_RADIUS) && talk == 1) {
			player.setCurHP(player.getMaxHP());
		}
	}

	/**
	 * Update Elvira's state for a frame
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

		// Select correct dialogue
		if (player.getCurHP() == player.getMaxHP()) {
			this.setDialogue("Return to me if you ever need healing");
		} else if (player.getCurHP() < player.getMaxHP()) {
			this.setDialogue("You're looking much healthier now");
		}

		// Apply any special effects
		this.effect(player, talk);

		// Determine if dialogue can be rendered
		this.canRenderDialogue(player, talk, delta);
	}
}
