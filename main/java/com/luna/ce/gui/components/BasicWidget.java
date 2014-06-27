package com.luna.ce.gui.components;

import com.luna.ce.gui.a.AbstractComponent;
import com.luna.lib.datastructures.Tuple;

@SuppressWarnings("unchecked")
public class BasicWidget extends AbstractComponent {
    public BasicWidget(String name, String type, Tuple<String, String>... tags) {
        this.setText(name);
        this.setX(0);
        this.setY(0);
        this.setWidth(70);
        this.setHeight(14);
        this.addTag("type", type);
        this.addTags(tags);
    }
}
