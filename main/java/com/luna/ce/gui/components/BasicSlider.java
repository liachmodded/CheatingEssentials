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
