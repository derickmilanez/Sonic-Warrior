package com.d1games.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.d1games.main.Game;
import com.d1games.world.Camera;

public class Bullet extends Entity {
	
	private int dx;
	private int dy;
	private double speed = 5;
	private int duration =  120, curDuration = 0;
	
	public Bullet(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x+=dx*speed;
		y+=dy*speed;
		curDuration++;
		if(curDuration == duration) {
			Game.bullets.remove(this);
			Game.combo = 0;
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 5, 5);
	}
}
