package com.luna.ce;

import java.io.File;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.luna.ce.api.APIModuleSetup;
import com.luna.ce.config.Config;
import com.luna.ce.forge.ForgeEventManager;
import com.luna.ce.log.CELogger;
import com.luna.ce.manager.ManagerCommand;
import com.luna.ce.manager.ManagerModule;
import com.luna.lib.loggers.enums.EnumLogType;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod( modid = CheatingEssentials.MODID, name = CheatingEssentials.NAME, version = CheatingEssentials.VERSION, canBeDeactivated = true )
public class CheatingEssentials {
	static final String					MODID			= "cheatingessentials";
	static final String					VERSION			= "1.1.0";
	static final String					NAME			= "CheatingEssentials";
	
	static final String					RELEASE			= "Muffin";
	
	private String						commandPrefix	= ":";
	
	@Instance( MODID )
	private static CheatingEssentials	instance;
	
	private ForgeEventManager			eventManager;
	
	private final File					ceDataDir		= new File( String.format(
																"%s%scheatingessentials%s",
																Minecraft.getMinecraft( ).mcDataDir,
																File.separator, File.separator ) );
	
	@EventHandler
	public void onPreInitialization( final FMLPreInitializationEvent event ) {
		CELogger.getInstance( ).log( String.format( "Adding %s metadata...", NAME ) );
		final ModMetadata metadata = event.getModMetadata( );
		metadata.credits = "godshawk";
		metadata.version = VERSION;
		metadata.description = "SSP Cheating as never seen!";
		metadata.autogenerated = false;
		metadata.authorList = Arrays.asList( "godshawk" );
		metadata.modId = MODID;
		metadata.name = NAME;
	}
	
	@EventHandler
	public void init( final FMLInitializationEvent event ) {
		CELogger.getInstance( ).log( String.format( "Starting up %s v%s (5s)...", NAME, VERSION, RELEASE ) );
		CELogger.getInstance( ).log(
				String.format( "Running in Minecraft \"%s\", Forge \"%s\"", MinecraftForge.MC_VERSION,
						MinecraftForge.getBrandingVersion( ) ) );
		instance = new CheatingEssentials( );
		ceInit( );
	}
	
	private void ceInit( ) {
		CELogger.getInstance( ).log( "Loading modules..." );
		ManagerModule.getInstance( );
		CELogger.getInstance( ).log( "Registering Forge event stuff..." );
		eventManager = new ForgeEventManager( );
		FMLCommonHandler.instance( ).bus( ).register( eventManager );
		MinecraftForge.EVENT_BUS.register( eventManager );
		CELogger.getInstance( ).log( "Setting up modules that need setting up..." );
		APIModuleSetup.setupModules( );
		CELogger.getInstance( ).log( "Loading commands..." );
		ManagerCommand.getInstance( );
		CELogger.getInstance( ).log( "Loading config..." );
		Config.getInstance( );
	}
	
	public static CheatingEssentials getInstance( ) {
		return instance;
	}
	
	public String getCommandPrefix( ) {
		return commandPrefix;
	}
	
	public void setCommandPrefix( final String newp ) {
		commandPrefix = newp;
	}
	
	public File getDataDir( ) {
		if( !ceDataDir.exists( ) ) {
			if( ceDataDir.mkdirs( ) ) {
				return ceDataDir;
			} else {
				CELogger.getInstance( ).log( EnumLogType.FATAL,
						"Could not create the CheatingEssentials data directory!" );
				CELogger.getInstance( ).log( EnumLogType.FATAL, "Shutting down..." );
				Minecraft.getMinecraft( ).shutdown( );
			}
		}
		return ceDataDir;
	}
	
	public String getChatColor( final char col ) {
		switch( col ) {
			case '0':
				return "\u00a70";
			case '1':
				return "\u00a71";
			case '2':
				return "\u00a72";
			case '3':
				return "\u00a73";
			case '4':
				return "\u00a74";
			case '5':
				return "\u00a75";
			case '6':
				return "\u00a76";
			case '7':
				return "\u00a77";
			case '8':
				return "\u00a78";
			case '9':
				return "\u00a79";
			case 'a':
				return "\u00a7a";
			case 'b':
				return "\u00a7b";
			case 'c':
				return "\u00a7c";
			case 'd':
				return "\u00a7d";
			case 'e':
				return "\u00a7e";
			case 'f':
				return "\u00a7f";
			case 'k':
				return "\u00a7k";
			case 'm':
				return "\u00a7m";
			case 'n':
				return "\u00a7n";
			case 'r':
			default:
				return "\u00a7r";
		}
	}
}
