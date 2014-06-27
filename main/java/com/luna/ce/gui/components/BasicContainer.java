package com.luna.ce.gui.components;

import com.luna.ce.gui.a.AbstractContainer;
import com.luna.lib.datastructures.Tuple;

public class BasicContainer extends AbstractContainer {
    @SuppressWarnings("unchecked")
    public BasicContainer(String name, Tuple<String, String>... tags) {
        this.setX(0);
        this.setY(0);
        this.setWidth(100);
        this.setHeight(61);
        this.setMaxVisible(10);
        this.addTag("type", "container");
        this.addTags(tags);
        this.addChild(new BasicWidget(name, "title"));
        this.initialize();
    }
}
