package com.luna.ce.util.gl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public final class GLHelper {
    private static final Cylinder cyl;

    static {
        cyl = new Cylinder();
        cyl.setDrawStyle(GLU.GLU_FILL);
        cyl.setNormals(GLU.GLU_SMOOTH);
        cyl.setOrientation(GLU.GLU_OUTSIDE);
    }

    public static AxisAlignedBB getOffsetBB(double x, double y, double z, double l, double h, double w) {
        return AxisAlignedBB.fromBounds(x, y, z, x + l, y + h, z + w);
    }

    public static void drawESP(final AxisAlignedBB bb, final double r, final double g, final double b) {
        //Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(1.5F);
        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glColor4d(r, g, b, 0.1825F);
        drawBoundingBox(bb);
        glColor4d(r, g, b, 1.0F);
        drawOutlinedBoundingBox(bb);
        glLineWidth(2.0F);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_LIGHTING);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glPopMatrix();
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }

    public static void drawLines(final double x1, final double y1, final double z1, final double x2,
                                 final double y2, final double z2, final float width, int color) {
        //Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(width);
        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        GuiUtils.color(color);
        final Tessellator t = Tessellator.getInstance();
        final WorldRenderer wr = t.getWorldRenderer();
        wr.startDrawing(GL_LINES);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x2, y2, z2);
        t.draw();
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_LIGHTING);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glPopMatrix();
        Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
    }

    public static void drawBoundingBox(final AxisAlignedBB axisalignedbb) {
        final Tessellator tessellator = Tessellator.instance;
        final WorldRenderer wr = tessellator.getWorldRenderer();
        tessellator.startDrawingQuads(); // starts x
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.draw(); // ends x
        tessellator.startDrawingQuads(); // starts y
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.draw(); // ends y
        tessellator.startDrawingQuads(); // starts z
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        tessellator.draw(); // ends z
    }

    /**
     * Draws lines for the edges of the bounding box.
     */
    public static void drawOutlinedBoundingBox(final AxisAlignedBB axisalignedbb) {
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawing(3);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.draw();
        var2.startDrawing(3);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.draw();
        var2.startDrawing(1);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.minZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.maxX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.minY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.addVertex(axisalignedbb.minX - RenderManager.renderPosX, axisalignedbb.maxY
                - RenderManager.renderPosY, axisalignedbb.maxZ - RenderManager.renderPosZ);
        var2.draw();
    }

    public static void renderCylinder(double x, double y, double z, double radius, double height, int color) {
        glPushMatrix();
        {
            glTranslated(x, y, z);
            glRotated(-90, 1, 0, 0);
            glEnable(GL_COLOR_MATERIAL);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_DEPTH_TEST);
            GuiUtils.color(color);
            Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
            cyl.draw((float) radius, (float) radius, (float) height, 32, 32);
            Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
            GuiUtils.color(0xFFFFFFFF);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_DEPTH_TEST);
            glDisable(GL_COLOR_MATERIAL);
            glRotated(90, 1, 0, 0);
            glTranslated(-x, -y, -z);
        }
        glPopMatrix();
    }

    /**
     * Renders a two-dimensional box in three-dimensional space around an {@link net.minecraft.entity.Entity}
     * Based off of {@link net.minecraft.client.renderer.entity.Render#func_147906_a}, ie the nametag rendering method
     *
     * @param e     The entity to render the box around
     * @param color Color to render the box. Color is passed in in 0xRRGGBB
     *              format; alpha values are done automatically
     */
    @Deprecated
    public static void renderTwoDimensionalESP(Entity e, int color) {
        glPushMatrix();
        // Translate to the entities position
        glTranslated(
                // Add 0.5 for tile entities
                // There's gotta be a better way to do this, though...
                e.posX - RenderManager.renderPosX,
                e.posY - RenderManager.renderPosY + (e.height / 2),
                e.posZ - RenderManager.renderPosZ
        );
        glNormal3f(0.0F, 1.0F, 0.0F);
        // Rotate the ESP "plate" to always be facing the player
        glRotatef(-Minecraft.getMinecraft().thePlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
        glRotatef(Minecraft.getMinecraft().thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
        Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
        glDisable(GL_DEPTH_TEST);
        double extraDist = 0.2D;
        // Main rectangle
        // TODO: Add a parameter that 'toggles' this?
        GuiUtils.drawRect(-e.width, -e.height / 2 - extraDist, e.width * 2, e.height + extraDist * 2, (0x55 << 24) | color);

        // Bottom right
        GuiUtils.drawRect(-e.width, -e.height / 2 - extraDist, extraDist, extraDist / 10, (0xFF << 24) | color);
        GuiUtils.drawRect(-e.width, -e.height / 2 - extraDist, extraDist / 10, extraDist, (0xFF << 24) | color);
        // Bottom left
        GuiUtils.drawRect(e.width - extraDist * 1.1, -e.height / 2 - extraDist, extraDist, extraDist / 10, (0xFF << 24) | color);
        GuiUtils.drawRect(e.width - extraDist / 10, -e.height / 2 - extraDist, extraDist / 10, extraDist, (0xFF << 24) | color);
        // Top right
        GuiUtils.drawRect(-e.width, e.height / 2 + extraDist * 0.9, extraDist, extraDist / 10, (0xFF << 24) | color);
        GuiUtils.drawRect(-e.width, e.height / 2, extraDist / 10, extraDist, (0xFF << 24) | color);
        // Top left
        GuiUtils.drawRect(e.width - extraDist, e.height / 2 + extraDist * 0.9D, extraDist, extraDist / 10, (0xFF << 24) | color);
        GuiUtils.drawRect(e.width - extraDist / 10, e.height / 2, extraDist / 10, extraDist * 0.9D, (0xFF << 24) | color);

        glEnable(GL_DEPTH_TEST);
        Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
        glPopMatrix();
    }
}