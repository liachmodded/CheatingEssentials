package com.luna.ce.gui.a;

import com.luna.ce.gui.action.i.*;
import com.luna.ce.gui.action.i.base.IGuiAction;
import com.luna.ce.gui.registry.TagRegistry;
import com.luna.lib.datastructures.Tuple;
import lombok.Data;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Data
@SuppressWarnings({"unchecked", "unused"})
public abstract class AbstractComponent {
    private final Rectangle2D.Double area = new Rectangle2D.Double();
    private final List<IGuiAction<?>> actions = new ArrayList<IGuiAction<?>>();
    private final Map<String, String> tags = new HashMap<String, String>();
    private boolean focused = false;
    private AbstractContainer parent;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String st) {
        text = st;
    }

    public List<IGuiAction<?>> getActions() {
        return actions;
    }

    public double getX() {
        return area.getX();
    }

    public void setX(double e) {
        area.x = e;
    }

    public void setParent(AbstractContainer par) {
        parent = par;
    }

    public AbstractContainer getParent() {
        return parent;
    }

    public double getY() {
        return area.getY();
    }

    public void setY(double e) {
        area.y = e;
    }

    public double getWidth() {
        return area.getWidth();
    }

    public void setWidth(double e) {
        area.width = e;
    }

    public double getHeight() {
        return area.getHeight();
    }

    public void setHeight(double e) {
        area.height = e;
    }

    public boolean getFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public String getTagValue(final String tag) {
        for (final Entry<String, String> en : tags.entrySet()) {
            if (en.getKey().equals(tag)) {
                return en.getValue();
            }
        }
        return String.format("Unknown tag %s", tag);
    }

    public Rectangle2D.Double getArea() {
        return area;
    }

    public void addTag(final String tag, final String value) {
        if (!TagRegistry.validateTag(tag)) {
            throw new RuntimeException(String.format("%s is an invalid tag!", tag));
        }

        if (tags.containsKey(tag)) {
            return;
        }
        tags.put(tag, value);
    }

    public void addTags(Tuple<String, String>... tags) {
        for (Tuple<String, String> tag : tags) {
            addTag(tag.getKey(), tag.getValue());
        }
    }

    public void resize(final double newW, final double newH) {
        area.setRect(getX(), getY(), newW, newH);
    }

    public void translate(final double newX, final double newY) {
        area.setRect(newX, newY, getWidth(), getHeight());
    }

    public final void addAction(final IGuiAction<?> action) {
        synchronized (actions) {
            actions.add(action);
        }
    }

    protected final Point calculateMouseLocation() {
        final Minecraft minecraft = Minecraft.getMinecraft();
        int scale = minecraft.gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        int scaleFactor = 0;
        while ((scaleFactor < scale) && ((minecraft.displayWidth / (scaleFactor + 1)) >= 320)
                && ((minecraft.displayHeight / (scaleFactor + 1)) >= 240)) {
            scaleFactor++;
        }
        return new Point(Mouse.getX() / scaleFactor, (minecraft.displayHeight / scaleFactor)
                - (Mouse.getY() / scaleFactor) - 1);
    }

    public final boolean isMouseOver() {
        final Point e = calculateMouseLocation();
        //if (getParent() != null) {
        //    return area.contains(e) && getParent().getScrollComponents().contains(e);
        //}
        return area.contains(e);
    }

    public void focus() {
        focused = true;
    }

    public void unfocus() {
        focused = false;
    }

    public final void clickMouse(final int button) {
        if (isMouseOver()) {
            // setFocused(!isFocused());
            focus();
            synchronized (actions) {
                for (final IGuiAction e : actions) {
                    if (e instanceof MouseDownAction) {
                        ((MouseDownAction) e).onClick(this, button);
                    }
                }
            }
        }
    }

    public final void mouseUp(final int button) {
        if (isMouseOver() && isFocused()) {
            synchronized (actions) {
                for (final IGuiAction e : actions) {
                    if (e instanceof MouseUpAction) {
                        ((MouseUpAction) e).onUp(this, button);
                    }
                }
            }
        }
    }

    public void tick() {
        synchronized (actions) {
            for (final IGuiAction e : actions) {
                if (e instanceof TickAction) {
                    ((TickAction) e).onTick(this);
                }
            }
        }
    }

    public final void mouseDrag(final int button) {
        if (isFocused()) {
            synchronized (actions) {
                for (final IGuiAction e : actions) {
                    if (e instanceof MouseDragAction) {
                        ((MouseDragAction) e).onDrag(this, button);
                    }
                }
            }
        }
    }

    public final void keyPressed(final int key, final char ch) {
        if (focused) {
            synchronized (actions) {
                for (final IGuiAction e : actions) {
                    if (e instanceof KeyAction) {
                        ((KeyAction) e).onKey(this, key, ch);
                    }
                }
            }
        }
    }

    public void initialize() {
    }

    public boolean isFocused() {
        return focused;
    }
}
