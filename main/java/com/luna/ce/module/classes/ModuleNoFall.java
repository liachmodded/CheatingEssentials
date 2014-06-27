package com.luna.ce.module.classes;

import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;

// @Loadable
public class ModuleNoFall extends Module {

    public ModuleNoFall() {
        super("NoFall", "Take no fall damage", Keyboard.KEY_O, EnumModuleType.PLAYER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onWorldRender() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWorldTick() {
        getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer(false));

    }
}
