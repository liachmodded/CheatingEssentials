package com.luna.ce.api;

import com.luna.ce.module.Module;

import java.util.HashSet;

public final class APIModuleSetup {
    private static final HashSet<Module> setupModules = new HashSet<Module>();

    public static void addModuleToSetupQueue(final Module mod) {
        synchronized (setupModules) {
            setupModules.add(mod);
        }
    }

    public static void setupModules() {
        synchronized (setupModules) {
            for (final Module e : setupModules) {
                e.initializeLater();
            }
        }
    }
}
