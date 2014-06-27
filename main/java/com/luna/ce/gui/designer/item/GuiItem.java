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
