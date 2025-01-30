package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int[] randArray;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		randArray = new int[gp.worldTileSizeCol*gp.worldTileSizeRow];
	
		getTileImage();
		generateRandomArray();
	}
	
	public void generateRandomArray() {
		for(int i = 0; i < gp.worldTileSizeCol*gp.worldTileSizeRow; i++) {

			randArray[i] = (int)(Math.random() * 4);
		}
	}
	
	public void getTileImage() {

		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_1.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_2.png"));
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_3.png"));
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_4.png"));
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.worldTileSizeCol && worldRow < gp.worldTileSizeRow) {
			
			int x = worldCol * gp.tileSize;
			int y = worldRow * gp.tileSize;

			g2.drawImage(tile[randArray[worldCol + 1 * worldRow + 1]].image, x, y, gp.tileSize, gp.tileSize, null);
			
			if(worldCol == 0) {

				System.out.println(randArray[worldCol + 1 * worldRow + 1]);
			}
			worldCol++;
			
			if(worldCol == gp.worldTileSizeCol) {
				worldRow++;
				worldCol = 0;
			}
		}
	}
	
}
