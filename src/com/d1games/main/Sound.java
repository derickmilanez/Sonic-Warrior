package com.d1games.main;

import java.io.*;
import javax.sound.sampled.*;

public class Sound {

	public static class Clips{
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if(buffer == null)
				return;
			
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		
		public void play() {
			if(clips == null) return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if(p>=count) p = 0;
		}
		
		public void loop() {
			if(clips == null) return;
			clips[p].loop(300);
		}
		
		public void stop() {
			if(clips == null) return;
			clips[p].stop();
			clips[p].setFramePosition(0);
		}

	}
	
	public static Clips music1 = load("/music1.wav",1);
	public static Clips music2 = load("/music2.wav",1);
	public static Clips music3 = load("/music3.wav",1);
	public static Clips boss1shoot = load("/boss1shoot.wav",1);
	public static Clips boss2shoot = load("/boss2shoot.wav",1);
	public static Clips menu = load("/menu.wav",1);
	public static Clips life = load("/life.wav",1);
	public static Clips lifeup = load("/lifeupgrade.wav",1);
	public static Clips ammo = load("/ammo.wav",1);
	public static Clips ammoup = load("/ammoupgrade.wav",1);
	public static Clips shoot = load("/shoot.wav",1);
	public static Clips hurt = load("/hurt.wav",1);
	
	private static Clips load(String name,int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer,0,read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data,count);
		}catch(Exception e) {
			try {
				return new Clips(null,0);
			}catch(Exception ee) {
				return null;
			}
		}
	}
	
}
