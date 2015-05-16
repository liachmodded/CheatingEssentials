package com.luna.ce.module.classes;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.input.Keyboard;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.ce.util.gl.GLHelper;
import com.luna.lib.annotations.reflection.loading.Loadable;

@Loadable
public class ModulePlayerESP extends Module {
    public ModulePlayerESP() {
        super("PlayerESP", "See players through walls", Keyboard.KEY_R, EnumModuleType.RENDER);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onWorldRender() {
        for (final EntityPlayer e : (List<EntityPlayer>) getWorld().playerEntities) {
            if (e.equals(getPlayer())) {
                continue;
            }

            final float halfWidth = e.width / 2.0F;
            final AxisAlignedBB bb = AxisAlignedBB.fromBounds(e.posX - halfWidth, e.posY - 1.62,
                    e.posZ - halfWidth, (e.posX + halfWidth), (e.posY + (2 - 1.62)),
                    (e.posZ + halfWidth));

            GLHelper.drawESP(bb, 0.1, 0.7, 0.7);
        }
    }

    @Override
    public void onWorldTick() {
    }
}
