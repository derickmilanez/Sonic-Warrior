package com.d1games.main;

import com.d1games.entities.*;
import com.d1games.graphics.Spritesheet;
import com.d1games.graphics.UI;
import com.d1games.world.Camera;
import com.d1games.world.World;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 288;
	public static final int SCALE = 2;
	
	public static int CUR_LEVEL = 8, MAX_LEVEL = 12;
	private BufferedImage image;
	
	
	public static Spritesheet spritesheet;
	public static Spritesheet spritesheet2;
	public static World world;
	public static Player player;
	public static List<Entity> entities;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<EnemyBullet> enemyBullets;
	public static List<Entity> groundEnemies;
	public static List<Entity> flyingEnemies;
	public static List<Entity> floorEntities;
	public static List<Entity> bosses;
	
	public static Random rand;
	
	public UI ui;
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("msgothic.ttc");
	public InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("msgothic.ttc");
	public static Font newFont, newFont2;
	
	public static String gameState = "menu";
	private boolean showMessageGameOver = false;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	public boolean saveGame = false;
	public static int score = 0;
	public static String scr;
	public static int maxLife = 50;
	public static double life = maxLife;
	public static int maxAmmo = 100;
	public static int ammo = 30;
	public static int combo = 0;

	
	public Menu menu;
	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		menu = new Menu();
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		bullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<EnemyBullet>();
		groundEnemies = new ArrayList<Entity>();
		flyingEnemies = new ArrayList<Entity>();
		floorEntities = new ArrayList<Entity>();
		bosses = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		spritesheet2 = new Spritesheet("/spritesheet2.png");
		player = new Player(0,0,32,32,spritesheet.getSprite(64,0, 32,32));
		entities.add(player);
		world = new World("/level8.png");
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(72f);
			newFont2 = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(28f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initFrame() {
		frame = new JFrame("Sonic Warrior");
		frame.add(this);
		frame.setUndecorated(false);
		frame.setResizable(false);
		frame.pack();
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/cybersonic.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(img);
		//frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		//Game.life = Game.maxLife;
		scr = String.valueOf(score);
		if(gameState.equals("normal")) {
			restartGame =  false;
			if(saveGame) {
				saveGame = false;
				String[] opt1 = {"level", "score", "maxLife", "maxAmmo"};
				int[] opt2 = {CUR_LEVEL, score, maxLife, maxAmmo};
				Menu.saveGame(opt1, opt2, 20);
			}
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		
		for(int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).tick();
		}
		
		for(int i = 0; i < groundEnemies.size(); i++) {
			groundEnemies.get(i).tick();
		}
		
		for(int i = 0; i < flyingEnemies.size(); i++) {
			flyingEnemies.get(i).tick();
		}
		
		for(int i = 0; i < floorEntities.size(); i++) {
			floorEntities.get(i).tick();
		}
		
		for(int i = 0; i < bosses.size(); i++) {
			Entity e = bosses.get(i);
			e.tick();
		}
		
		if(player.checkCollisionNextLevel()) {
			//Próximo nível
			saveGame = true;
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL) {
				CUR_LEVEL = 1;
			}
			life = maxLife;
			ammo = 30;
			String newWorld = "level"+CUR_LEVEL+".png";
			World.restartWorld(newWorld);
		}
		
		if(CUR_LEVEL == 3 || CUR_LEVEL == 6|| CUR_LEVEL == 9|| CUR_LEVEL == 12) {
			if(bosses.size() == 0) {
				saveGame = true;
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
				}
				life = maxLife;
				ammo = 30;
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartWorld(newWorld);
			}
		}
		
	  }else if(gameState == "game over") {
		  score = 0;
		  framesGameOver++;
		  if(framesGameOver > 30) {
			  framesGameOver = 0;
			  if(showMessageGameOver) {
				  showMessageGameOver = false;
			  }else {
				  showMessageGameOver = true;
			  }
		  }
	  }else if(gameState.equals("menu")) {
		  Camera.x = Camera.clamp(player.getX() - (Game.WIDTH/2), 0, World.WIDTH*32 - Game.WIDTH);
		  Camera.y = Camera.clamp(player.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*32 - Game.HEIGHT);
		  menu.tick();
	  }
		
		if(restartGame) {
			  restartGame = false;
			  gameState = "normal";
			  String newWorld = "level"+CUR_LEVEL+".png";
			  World.restartWorld(newWorld);
		  }
	};
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		world.render(g);
		for(int i = 0; i < floorEntities.size(); i++) {
			Entity e = floorEntities.get(i);
			e.render(g);
		}
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < groundEnemies.size(); i++) {
			Entity e = groundEnemies.get(i);
			e.render(g);
		}
		for(int i = 0; i < flyingEnemies.size(); i++) {
			Entity e = flyingEnemies.get(i);
			e.render(g);
		}
		for(int i = 0; i < bosses.size(); i++) {
			Entity e = bosses.get(i);
			e.render(g);
		}
		for(int i = 0; i < enemyBullets.size(); i++) {
			Entity e = enemyBullets.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		ui.render(g);
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		if(gameState.equals("game over")) {
			Graphics2D  g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial",Font.BOLD,36));
			g.setColor(Color.red);
			g.drawString("Game Over", (WIDTH*SCALE/2)-50, (HEIGHT*SCALE/2));
			g.setFont(new Font("arial",Font.BOLD,20));
			g.setColor(Color.yellow);
			if(showMessageGameOver) {
			g.drawString("Press R to restart", (WIDTH*SCALE/2)-35, (HEIGHT*SCALE/2)+50);
			}
		}else if(gameState.equals("menu")) {
			  menu.render(g);
		  }else if(gameState.equals("paused")) {
			  Graphics2D  g2 = (Graphics2D) g;
				g2.setColor(new Color(0,0,0,100));
				g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
				g.setFont(new Font("arial",Font.BOLD,36));
				g.setColor(Color.cyan);
				g.drawString("Paused", (WIDTH*SCALE/2)-50, HEIGHT*SCALE/2);
		  }
		bs.show();
	};

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void run() {
		//game looping
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns= 1000000000 / amountOfTicks;
		double delta  = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime=now;
			if(delta>=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W||
				e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
			if(gameState.equals("menu"))
				menu.up=true;
		}else if(e.getKeyCode() == KeyEvent.VK_S||
				e.getKeyCode() == KeyEvent.VK_DOWN) {
				player.down = true;
				if(gameState.equals("menu"))
					menu.down=true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A||
				e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}else if(e.getKeyCode() == KeyEvent.VK_D||
				e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
			player.shoot = true;
			if(ammo <= 0) {
				player.shoot = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R && gameState.equals("game over")) {
			maxLife = 50;
			maxAmmo = 100;
			life = maxLife;
			ammo = 30;
			restartGame = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState.equals("menu"))
				menu.enter =  true;
			if(gameState.equals("normal")) {
				gameState = "paused";
			}else if(gameState.equals("paused")){
				gameState = "normal"; 
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W||
				e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S||
				e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A||
				e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}else if(e.getKeyCode() == KeyEvent.VK_D||
				e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton()==1) {
			player.shoot = true;
			if(ammo <= 0) {
				player.shoot = false;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
