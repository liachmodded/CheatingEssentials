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