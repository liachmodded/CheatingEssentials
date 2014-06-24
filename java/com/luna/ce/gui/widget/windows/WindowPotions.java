package com.luna.ce.gui.widget.windows;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;

import com.luna.ce.gui.widget.base.ISkin;
import com.luna.ce.gui.widget.base.Window;
import com.luna.ce.gui.widget.components.Button;
import com.luna.ce.gui.widget.components.Slider;
import com.luna.ce.gui.widget.skin.SkinCE;
import com.luna.lib.datastructures.Value;

public class WindowPotions extends Window {
	private final Value	effectLevel	= new Value( "Effect Level", 1, 1, 10 );
	
	public WindowPotions( final int x, final int y ) {
		super( "Potion Effects", new SkinCE( ), x, y, 0, 0, true );
		addComponentsLater( );
	}
	
	@Override
	public void addComponents( ) {
	}
	
	private void addComponentsLater( ) {
		addChild( new Slider( effectLevel, 80, 1 ) );
		for( final Potion ef : Potion.potionTypes ) {
			if( ef != null ) {
				addChild( new Button( I18n.format( ef.getName( ) ), null ) {
					@Override
					public void mouseClicked( final Window window, final int mouseX, final int mouseY,
							final int button ) {
						if( Minecraft.getMinecraft( ).thePlayer.isPotionActive( ef.getId( ) ) ) {
							if( Minecraft.getMinecraft( ).thePlayer.getActivePotionEffect( ef )
									.getAmplifier( ) == ( int ) effectLevel.getValue( ) ) {
								Minecraft.getMinecraft( ).thePlayer.removePotionEffect( ef.getId( ) );
								Minecraft.getMinecraft( ).thePlayer
										.addChatMessage( new ChatComponentText(
												"\u00a7cRemoved\u00a7r potion \u00a76"
														+ I18n.format( ef.getName( ) ) ) );
								return;
							}
						}
						Minecraft.getMinecraft( ).thePlayer.removePotionEffect( ef.getId( ) );
						Minecraft.getMinecraft( ).thePlayer.addPotionEffect( new PotionEffect( ef.getId( ),
								999999999, ( int ) effectLevel.getValue( ) - 1, true ) );
						Minecraft.getMinecraft( ).thePlayer.addChatMessage( new ChatComponentText(
								"\u00a7aAdded\u00a7r potion \u00a76" + I18n.format( ef.getName( ) ) + " "
										+ ( int ) effectLevel.getValue( ) ) );
					}
					
					@Override
					public void draw( final Window window, final ISkin skin, final int mouseX,
							final int mouseY ) {
						skin.drawButton( getX( ), getY( ), getWidth( ), getHeight( ), mouseOver( ) );
						getFontRenderer( ).drawString( getText( ), getX( ) + 3, getY( ) + 3,
								skin.getTextColor( false ) );
					}
				} );
				
			}
		}
		
		addChild( new Button( "Remove all effects", null ) {
			@Override
			public void mouseClicked( final Window window, final int mouseX, final int mouseY,
					final int button ) {
				for( final Potion ef : Potion.potionTypes ) {
					if( ef != null ) {
						Minecraft.getMinecraft( ).thePlayer.removePotionEffect( ef.getId( ) );
					}
				}
			}
			
			@Override
			public void draw( final Window window, final ISkin skin, final int mouseX, final int mouseY ) {
				skin.drawButton( getX( ), getY( ), getWidth( ), getHeight( ), mouseOver( ) );
				getFontRenderer( ).drawString( getText( ), getX( ) + 3, getY( ) + 3,
						skin.getTextColor( false ) );
			}
		} );
	}
}
