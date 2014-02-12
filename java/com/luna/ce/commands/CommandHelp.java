package com.luna.ce.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.StringUtils;

import com.luna.ce.CheatingEssentials;
import com.luna.ce.manager.ManagerCommand;
import com.luna.ce.manager.ManagerModule;
import com.luna.ce.module.Module;
import com.luna.lib.interfaces.Command;

public class CommandHelp extends ACommand {
	/**
	 * Max commands/page. Each page is 7 lines, formatted as
	 * 
	 * <pre>
	 * --------HEADER--------
	 * Command - Help
	 * Command - Help
	 * Command - Help
	 * Command - Help
	 * Command - Halp
	 * --------FOOTER--------
	 * </pre>
	 */
	private final int	HELP_MAX_SIZE	= 5;
	
	public CommandHelp( ) {
		super( "help" );
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCommand( final String[ ] args ) {
		if( args.length > 2 ) {
			addChatMessage( String
					.format( "Too many arguments for %shelp%s!", CheatingEssentials.getInstance( )
							.getChatColor( 'c' ), CheatingEssentials.getInstance( ).getChatColor( 'r' ) ) );
			return;
		}
		try {
			if( args.length == 1 ) {
				for( final String e : createHelpPage( 1 ) ) {
					addChatMessage( e );
				}
				return;
			}
			for( final String e : createHelpPage( Integer.parseInt( args[ 1 ] ) ) ) {
				addChatMessage( e );
			}
			return;
		} catch( final Exception e ) {
			try {
				final Module m = ManagerModule.getInstance( ).getModuleByName( args[ 1 ] );
				final String[ ] halp = m.getHelp( );
				// @formatter:off
				/*for( int i = 0; i < halp.length; i++ ) {
					halp[ i ] = String.format( "[%s%s%s] %s",
							CheatingEssentials.getInstance( ).getChatColor( 'a' ), m.getName( ),
							CheatingEssentials.getInstance( ).getChatColor( 'r' ), halp[ i ] );
				}*/
				// @formatter:on
				addChatMessage( getHeader( m.getName( ) ) );
				addChatMessage( halp );
				addChatMessage( getFooter( m.getName( ) ) );
				return;
			} catch( final Exception f ) {
				try {
					for( final Command c : ManagerCommand.getInstance( ).getCommands( ) ) {
						if( c.getName( ).toLowerCase( ).replaceAll( " ", "" )
								.equals( args[ 1 ].toLowerCase( ) ) ) {
							String cmd = c.toString( );
							if( c instanceof Module ) {
								final Module q = ( Module ) c;
								cmd = String.format( "%s: %s", q.getName( ), findUsage( q ) );
							}
							if( c instanceof ACommand ) {
								cmd = ( ( ACommand ) c ).getSyntax( );
							}
							addChatMessage( getHeader( cmd.split( " " )[ 0 ] ) );
							addChatMessage( cmd );
							addChatMessage( getFooter( cmd.split( " " )[ 0 ] ) );
							break;
						}
					}
					return;
				} catch( final Exception g ) {
					g.printStackTrace( );
					addChatMessage( String.format( "Invalid command: %s%s%s", CheatingEssentials
							.getInstance( ).getChatColor( 'c' ), args.length > 1 ? args[ 1 ] : "NULL",
							CheatingEssentials.getInstance( ).getChatColor( 'r' ) ) );
				}
			}
		}
	}
	
	private List< String > createHelpPage( int p ) {
		final List< String > dump = ManagerCommand.getInstance( ).dumpCommands( );
		final int pageCount = Math.round( dump.size( ) / HELP_MAX_SIZE );
		
		if( p > pageCount ) {
			p = pageCount;
		}
		if( p <= 0 ) {
			p = 1;
		}
		
		final List< String > page = new ArrayList<>( );
		page.add( getHeader( p ) );
		for( int i = p * HELP_MAX_SIZE; i < ( ( p * HELP_MAX_SIZE ) + HELP_MAX_SIZE ); i++ ) {
			if( i >= dump.size( ) ) {
				break;
			}
			page.add( dump.get( i ).replaceAll( "Usage: ", "" ) );
		}
		page.add( getFooter( p ) );
		
		return page;
	}
	
	private String getHeader( final Object p ) {
		return String.format( "%s--------%sHELP %s%s%s--------%s", CheatingEssentials.getInstance( )
				.getChatColor( '6' ), CheatingEssentials.getInstance( ).getChatColor( 'c' ),
				CheatingEssentials.getInstance( ).getChatColor( 'a' ), p, CheatingEssentials.getInstance( )
						.getChatColor( '6' ), CheatingEssentials.getInstance( ).getChatColor( 'r' ) );
	}
	
	private String getFooter( final Object p ) {
		String footer = CheatingEssentials.getInstance( ).getChatColor( '6' );
		for( int i = 0; i < StringUtils.stripControlCodes( getHeader( p ) ).length( ); i++ ) {
			footer += "-";
		}
		footer += CheatingEssentials.getInstance( ).getChatColor( 'r' );
		return footer;
	}
	
	@Override
	public String getSyntax( ) {
		return "help [commmand/page]";
	}
	
	private String findUsage( final Module e ) {
		for( final String s : e.getHelp( ) ) {
			if( s.startsWith( "" ) ) {
				return s;
			}
		}
		return e.toString( );
	}
}
