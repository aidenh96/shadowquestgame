/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Villager Class (Extends Unit)
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Represents the base class for all Villager sub-types in the game world.
 */
public abstract class Villager extends Unit {
	// Attributes
	/** Dialogue Display Time */
	private static final int DISPLAY_TIME = 4000; // 4 seconds

	/** Player talk radius */
	public static final int TALK_RADIUS = 50;

	/** Dialogue control */
	private String dialogue = "null";
	private boolean renderDia = false;

	/** Timer control */
	private int pastTime;
	private boolean timerStarted = false;

	// Getters & setters
	public String getDialogue() {
		return dialogue;
	}

	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	// Constructor
	/**
	 * Create a Villager and set its initial position
	 * 
	 * @param startX
	 *            the initial x position of the Villager
	 * @param startY
	 *            the initial y position of the Villager
	 */
	public Villager(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Combat Stats
		this.setMaxHP(1);
		this.setCurHP(this.getMaxHP());
		this.setMaxDamage(0);
		this.setCooldown(0);
	}

	// Methods
	/**
	 * Keeps the time for movement direction changes
	 * 
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 * @param timeLimit
	 *            how long the timer runs for (milliseconds)
	 */
	public void timer(int delta, int timeLimit) {
		if (timerStarted) {
			if (pastTime < timeLimit)
				pastTime += delta;
			if (pastTime == timeLimit) {
				pastTime = 0;
				timerStarted = false;
			}
		} else {
			pastTime = 0;
			timerStarted = true;
			pastTime += delta;
		}
	}

	/**
	 * Determines if dialogue can be render based on player proximity, if talk
	 * is pressed and if dialogue is already pressed
	 * 
	 * @param player
	 *            the Player to check proximity against
	 * @param talk
	 *            if the talk key is pressed
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	public void canRenderDialogue(Player player, int talk, int delta) {

		// Check radius, input pressed and if timer has started
		if (checkRadius(player.getX(), player.getY(), TALK_RADIUS) && talk == 1 && !timerStarted) {
			renderDia = true;
			timer(delta, DISPLAY_TIME);
		} else if (timerStarted) {
			timer(delta, DISPLAY_TIME);
		} else {
			renderDia = false;
		}
	}

	/**
	 * Determines correct properties to draw the Villagers dialogue
	 * 
	 * @param g
	 *            the Slick graphics object, used for drawing
	 */
	public void renderDialogue(Graphics g) throws SlickException {
		// Panel colours
		Color VALUE = new Color(1.0f, 1.0f, 1.0f); // White
		Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // Black, transparent

		// Variables for layout
		String text; // Text to display
		int textX, textY; // Coordinates to draw text
		int textWidth, textHeight; // width of the text when rendered
		int barX, barY; // Coordinates to draw rectangles
		int barWidth, barHeight; // Size of rectangle to draw

		// Set Variables
		text = dialogue;
		textWidth = RPG.font.getWidth(text);
		textHeight = RPG.font.getHeight(text);

		barWidth = textWidth + RPG.PANEL_OFFSET;
		barHeight = RPG.PANEL_OFFSET;

		barX = (int) this.getX() - (barWidth / 2);
		barY = (int) this.getY() - (RPG.TILE_SIZE / 2) - (RPG.PANEL_OFFSET * 2);
		textX = (int) this.getX() - (textWidth / 2);
		textY = (int) barY + ((barHeight - textHeight) / 2);

		// Draw text bar
		g.setColor(BAR_BG);
		g.fillRect(barX, barY, barWidth, barHeight);
		g.setColor(VALUE);
		g.drawString(text, textX, textY);
	}

	/**
	 * Update the game state for a frame
	 * 
	 * @param player
	 *            the Player object thats is interacting with the Villager
	 * @param input
	 *            array of keyboard inputs
	 * @param items
	 *            array of all items loaded into the World
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	public abstract void update(Player player, int[] input, Item[] items, int delta);

	/**
	 * Renders the Villagers dialogue
	 * 
	 * @param g
	 *            the Slick graphics object, used for drawing
	 */
	@Override
	public void render(Graphics g) throws SlickException {
		if (renderDia)
			renderDialogue(g);
		super.render(g);

	}
}
