package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
	public int[][] completeTileNum;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		floorTiles = new Tile[10];
		wallTiles = new Tile[20];
		mapTileNum = new int [gp.worldTileSizeCol][gp.worldTileSizeRow];
		completeTileNum = new int [gp.worldTileSizeCol][gp.worldTileSizeRow];
		randArray = new int[(gp.worldTileSizeCol + 1)*(gp.worldTileSizeRow + 1)];
		
		getTileImage();
		loadMap("/maps/dMap.txt");
		generateRandomArray();
		completeTiles();
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
			wallTiles[9] = new Tile();
			wallTiles[9].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_top_left.png"));
			wallTiles[10] = new Tile();
			wallTiles[10].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_top_right.png"));
			wallTiles[11] = new Tile();
			wallTiles[11].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_bottom_inner_right.png"));
			wallTiles[12] = new Tile();
			wallTiles[12].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/wall_bottom_inner_left.png"));
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

			col = 0;
			row = 0; 
			while(col < gp.worldTileSizeCol && row < gp.worldTileSizeRow) {
				
				while(col < gp.worldTileSizeCol) {
					
					try {

						if(mapTileNum[col][row + 1] == 1 && mapTileNum[col][row] == 0) {

							mapTileNum[col][row] = 2;
						}
					} catch(Exception e) {

					}
					col++;
				}
				
				if(col == gp.worldTileSizeCol) {
					col = 0;
					row++;
				}
			}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void completeTiles() {

		int worldCol = 0;
		int worldRow = 0;

		int dTileNum = 0;
		int rTileNum = 0;
		int drTileNum = 0;
		int lTileNum = 0;
		int dlTileNum = 0;
		
		while(worldCol < gp.worldTileSizeCol && worldRow < gp.worldTileSizeRow) {
			
			int x = worldCol * gp.tileSize;
			int y = worldRow * gp.tileSize;
			
			int tileNum = mapTileNum[worldCol][worldRow];
			try {dTileNum = mapTileNum[worldCol][worldRow+1];}catch(Exception e) {dTileNum = 0;}
			try {rTileNum = mapTileNum[worldCol+1][worldRow];}catch(Exception e) {rTileNum = 0;}
			try {drTileNum = mapTileNum[worldCol+1][worldRow+1];}catch(Exception e) {drTileNum = 0;}
			try {lTileNum = mapTileNum[worldCol-1][worldRow];}catch(Exception e) {lTileNum = 0;}
			try {dlTileNum = mapTileNum[worldCol-1][worldRow+1];}catch(Exception e) {dlTileNum = 0;}

			if(tileNum == 0) {

				if(dTileNum == 2 && rTileNum > 0) {
					
					completeTileNum[worldCol][worldRow] = 3;
				} else if(dTileNum == 2 && lTileNum > 0) {

					completeTileNum[worldCol][worldRow] = 2;
				} else if(dTileNum == 2) {

					completeTileNum[worldCol][worldRow] = 1;
				}  else if(rTileNum == 1 && dTileNum == 0 && drTileNum == 0) {

					completeTileNum[worldCol][worldRow] = 12;
				} else if(lTileNum == 1 && dTileNum == 0 && dlTileNum == 0) {

					completeTileNum[worldCol][worldRow] = 11;
				} else if (lTileNum == 0 && dTileNum == 0 && dlTileNum > 0) {

					completeTileNum[worldCol][worldRow] = 4;
				} else if (rTileNum == 0 && dTileNum == 0 && drTileNum > 0) {

					completeTileNum[worldCol][worldRow] = 5;
				}else if(rTileNum > 0) {

					completeTileNum[worldCol][worldRow] = 9;
				} else if(lTileNum > 0) {

					completeTileNum[worldCol][worldRow] = 10;
				}
			} else if(tileNum == 1) {
				
				if(dTileNum == 0 && dlTileNum > 0) {

					completeTileNum[worldCol][worldRow] = 7;
				}else if(dTileNum == 0 && drTileNum > 0) {

					completeTileNum[worldCol][worldRow] = 6;
				}else if(dTileNum == 0) {

					completeTileNum[worldCol][worldRow] = 8;
				}
			}
			worldCol++;
			if(worldCol == gp.worldTileSizeCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		BufferedImage currentImage = null;
		
		while(worldCol < gp.worldTileSizeCol && worldRow < gp.worldTileSizeRow) {
			
			int x = worldCol * gp.tileSize;
			int y = worldRow * gp.tileSize;

			int tileNum = mapTileNum[worldCol][worldRow];
			
			currentImage = null;
			
			switch(tileNum) {
			case 2 :
				//Draw wall
				currentImage = wallTiles[0].image;
				break;
			case 1 :
				currentImage = floorTiles[randArray[(worldCol + 1) * (worldRow + 1)]].image;
				break;
			}
			g2.drawImage(currentImage, x, y, gp.tileSize, gp.tileSize, null);
			
			worldCol++;
			
			if(worldCol == gp.worldTileSizeCol) {
				worldRow++;
				worldCol = 0;
			}
		}
	}
	
	public void drawWallBorder(Graphics2D g2){

		int worldCol = 0;
		int worldRow = 0;

		BufferedImage currentImage = null;
		
		while(worldCol < gp.worldTileSizeCol && worldRow < gp.worldTileSizeRow) {
			
			int x = worldCol * gp.tileSize;
			int y = worldRow * gp.tileSize;
			
			if(completeTileNum[worldCol][worldRow] != 0) {

				currentImage = wallTiles[completeTileNum[worldCol][worldRow]].image;
				g2.drawImage(currentImage, x, y, gp.tileSize, gp.tileSize, null);
			}

			
			worldCol++;
			
			if(worldCol == gp.worldTileSizeCol) {
				worldRow++;
				worldCol = 0;
			}
		}
	}
	
}
