/*
 * Isis modified client for Minecraft.
 * Copyright (C) 2014-2015  godshawk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ===========================================================================
 *
 * ALL SOURCE CODE WITHOUT THIS COPYRIGHT IS THE PROPERTY OF ITS RESPECTIVE
 * OWNER(S). I CLAIM NO RIGHT TO OR OWNERSHIP OF ANY OF IT.
 *
 * Minecraft is owned by Mojang AB.
 * Java itself is owned by Oracle.
 * All other code is not owned by me.
 * Thank you, and have a good day!
 */

package com.luna.ce.gui.designer.item;

import com.luna.ce.gui.action.defaults.DefaultMouseAction;
import com.luna.ce.gui.action.i.MouseDownAction;
import com.luna.ce.gui.action.i.MouseDragAction;
import com.luna.ce.gui.action.i.MouseUpAction;
import com.luna.ce.gui.action.i.TickAction;
import com.luna.ce.gui.components.BasicWidget;
import com.luna.ce.gui.designer.ItemFormatter;
import com.luna.ce.gui.skin.manager.SkinManager;
import lombok.Getter;

public class GuiItem extends BasicWidget {
    private final String rawText;
    @Getter
    private boolean dragging = false;
    private double xOffset, yOffset, prevX, prevY;

    @SuppressWarnings("unchecked")
    public GuiItem(String text) {
        super("", "label");
        rawText = text;
        initialize();
    }

    @Override
    public void initialize() {
        addAction(new DefaultMouseAction<GuiItem>());
        addAction(new MouseDownAction<GuiItem>() {
            @Override
            public void onClick(GuiItem component, int button) {
                if (button == 0) {
                    if (!dragging) {
                        GuiItem.this.startDragging();
                    }
                }
            }
        });
        addAction(new MouseDragAction<GuiItem>() {
            @Override
            public void onDrag(GuiItem component, int button) {
                if (dragging) {
                    GuiItem.this.drag();
                }
            }
        });
        addAction(new MouseUpAction<GuiItem>() {
            @Override
            public void onUp(GuiItem component, int button) {
                if (button == 0) {
                    if (dragging) {
                        GuiItem.this.stopDragging();
                    }
                }
            }
        });
        addAction(new TickAction<GuiItem>() {
            @Override
            public void onTick(GuiItem component) {
                GuiItem.this.setText(ItemFormatter.format(rawText));
            }
        });
        addAction(new TickAction<GuiItem>() {
                    @Override
                    public void onTick(GuiItem component) {
                        component.resize(
                                SkinManager.getInstance().getCurrentSkin().getFont().getStringWidth(GuiItem.this.getText()),
                                SkinManager.getInstance().getCurrentSkin().getFont().getStringHeight()
                        );
                    }
                }
        );
    }

    private void startDragging() {
        dragging = true;
        xOffset = calculateMouseLocation().x;
        yOffset = calculateMouseLocation().y;
        prevX = getX();
        prevY = getY();
    }

    private void drag() {
        setX(calculateMouseLocation().x - (xOffset - prevX));
        setY(calculateMouseLocation().y - (yOffset - prevY));
    }

    private void stopDragging() {
        dragging = false;
    }

    public String getText() {
        return ItemFormatter.format(super.getText());
    }
}
