package com.luna.ce.module.classes;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.luna.ce.api.APIModuleSetup;
import com.luna.ce.gui.screen.GuiTest;
import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.lib.annotations.reflection.loading.Loadable;

@Loadable
public class ModuleGui extends Module {
    private GuiTest gui;

    public ModuleGui() {
        super("Gui", "Opens up the GUI", Keyboard.KEY_Y, EnumModuleType.MISC);
        APIModuleSetup.addModuleToSetupQueue(this);
    }

    @Override
    public void initializeLater() {
        gui = new GuiTest();
        // gui.loadGuiConfig();
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen(gui);
        toggle();
    }

    @Override
    public void onWorldRender() {
    }

    @Override
    public void onWorldTick() {
    }

    public GuiTest getGui() {
        return gui;
    }
}
