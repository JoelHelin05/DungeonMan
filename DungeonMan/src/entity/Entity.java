package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	public int speed;
	public float x;
	public float y;
	public int screenX;
	public int screenY;
	public float xVelocity;
	public float yVelocity;

	public boolean isFlipped;
	public BufferedImage idle0, idle1, idle2, idle3, idle4, idle5;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle collisionArea;
	public boolean collisionY = false;
	public boolean collisionX = false;
}
