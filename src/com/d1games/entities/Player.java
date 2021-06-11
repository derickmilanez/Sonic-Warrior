package com.d1games.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.d1games.main.Game;
import com.d1games.main.Sound;
import com.d1games.world.Camera;
import com.d1games.world.World;

public class Player extends Entity {

	public boolean right, left, up, down, stop;
	public double speed = 2;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = 3;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	public boolean isDamaged = false;
	public boolean shoot =  false; 
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		rightPlayer[0] = Game.spritesheet.getSprite(128, 0, 32, 32);
		rightPlayer[1] = Game.spritesheet.getSprite(160, 0, 32, 32);
		rightPlayer[2] = Game.spritesheet.getSprite(192, 0, 32, 32);
		rightPlayer[3] = Game.spritesheet.getSprite(224, 0, 32, 32);
		
		leftPlayer = new BufferedImage[4];
		leftPlayer[0] = Game.spritesheet.getSprite(128, 32, 32, 32);
		leftPlayer[1] = Game.spritesheet.getSprite(96, 32, 32, 32);
		leftPlayer[2] = Game.spritesheet.getSprite(64, 32, 32, 32);
		leftPlayer[3] = Game.spritesheet.getSprite(32, 32, 32, 32);
		
		upPlayer = new BufferedImage[4];
		upPlayer[0] = Game.spritesheet.getSprite(96, 0, 32, 32);
		upPlayer[1] = Game.spritesheet.getSprite(160, 32, 32, 32);
		upPlayer[2] = Game.spritesheet.getSprite(192, 32, 32, 32);
		upPlayer[3] = Game.spritesheet.getSprite(224, 32, 32, 32);
		
		downPlayer = new BufferedImage[4];
		downPlayer[0] = Game.spritesheet.getSprite(64, 0, 32, 32);
		downPlayer[1] = Game.spritesheet.getSprite(256, 0, 32, 32);
		downPlayer[2] = Game.spritesheet.getSprite(288, 0, 32, 32);
		downPlayer[3] = Game.spritesheet.getSprite(0, 32, 32, 32);
	}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		else if(up && World.isFree(this.getX(),(int)(y - speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}else if(down && World.isFree(this.getX(),(int)(y + speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		checkCollisionLife();
		checkCollisionAmmo();
		checkCollisionLifeUpgrade();
		checkCollisionAmmoUpgrade();
		checkCollisionNextLevel();
		collidingEnemyBullet();
		
		if(shoot && Game.ammo >  0) {
			shoot = false;
			Game.ammo--;
			Sound.shoot.play();
			int dx = 0, dy = 0;
			int px = 0, py = 0;
			if(dir == right_dir) {
				px = 23;
				py = 12;
				dx = 1;
			}else if(dir == left_dir){
				px = 9;
				py = 12;
				dx = -1;
			}else if(dir == up_dir) {
				px = 25;
				py = 11;
				dy = -1;
			}else {
				px = 24;
				py = 11;
				dy = 1;
			}
			
			Bullet bullet =  new Bullet(this.getX()+px,this.getY()+py,1,1,null, dx, dy);
			Game.bullets.add(bullet);
		}
		
		if(Game.life < 0) {
			//Game Over
			Game.gameState = "game over";
		}
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*32 - Game.HEIGHT);
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof AmmoBattery) {
				if(Entity.isColidding(this, atual)) {
					Sound.ammo.play();
					Game.ammo+=10;
					if(Game.ammo > Game.maxAmmo) {
						Game.ammo = Game.maxAmmo;
					}
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionAmmoUpgrade() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof AmmoUpgrade) {
				if(Entity.isColidding(this, atual)) {
					Sound.ammoup.play();
					Game.maxAmmo+=10;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLifeUpgrade() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof LifeUpgrade) {
				if(Entity.isColidding(this, atual)) {
					Sound.lifeup.play();
					Game.maxLife+=10;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLife() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof LifeBattery) {
				if(Entity.isColidding(this, atual)) {
					Sound.life.play();
					Game.life+=10;
					if(Game.life > Game.maxLife) {
						Game.life = Game.maxLife;
					}
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public boolean checkCollisionNextLevel() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof NextLevel) {
				if(Entity.isColidding(this, atual)) {
					return true;
					}
			}
		}
		return false;
	}
	
	public void collidingEnemyBullet() {
		for(int i = 0; i < Game.enemyBullets.size(); i++) {
			Entity atual = Game.enemyBullets.get(i);
			if(atual instanceof EnemyBullet) {
				if(Entity.isColidding(this, atual)){
					isDamaged = true;
					Game.life--;
					Game.combo = 0;
					Game.enemyBullets.remove(i);
					Sound.hurt.play();
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			else if(dir == up_dir) {
				g.drawImage(upPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(dir == down_dir) {
			g.drawImage(downPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
	}

}
