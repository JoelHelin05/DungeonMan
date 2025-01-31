package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.screenX + entity.collisionArea.x;
		int entityRightWorldX = entity.screenX + entity.collisionArea.x + entity.collisionArea.width;
		int entityTopWorldY = entity.screenY + entity.collisionArea.y;
		int entityBottomWorldY = entity.screenY + entity.collisionArea.y + entity.collisionArea.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		int tileNum1, tileNum2;
		
		if(entity.yVelocity < 0) {
			
			entityTopRow = (int) (entityTopWorldY + entity.yVelocity * entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(tileNum1 != 1 || tileNum2 != 1) {
				entity.collisionY = true;
			}
			//Resets entityTopRow
			entityTopRow = entityTopWorldY / gp.tileSize;
		} else if(entity.yVelocity > 0) {
				
			entityBottomRow = (int)(entityBottomWorldY + entity.yVelocity * entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(tileNum1 != 1 || tileNum2 != 1) {
				entity.collisionY = true;
			}
			//Resets entityBottomRow
			entityBottomRow = entityBottomWorldY / gp.tileSize;
		}
		if(entity.xVelocity < 0) {
			
			entityLeftCol = (int) (entityLeftWorldX + entity.xVelocity * entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(tileNum1 != 1 || tileNum2 != 1) {
				entity.collisionX = true;
			}
			//Resets entityLeftCol
			entityLeftCol = entityLeftWorldX / gp.tileSize;
		} else if(entity.xVelocity > 0) {
			
			entityRightCol = (int) (entityRightWorldX + entity.xVelocity * entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(tileNum1 != 1 || tileNum2 != 1) {
				entity.collisionX = true;
			}
			//Resets entityRightCol
			entityRightCol = entityRightWorldX / gp.tileSize;
		}
	}
		
}
