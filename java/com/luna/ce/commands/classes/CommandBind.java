package com.luna.ce.commands.classes;

import org.lwjgl.input.Keyboard;

import com.luna.ce.CheatingEssentials;
import com.luna.ce.commands.ACommand;
import com.luna.ce.manager.ManagerModule;

public class CommandBind extends ACommand {
	
	public CommandBind( ) {
		super( "bind" );
	}
	
	@Override
	public void onCommand( final String[ ] args ) {
		if( ( args.length <= 1 ) || ( args.length > 3 ) ) {
			throw new java.lang.IllegalArgumentException( );
		} else {
			if( args.length == 2 ) {
				final String key = Keyboard.getKeyName( ManagerModule.getInstance( )
						.getModuleByName( args[ 1 ] ).getKey( ) );
				
				addChatMessage( String.format( "%s%s%s is currently bound to %s%s", CheatingEssentials
						.getInstance( ).getChatColor( 'b' ), args[ 1 ], CheatingEssentials.getInstance( )
						.getChatColor( 'r' ), CheatingEssentials.getInstance( ).getChatColor( 'a' ), key ) );
			} else if( args.length == 3 ) {
				ManagerModule.getInstance( ).getModuleByName( args[ 1 ] )
						.setKey( Keyboard.getKeyIndex( args[ 2 ].toUpperCase( ) ) );
				addChatMessage( String.format( "%s%s%s is now bound to %s%s", CheatingEssentials
						.getInstance( ).getChatColor( 'b' ), args[ 1 ], CheatingEssentials.getInstance( )
						.getChatColor( 'r' ), CheatingEssentials.getInstance( ).getChatColor( 'a' ), args[ 2 ]
						.toUpperCase( ) ) );
			} else {
				throw new Error( "How can you even." );
			}
		}
	}
	
	@Override
	public String getSyntax( ) {
		return "bind <module> [key]";
	}
	
}
