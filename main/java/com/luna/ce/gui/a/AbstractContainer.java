package com.luna.ce.gui.a;

import com.luna.ce.gui.action.defaults.DefaultMouseAction;
import com.luna.ce.gui.action.i.*;
import com.luna.ce.gui.action.i.base.IGuiAction;
import com.luna.ce.gui.components.BasicWidget;
import com.luna.lib.datastructures.Tuple;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lwjgl.input.Mouse;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Note that all items that will be scrolled must be the same size.
 *
 * @author audrey
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractContainer extends AbstractComponent {
    private final List<AbstractComponent> children = new ArrayList<AbstractComponent>();

    private Rectangle2D.Double scrollArea;
    private Rectangle2D.Double scrollButton;
    private Rectangle2D.Double scrollComponents;
    private Rectangle2D.Double pinArea;
    private Rectangle2D.Double minimizeArea;
    private Rectangle2D.Double scrollButtonTop;
    private Rectangle2D.Double scrollButtonBottom;

    private boolean scrolling = false;
    private boolean needsScrolling = false;
    private boolean draggable = false;
    private boolean dragging = false;
    private boolean pinned = false;
    private boolean minimized = false;
    private boolean minimizable = true;
    private boolean pinnable = false;
    private boolean resizable = true;
    @SuppressWarnings("unused")
    private boolean scrollable = true;
    private boolean shouldSort = true;

    private double amountScrolled = 0.0D;
    private double padding = 1;
    private double maxVisible;
    private double xOffset, yOffset, prevX, prevY;

    @Override
    public void initialize() {
        draggable = getTagValue("type").equals("frame");

        setScrollArea(new Rectangle2D.Double((getX() + getWidth()) - 7, getY() + getTitle().getHeight()
                + (padding * 2), 6, getScrollEdge()));

        setScrollButton(new Rectangle2D.Double((getX() + getWidth()) - 7, getY() + getTitle().getHeight()
                + (padding * 2), 6, 6));

        setScrollComponents(new Rectangle2D.Double(getX() + padding, getY() + getTitle().getHeight()
                + (padding * 2), (getX() + getWidth()) - 6 - padding, getOldScrollEdge() - padding));
        setScrollButtons(new Tuple<Rectangle2D.Double, Rectangle2D.Double>(
                new Rectangle2D.Double(getScrollArea().getX(), getScrollArea().getY() - 6, 6, 6),
                new Rectangle2D.Double(getScrollArea().getX(), getScrollArea().getY() + getScrollEdge(), 6, 6)
        ));

        setPinArea(new Rectangle2D.Double(0, 0, 0, 0));
        setMinimizeArea(new Rectangle2D.Double(0, 0, 0, 0));

        if (draggable) {
            if (minimizable) {
                setMinimizeArea(new Rectangle2D.Double((getX() + getWidth()) - getTitle().getHeight(), getY()
                        + (padding * 2), getTitle().getHeight() - (padding * 2), getTitle().getHeight()
                        - (padding * 2)));
            }
            if (pinnable) {
                setPinArea(new Rectangle2D.Double((getX() + getWidth()) - (getMinimizeArea().getWidth()),
                        getY() + (padding * 2), getTitle().getHeight() - (padding * 2), getTitle()
                        .getHeight() - (padding * 2)
                ));
            }
        }

        if (getParent() != null) {
            getParent().unfocusAllChildrenExcept(this);
        }

        addAction(new DefaultMouseAction<AbstractContainer>());
        addAction(new MouseDownAction<AbstractContainer>() {
            @Override
            public void onClick(AbstractContainer component, int button) {
                if (button == 0) {
                    if (mouseOverScrollArea() && !getTitle().isMouseOver() && needsScrolling) {
                        if (draggable) {
                            if (isMinimized()) {
                                return;
                            }
                        }
                        startScrolling();
                    } else if (draggable && getTitle().getArea().contains(calculateMouseLocation())) {
                        startDragging();
                    } else if (draggable && mouseOverPin()) {
                        setPinned(!isPinned());
                    } else if (draggable && mouseOverMinimize()) {
                        setMinimized(!isMinimized());
                    } else if (scrollButtonTop.contains(calculateMouseLocation())) {
                        amountScrolled -= 0.15D;
                        if (amountScrolled < 0) {
                            amountScrolled = 0;
                        } else if (amountScrolled > 1) {
                            amountScrolled = 1;
                        }
                    } else if (scrollButtonBottom.contains(calculateMouseLocation())) {
                        amountScrolled += 0.15D;
                        if (amountScrolled < 0) {
                            amountScrolled = 0;
                        } else if (amountScrolled > 1) {
                            amountScrolled = 1;
                        }
                    } else {
                        if (draggable) {
                            if (isMinimized()) {
                                return;
                            }
                        }
                        relayMouseDownToChildren(button);
                    }
                }
            }
        });
        addAction(new MouseDragAction<AbstractContainer>() {
            @Override
            public void onDrag(AbstractContainer component, int button) {
                if (button == 0) {
                    if (AbstractContainer.this.getScrolling()) {
                        AbstractContainer.this.scroll();
                    } else if (draggable && dragging) {
                        AbstractContainer.this.drag();
                    } else {
                        AbstractContainer.this.relayMouseDragToChildren(button);
                    }
                }
            }
        });
        addAction(new MouseUpAction<AbstractContainer>() {
            @Override
            public void onUp(AbstractContainer component, int button) {
                if (button == 0) {
                    if (AbstractContainer.this.getScrolling()) {
                        AbstractContainer.this.stopScrolling();
                    } else if (draggable && dragging) {
                        AbstractContainer.this.stopDragging();
                    } else {
                        AbstractContainer.this.relayMouseUpToChildren(button);
                    }
                }
            }
        });
        addAction(new TickAction<AbstractContainer>() {
            @Override
            public void onTick(AbstractContainer component) {
                AbstractContainer.this.sort();
                if (AbstractContainer.this.isFocused() || AbstractContainer.this.isMouseOver()) {
                    int dWheel = Mouse.getDWheel();
                    if (dWheel > 0) {
                        amountScrolled -= 0.15D;
                    }
                    if (dWheel < 0) {
                        amountScrolled += 0.15D;
                    }
                    if (amountScrolled < 0) {
                        amountScrolled = 0;
                    } else if (amountScrolled > 1) {
                        amountScrolled = 1;
                    }
                }
                AbstractContainer.this.positionChildren();
                AbstractContainer.this.updateScrollThings();
                AbstractContainer.this.updateControls();
                for (final AbstractComponent e : AbstractContainer.this.getChildren()) {
                    e.tick();
                }
                AbstractContainer.this.setupHeight();
            }
        });
        addAction(new KeyAction<AbstractContainer>() {
            @Override
            public void onKey(AbstractContainer component, int key, char c) {
                AbstractContainer.this.relayKeyPressToChildren(key, c);
            }
        });
    }

    public void startScrolling() {
        scrolling = true;
        scroll();
    }

    public void scroll() {
        final double difference = (calculateMouseLocation().y - getY()) - (getScrollArea().getY() - getY());
        amountScrolled = (difference - getScrollButton().getHeight()) / (getScrollArea().getHeight() - getScrollButton().getHeight());
        amountScrolled = amountScrolled > 1.0D ? 1.0D : amountScrolled < 0.0D ? 0.0D : amountScrolled;

        setScrollButton(new Rectangle2D.Double((getX() + getWidth()) - 7, getY() + getTitle().getHeight()
                + (padding * 2) + (getScrollEdge() * amountScrolled), 6, 6));
    }

    public void stopScrolling() {
        scrolling = false;
    }

    public void startDragging() {
        dragging = true;
        xOffset = calculateMouseLocation().x;
        yOffset = calculateMouseLocation().y;
        prevX = getX();
        prevY = getY();
    }

    public void drag() {
        setX(calculateMouseLocation().x - (xOffset - prevX));
        setY(calculateMouseLocation().y - (yOffset - prevY));
    }

    public void stopDragging() {
        dragging = false;
    }

    private void updateScrollThings() {
        if (!isMinimized()) {
            setScrollArea(new Rectangle2D.Double((getX() + getWidth()) - (padding * 8), getY() + getTitle().getHeight() +
                    (padding * 2) + (needsScrolling ? 7 : 0), 6, getScrollEdge() - 2));

            setScrollComponents(new Rectangle2D.Double(getX() + padding, getY() + getTitle().getHeight()
                    + (padding * 2), (getX() + getWidth()) - 6 - (padding * 2), getScrollEdge() + padding * 12));

            setScrollButton(new Rectangle2D.Double(getScrollArea().getX(), getScrollArea().getY()// + getTitle().getHeight()
                    //+ (padding * 2)
                    + (getScrollEdge() * amountScrolled), 6, 6));
            setScrollButtons(new Tuple<Rectangle2D.Double, Rectangle2D.Double>(
                    new Rectangle2D.Double(getScrollArea().getX(), getScrollArea().getY() - 6, 6, 6),
                    new Rectangle2D.Double(getScrollArea().getX(), getScrollArea().getY() + getScrollEdge() - 2, 6, 6)
            ));
        } else {
            setScrollArea(new Rectangle2D.Double(0, 0, 0, 0));

            setScrollComponents(new Rectangle2D.Double(0, 0, 0, 0));

            setScrollButton(new Rectangle2D.Double(0, 0, 0, 0));
        }

        if (!needsScrolling) {
            setScrollArea(new Rectangle2D.Double(0, 0, 0, 0));
            setScrollButton(new Rectangle2D.Double(0, 0, 0, 0));
            setScrollComponents(new Rectangle2D.Double(getX() + padding, getY() + getTitle().getHeight()
                    + (padding * 2), (getX() + getWidth()) - (padding * 2), getScrollEdge()));
        }
    }

    private void updateControls() {
        if (draggable) {
            setMinimizeArea(new Rectangle2D.Double((getX() + getWidth()) - getTitle().getHeight()
                    - getPadding(), getY() + (getPadding() * 2), getTitle().getHeight() - (getPadding()),
                    getTitle().getHeight() - (getPadding() * 2)
            ));
            setPinArea(new Rectangle2D.Double((getX() + getWidth()) - (getMinimizeArea().getWidth() * 2)
                    - (getPadding() * 4), getY() + (getPadding() * 2), getTitle().getHeight()
                    - (getPadding()), getTitle().getHeight() - (getPadding() * 2)));
        } else {
            setPinArea(new Rectangle2D.Double(0, 0, 0, 0));
            setMinimizeArea(new Rectangle2D.Double(0, 0, 0, 0));
        }
    }

    @SuppressWarnings("unchecked")
    public void tick() {
        for (IGuiAction e : getActions()) {
            if (e instanceof TickAction) {
                ((TickAction) e).onTick(this);
            }
        }
    }

    public void setupHeight() {
        if (!resizable) {
            return;
        }
        if (minimized) {
            setHeight((padding * 2) + getTitle().getHeight());
            return;
        }
        // Subtract 1 because the title is itself an AbstractComponent
        if (((getChildren().size() - 1) >= 10) && isScrollable()) {
            needsScrolling = true;
        }

        if (!needsScrolling) {
            int h = 0;
            for (final AbstractComponent e : getChildren()) {
                h += e.getHeight() + padding;
            }
            h += padding;
            setHeight(h);
            amountScrolled = 0;
        } else {
            setHeight((getTitle().getHeight() * 11)
                    + (padding * (Math.min(getChildren().size(), maxVisible) + 2)));
        }
    }

    public void positionChildren() {
        double heightTotal = 0;
        double firstTen = 0;
        int i = 0;
        for (final AbstractComponent child : children) {
            if (child.getTagValue("type").equals("title")) {
                continue;
            }
            heightTotal += child.getHeight() + padding;
            if (i < getMaxVisible()) {
                firstTen += child.getHeight() + padding;
            }
            i++;
        }

        int lastY = (int) (getY() + (getTitle().getHeight()) + (padding * 2))
                - (int) ((heightTotal - firstTen) * amountScrolled);
        for (final AbstractComponent e : getChildren()) {
            e.setX(getX() + padding);
            e.setWidth(getWidth()
                    - (needsScrolling ? getScrollArea().getWidth() + (padding * 4) : (padding * 2)));
            if (e.getTagValue("type").equals("title")) {
                e.setX(getX() + padding);
                e.setY(getY() + padding);
                e.setWidth(getWidth()
                        - (padding * 2.5)
                        - (draggable ? (((e.getHeight() - (padding * 2)) * 2) + (padding * 4)) : 0));
                continue;
            }
            e.setY(lastY);
            lastY = (int) (e.getY() + e.getHeight() + padding);
        }
    }

    public final List<AbstractComponent> getChildren() {
        synchronized (children) {
            return children;
        }
    }

    public final void addChild(final AbstractComponent child) {
        synchronized (children) {
            child.setParent(this);
            child.initialize();
            children.add(child);
            sort();
            setupHeight();
        }
    }

    public void sort() {
        if (!shouldSort) {
            return;
        }
        synchronized (children) {
            try {
                Collections.sort(children, new Comparator<AbstractComponent>() {
                    @Override
                    public int compare(AbstractComponent o1, AbstractComponent o2) {
                        return o1.getText().compareTo(o2.getText());
                    }
                });
                int i = 0;
                for (final AbstractComponent e : children) {
                    if (e.getTagValue("type").equalsIgnoreCase("title")) {
                        Collections.rotate(children, -i);

                        break;
                    }
                    i++;
                }
            } catch (final NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void unfocusAllChildrenExcept(final AbstractComponent e) {
        if (!getChildren().contains(e) || !e.getParent().equals(this)) {
            return;
        }
        unfocus();
        unfocusAllChildren();
        e.focus();
    }

    public void unfocusAllChildren() {
        for (final AbstractComponent e : getChildren()) {
            e.unfocus();
        }
    }

    public Rectangle2D.Double getScrollArea() {
        return scrollArea;
    }

    public void setScrollArea(final Rectangle2D.Double area) {
        scrollArea = area;
    }

    public boolean getScrolling() {
        return scrolling;
    }

    public boolean mouseOverScrollArea() {
        return getScrollArea().contains(calculateMouseLocation());
    }

    public Rectangle2D.Double getScrollButton() {
        return scrollButton;
    }

    public void setScrollButton(final Rectangle2D.Double area) {
        if ((area.y + area.height) > (getScrollArea().getY() + getScrollEdge() - getPadding() * 2 /*+ getTitle().getHeight() + (padding * 2)*/)) {
            while ((area.y + area.height) > (getScrollArea().getY() + getScrollEdge() - getPadding() * 2 /*+ getTitle().getHeight() + (padding * 2)*/)) {
                area.y -= 1;
            }
        }

        if ((area.y) < (getScrollArea().getY() /*+ getTitle().getHeight() + (padding * 2)*/)) {
            while ((area.y) < (getScrollArea().getY() /*+ getTitle().getHeight() + (padding * 2)*/)) {
                area.y += 1;
            }
        }

        scrollButton = area;
    }

    public Rectangle2D.Double getScrollComponents() {
        return scrollComponents;
    }

    public void setScrollComponents(final Rectangle2D.Double area) {
        scrollComponents = area;
    }

    /*public double getMaxVisible() {
        return maxVisible;
    }

    public void setMaxVisible(final double e) {
        maxVisible = e;
    }*/

    private double getScrollEdge() {
        if (needsScrolling) {
            return (getY() + getHeight()) - getTitle().getHeight() -
                    (getTitle().getY() != 0 ? getTitle().getY() : getY() + 2) - (padding * 2) - 12;
        } else {
            return getOldScrollEdge();
        }
    }

    private double getOldScrollEdge() {
        return (getY() + getHeight()) - getTitle().getHeight() -
                (getTitle().getY() != 0 ? getTitle().getY() : getY() + 2) - (padding * 2);
    }

    @SuppressWarnings("unchecked")
    public AbstractComponent getTitle() {
        for (final AbstractComponent e : getChildren()) {
            if (e.getTagValue("type").equals("title")) {
                return e;
            }
        }
        BasicWidget qaz = new BasicWidget("", "title");
        qaz.setX(0);
        qaz.setY(0);
        qaz.setWidth(0);
        qaz.setHeight(0);
        return qaz;
    }

    public boolean getScrollComponentsContains(final Rectangle2D.Double rect) {
        return getScrollComponents().intersects(rect) || getScrollComponents().contains(rect);
    }

    public boolean mouseOverPin() {
        return getPinArea().contains(calculateMouseLocation()) && draggable;
    }

    public boolean mouseOverMinimize() {
        return getMinimizeArea().contains(calculateMouseLocation()) && draggable;
    }

    public double getX() {
        return getArea().getX();
    }

    public void setX(double e) {
        getArea().x = e;
    }

    public double getY() {
        return getArea().getY();
    }

    public void setY(double e) {
        getArea().y = e;
    }

    public double getWidth() {
        return getArea().getWidth();
    }

    public void setWidth(double e) {
        getArea().width = e;
    }

    public double getHeight() {
        return getArea().getHeight();
    }

    public void setHeight(double e) {
        getArea().height = e;
    }

    public Tuple<Rectangle2D.Double, Rectangle2D.Double> getScrollButtons() {
        return new Tuple<Rectangle2D.Double, Rectangle2D.Double>(scrollButtonTop, scrollButtonBottom);
    }

    public void setScrollButtons(Tuple<Rectangle2D.Double, Rectangle2D.Double> newAreas) {
        scrollButtonTop = newAreas.getKey();
        scrollButtonBottom = newAreas.getValue();
    }

    public void relayMouseDownToChildren(final int button) {
        for (final AbstractComponent e : getChildren()) {
            e.clickMouse(button);
        }
    }

    public void relayMouseDragToChildren(final int button) {
        for (final AbstractComponent e : getChildren()) {
            e.mouseDrag(button);
        }
    }

    public void relayMouseUpToChildren(final int button) {
        for (final AbstractComponent e : getChildren()) {
            e.mouseUp(button);
        }
    }

    public void relayKeyPressToChildren(final int key, final char ch) {
        for (final AbstractComponent e : getChildren()) {
            e.keyPressed(key, ch);
        }
    }
}
