package com.luna.ce.module.classes;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.lib.annotations.Broken;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.entity.Entity;

import java.lang.reflect.Field;

// @Loadable
@Broken
public class ModuleNoWeb extends Module {

    public ModuleNoWeb() {
        super("NoWeb", "Don't get caught in those nasty webs!", EnumModuleType.PLAYER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onWorldRender() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWorldTick() {
        try {
            ((Field) ObfuscationReflectionHelper.getPrivateValue(Entity.class, getPlayer(), "isInWeb"))
                    .setAccessible(true);
            ObfuscationReflectionHelper.setPrivateValue(Entity.class, getPlayer(), false, "isInWeb");
        } catch (final Exception e) {
            // Stupid, I know
        }
    }
}
