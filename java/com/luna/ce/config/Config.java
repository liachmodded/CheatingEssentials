package com.luna.ce.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.luna.ce.CheatingEssentials;
import com.luna.ce.gui.widget.base.Window;
import com.luna.ce.log.CELogger;
import com.luna.ce.manager.ManagerModule;
import com.luna.ce.module.Module;
import com.luna.ce.module.classes.ModuleGui;
import com.luna.lib.io.config.ConfigParser;
import com.luna.lib.loggers.enums.EnumLogType;

public class Config {
	private static final File	moduleFile	= new File( String.format( "%s%smodules.cheat",
													CheatingEssentials.getInstance( ).getDataDir( ),
													File.separator ) );
	
	private static final File	guiFile		= new File( String.format( "%s%sgui.cheat", CheatingEssentials
													.getInstance( ).getDataDir( ), File.separator ) );
	
	private static boolean		initialized	= false;
	private static Config		instance;
	private static final String	SEP_CHAR	= ";";
	
	private final ConfigParser	ioModule, ioGui;
	
	private Config( ) {
		ioModule = ConfigParser.getInstance( moduleFile );
		ioGui = ConfigParser.getInstance( guiFile );
	}
	
	public static Config getInstance( ) {
		if( !initialized ) {
			CELogger.getInstance( ).log( "Initializing config singleton..." );
			instance = new Config( );
			initialize( );
		}
		return instance;
	}
	
	private static void initialize( ) {
		if( !moduleFile.exists( ) || !guiFile.exists( ) ) {
			CELogger.getInstance( ).log( EnumLogType.IO, "Creating files that were not found..." );
			if( !moduleFile.exists( ) ) {
				instance.saveModuleConfig( );
			}
			if( !guiFile.exists( ) ) {
				instance.saveGuiConfig( );
			}
		}
		instance.loadModuleConfig( );
		initialized = true;
	}
	
	private void createModuleFileComments( final List< String > moduleInfo ) {
		moduleInfo.add( "# Format:\n" );
		moduleInfo.add( "# module_name;module_key;module_state\n" );
		moduleInfo.add( "# \n" );
		moduleInfo.add( "# The state and keys can be manually edited, \n" );
		moduleInfo.add( "# just refer to the LWJGL Keyboard documentation.\n" );
	}
	
	private void createGuiFileComments( final List< String > guiInfo ) {
		guiInfo.add( "# Format:\n" );
		guiInfo.add( "# window_name;x;y;window_pinned;window_minimized\n" );
		guiInfo.add( "# \n" );
		guiInfo.add( "# The states can be manually edited, \n" );
		guiInfo.add( "# just know how booleans work.\n" );
	}
	
	private void recreate( final File f ) {
		if( f.exists( ) ) {
			f.delete( );
		}
		try {
			f.createNewFile( );
		} catch( final IOException e ) {
			CELogger.getInstance( ).log( EnumLogType.WARNING,
					String.format( "Failed to recreate file '%s', this is VERY bad.", f.getName( ) ) );
			e.printStackTrace( );
		}
	}
	
	public void saveGuiConfig( ) {
		recreate( guiFile );
		final List< String > guiInfo = new ArrayList< String >( );
		createGuiFileComments( guiInfo );
		for( final Window e : ManagerModule.getInstance( ).getModuleByClass( ModuleGui.class ).getGui( )
				.getWindows( ) ) {
			guiInfo.add( String.format( "%s%s%d%s%d%s%b%s%b\n", e.getText( ), SEP_CHAR, e.getX( ), SEP_CHAR,
					e.getY( ), SEP_CHAR, e.getPinned( ), SEP_CHAR, e.getExpanded( ) ) );
		}
	}
	
	public void loadGuiConfig( ) {
		ioGui.setupRead( );
		final List< String > configLines = ioGui.read( );
		ioGui.closeStream( );
		
		for( final String e : configLines ) {
			final String[ ] info = e.split( SEP_CHAR );
			for( final Window w : ManagerModule.getInstance( ).getModuleByClass( ModuleGui.class ).getGui( )
					.getWindows( ) ) {
				CELogger.getInstance( ).log(
						String.format( "Trying %s for input %s...", w.getText( ), info[ 0 ] ) );
				if( w.getText( ).equals( info[ 0 ] ) ) {
					w.setX( Integer.parseInt( info[ 1 ] ) );
					w.setY( Integer.parseInt( info[ 2 ] ) );
					w.setPinned( Boolean.parseBoolean( info[ 3 ] ) );
					w.setExpanded( Boolean.parseBoolean( info[ 4 ] ) );
					break;
				}
			}
		}
	}
	
	public void saveModuleConfig( ) {
		recreate( moduleFile );
		final List< String > moduleInfo = new ArrayList< String >( );
		createModuleFileComments( moduleInfo );
		for( final Module e : ManagerModule.getInstance( ).getModules( ) ) {
			moduleInfo.add( String.format( "%s%s%d%s%b\n", e.getName( ).replaceAll( " ", "" ).toLowerCase( ),
					SEP_CHAR, e.getKey( ), SEP_CHAR, e.getActive( ) ) );
		}
		ioModule.setupWrite( );
		ioModule.write( moduleInfo );
		ioModule.closeStream( );
	}
	
	public void loadModuleConfig( ) {
		ioModule.setupRead( );
		final List< String > configLines = ioModule.read( );
		ioModule.closeStream( );
		
		int rm = 0;
		
		for( final String e : configLines ) {
			final String[ ] args = e.split( SEP_CHAR );
			final Module m = ManagerModule.getInstance( ).getModuleByName( args[ 0 ] );
			try {
				m.setKey( Integer.parseInt( args[ 1 ] ) );
				if( Boolean.parseBoolean( args[ 2 ] ) ) {
					CELogger.getInstance( ).log( String.format( "Toggling %s...", args[ 0 ] ) );
					m.toggle( );
				}
			} catch( final NullPointerException q ) {
				rm++;
			}
		}
		
		if( rm > 0 ) {
			saveModuleConfig( );
		}
	}
}
