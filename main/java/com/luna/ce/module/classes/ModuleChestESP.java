package com.luna.ce.module.classes;

import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;

import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import com.luna.ce.module.EnumModuleType;
import com.luna.ce.module.Module;
import com.luna.ce.util.gl.GLHelper;
import com.luna.lib.annotations.reflection.loading.Loadable;

@Loadable
public class ModuleChestESP extends Module {
    private LinkedHashMap<Class<? extends TileEntity>, double[]> acceptedTileEntities;

    {
        acceptedTileEntities = new LinkedHashMap<Class<? extends TileEntity>, double[]>();
        acceptedTileEntities.put(TileEntityChest.class, new double[]{
                0.2, 0.7, 0.7
        });
        acceptedTileEntities.put(TileEntityEnderChest.class, new double[]{
                0.0, 0.0, 0.0
        });
        acceptedTileEntities.put(TileEntityBeacon.class, new double[]{
                0.7, 0.7, 0.2
        });
        acceptedTileEntities.put(TileEntityEnchantmentTable.class, new double[]{
                0.7, 0.2, 0.7
        });
        acceptedTileEntities.put(TileEntityEndPortal.class, new double[]{
                0.5, 0.5, 0.5
        });
        acceptedTileEntities.put(TileEntityMobSpawner.class, new double[]{
                1.0, 0.5, 0.0
        });
    }

    public ModuleChestESP() {
        super("ChestESP", "Find chests and other goodies through walls", Keyboard.KEY_C,
                EnumModuleType.RENDER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onWorldRender() {
        for (final TileEntity e : (List<TileEntity>) getWorld().loadedTileEntityList) {
            if (acceptedTileEntities.containsKey(e.getClass())) {
                final int tx = e.getPos().getX();
                final int ty = e.getPos().getY();
                final int tz = e.getPos().getZ();

                final double[] colors = e.getBlockType().equals(
                        Block.getBlockFromName("minecraft:trapped_chest")) ? new double[]{
                        1.0, 0.2, 0.2
                } : acceptedTileEntities.get(e.getClass());

                if (getWorld().getChunkProvider().chunkExists(tx,tz)) {
                    GLHelper.drawESP(
                            AxisAlignedBB.fromBounds(tx, ty, tz, (tx) + 1, (ty) + 1, (tz) + 1),
                            colors[0], colors[1], colors[2]);
                }
            }
        }
    }

    @Override
    public void onWorldTick() {
    }

}
