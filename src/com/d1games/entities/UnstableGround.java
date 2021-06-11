package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.main.Sound;
import com.d1games.world.Camera;

public class UnstableGround extends Entity {
	
	private int maskx = 15, masky = 15, maskw = 1, maskh = 1;
	private int time = 0, maxTime= 60, index = 0;
	private BufferedImage[] sprites;
	private boolean isLava = false;
	private boolean colided = false;

	public UnstableGround(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet2.getSprite(0, 0, 32, 32);
		sprites[1] = Game.spritesheet2.getSprite(32, 0, 32, 32);
		sprites[2] = Game.spritesheet2.getSprite(64, 0, 32, 32);
		sprites[3] = Game.spritesheet2.getSprite(96, 0, 32, 32);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(isColiddingWithPlayer()) {
			colided = true;
			}
		
		if(colided == true) {
			time++;
			if(time==20) {
				index = 1;
			}else if(time == 40) {
				index = 2;
			}else if(time == maxTime) {
				index = 3;
				isLava = true;
			}
			
			if(isLava == true && isColiddingWithPlayer() == true) {
				Game.life-=2;
				Game.combo = 0;
				Sound.hurt.play();
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
}

}
