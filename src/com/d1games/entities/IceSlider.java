package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.world.Camera;
import com.d1games.world.World;

public class IceSlider  extends Entity{
	
	private int maskx = 0, masky = 0, maskw = 32, maskh = 32;

	public IceSlider(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(isColiddingWithPlayer()) {
			if(World.isFree(Game.player.getX(), Game.player.getY())) {
				if(Game.player.dir == 0) {
					Game.player.x++;
				}else if(Game.player.dir == 1) {
					Game.player.x--;
				}else if(Game.player.dir == 2) {
					Game.player.y--;
				}else if(Game.player.dir == 3) {
					Game.player.y++;
				}
			}
		}	
	}
	
	public void render(Graphics g) {
		g.drawImage(Entity.ICE_SLIDER, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
