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
