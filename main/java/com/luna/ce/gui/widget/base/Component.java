package com.luna.ce.gui.widget.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;

/**
 * Base for all components in the GUI API
 *
 * @author godshawk
 */
public abstract class Component {
    private final FontRenderer vanillaFontRenderer;

    {
        vanillaFontRenderer = new FontRenderer(Minecraft.getMinecraft().gameSettings, new ResourceLocation(
                "textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
    }

    /*
     * Variable declarations
     */
    private int x, y, w, h;
    private String text;
    private boolean visible;
    private Window parent;

    /*
     * Constructors
     */
    public Component() {
        this(100, 20);
    }

    public Component(final int w, final int h) {
        this("", w, h);
    }

    public Component(final String text, final int w, final int h) {
        this(text, w, h, true);
    }

    public Component(final String text, final int w, final int h, final boolean visible) {
        this.text = text;
        this.w = w;
        this.h = h;
        this.visible = visible;
    }

    protected static FontRenderer getFontRenderer() {
        return Minecraft.getMinecraft().fontRenderer;
    }

    /*
     * Getters
     */
    public String getText() {
        return text;
    }

    /*
     * Setters
     */
    public void setText(final String t) {
        text = t;
    }

    public int getX() {
        return x;
    }

    public void setX(final int e) {
        x = e;
    }

    public int getY() {
        return y;
    }

    public void setY(final int e) {
        y = e;
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(final int e) {
        w = e;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(final int e) {
        h = e;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(final boolean e) {
        visible = e;
    }

    public Window getParent() {
        return parent;
    }

	/*
	 * Abstract methods
	 */

    public void setParent(final Window w) {
        parent = w;
    }

    public abstract void draw(Window window, ISkin skin, int mouseX, int mouseY);

    public abstract void update();

    public abstract void drawExtra();

    /**
     * Always always ALWAYS check if mouseOver()!
     * <p/>
     * TODO Event based?
     */
    public abstract void mouseClicked(Window window, int mouseX, int mouseY, int button);
	
	/*
	 * Utility methods
	 */

    public abstract void keyTyped(Window window, int key, char c);

    /**
     * DarkStorm_'s utility method for calculating the location of the mouse.
     *
     * @return
     */
    protected Point calculateMouseLocation() {
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

    /**
     * Returns true if the mouse is hovering over this
     *
     * @return
     */
    public boolean mouseOver() {
        final Point p = calculateMouseLocation();
        if (p.x >= x) {
            if (p.x <= (x + w)) {
                if (p.y >= y) {
                    if (p.y <= (y + h)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Binds a texture
     *
     * @param path
     */
    protected void bindTexture(final String path) {
        getTextureManager().bindTexture(genResourceLocation(path));
    }

    /**
     * Generates a ResourceLocation for use with the new texture system
     *
     * @param path
     * @return
     */
    protected ResourceLocation genResourceLocation(final String path) {
        return new ResourceLocation(path);
    }

    /**
     * RenderEngine.java
     *
     * @return
     */
    protected TextureManager getTextureManager() {
        return Minecraft.getMinecraft().getTextureManager();
    }

    protected FontRenderer getVanillaFontRenderer() {
        return vanillaFontRenderer;
    }

    protected void playSound(final String sound) {
        Minecraft.getMinecraft().getSoundHandler()
                .playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(sound), 1.0F));
    }
}
