package com.luna.ce.module.classes;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.ce.util.gl.GLHelper;
import com.luna.lib.annotations.reflection.loading.Loadable;

@Loadable
public class ModuleBlockOverlay extends Module {

    public ModuleBlockOverlay() {
        super("Block Overlay", "Renders a better block selection box", EnumModuleType.RENDER);
    }

    @Override
    public void onWorldRender() {
        final MovingObjectPosition pos = getMinecraft().objectMouseOver;
        final BlockPos bp = new BlockPos(pos.hitVec);
        final Block b = getWorld().getChunkFromBlockCoords(bp).getBlock(bp);
        if (Block.getIdFromBlock(b) != 0) {
            GLHelper.drawESP(AxisAlignedBB.fromBounds(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord,
                    pos.hitVec.xCoord + 1, pos.hitVec.yCoord + 1, pos.hitVec.zCoord + 1), 0.2, 0.9, 0.2);
        }
    }

    @Override
    public void onWorldTick() {

    }

}
