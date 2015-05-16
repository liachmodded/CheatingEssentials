package com.luna.ce.gui.action.defaults;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.action.i.MouseDownAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class ClickSoundAction<T extends AbstractComponent> implements MouseDownAction<T> {
    @Override
    public void onClick(final T component, final int button) {
        Minecraft
                .getMinecraft()
                .getSoundHandler()
                .playSound(
                        PositionedSoundRecord
                                .create(new ResourceLocation("gui.button.press"), 1.0F)
                );
    }
}
