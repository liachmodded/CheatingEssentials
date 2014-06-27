package com.luna.ce.gui.components;

import com.luna.ce.gui.a.AbstractContainer;
import com.luna.lib.datastructures.Tuple;

public class BasicFrame extends AbstractContainer {
    @SuppressWarnings("unchecked")
    public BasicFrame(String name, Tuple<String, String>... tags) {
        this.addTag("type", "frame");
        this.setX(0);
        this.setY(0);
        this.setMaxVisible(10);
        this.setWidth(100);
        this.setHeight(61);
        this.addTags(tags);
        this.addChild(new BasicWidget(name, "title"));
        this.initialize();
    }
}
