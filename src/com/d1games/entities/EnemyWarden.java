package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.main.Sound;
import com.d1games.world.Camera;
import com.d1games.world.World;

public class EnemyWarden extends Entity {
	
	private double speed = 2;
	private int maskx = 5, masky = 0, maskw = 23, maskh = 32;
	private BufferedImage sprites;
	private int life = 6;
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public EnemyWarden(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = Game.spritesheet.getSprite(160+32, 64, 32, 32);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(this.calcDistance(this.getX(),this.getY(), Game.player.getX(), Game.player.getY()) < 200) {
		if(isColiddingWithPlayer() == false) {
			if(Game.rand.nextInt(100) < 10) {
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
			if(Game.rand.nextInt(100) < 15) {
				Game.life--;
				Game.combo = 0;
				Sound.hurt.play();
			}
		}
		}
		
		collidingBullet();
		if(life <= 0) {
			Game.score += 30 + Game.combo;
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
		g.drawImage(sprites, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//debug
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
		}else {
			g.drawImage(Entity.WARDEN_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
