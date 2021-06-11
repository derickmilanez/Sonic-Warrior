package com.d1games.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.d1games.entities.*;
import com.d1games.graphics.Spritesheet;
import com.d1games.main.Game;
import com.d1games.main.Sound;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	
	public World(String path) {
		try {
			//Contando os pixels do mapa
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.SPAWNER);
					if(pixelAtual == 0xFF267F00) {
						//Grass Ground
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.GRASS);
					}else if(pixelAtual == 0xFFCCB153) {
						//Ground
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.GROUND);
					}else if(pixelAtual == 0xFFC0C0C0) {
						//Stone Ground
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.STONE_GROUND);
					}else if(pixelAtual == 0xFF260E00) {
						//Wood
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.WOOD);
					}else if(pixelAtual == 0xFFC10000) {
						//Carpet Top
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.CARPET_TOP);
					}else if(pixelAtual == 0xFF7F0000) {
						//Carpet Bottom
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.CARPET_BOTTOM);
					}else if(pixelAtual == 0xFFFFFFFF) {
						//Snow
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.SNOW);
					}else if(pixelAtual == 0xFF493528) {
						//Wood Gate
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.WOOD_GATE);
					}else if(pixelAtual == 0xFF606060) {
						//Stone Wall
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.CASTLE_WALL);
					}else if(pixelAtual == 0xFF414141) {
						//Lab Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.LAB_FLOOR);
					}else if(pixelAtual == 0xFFE5E5E5) {
						//Lab Wall
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.LAB_WALL);
					}else if(pixelAtual == 0xFFC3C3C3) {
						//Lab Gate
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.LAB_GATE);
					}else if(pixelAtual == 0xFFA5A5A5) {
						//Lab Box
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.LAB_BOX);
					}else if(pixelAtual == 0xFFA4A4A4) {
						//Lab Tube Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.LAB_TUBETOP);
					}else if(pixelAtual == 0xFF008048) {
						//Lab Tube Bottom
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.LAB_TUBEBOTTOM);
					}else if(pixelAtual == 0xFF828282) {
						//Lab Computer Left
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.LAB_COMPUTER_L);
					}else if(pixelAtual == 0xFF646464) {
						//Lab Computer Right
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.LAB_COMPUTER_R);
					}else if(pixelAtual == 0xFF7F1010) {
						//Volcano Ground
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.VOLCANO_GROUND);
					}else if(pixelAtual == 0xFF7F3311) {
						//Volcano Mountain Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.VOLCANO_MOUNTAIN_TOP);
					}else if(pixelAtual == 0xFF7F593F) {
						//Volcano Mountain Bottom
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.VOLCANO_MOUNTAIN_BOTTOM);
					}else if(pixelAtual == 0xFFFFE630) {
						//Desert Ground
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.DESERT_GROUND);
					}else if(pixelAtual == 0xFFFF9028) {
						//Desert Stone
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.DESERT_STONE);
					}else if(pixelAtual == 0xFF00FF21) {
						//Cactus Bottom
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.CACTUS_BOT);
					}else if(pixelAtual == 0xFF267D00) {
						//Cactus Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.CACTUS_TOP);
					}else if(pixelAtual == 0xFFFF6400) {
						//Desert Mountain Bottom
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.DESERT_MOUNTAIN_BOT);
					}else if(pixelAtual == 0xFFFFB200) {
						//Desert Mountain Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.DESERT_MOUNTAIN_TOP);
					}else if(pixelAtual == 0xFF6B2A00) {
						//Desert Bush
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.DESERT_BUSH);
					}else if(pixelAtual == 0xFF616161) {
						//Fake Stone Wall
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.CASTLE_WALL);
					}else if(pixelAtual == 0xFF007F7F) {
						//Ice Mountain
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.ICE_MOUNTAIN);
					}else if(pixelAtual == 0xFF004A7F) {
						//Ice Mountain Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.ICE_MOUNTAIN_TOP);
					}else if(pixelAtual == 0xFF0026FF) {
						//Water
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.WATER);
					}else if(pixelAtual == 0xFF004907) {
						//Tree Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.TREE_TOP);
					}else if(pixelAtual == 0xFF7F3300) {
						//Tree Bottom
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.TREE_BOTTOM);
					}else if(pixelAtual == 0xFF303030) {
						//Pillar Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.PILLAR_TOP);
					}else if(pixelAtual == 0xFF808080) {
						//Pillar Bottom
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.PILLAR_BOTTOM);
					}else if(pixelAtual == 0xFFCECECE) {
						//Snow Tree Top
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.SNOWTREE_TOP);
					}else if(pixelAtual == 0xFF4C2E00) {
						//Snow Tree Bottom
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.SNOWTREE_BOTTOM);
					}else if(pixelAtual == 0xFF002603) {
						//Bush
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.BUSH);
					}else if(pixelAtual == 0xFFB5B500) {
						//Vase
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.VASE);
					}else if(pixelAtual == 0xFF7F4400) {
						//Snow Bush
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.SNOW_BUSH);
					}else if(pixelAtual == 0xFF7F5500) {
						//Fake Snow Bush
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32, Tile.SNOW_BUSH);
					}else if(pixelAtual == 0xFF6B5C2B) {
						//Invisible Wall Ground
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.GROUND);
					}else if(pixelAtual == 0xFFF0F0F0) {
						//Invisible Wall Snow
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.SNOW);
					}else if(pixelAtual == 0xFF7F1111) {
						//Invisible Wall Volcanic
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*32,yy*32, Tile.VOLCANO_GROUND);
					}else if(pixelAtual == 0xFF8E7B3A) {
						//Next Level Ground
						NextLevel nextLevel = new NextLevel(xx*32,yy*32,32,32,Tile.GRASS);
						Game.entities.add(nextLevel);
					}else if(pixelAtual == 0xFFCCCCCC) {
						//Next Level Stone Ground
						NextLevel nextLevel = new NextLevel(xx*32,yy*32,32,32,Tile.STONE_GROUND);
						Game.entities.add(nextLevel);
					}else if(pixelAtual == 0xFFFAFAFA) {
						//Next Level Snow Ground
						NextLevel nextLevel = new NextLevel(xx*32,yy*32,32,32,Tile.SNOW);
						Game.entities.add(nextLevel);
					}else if(pixelAtual == 0xFF414141) {
						//Next Level Lab Floor
						NextLevel nextLevel = new NextLevel(xx*32,yy*32,32,32,Tile.LAB_FLOOR);
						Game.entities.add(nextLevel);
					}else if(pixelAtual == 0xFF7F1110) {
						//Next Level Volcanic Ground
						NextLevel nextLevel = new NextLevel(xx*32,yy*32,32,32,Tile.VOLCANO_GROUND);
						Game.entities.add(nextLevel);
					}else if(pixelAtual == 0xFF00FFFF) {
						//Ice Slider
						IceSlider iceSlider = new IceSlider(xx*32,yy*32,32,32,Entity.ICE_SLIDER);
						Game.floorEntities.add(iceSlider);
					}else if(pixelAtual == 0xFF3F7F7F) {
						//Ice Spikes
						Spikes iceSpikes = new Spikes(xx*32,yy*32,32,32,Entity.ICE_SPIKES);
						Game.floorEntities.add(iceSpikes);
					}else if(pixelAtual == 0xFFFF6A11) {
						//Lava
						Lava lava = new Lava(xx*32,yy*32,32,32,Entity.LAVA);
						Game.floorEntities.add(lava);
					}else if(pixelAtual == 0xFF722E00) {
						//Unstable Ground
						UnstableGround us = new UnstableGround(xx*32,yy*32,32,32,Entity.UNSTABLE_GROUND);
						Game.floorEntities.add(us);
					}else if(pixelAtual == 0xFF7F6A00) {
						//Quicksand
						QuickSand qs = new QuickSand(xx*32,yy*32,32,32,Entity.QUICKSAND);
						Game.floorEntities.add(qs);
					}else if(pixelAtual == 0xFF0094FF) {
						//Player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
					}else if(pixelAtual == 0xFFB6FF00) {
						//Slime
						EnemySlime enemySlime = new EnemySlime(xx*32,yy*32,32,32,Entity.SLIME_EN);
						Game.groundEnemies.add(enemySlime);
					}else if(pixelAtual == 0xFF461F00) {
						//Strix
						EnemyStrix enemyStrix = new EnemyStrix(xx*32,yy*32,32,32,Entity.STRIX_EN);
						Game.flyingEnemies.add(enemyStrix);
					}else if(pixelAtual == 0xFFA0A0A0) {
						//Warden
						EnemyWarden enemyWarden = new EnemyWarden(xx*32,yy*32,32,32,Entity.WARDEN_EN);
						Game.groundEnemies.add(enemyWarden);
					}else if(pixelAtual == 0xFFFF00DC) {
						//Daemon
						EnemyDaemon enemyDaemon = new EnemyDaemon(xx*32,yy*32,32,32,Entity.DAEMON_EN);
						Game.flyingEnemies.add(enemyDaemon);
					}else if(pixelAtual == 0xFFF1F1F1) {
						//Snow Sentry
						EnemySnowSentry snowSentry = new EnemySnowSentry(xx*32,yy*32,32,32,Entity.SNOWSENTRY_EN);
						Game.groundEnemies.add(snowSentry);
					}else if(pixelAtual == 0xFFAF0000) {
						//Chaser
						EnemyChaser enemyChaser = new EnemyChaser(xx*32,yy*32,32,32,Entity.CHASER_EN);
						Game.groundEnemies.add(enemyChaser);
					}else if(pixelAtual == 0xFFFFAA12) {
						//Blaze
						EnemyBlaze enemyBlaze = new EnemyBlaze(xx*32,yy*32,32,32,Entity.BLAZE_EN);
						Game.flyingEnemies.add(enemyBlaze);
					}else if(pixelAtual == 0xFF707070) {
						//Boss1
						Boss1 boss1 = new Boss1(xx*32,yy*32,32,32,Entity.BOSS1_EN);
						Game.bosses.add(boss1);
					}else if(pixelAtual == 0xFFE6E6E6) {
						//Boss2
						Boss2 boss2 = new Boss2(xx*32,yy*32,32,32,Entity.BOSS2_EN);
						Game.bosses.add(boss2);
					}else if(pixelAtual == 0xFFFF0000) {
						//LifeBattery
						LifeBattery lifeBattery = new LifeBattery(xx*32,yy*32,32,32,Entity.LIFEBATTERY_EN);
						lifeBattery.setMask(12, 6, 8, 15);
						Game.entities.add(lifeBattery);
					}else if(pixelAtual == 0xFFFFD800) {
						//AmmoBattery
						AmmoBattery ammoBattery = new AmmoBattery(xx*32,yy*32,32,32,Entity.AMMOBATTERY_EN);
						ammoBattery.setMask(12, 6, 8, 15);
						Game.entities.add(ammoBattery);
					}else if(pixelAtual == 0xFFFF7F7F) {
						//LifeUpgrade
						LifeUpgrade lifeUpgrade = new LifeUpgrade(xx*32,yy*32,32,32,Entity.LIFEUPGRADE_EN);
						lifeUpgrade.setMask(9, 6, 14, 15);
						Game.entities.add(lifeUpgrade);
					}else if(pixelAtual == 0xFFFF6A00) {
						//AmmoUpgrade
						AmmoUpgrade ammoUpgrade = new AmmoUpgrade(xx*32,yy*32,32,32,Entity.AMMOUPGRADE_EN);
						ammoUpgrade.setMask(9, 6, 14, 15);
						Game.entities.add(ammoUpgrade);
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext/TILE_SIZE;
		int y1 = ynext/TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1)/TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1)/TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1)/ TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1)/TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public static void generateParticles(int amount, int x, int y) {
		for(int i =0; i < amount; i++) {
			Game.entities.add(new Particle(x,y,2,2,null));
		}
	}
	
	public static void restartWorld(String level) {
		Game.entities.clear();
		Game.floorEntities.clear();
		Game.groundEnemies.clear();
		Game.flyingEnemies.clear();
		Game.bosses.clear();
		Game.enemyBullets.clear();
		Game.bullets.clear();
		Game.entities = new ArrayList<Entity>();
		Game.groundEnemies = new ArrayList<Entity>();
		Game.flyingEnemies = new ArrayList<Entity>();
		Game.floorEntities = new ArrayList<Entity>();
		Game.bosses = new ArrayList<Entity>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.spritesheet2 = new Spritesheet("/spritesheet2.png");
		Game.player = new Player(0,0,32,32,Game.spritesheet.getSprite(64,0, 32,32));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		Game.combo = 0;
		if(Game.CUR_LEVEL == 1)
		{
			Sound.music1.loop();
		}
		
		if(Game.CUR_LEVEL == 2)
		{
			Sound.music1.stop();
			Sound.music2.loop();
		}
		
		if(Game.CUR_LEVEL == 3)
		{
			Boss1.life = 50;
			Sound.music2.stop();
			Sound.music3.loop();
		}
		if(Game.CUR_LEVEL == 4)
		{
			Sound.music3.stop();
			Sound.music1.loop();
		}
		if(Game.CUR_LEVEL == 5)
		{
			Sound.music1.stop();
			Sound.music2.loop();
		}
		if(Game.CUR_LEVEL == 6)
		{
			Boss2.life = 50;
			Sound.music2.stop();
			Sound.music3.loop();
		}
		if(Game.CUR_LEVEL == 7)
		{
			Sound.music3.stop();
			Sound.music1.loop();
		}
		if(Game.CUR_LEVEL == 8)
		{
			Sound.music1.stop();
			Sound.music2.loop();
		}
		return;
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x/32;
		int ystart =  Camera.y/32;
		
		int xfinal = xstart + (Game.WIDTH/32);
		int yfinal = ystart + (Game.HEIGHT/32);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH ||  yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}