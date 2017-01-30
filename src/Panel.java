/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Panel Class
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Maintains the information needed to be displayed in the HUD Panel at the bottom of the game window.
 */
public class Panel {
	// Attributes

	/** Define a image for the Panel */
	public Image panelBack;

	// Constructor
	/**
	 * Create a Panel, and set its background sprite
	 * 
	 * @param sprite
	 *            the path to the panel background sprite
	 */
	public Panel(String sprite) throws SlickException {
		panelBack = new Image(sprite);
	}

	// Methods
	/**
	 * Renders the player's status panel
	 * 
	 * @param g
	 *            the current Slick graphics context
	 * @param player
	 *            the Player object whose stats are to be displayed
	 * @param items
	 *            an array of all the item in the game
	 */
	public void renderPanel(Graphics g, Player player, Item[] items) {
		// Panel colours
		Color LABEL = new Color(0.9f, 0.9f, 0.4f); // Gold
		Color VALUE = new Color(1.0f, 1.0f, 1.0f); // White
		Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // Black, transparent
		Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f); // Red, transparent

		// Variables for layout
		String text; // Text to display
		int textX, textY; // Coordinates to draw text
		int barX, barY; // Coordinates to draw rectangles
		int barWidth, barHeight; // Size of rectangle to draw
		int HPBarWidth; // Size of red (HP) rectangle
		int invX, invY; // Coordinates to draw inventory item
		double healthPercent; // Player's health, as a percentage

		// Panel background image
		panelBack.draw(0, RPG.SCR_HEIGHT - RPG.PANEL_HEIGHT);

		// Display the player's health
		textX = 15;
		textY = RPG.SCR_HEIGHT - RPG.PANEL_HEIGHT + 25;
		g.setColor(LABEL);
		g.drawString("Health:", textX, textY);

		text = Integer.toString((int) player.getCurHP()) + "/" + Integer.toString((int) player.getMaxHP());

		barX = 90;
		barY = RPG.SCR_HEIGHT - RPG.PANEL_HEIGHT + 20;
		barWidth = 90;
		barHeight = 30;

		healthPercent = (player.getCurHP() / player.getMaxHP());

		HPBarWidth = (int) (barWidth * healthPercent);
		textX = barX + (barWidth - g.getFont().getWidth(text)) / 2;
		g.setColor(BAR_BG);
		g.fillRect(barX, barY, barWidth, barHeight);
		g.setColor(BAR);
		g.fillRect(barX, barY, HPBarWidth, barHeight);
		g.setColor(VALUE);
		g.drawString(text, textX, textY);

		// Display the player's damage and cooldown
		textX = 200;
		g.setColor(LABEL);
		g.drawString("Damage:", textX, textY);
		textX += 80;
		text = Integer.toString(player.getMaxDamage());
		g.setColor(VALUE);
		g.drawString(text, textX, textY);
		textX += 40;
		g.setColor(LABEL);
		g.drawString("Rate:", textX, textY);
		textX += 55;
		text = Integer.toString(player.getCurCooldown());
		g.setColor(VALUE);
		g.drawString(text, textX, textY);

		// Display the player's inventory
		g.setColor(LABEL);
		g.drawString("Items:", 420, textY);
		barX = 490;
		barY = RPG.SCR_HEIGHT - RPG.PANEL_HEIGHT + 10;
		barWidth = 288;
		barHeight = barHeight + 20;
		g.setColor(BAR_BG);
		g.fillRect(barX, barY, barWidth, barHeight);

		invX = 490 + (72 / 2);
		invY = (RPG.SCR_HEIGHT - RPG.PANEL_HEIGHT) + (RPG.PANEL_HEIGHT - (72 / 2));

		for (Item i : items) // each item in the player's inventory)
		{
			if (i.isPickedUp()) {
				// Render the item to (invX, invY)
				i.setItemX(invX);
				i.setItemY(invY);
				i.render();
				invX += RPG.TILE_SIZE;
			}
		}
	}
}
