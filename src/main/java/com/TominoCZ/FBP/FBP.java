package com.TominoCZ.FBP;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import com.TominoCZ.FBP.handler.FBPConfigHandler;
import com.TominoCZ.FBP.handler.FBPEventHandler;
import com.TominoCZ.FBP.handler.FBPKeyInputHandler;
import com.TominoCZ.FBP.handler.FBPRenderGuiHandler;
import com.TominoCZ.FBP.keys.FBPKeyBindings;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(clientSideOnly = true, modid = FBP.MODID)
public class FBP {
	@Instance(FBP.MODID)
	public static FBP instance;

	protected final static String MODID = "fbp";

	public static File config;

	public static int minAge, maxAge;

	public static double minScale, maxScale, gravityMult, rotationMult;

	public static boolean enabled = true;
	public static boolean showInMillis = false;

	public static boolean legacyMode = false, cartoonMode = false, spawnWhileFrozen = true,
			spawnRedstoneBlockParticles = false, inheritBlockTopTexture = true, smoothTransitions = true,
			randomDisappearSpeed = true, randomFadingSpeed = false, entityCollision = false, bounceOffWalls = false,
			frozen = false;

	public static ThreadLocalRandom random = ThreadLocalRandom.current();

	public static FBPEventHandler eventHandler = new FBPEventHandler();

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		config = new File(evt.getModConfigurationDirectory() + "/FBP/Particle.properties");

		FBPConfigHandler.init();

		MinecraftForge.EVENT_BUS.register(new FBPRenderGuiHandler());

		FBPKeyBindings.init();

		FMLCommonHandler.instance().bus().register(new FBPKeyInputHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		MinecraftForge.EVENT_BUS.register(new FBPRenderGuiHandler());
	}

	public static boolean isEnabled() {
		boolean result = enabled;

		if (!result)
			frozen = false;

		return result;
	}

	public static boolean isDev() {
		return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}
}