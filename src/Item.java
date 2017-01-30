/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Item Class
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the base class for all Item sub-types in the game world.
 */
public abstract class Item {
	// Attributes

	/** Interaction radius */
	public static final int RADIUS = 50;

	/** Item assets */
	private Image sprite;

	/** Item position */
	private int itemX;
	private int itemY;

	/** Item current state */
	private boolean pickedUp = false;
	private boolean renderItem = true;

	// Getters & Setters
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public int getItemX() {
		return itemX;
	}

	public void setItemX(int itemX) {
		this.itemX = itemX;
	}

	public int getItemY() {
		return itemY;
	}

	public void setItemY(int itemY) {
		this.itemY = itemY;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public boolean isRenderItem() {
		return renderItem;
	}

	public void setRenderItem(boolean renderItem) {
		this.renderItem = renderItem;
	}

	// Constructor
	/**
	 * Create an Item and set its position
	 * 
	 * @param x
	 *            the initial x position of Prince Aldric
	 * 
	 * @param y
	 *            the initial y position of Prince Aldric
	 */
	public Item(int x, int y) throws SlickException {
		itemX = x;
		itemY = y;

	}

	// Methods
	/**
	 * Check if a position is within a radius to the object calling this method
	 * 
	 * @param pX
	 *            the x value of a position to check
	 * @param pY
	 *            the y value of the position to check
	 * @param radius
	 *            the maximum radius value
	 * @return true if the specified position is within the specified radius of the object calling this method
	 */
	public boolean checkRadius(double pX, double pY, int radius) {

		double distance = Math.sqrt(Math.pow((pX - itemX), 2) + Math.pow((pY - itemY), 2));

		if (distance <= radius)
			return true;

		return false;
	}

	/**
	 * Update the game state for a frame
	 * 
	 * @param player
	 *            the Player that is to be attacked
	 */
	public abstract void update(Player player);

	/**
	 * Render the Unit in a given location
	 * 
	 */
	public void render() {
		if (renderItem)
			sprite.drawCentered(itemX, itemY);
	}
}
