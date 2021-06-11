package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.d1games.main.Game;
import com.d1games.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEBATTERY_EN = Game.spritesheet.getSprite(256, 32, 32, 32);
	public static BufferedImage AMMOBATTERY_EN = Game.spritesheet.getSprite(288, 32, 32, 32);
	public static BufferedImage LIFEUPGRADE_EN = Game.spritesheet.getSprite(256, 64, 32, 32);
	public static BufferedImage AMMOUPGRADE_EN = Game.spritesheet.getSprite(288, 64, 32, 32);
	public static BufferedImage SLIME_EN = Game.spritesheet.getSprite(0, 64, 32, 32);
	public static BufferedImage SLIME_FEEDBACK = Game.spritesheet.getSprite(64, 64, 32, 32);
	public static BufferedImage STRIX_EN = Game.spritesheet.getSprite(96, 64, 32, 32);
	public static BufferedImage STRIX_FEEDBACK = Game.spritesheet.getSprite(160, 64, 32, 32);
	public static BufferedImage WARDEN_EN = Game.spritesheet.getSprite(160+32, 64, 32, 32);
	public static BufferedImage WARDEN_FEEDBACK = Game.spritesheet.getSprite(160+64, 64, 32, 32);
	public static BufferedImage DAEMON_EN = Game.spritesheet.getSprite(0, 160, 32, 32);
	public static BufferedImage DAEMON_FEEDBACK = Game.spritesheet.getSprite(64, 160, 32, 32);
	public static BufferedImage BOSS1_EN = Game.spritesheet.getSprite(64+32, 160, 32, 32);
	public static BufferedImage BOSS1_FEEDBACK = Game.spritesheet.getSprite(160+64, 160, 32, 32);
	public static BufferedImage BOSS2_EN = Game.spritesheet.getSprite(32, 224, 32, 32);
	public static BufferedImage BOSS2_FEEDBACK =  Game.spritesheet.getSprite(160, 224, 32, 32);
	public static BufferedImage SNOWSENTRY_EN = Game.spritesheet.getSprite(160+96, 160, 32, 32);
	public static BufferedImage SNOWSENTRY_FEEDBACK = Game.spritesheet.getSprite(160+128, 160, 32, 32);
	public static BufferedImage ICE_SLIDER = Game.spritesheet.getSprite(0, 192, 32, 32);
	public static BufferedImage CHASER_EN = Game.spritesheet.getSprite(32, 192, 32, 32);
	public static BufferedImage CHASER_FEEDBACK = Game.spritesheet.getSprite(160, 192, 32, 32);
	public static BufferedImage ICE_SPIKES = Game.spritesheet.getSprite(160+32, 192, 32, 32);
	public static BufferedImage LAVA = Game.spritesheet.getSprite(96, 192+32+32, 32, 32);
	public static BufferedImage BLAZE_EN = Game.spritesheet.getSprite(0, 192+32+32+32, 32, 32);
	public static BufferedImage BLAZE_FEEDBACK = Game.spritesheet.getSprite(64, 192+32+32+32, 32, 32);
	public static BufferedImage UNSTABLE_GROUND = Game.spritesheet2.getSprite(0, 0, 32, 32);
	public static BufferedImage QUICKSAND = Game.spritesheet2.getSprite(0, 32, 32, 32);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	private int maskx, masky, maskw, maskh;

	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;
	}
	
	public void setMask(int maskx, int masky, int maskw, int maskh) {
		this.maskx = maskx;
		this.masky = masky;
		this.maskw = maskw;
		this.maskh = maskh;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//debug
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
	
	public void tick() {
		
	}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskw, e1.maskh);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskw, e2.maskh);
		return e1Mask.intersects(e2Mask);
	}
	
	public double calcDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
}
