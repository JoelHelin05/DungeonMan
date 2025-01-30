package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] floorTiles;
	public Tile[] wallTiles;
	public int[] randArray;
	public int[][] mapTileNum;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		floorTiles = new Tile[10];
		wallTiles = new Tile[10];
		mapTileNum = new int [gp.worldTileSizeCol][gp.worldTileSizeRow];
		randArray = new int[(gp.worldTileSizeCol + 1)*(gp.worldTileSizeRow + 1)];
		
		getTileImage();
		loadMap("/maps/dMap.txt");
		generateRandomArray();
	}
	
	public void generateRandomArray() {
		for(int i = 0; i < gp.worldTileSizeCol*gp.worldTileSizeRow; i++) {

			randArray[i] = (int)(Math.random() * 4);
		}
	}
	
	public void getTileImage() {

		try {
			// Get floor tiles
			floorTiles[0] = new Tile();
			floorTiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_1.png"));
			floorTiles[1] = new Tile();
			floorTiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_2.png"));
			floorTiles[2] = new Tile();
			floorTiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_3.png"));
			floorTiles[3] = new Tile();
			floorTiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_4.png"));
			floorTiles[4] = new Tile();
			floorTiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_5.png"));
			
			//Get wall tiles
			wallTiles[0] = new Tile();
			wallTiles[0].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_1.png"));
			wallTiles[1] = new Tile();
			wallTiles[1].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_top_1.png"));
			wallTiles[2] = new Tile();
			wallTiles[2].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_top_inner_right_2.png"));
			wallTiles[3] = new Tile();
			wallTiles[3].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_top_inner_left_2.png"));
			wallTiles[4] = new Tile();
			wallTiles[4].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_top_inner_right.png"));
			wallTiles[5] = new Tile();
			wallTiles[5].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_top_inner_left.png"));
			wallTiles[6] = new Tile();
			wallTiles[6].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_bottom_right.png"));
			wallTiles[7] = new Tile();
			wallTiles[7].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_bottom_left.png"));
			wallTiles[8] = new Tile();
			wallTiles[8].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_bottom.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0; 
			
			while(col < gp.worldTileSizeCol && row < gp.worldTileSizeRow) {
				String line = br.readLine();
				
				while(col < gp.worldTileSizeCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.worldTileSizeCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.worldTileSizeCol && worldRow < gp.worldTileSizeRow) {
			
			int x = worldCol * gp.tileSize;
			int y = worldRow * gp.tileSize;

			int tileNum = mapTileNum[worldCol][worldRow];
			
			
			switch(tileNum) {
			case 0 :
				try {

					if(mapTileNum[worldCol][worldRow + 1] == 1) {
						//Draw wall
						g2.drawImage(wallTiles[0].image, x, y, gp.tileSize, gp.tileSize, null);
						if(mapTileNum[worldCol - 1][worldRow] == 1) {

							g2.drawImage(wallTiles[2].image, x, y - gp.tileSize, gp.tileSize, gp.tileSize, null);
						}
						else if(mapTileNum[worldCol + 1][worldRow] == 1) {

							g2.drawImage(wallTiles[3].image, x, y - gp.tileSize, gp.tileSize, gp.tileSize, null);
						}
						else {

							g2.drawImage(wallTiles[1].image, x, y - gp.tileSize, gp.tileSize, gp.tileSize, null);
						}
						
						if(mapTileNum[worldCol + 1][worldRow + 1] == 0) {

							g2.drawImage(wallTiles[4].image, x + gp.tileSize, y - gp.tileSize, gp.tileSize, gp.tileSize, null);
						}
						if(mapTileNum[worldCol - 1][worldRow + 1] == 0) {

							g2.drawImage(wallTiles[5].image, x - gp.tileSize, y - gp.tileSize, gp.tileSize, gp.tileSize, null);
						}
						
					}
					
				} catch(Exception e){
					
					System.out.println("erm");
				}
				break;
			case 1 :
				g2.drawImage(floorTiles[randArray[(worldCol + 1) * (worldRow + 1)]].image, x, y, gp.tileSize, gp.tileSize, null);
				if(mapTileNum[worldCol][worldRow + 1] == 0) {
					
					if(mapTileNum[worldCol +1][worldRow + 1] == 1) {

						g2.drawImage(wallTiles[6].image, x, y, gp.tileSize, gp.tileSize, null);
					}
					else {

						g2.drawImage(wallTiles[8].image, x, y, gp.tileSize, gp.tileSize, null);
					}
				}
				break;
			}
			
			worldCol++;
			
			if(worldCol == gp.worldTileSizeCol) {
				worldRow++;
				worldCol = 0;
			}
		}
	}
	
}
