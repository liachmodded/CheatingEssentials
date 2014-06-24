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

package com.luna.ce.gui.components;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.ce.gui.action.i.MouseDownAction;
import com.luna.ce.gui.action.i.MouseDragAction;
import com.luna.ce.gui.action.i.MouseUpAction;
import com.luna.lib.datastructures.Tuple;
import com.luna.lib.datastructures.Value;

public class BasicSlider extends BasicWidget {
    private final Value sliderValue;
    private double amountScrolled = 0.0D;
    private boolean dragging = false;

    @SuppressWarnings("unchecked")
    public BasicSlider(Value value, Tuple<String, String>... tags) {
        super("", "slider", tags);
        sliderValue = value;
        amountScrolled = value.getDef() / value.getMax();
    }

    @Override
    public void initialize() {
        this.addAction(new MouseDownAction<BasicSlider>() {
            @Override
            public void onClick(final BasicSlider component, int button) {

                for(AbstractComponent e : getParent().getChildren()) {
                    if(!e.equals(component) && e instanceof BasicSlider) {
                        e.unfocus();
                    }
                }
                BasicSlider.this.focus();
                double mouseX = BasicSlider.this.calculateMouseLocation().x;
                double sliderX = BasicSlider.this.getX();
                if (mouseX > sliderX && BasicSlider.this.getArea().contains(BasicSlider.this.calculateMouseLocation())) {
                    dragging = true;
                    double diff = mouseX - sliderX;
                    amountScrolled = diff / BasicSlider.this.getWidth();
                    amountScrolled = amountScrolled < 0 ? 0 : amountScrolled > 1 ? 1 : amountScrolled;
                    BasicSlider.this.setValue();
                }
            }
        });
        this.addAction(new MouseDragAction<BasicSlider>() {
            @Override
            public void onDrag(BasicSlider component, int button) {
                double mouseX = BasicSlider.this.calculateMouseLocation().x;
                double sliderX = BasicSlider.this.getX();
                if (mouseX > sliderX && dragging) {
                    double diff = mouseX - sliderX;
                    amountScrolled = diff / BasicSlider.this.getWidth();
                    amountScrolled = amountScrolled < 0 ? 0 : amountScrolled > 1 ? 1 : amountScrolled;
                    BasicSlider.this.setValue();
                }
            }
        });
        this.addAction(new MouseUpAction<BasicSlider>() {
            @Override
            public void onUp(BasicSlider component, int button) {
                dragging = false;
                BasicSlider.this.unfocus();
            }
        });
    }

    private void setValue() {
        if (!dragging) {
            return;
        }
        @SuppressWarnings("unused")
        final float incrementValue = sliderValue.getIncrementValue();
        final float calculatedValue = ((float) amountScrolled * (sliderValue.getMax() - sliderValue.getMin()));

        sliderValue.setValue(calculatedValue + sliderValue.getMin());

        /*if (((calculatedValue % incrementValue) != 0) && (incrementValue != -1)) {
            sliderValue.setValue(((calculatedValue) - ((calculatedValue) % incrementValue))
                    + sliderValue.getMin());
        } else {
            sliderValue.setValue(calculatedValue + sliderValue.getMin());
        }*/
    }

    public Value getValue() {
        return sliderValue;
    }

    public double getAmountScrolled() {
        return amountScrolled;
    }
}
