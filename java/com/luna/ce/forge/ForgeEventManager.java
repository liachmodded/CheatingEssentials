package com.luna.ce.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ServerChatEvent;

import org.lwjgl.input.Keyboard;

import com.luna.ce.CheatingEssentials;
import com.luna.ce.config.Config;
import com.luna.ce.gui.widget.base.Window;
import com.luna.ce.manager.ManagerCommand;
import com.luna.ce.manager.ManagerModule;
import com.luna.ce.module.Module;
import com.luna.ce.module.classes.ModuleGui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ForgeEventManager {
	private final boolean[ ]	keyStates	= new boolean[ 256 ];
	
	@SubscribeEvent
	public void onServerTick( final TickEvent.ServerTickEvent ev ) {
		if( Minecraft.getMinecraft( ).theWorld != null ) {
			for( final Module e : ManagerModule.getInstance( ).getModules( ) ) {
				if( e.getActive( ) ) {
					try {
						e.onWorldTick( );
					} catch( final Exception x ) {
						handleException( e, x );
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldRender( final RenderWorldLastEvent ev ) {
		if( Minecraft.getMinecraft( ).theWorld != null ) {
			for( final Module e : ManagerModule.getInstance( ).getModules( ) ) {
				if( e.getActive( ) ) {
					try {
						e.onWorldRender( );
					} catch( final Exception x ) {
						handleException( e, x );
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onClientTick( final TickEvent.ClientTickEvent ev ) {
		if( Minecraft.getMinecraft( ).theWorld != null ) {
			for( final Module e : ManagerModule.getInstance( ).getModules( ) ) {
				if( checkKey( e.getKey( ) ) ) {
					try {
						e.toggle( );
					} catch( final Exception x ) {
						handleException( e, x );
					}
					return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onChatSend( final ServerChatEvent ev ) {
		ManagerCommand.getInstance( ).parseCommands( ev );
	}
	
	@SubscribeEvent
	public void onGuiRender( final RenderGameOverlayEvent.Chat ev ) {
		if( Minecraft.getMinecraft( ).theWorld != null ) {
			if( Minecraft.getMinecraft( ).currentScreen == null ) {
				for( final Module e : ManagerModule.getInstance( ).getModules( ) ) {
					if( e.getActive( ) ) {
						try {
							e.onGuiRender( );
						} catch( final Exception x ) {
							handleException( e, x );
						}
					}
				}
				for( final Window e : ManagerModule.getInstance( ).getModuleByClass( ModuleGui.class )
						.getGui( ).getWindows( ) ) {
					// if( e.getVisible( ) ) {
					if( e.getPinned( ) ) {
						// final Point p = calculateMouseLocation( );
						e.drawWindow( 0, 0 );
					}
					// }
				}
			}
		}
	}
	
	private boolean checkKey( final int key ) {
		if( Minecraft.getMinecraft( ).currentScreen != null ) {
			return false;
		}
		
		try {
			if( Keyboard.getEventKey( ) > -1 ) {
				if( Keyboard.isKeyDown( key ) != keyStates[ key ] ) {
					return keyStates[ key ] = !keyStates[ key ];
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch( final IndexOutOfBoundsException e ) {
			// Don't understand why this happens, but it does. =|
			// e.printStackTrace( );
			return false;
		}
	}
	
	private void addChatMessage( final String... message ) {
		if( Minecraft.getMinecraft( ).thePlayer == null ) {
			return;
		}
		for( final String e : message ) {
			Minecraft.getMinecraft( ).thePlayer.addChatMessage( new ChatComponentText( String.format(
					"[CE] %s", e ) ) );
		}
	}
	
	private void handleException( final Module m, final Exception x ) {
		addChatMessage(
				String.format( "%sDisabling %s%s%s due to an error:", CheatingEssentials.getInstance( )
						.getChatColor( 'c' ), CheatingEssentials.getInstance( ).getChatColor( '4' ), m
						.getName( ), CheatingEssentials.getInstance( ).getChatColor( 'c' ) ),
				( x.getMessage( ) == null ? "Unknown error; check log" : String.format( "%s [%s]",
						x.getMessage( ), x.getClass( ).getSimpleName( ) ) ) );
		x.printStackTrace( );
		m.toggle( );
		Config.getInstance( ).saveModuleConfig( );
	}
}
