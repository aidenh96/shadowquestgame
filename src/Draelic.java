/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Draelic Class (Extends Aggressive)
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the "Draelic" aggressive enemy and set its combat stats.
 */
public class Draelic extends Aggressive {

	/**
	 * Create Draelic and set its initial position
	 * 
	 * @param startX
	 *            the initial x position of Draelic
	 * @param startY
	 *            the initial y position of Draelic
	 */
	public Draelic(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "necromancer.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Draelic");

		// Combat Stats
		this.setMaxHP(140);
		this.setCurHP(this.getMaxHP());
		this.setMaxDamage(30);
		this.setCooldown(400);

		// Choose initial movement direction
		chooseDir(false, 0, 9);
	}
}
