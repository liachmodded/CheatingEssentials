package com.luna.ce.module.classes;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.lib.annotations.Loadable;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

@Loadable
public class ModuleFastplace extends Module {
	
	public ModuleFastplace( ) {
		super( "Fastplace", "Place blocks faster than normal", Keyboard.KEY_N, EnumModuleType.PLAYER );
	}
	
	@Override
	public void onWorldRender( ) {
	}
	
	@Override
	public void onWorldTick( ) {
		ObfuscationReflectionHelper.setPrivateValue( Minecraft.class, getMinecraft( ), 0,
				"rightClickDelayTimer" );
	}
	
}
