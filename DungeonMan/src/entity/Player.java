package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public float x;
	public float y;
	public int worldX;
	public int worldY;
	
	boolean isMoving;
	int spriteNum;
	BufferedImage idleImage[] = new BufferedImage[6];
	BufferedImage runImage[] = new BufferedImage[6];
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		x = gp.screenWidth/2 - (gp.tileSize/2);
		y = gp.screenHeight/2 - (gp.tileSize/2);
		worldX = (int)x;
		worldY = (int)y;
		
		collisionArea = new Rectangle(15, 24, 18, 18);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		speed = 4;
	}
	
	public void getPlayerImage() {
		
		try {
			
			idleImage[0] = ImageIO.read(getClass().getResourceAsStream("/player/knight_idle_anim_f0.png"));
			idleImage[1] = ImageIO.read(getClass().getResourceAsStream("/player/knight_idle_anim_f1.png"));
			idleImage[2] = ImageIO.read(getClass().getResourceAsStream("/player/knight_idle_anim_f2.png"));
			idleImage[3] = ImageIO.read(getClass().getResourceAsStream("/player/knight_idle_anim_f3.png"));
			idleImage[4] = ImageIO.read(getClass().getResourceAsStream("/player/knight_idle_anim_f4.png"));
			idleImage[5] = ImageIO.read(getClass().getResourceAsStream("/player/knight_idle_anim_f5.png"));
			runImage[0] = ImageIO.read(getClass().getResourceAsStream("/player/knight_run_anim_f0.png"));
			runImage[1] = ImageIO.read(getClass().getResourceAsStream("/player/knight_run_anim_f1.png"));
			runImage[2] = ImageIO.read(getClass().getResourceAsStream("/player/knight_run_anim_f2.png"));
			runImage[3] = ImageIO.read(getClass().getResourceAsStream("/player/knight_run_anim_f3.png"));
			runImage[4] = ImageIO.read(getClass().getResourceAsStream("/player/knight_run_anim_f4.png"));
			runImage[5] = ImageIO.read(getClass().getResourceAsStream("/player/knight_run_anim_f5.png"));
		}catch(IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public void update() {

		yVelocity = 0;
		xVelocity = 0;
		if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			if(keyH.upPressed) {
				yVelocity --;
			}
			if(keyH.downPressed) {
				yVelocity ++;
			}
			if(keyH.leftPressed) {
				isFlipped = true;
				xVelocity --;
			}
			if(keyH.rightPressed) {
				isFlipped = false;
				xVelocity ++;
			}

			float magnitude = (xVelocity * xVelocity) + (yVelocity*yVelocity);
			magnitude = (float)Math.sqrt(magnitude);

			if(Math.abs(xVelocity) + Math.abs(yVelocity) != 0) {

				y += (yVelocity/magnitude) * speed;
				x += (xVelocity/magnitude) * speed;
			}
			worldX = (int)x;
			worldY = (int)y;
			
			
			//Resets sprite counter if you were still
			if(!isMoving) {
				spriteNum = 0;
				isMoving = true;
			}
		} else {
			
			isMoving = false;
		}

		
		spriteCounter++;
		//Animation speed
		if(spriteCounter>7) {
			spriteNum++;
			if(spriteNum > 5) {
				spriteNum = 0;
			}
			spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		
		if(isMoving) {
			image = runImage[spriteNum];
		}else {
			image = idleImage[spriteNum];
		}
		
		g2.drawImage(image, isFlipped ? worldX + gp.tileSize: worldX, worldY, isFlipped? -gp.tileSize : gp.tileSize, gp.tileSize, null);
	}
}
