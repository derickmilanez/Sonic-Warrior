package com.d1games.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.d1games.world.World;

public class Menu {
	
	public String[] options = {"New Game", "Load Game", "Exit"};
	public int currentOption = 0;
	public int maxOption = options.length -1;
	public boolean down, up, enter;
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "New Game") {
				//Sound.music1.loop();
				Game.gameState = "normal";
				file = new File("save.txt");
				file.delete();
			}else if(options[currentOption] == "Load Game") {
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(20);
					applySave(saver);	
				}
				if(Game.CUR_LEVEL == 2)
				{
					Sound.music1.stop();
					Sound.music2.loop();
				}
				if(Game.CUR_LEVEL == 3)
				{
					Sound.music1.stop();
					Sound.music2.stop();
					Sound.music3.loop();
				}
				if(Game.CUR_LEVEL == 4) {
					Sound.music1.loop();
					Sound.music2.stop();
					Sound.music3.stop();
				}
				if(Game.CUR_LEVEL == 5) {
					Sound.music1.stop();
					Sound.music2.loop();
					Sound.music3.stop();
				}
				if(Game.CUR_LEVEL == 6) {
					Sound.music1.stop();
					Sound.music2.stop();
					Sound.music3.play();
				}
				if(Game.CUR_LEVEL == 7) {
					Sound.music1.loop();
					Sound.music2.stop();
					Sound.music3.stop();
				}
			}else if(options[currentOption] == "Exit") {
				System.exit(1);
			}
		}
	}
	
	public static void  applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0])
			{
			case "level":
				World.restartWorld("level"+spl2[1]+".png");
				Game.gameState = "normal";
				Game.CUR_LEVEL = Integer.parseInt(spl2[1]);
				break;
			case "score":
				Game.score = Integer.parseInt(spl2[1]);
				break;
			case "maxLife":
				Game.maxLife = Integer.parseInt(spl2[1]);
				Game.life = Game.maxLife;
				break;
			case "maxAmmo":
				Game.maxAmmo = Integer.parseInt(spl2[1]);
				break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i =0; i < val.length; i++) {
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {
					
				}
			}catch(FileNotFoundException e) {
				
			}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, int val2[], int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i =0; i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n =  0; n < value.length;n++) {
				value[n]+=encode;
				current+=value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1)
					write.newLine();
			}catch(IOException e) {
				
			}
			try {
				write.flush();
				write.flush();
			}catch(IOException e) {
				
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,200));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.cyan);
		g.setFont(Game.newFont);
		g.drawString("ソニック", Game.WIDTH/2, Game.HEIGHT/2);
		g.drawString("ウォリアー", Game.WIDTH/2, Game.HEIGHT/2+64);
		g.fillRect(Game.WIDTH/2, Game.HEIGHT/2+96, Game.WIDTH+32,  Game.HEIGHT-283);
		g.setFont(Game.newFont2);
		g.drawString("S O N I C   W A R R I O R", Game.WIDTH/2, Game.HEIGHT/2+128);
		
		g.setColor(Color.green);
		g.setFont(Game.newFont2);
		g.drawString("New Game", (Game.WIDTH*Game.SCALE)/2-64, Game.HEIGHT*Game.SCALE-256+32);
		
		g.setColor(Color.yellow);
		g.setFont(Game.newFont2);
		g.drawString("Load Game", (Game.WIDTH*Game.SCALE)/2-64, Game.HEIGHT*Game.SCALE-224+32);
		
		g.setColor(Color.red);
		g.setFont(Game.newFont2);
		g.drawString("Exit", (Game.WIDTH*Game.SCALE)/2-64, Game.HEIGHT*Game.SCALE-192+32);
		
		if(options[currentOption] == "New Game") {
			g.setColor(Color.white);
			g.setFont(new Font("arial",Font.BOLD,28));
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-96,  Game.HEIGHT*Game.SCALE-256+32);
		}else if(options[currentOption] == "Load Game") {
			g.setColor(Color.white);
			g.setFont(new Font("arial",Font.BOLD,28));
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-96, Game.HEIGHT*Game.SCALE-224+32);
		}if(options[currentOption] == "Exit") {
			g.setColor(Color.white);
			g.setFont(new Font("arial",Font.BOLD,28));
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-96,  Game.HEIGHT*Game.SCALE-192+32);
		}
	}
}
