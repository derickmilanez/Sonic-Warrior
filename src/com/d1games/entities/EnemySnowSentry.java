package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.main.Sound;
import com.d1games.world.Camera;
import com.d1games.world.World;

public class EnemySnowSentry extends Entity {
	
	private int time = 0, maxTime = 120;
	private int maskx = 10, masky = 5, maskw = 12, maskh = 27;
	private BufferedImage sprites;
	private int life = 5;
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public EnemySnowSentry(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = Game.spritesheet.getSprite(160+96, 160, 32, 32);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(this.calcDistance(this.getX(),this.getY(), Game.player.getX(), Game.player.getY()) < 250) {
		if(isColiddingWithPlayer() == false) {
			time++;
			if(time == maxTime) {
				time = 0;
				EnemyBullet bullet =  new EnemyBullet(this.getX()+16,this.getY()+16,1,1,null, Game.player.getX() - this.getX(), Game.player.getY() - this.getY());
				Game.enemyBullets.add(bullet);
				//Sound.boss1shoot.play();
				}
			}
		else {
				Game.player.isDamaged = true;
				Game.life--;
				Game.combo = 0;
				Sound.hurt.play();
			}
		}

		collidingBullet();
		if(life <= 0) {
			Game.score += 50 + Game.combo;
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
			g.drawImage(Entity.SNOWSENTRY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
