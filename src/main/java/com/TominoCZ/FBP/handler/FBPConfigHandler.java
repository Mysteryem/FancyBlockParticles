package com.TominoCZ.FBP.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import com.TominoCZ.FBP.FBP;

import scala.reflect.io.Directory;

public class FBPConfigHandler {
	static FileInputStream fis;
	static InputStreamReader isr;
	static BufferedReader br;

	static File f;

	public static void init() {
		try {
			f = FBP.config;

			defaults(false);

			if (!f.exists()) {
				if (!Directory.apply(f.getParent()).exists())
					Directory.apply(f.getParent()).createDirectory(true, false);

				f.createNewFile();

				write();
			}

			read();

			write();

			closeStreams();
		} catch (IOException e) {
			closeStreams();

			write();
		}
	}

	public static void write() {
		try {
			check();

			PrintWriter writer = new PrintWriter(f.getPath(), "UTF-8");
			writer.println("enabled=" + FBP.enabled);
			writer.println("bounceOffWalls=" + FBP.bounceOffWalls);
			writer.println("showInMillis=" + FBP.showInMillis);
			writer.println("legacyMode=" + FBP.legacyMode);
			writer.println("cartoonMode=" + FBP.cartoonMode);
			writer.println("entityCollision=" + FBP.entityCollision);
			writer.println("smoothTransitions=" + FBP.smoothTransitions);
			writer.println("randomFadingSpeed=" + FBP.randomFadingSpeed);
			writer.println("spawnRedstoneBlockParticles=" + FBP.spawnRedstoneBlockParticles);
			writer.println("spawnWhileFrozen=" + FBP.spawnWhileFrozen);
			writer.println("minScale=" + FBP.minScale);
			writer.println("maxScale=" + FBP.maxScale);
			writer.println("minAge=" + FBP.minAge);
			writer.println("maxAge=" + FBP.maxAge);
			writer.println("gravityMult=" + FBP.gravityMult);
			writer.print("rotationMult=" + FBP.rotationMult);
			writer.close();
		} catch (Exception e) {
			closeStreams();

			if (!f.exists()) {
				if (!Directory.apply(f.getParent()).exists())
					Directory.apply(f.getParent()).createDirectory(true, false);

				try {
					f.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			write();
		}
	}

	static void read() {
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			br = new BufferedReader(isr);

			String line;

			while ((line = br.readLine()) != null) {
				if (line.contains("enabled="))
					FBP.enabled = Boolean.valueOf(line.replaceAll(" ", "").replace("enabled=", ""));
				else if (line.contains("bounceOffWalls="))
					FBP.bounceOffWalls = Boolean.valueOf(line.replaceAll(" ", "").replace("bounceOffWalls=", ""));
				else if (line.contains("showInMillis="))
					FBP.showInMillis = Boolean.valueOf(line.replaceAll(" ", "").replace("showInMillis=", ""));
				else if (line.contains("legacyMode="))
					FBP.legacyMode = Boolean.valueOf(line.replaceAll(" ", "").replace("legacyMode=", ""));
				else if (line.contains("cartoonMode="))
					FBP.cartoonMode = Boolean.valueOf(line.replaceAll(" ", "").replace("cartoonMode=", ""));
				else if (line.contains("entityCollision="))
					FBP.entityCollision = Boolean.valueOf(line.replaceAll(" ", "").replace("entityCollision=", ""));
				else if (line.contains("randomFadingSpeed="))
					FBP.randomFadingSpeed = Boolean.valueOf(line.replaceAll(" ", "").replace("randomFadingSpeed=", ""));
				else if (line.contains("smoothTransitions="))
					FBP.smoothTransitions = Boolean.valueOf(line.replaceAll(" ", "").replace("smoothTransitions=", ""));
				else if (line.contains("spawnWhileFrozen="))
					FBP.spawnWhileFrozen = Boolean.valueOf(line.replaceAll(" ", "").replace("spawnWhileFrozen=", ""));
				else if (line.contains("spawnRedstoneBlockParticles="))
					FBP.spawnRedstoneBlockParticles = Boolean
							.valueOf(line.replaceAll(" ", "").replace("spawnRedstoneBlockParticles=", ""));
				else if (line.contains("minScale="))
					FBP.minScale = Double.valueOf(line.replaceAll(" ", "").replace("minScale=", ""));
				else if (line.contains("maxScale="))
					FBP.maxScale = Double.valueOf(line.replaceAll(" ", "").replace("maxScale=", ""));
				else if (line.contains("minAge="))
					FBP.minAge = Integer.valueOf(line.replaceAll(" ", "").replace("minAge=", ""));
				else if (line.contains("maxAge="))
					FBP.maxAge = Integer.valueOf(line.replaceAll(" ", "").replace("maxAge=", ""));
				else if (line.contains("gravityMult="))
					FBP.gravityMult = Double.valueOf(line.replaceAll(" ", "").replace("gravityMult=", ""));
				else if (line.contains("rotationMult="))
					FBP.rotationMult = Double.valueOf(line.replaceAll(" ", "").replace("rotationMult=", ""));
			}

			closeStreams();

			check();
		} catch (Exception e) {
			closeStreams();

			check();

			write();
		}
	}

	static void closeStreams() {
		try {
			br.close();
			isr.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void defaults(boolean write) {
		FBP.minAge = 10;
		FBP.maxAge = 25;
		FBP.minScale = 1.0;
		FBP.maxScale = 1.2;
		FBP.gravityMult = 1.0;
		FBP.rotationMult = 1.0;
		// FBP.showInMillis = false;
		FBP.bounceOffWalls = false;
		FBP.legacyMode = false;
		FBP.cartoonMode = false;
		FBP.entityCollision = false;
		FBP.smoothTransitions = true;
		FBP.randomFadingSpeed = true;
		FBP.spawnRedstoneBlockParticles = false;
		FBP.inheritBlockTopTexture = true;
		FBP.spawnWhileFrozen = true;

		if (write)
			write();
	}

	public static void check() {
		FBP.minScale = Math.abs(FBP.minScale);
		FBP.maxScale = Math.abs(FBP.maxScale);
		FBP.minAge = Math.abs(FBP.minAge);
		FBP.maxAge = Math.abs(FBP.maxAge);
		FBP.gravityMult = Math.abs(FBP.gravityMult);
		FBP.rotationMult = Math.abs(FBP.rotationMult);

		if (FBP.minScale < 0.5D)
			FBP.minScale = 0.5D;
		if (FBP.maxScale > 2.0D)
			FBP.maxScale = 2.0D;
		else if (FBP.maxScale < 0.5D)
			FBP.maxScale = 0.5D;

		if (FBP.minAge < 10)
			FBP.minAge = 10;
		if (FBP.maxAge < 10)
			FBP.maxAge = 10;
		else if (FBP.maxAge > 100)
			FBP.maxAge = 100;

		FBP.minAge = fix(FBP.minAge);
		FBP.maxAge = fix(FBP.maxAge);

		if (FBP.gravityMult > 2.0D)
			FBP.gravityMult = 2.0D;
		else if (FBP.gravityMult < 0.1D)
			FBP.gravityMult = 0.1D;

		if (FBP.rotationMult > 1.5D)
			FBP.rotationMult = 1.5D;
		else if (FBP.rotationMult < 0)
			FBP.rotationMult = 0;

		// Final check
		if (FBP.minScale > FBP.maxScale)
			FBP.minScale = FBP.maxScale;

		if (FBP.minAge > FBP.maxAge)
			FBP.minAge = FBP.maxAge;
	}

	private static int fix(int num) {
		for (int i = num; i > 0; i--) {
			if (i % 5 == 0)
				return i;
		}

		return num;
	}
}
