package com.d1games.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.main.Sound;
import com.d1games.world.Camera;
import com.d1games.world.World;

public class Boss1 extends Entity {
	
	private double speed = 0.9;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 3;
	private int time = 0, maxTime = 120;
	private int maskx = 0, masky = 0, maskw = 32, maskh = 32;
	private BufferedImage[] sprites;
	public static int life = 50;
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public Boss1(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(64+32, 160, 32, 32);
		sprites[1] = Game.spritesheet.getSprite(96+32, 160, 32, 32);
		sprites[2] = Game.spritesheet.getSprite(128+32, 160, 32, 32);
		sprites[3] = Game.spritesheet.getSprite(160+32, 160, 32, 32);
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 32,32);
		return enemyCurrent.intersects(player);
	}
	
	public void tick() {
		if(isColiddingWithPlayer() == false) {
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
		}else{
				Game.player.isDamaged = true;
				Game.life--;
				Sound.hurt.play();
				Game.combo = 0;
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		
		time++;
		if(time == maxTime) {
			time = 0;
			EnemyBullet bullet =  new EnemyBullet(this.getX()+25,this.getY()+20,1,1,null, Game.player.getX() - this.getX(), Game.player.getY() - this.getY());
			Game.enemyBullets.add(bullet);
			Sound.boss1shoot.play();
		}
		
		collidingBullet();
		if(life <= 0) {
			Game.score += 1000 + Game.combo;
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
		Game.bosses.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity atual = Game.bullets.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)){
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					Game.combo++;
					World.generateParticles(10, this.getX()+16, this.getY()+16);
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
			g.drawImage(Entity.BOSS1_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
