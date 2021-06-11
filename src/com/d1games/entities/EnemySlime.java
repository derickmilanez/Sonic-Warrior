package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.main.Sound;
import com.d1games.world.Camera;
import com.d1games.world.World;

public class EnemySlime extends Entity {
	
	private double speed = 3;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	private int maskx = 9, masky = 12, maskw = 15, maskh = 8;
	private BufferedImage[] sprites;
	private int life = 2;
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public EnemySlime(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(0, 64, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(32, 64, 32, 32);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(this.calcDistance(this.getX(),this.getY(), Game.player.getX(), Game.player.getY()) < 150) {
		if(isColiddingWithPlayer() == false) {
			if(Game.rand.nextInt(100) < 20) {
				if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())) {
					x+=speed;
				}
				else if((int) x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())) {
					x-=speed;
				}
				if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))) {
					y+=speed;
				}
				else if((int) y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))) {
					y-=speed;
				}
			}
		}else {
			Game.player.isDamaged = true;
			if(Game.rand.nextInt(100) < 5) {
				Game.life--;
				Game.combo = 0;
				Sound.hurt.play();
			}
		}
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		
		collidingBullet();
		if(life <= 0) {
			Game.score += 10 + Game.combo;
			destroySelf();
			return;
		}
		
		if(isDamaged) {
			damageCurrent++;
			if(damageCurrent == damageFrames) {
				damageCurrent = 0;
				isDamaged = false;
			}
		}
	}
	
	public void destroySelf() {
		Game.groundEnemies.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity atual = Game.bullets.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)){
					isDamaged = true;
					life--;
					Game.combo++;
					World.generateParticles(10, this.getX()+16, this.getY()+16);
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		//debug
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
		}else {
			g.drawImage(Entity.SLIME_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
