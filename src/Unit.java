/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Unit Class
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Represents the base class for all Unit sub-types in the game world.
 */
public class Unit {
	// Attributes
	/** Ratio size to fix issue with cropped sprites */
	private static final int RATIO = 4;

	/** Assets needed for the Unit */
	private Image unit;
	private Image unitFlip;
	private String unitName;

	/** Unit Position */
	private double x;
	private double y;
	private int moveDir;

	/** Combat stats */
	private double maxHP;
	private double curHP;
	private int maxDamage;
	private int cooldown;
	private int curCooldown;

	/** Unit state stats */
	private boolean alive = true;
	private boolean attacking; // the unit is attacking
	private boolean faceLeft = false;

	// Getters & Setters
	public void setUnit(Image unit) {
		this.unit = unit;
	}

	public void setUnitFlip(Image unitFlip) {
		this.unitFlip = unitFlip;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getMoveDir() {
		return moveDir;
	}

	public double getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(double maxHP) {
		this.maxHP = maxHP;
	}

	public double getCurHP() {
		return curHP;
	}

	public void setCurHP(double curHP) {
		this.curHP = curHP;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public int getCurCooldown() {
		return curCooldown;
	}

	public void setCurCooldown(int curCooldown) {
		this.curCooldown = curCooldown;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	// Constructor
	/**
	 * Create a Unit and set it's initial map position
	 * 
	 * @param startX
	 *            the starting x position of the Unit
	 * @param startY
	 *            the starting y position of the Unit
	 */
	public Unit(int startX, int startY) throws SlickException {
		// Set start location
		this.x = startX;
		this.y = startY;
	}

	// Methods
	/**
	 * Checks if a unit is still alive (has > 0 HP) and updates alive flag as such
	 */
	public void checkAlive() {
		if (this.getCurHP() < 0) {
			this.setAlive(false);
		}
	}

	/**
	 * Check if a position is within a radius to the Unit calling this method
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

		double distance = Math.sqrt(Math.pow((pX - this.getX()), 2) + Math.pow((pY - this.getY()), 2));

		if (distance <= radius)
			return true;

		return false;
	}

	/**
	 * Controls a Unit's cooldown (if applicable)
	 * 
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	public void cooldownTimer(int delta) {
		if (curCooldown > 0) {
			curCooldown = curCooldown - delta;
		} else {
			curCooldown = cooldown; // reset cooldown value
		}
	}

	/**
	 * Move the Unit in a given direction. Also blocks terrain and, updates the direction the player is facing
	 * 
	 * @param world
	 *            the world the Unit is in (to check blocking)
	 * @param dirX
	 *            the Unit's movement in the x axis (-1, 0 or 1)
	 * @param dirY
	 *            the Unit's movement in the y axis (-1, 0 or 1)
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 * @param speed
	 *            the speed in (pixel/second) to move the unit
	 */
	public void move(World world, double dirX, double dirY, int delta, double speed) {
		/* Record the movement direction */
		moveDir = 0; // Reset

		if (dirX == 1 && dirY == 1) {
			moveDir = 1; // Down-Right
		} else if (dirX == 1 && dirY == -1) {
			moveDir = 2; // Up-Right
		} else if (dirX == 1 && dirY == 0) {
			moveDir = 3; // Right
		} else if (dirX == -1 && dirY == 1) {
			moveDir = 4; // Down-Left
		} else if (dirX == -1 && dirY == -1) {
			moveDir = 5; // Up-Left
		} else if (dirX == -1 && dirY == 0) {
			moveDir = 6; // Left
		} else if (dirX == 0 && dirY == -1) {
			moveDir = 7; // Up
		} else if (dirX == 0 && dirY == 1) {
			moveDir = 8; // Down
		} else if (dirX == 0 && dirY == 0) {
			moveDir = 9; // None
		}

		/* Update player facing based on X direction */
		if (dirX > 0)
			this.faceLeft = false;
		else if (dirX < 0)
			this.faceLeft = true;

		/* Move the player by dir_x, dir_y, as a multiple of delta * speed */
		double newX = this.x + dirX * delta * speed;
		double newY = this.y + dirY * delta * speed;

		/* Move in x first */
		double xSign = Math.signum(dirX);
		if (!world.terrainBlocks(newX + xSign * RPG.TILE_SIZE / RATIO, this.y + RPG.TILE_SIZE / RATIO)
				&& !world.terrainBlocks(newX + xSign * RPG.TILE_SIZE / RATIO, this.y - RPG.TILE_SIZE / RATIO)) {
			this.x = newX;
		}

		/* Then move in y */
		double ySign = Math.signum(dirY);
		if (!world.terrainBlocks(this.x + RPG.TILE_SIZE / RATIO, newY + ySign * RPG.TILE_SIZE / RATIO)
				&& !world.terrainBlocks(this.x - RPG.TILE_SIZE / RATIO, newY + ySign * RPG.TILE_SIZE / RATIO)) {
			this.y = newY;
		}
	}

	/**
	 * Renders each Unit's status bar above the Unit
	 * 
	 * @param g
	 *            the Slick graphics object, used for drawing
	 */
	public void renderBar(Graphics g) {
		// Panel colours
		Color VALUE = new Color(1.0f, 1.0f, 1.0f); // white
		Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // black, transparent
		Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f); // red, transparent

		// Variables for layout
		String text; // text to display
		int textX, textY; // coordinates to draw text
		int textWidth, textHeight; // width of the text when rendered
		int barX, barY; // coordinates to draw rectangles
		int barWidth, barHeight; // size of rectangle to draw
		double healthPercent, HPBarWidth; // size of red rectangle

		// Set Variables
		text = this.unitName;
		textWidth = RPG.font.getWidth(text);
		textHeight = RPG.font.getHeight(text);

		barWidth = textWidth + RPG.PANEL_OFFSET;
		barHeight = RPG.PANEL_OFFSET;

		barX = (int) this.getX() - (barWidth / 2);
		barY = (int) this.getY() - (RPG.TILE_SIZE / 2) - RPG.PANEL_OFFSET;
		textX = (int) this.getX() - (textWidth / 2);
		textY = (int) barY + ((barHeight - textHeight) / 2);

		healthPercent = (this.getCurHP() / this.getMaxHP());
		HPBarWidth = healthPercent * (barWidth); // size of red (HP) rectangle

		// Draw bars
		g.setColor(BAR_BG);
		g.fillRect(barX, barY, barWidth, barHeight);
		g.setColor(BAR);
		g.fillRect(barX, barY, (int) HPBarWidth, barHeight);
		g.setColor(VALUE);
		g.drawString(text, textX, textY);

	}

	/**
	 * Render the Unit in a given location
	 * 
	 * @param g
	 *            the Slick graphics object, used for drawing
	 */
	public void render(Graphics g) throws SlickException {
		if (alive) {

			// Render the correct image for the way the Unit is facing
			Image whichImg;
			whichImg = this.faceLeft ? this.unitFlip : this.unit;
			whichImg.drawCentered((int) x, (int) y);

			// Do not draw the status bar for the Player Unit
			if (!(this instanceof Player))
				renderBar(g);
		}
	}
}
