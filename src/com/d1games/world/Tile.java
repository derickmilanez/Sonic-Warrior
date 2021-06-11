package com.d1games.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;

public class Tile {
	public static BufferedImage SPAWNER = Game.spritesheet.getSprite(96, 96, 32, 32);
	public static BufferedImage GRASS = Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage CASTLE_WALL = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage TREE_TOP = Game.spritesheet.getSprite(0, 96, 32, 32);
	public static BufferedImage TREE_BOTTOM = Game.spritesheet.getSprite(0, 128, 32, 32);
	public static BufferedImage BUSH = Game.spritesheet.getSprite(32, 96, 32, 32);
	public static BufferedImage GROUND = Game.spritesheet.getSprite(32, 128, 32, 32);
	public static BufferedImage WATER = Game.spritesheet.getSprite(64, 96, 32, 32);
	public static BufferedImage WOOD = Game.spritesheet.getSprite(64, 128, 32, 32);
	public static BufferedImage STONE_GROUND = Game.spritesheet.getSprite(96, 128, 32, 32);
	public static BufferedImage VASE = Game.spritesheet.getSprite(128, 128, 32, 32);
	public static BufferedImage PILLAR_BOTTOM = Game.spritesheet.getSprite(160, 128, 32, 32);
	public static BufferedImage WOOD_GATE = Game.spritesheet.getSprite(128, 96, 32, 32);
	public static BufferedImage PILLAR_TOP = Game.spritesheet.getSprite(160, 96, 32, 32);
	public static BufferedImage CARPET_BOTTOM = Game.spritesheet.getSprite(192, 128, 32, 32);
	public static BufferedImage CARPET_TOP = Game.spritesheet.getSprite(192, 96, 32, 32);
	public static BufferedImage SNOW = Game.spritesheet.getSprite(192+32, 96, 32, 32);
	public static BufferedImage SNOW_BUSH = Game.spritesheet.getSprite(192+32, 128, 32, 32);
	public static BufferedImage SNOWTREE_BOTTOM = Game.spritesheet.getSprite(192+64, 128, 32, 32);
	public static BufferedImage SNOWTREE_TOP = Game.spritesheet.getSprite(192+64, 128-32, 32, 32);
	public static BufferedImage ICE_MOUNTAIN = Game.spritesheet.getSprite(192+96, 128-32, 32, 32);
	public static BufferedImage ICE_MOUNTAIN_TOP = Game.spritesheet.getSprite(192+96, 128, 32, 32);
	public static BufferedImage LAB_FLOOR = Game.spritesheet.getSprite(160+64, 192, 32, 32);
	public static BufferedImage LAB_WALL = Game.spritesheet.getSprite(160+64+32, 192, 32, 32);
	public static BufferedImage LAB_GATE = Game.spritesheet.getSprite(160+32, 192+32, 32, 32);
	public static BufferedImage LAB_BOX = Game.spritesheet.getSprite(0, 192+32, 32, 32);
	public static BufferedImage LAB_TUBETOP = Game.spritesheet.getSprite(160+64+32+32, 192, 32, 32);
	public static BufferedImage LAB_TUBEBOTTOM = Game.spritesheet.getSprite(160+64+32+32, 192+32, 32, 32);
	public static BufferedImage LAB_COMPUTER_L = Game.spritesheet.getSprite(160+32+32, 192+32, 32, 32);
	public static BufferedImage LAB_COMPUTER_R = Game.spritesheet.getSprite(160+32+32+32, 192+32, 32, 32);
	public static BufferedImage VOLCANO_GROUND = Game.spritesheet.getSprite(0, 192+32+32, 32, 32);
	public static BufferedImage VOLCANO_MOUNTAIN_BOTTOM = Game.spritesheet.getSprite(32, 192+32+32, 32, 32);
	public static BufferedImage VOLCANO_MOUNTAIN_TOP = Game.spritesheet.getSprite(64, 192+32+32, 32, 32);
	public static BufferedImage DESERT_GROUND = Game.spritesheet.getSprite(96+32, 192+32+32, 32, 32);
	public static BufferedImage MILITARY_WALL = Game.spritesheet.getSprite(96+32+32, 192+32+32, 32, 32);
	public static BufferedImage MILITARY_GROUND = Game.spritesheet.getSprite(96+32+32+32, 192+32+32, 32, 32);
	public static BufferedImage WOOD_CONTAINER = Game.spritesheet.getSprite(96+32+32+32, 192+32+32, 32, 32);
	public static BufferedImage BARREL = Game.spritesheet.getSprite(96+32+32+32, 192+32+32+32, 32, 32);
	public static BufferedImage DESERT_STONE = Game.spritesheet.getSprite(96+32+32+32, 192+32+32+32, 32, 32);
	public static BufferedImage CACTUS_BOT = Game.spritesheet.getSprite(96+32+32+32+32+32, 192+32+32+32, 32, 32);
	public static BufferedImage CACTUS_TOP = Game.spritesheet.getSprite(96+32+32+32+32+32, 192+32+32, 32, 32);
	public static BufferedImage DESERT_MOUNTAIN_BOT = Game.spritesheet.getSprite(96+32+32+32+32+32+32, 192+32+32+32, 32, 32);
	public static BufferedImage DESERT_MOUNTAIN_TOP = Game.spritesheet.getSprite(96+32+32+32+32+32+32, 192+32+32, 32, 32);
	public static BufferedImage DESERT_BUSH = Game.spritesheet2.getSprite(96+32, 0, 32, 32);
	
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
