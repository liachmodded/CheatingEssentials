package com.luna.ce.util.gl;

import static net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.staticPlayerX;
import static net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.staticPlayerY;
import static net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.staticPlayerZ;

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
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }

    public static void drawBoundingBox(final AxisAlignedBB axisalignedbb) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer wr = tessellator.getWorldRenderer();
        wr.startDrawingQuads(); // starts x
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        tessellator.draw();
        wr.startDrawingQuads();
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        tessellator.draw(); // ends x
        wr.startDrawingQuads(); // starts y
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        tessellator.draw();
        wr.startDrawingQuads();
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        tessellator.draw(); // ends y
        wr.startDrawingQuads(); // starts z
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        tessellator.draw();
        wr.startDrawingQuads();
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        wr.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        tessellator.draw(); // ends z
    }

    /**
     * Draws lines for the edges of the bounding box.
     */
    public static void drawOutlinedBoundingBox(final AxisAlignedBB axisalignedbb) {
        final Tessellator var2 = Tessellator.getInstance();
        final WorldRenderer v3 = var2.getWorldRenderer();
        v3.startDrawing(3);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.draw();
        v3.startDrawing(3);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.draw();
        v3.startDrawing(1);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.minZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.maxX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.minY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.addVertex(axisalignedbb.minX - staticPlayerX, axisalignedbb.maxY
                - staticPlayerY, axisalignedbb.maxZ - staticPlayerZ);
        v3.draw();
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
            //Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
            cyl.draw((float) radius, (float) radius, (float) height, 32, 32);
            Minecraft.getMinecraft().entityRenderer.enableLightmap();
            GuiUtils.color(0xFFFFFFFF);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_DEPTH_TEST);
            glDisable(GL_COLOR_MATERIAL);
            glRotated(90, 1, 0, 0);
            glTranslated(-x, -y, -z);
        }
        glPopMatrix();
    }
}