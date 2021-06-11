package com.d1games.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.world.Camera;

public class EnemyBullet extends Entity {
	
	private int dx;
	private int dy;
	private double speed = 0.035;
	private int duration =  120, curDuration = 0;
	
	public EnemyBullet(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x+=dx*speed;
		y+=dy*speed;
		curDuration++;
		if(curDuration == duration) {
			Game.enemyBullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		if(Game.CUR_LEVEL == 3||  Game.CUR_LEVEL == 8) {
			g.setColor(Color.RED);
			g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 5, 5);
		}else if(Game.CUR_LEVEL == 4 || Game.CUR_LEVEL == 5) {
			g.setColor(Color.cyan);
			g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 5, 5);
		}else if(Game.CUR_LEVEL == 6) {
			g.setColor(Color.blue);
			g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 5, 5);
		}else if(Game.CUR_LEVEL == 7) {
			g.setColor(Color.yellow);
			g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 5, 5);
		}
	}
}
