package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.main.Sound;
import com.d1games.world.Camera;

public class Spikes  extends Entity{
	
	private int maskx = 15, masky = 15, maskw = 1, maskh = 1;

	public Spikes(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(isColiddingWithPlayer()) {
				Game.life--;
				Game.combo = 0;
				Sound.hurt.play();
			}	
	}
	
	public void render(Graphics g) {
			g.drawImage(Entity.ICE_SPIKES, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
