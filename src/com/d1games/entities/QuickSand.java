package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.world.Camera;

public class QuickSand extends Entity{
	
	private int maskx = 0, masky = 0, maskw = 32, maskh = 32;

	public QuickSand(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(isColiddingWithPlayer()) {
			Game.player.speed = 1;
		}else {
			Game.player.speed = 2;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Entity.QUICKSAND, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
