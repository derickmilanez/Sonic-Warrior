package com.d1games.graphics;

import java.awt.Color;
import java.awt.Graphics;

import com.d1games.entities.Boss1;
import com.d1games.entities.Boss2;
import com.d1games.main.Game;

public class UI {
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.drawString("Life: ", Game.WIDTH-305, Game.HEIGHT-270);
		g.setColor(Color.red);
		g.drawString("Ammo: " + Game.ammo + "/" + Game.maxAmmo, Game.WIDTH-100, Game.HEIGHT-270);
		g.setColor(Color.green);
		g.drawString("Score:", Game.WIDTH-305, Game.HEIGHT-8);
		g.setColor(Color.yellow);
		g.drawString(Game.scr,  Game.WIDTH-260, Game.HEIGHT-8);
		g.setColor(Color.black);
		g.drawRect(Game.WIDTH-281, Game.HEIGHT-279, (int)(Game.maxLife)+1, Game.HEIGHT-279);
		g.setColor(Color.red);
		g.fillRect(Game.WIDTH-280, Game.HEIGHT-278, (int)(Game.maxLife), Game.HEIGHT-280);
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH-280, Game.HEIGHT-278, (int)(Game.life), Game.HEIGHT-280);
		
		if(Game.combo > 0) {
			g.setColor(Color.orange);
			g.drawString("Combo: " + Game.combo, Game.WIDTH-100, Game.HEIGHT-260);
		}
		
		//Bosses UI
		if(Game.CUR_LEVEL == 3) {
			g.setColor(Color.magenta);
			g.drawString("	Boss: ", 200, 280);
			g.setColor(Color.black);
			g.drawRect(239, 271, 50+1, 9);
			g.setColor(Color.red);
			g.fillRect(240, 272, 50, 8);
			g.setColor(Color.green);
			g.fillRect(240, 272, (int)(Boss1.life), 8);
		}
		
		if(Game.CUR_LEVEL == 6) {
			g.setColor(Color.magenta);
			g.drawString("	Boss: ", 200, 280);
			g.setColor(Color.black);
			g.drawRect(239, 271, 50+1, 9);
			g.setColor(Color.red);
			g.fillRect(240, 272, 50, 8);
			g.setColor(Color.green);
			g.fillRect(240, 272, (int)(Boss2.life), 8);
		}
	}
}
