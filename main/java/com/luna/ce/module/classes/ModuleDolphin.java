package com.luna.ce.module.classes;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.lib.annotations.reflection.loading.Loadable;

@Loadable
public class ModuleDolphin extends Module {

    public ModuleDolphin() {
        super("Dolphin", "Replaces WaterWalk", EnumModuleType.PLAYER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onWorldRender() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWorldTick() {
        if (getPlayer().isInWater()) {
            getPlayer().jump();
        }
    }

}
