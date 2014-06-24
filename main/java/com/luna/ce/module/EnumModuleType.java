package com.luna.ce.module;

public enum EnumModuleType {
    // TODO Add colors maybe?
    PLAYER(0x55FF55), WORLD(0xFF55FF), RENDER(0xFFFF55), MISC(0x5555FF);

    private final int col;

    private EnumModuleType(final int col) {
        this.col = col;
    }

    public String getRealName() {
        return name().substring(0, 1).toUpperCase().concat(name().substring(1).toLowerCase());
    }

    public int getColor() {
        return col;
    }
}
