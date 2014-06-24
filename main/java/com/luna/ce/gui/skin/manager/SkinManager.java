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

package com.luna.ce.gui.skin.manager;

import com.luna.ce.gui.skin.a.AbstractSkin;
import com.luna.ce.gui.skin.impl.SkinBackup;
import com.luna.ce.gui.skin.impl.SkinLight;
import com.luna.ce.gui.skin.impl.SkinNumixish;

import java.util.ArrayList;
import java.util.List;

public class SkinManager {
    private static SkinManager instance;
    private final List<AbstractSkin> skins;
    private final AbstractSkin backupSkin = new SkinBackup();
    private AbstractSkin currentSkin;

    private SkinManager() {
        skins = new ArrayList<AbstractSkin>();
        registerSkin(new SkinNumixish());
        registerSkin(new SkinLight());

        currentSkin = skins.get(0);
    }

    public static SkinManager getInstance() {
        synchronized (SkinManager.class) {
            return instance == null ? instance = new SkinManager() : instance;
        }
    }

    public void registerSkin(final AbstractSkin skin) {
        getAvailableSkins().add(skin);
    }

    public List<AbstractSkin> getAvailableSkins() {
        synchronized (skins) {
            return skins;
        }
    }

    public AbstractSkin getCurrentSkin() {
        return currentSkin;
    }

    public void setCurrentSkin(AbstractSkin skin) {
        currentSkin = skin;
    }

    public AbstractSkin getBackupSkin() {
        return backupSkin;
    }
}