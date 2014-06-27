package com.luna.ce.module.classes;

import org.lwjgl.input.Keyboard;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.lib.annotations.reflection.loading.Experimental;
import com.luna.lib.annotations.reflection.loading.TestClass;

// @Loadable
@Experimental
@TestClass
public class ModuleTest extends Module {
    public ModuleTest() {
        super("Test", "The test module :D", Keyboard.KEY_Z, EnumModuleType.MISC);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onEnable() {
        addChatMessage(String.format("%sTest module enabled!%s", getChatColor('a'), getChatColor('r')));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDisable() {
        addChatMessage(String.format("%sTest module disabled!%s", getChatColor('c'), getChatColor('r')));
    }

    @Override
    public void onWorldRender() {
    }

    @Override
    public void onWorldTick() {
        throw new NullPointerException("Test exception!");
    }
}
