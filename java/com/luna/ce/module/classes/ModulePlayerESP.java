package com.luna.ce.module.classes;

import java.util.List;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.input.Keyboard;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.ce.render.GLHelper;
import com.luna.lib.annotations.Loadable;

@Loadable
public class ModulePlayerESP extends Module {
	public ModulePlayerESP( ) {
		super( "PlayerESP", "See players through walls", Keyboard.KEY_R, EnumModuleType.RENDER );
	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void onWorldRender( ) {
		for( final EntityPlayer e : ( List< EntityPlayer > ) getWorld( ).playerEntities ) {
			if( e.equals( getPlayer( ) ) ) {
				continue;
			}
			GLHelper.drawESP( AxisAlignedBB.getBoundingBox( e.posX - RenderManager.renderPosX - 0.5, e.posY
					- RenderManager.renderPosY - 1.62, e.posZ - RenderManager.renderPosZ - 0.5,
					( e.posX - RenderManager.renderPosX ) + 0.5,
					( e.posY - RenderManager.renderPosY - 1.62 ) + 2,
					( e.posZ - RenderManager.renderPosZ ) + 0.5 ), 0.1, 0.7, 0.7 );
		}
	}
	
	@Override
	public void onWorldTick( ) {
	}
}
