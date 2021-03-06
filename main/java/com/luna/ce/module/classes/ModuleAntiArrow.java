package com.luna.ce.module.classes;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;

// @Loadable
public class ModuleAntiArrow extends Module {

    public ModuleAntiArrow() {
        super("AntiArrow", "Prevents arrows from hitting you", EnumModuleType.PLAYER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onEnable() {
        if (!getWorld().isRemote) {
            addChatMessage("\u00a7cThis module does not work in SMP, disabling...");
            toggle();
        }
    }

    @Override
    public void onWorldRender() {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onWorldTick() {
        for (final Entity e : (List<Entity>) getWorld().loadedEntityList) {
            if (e instanceof EntityArrow) {
                final EntityArrow a = (EntityArrow) e;
                if (!a.shootingEntity.equals(getPlayer())) {
                    if (getPlayer().getDistanceSqToEntity(a) <= 5.0F) {
                        a.setDead();
                        a.motionX = 0;
                        a.motionY = 0;
                        a.motionZ = 0;
                        getWorld().removeEntity(a);
                        getWorld().removeEntityFromWorld(a.getEntityId());
                    }
                }
            }
        }
    }
}
