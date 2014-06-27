package com.luna.ce.util.gl;

import com.luna.ce.gui.font.FontHandler;
import com.luna.ce.gui.font.StringCache;
import com.luna.ce.util.FormatUtil;
import com.luna.ce.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL13.GL_SAMPLE_ALPHA_TO_COVERAGE;

public final class GuiUtils {
    public static final StringCache stringCache;

    static {
        final int[] colorCode = new int[32];
        for (int var5 = 0; var5 < 32; ++var5) {
            int var6 = (var5 >> 3 & 1) * 85;
            int var7 = (var5 >> 2 & 1) * 170 + var6;
            int var8 = (var5 >> 1 & 1) * 170 + var6;
            int var9 = (var5 & 1) * 170 + var6;

            if (var5 == 6) {
                var7 += 85;
            }

            /*if (anaglyph)
            {
                int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                int var11 = (var7 * 30 + var8 * 70) / 100;
                int var12 = (var7 * 30 + var9 * 70) / 100;
                var7 = var10;
                var8 = var11;
                var9 = var12;
            }*/

            if (var5 >= 16) {
                var7 /= 4;
                var8 /= 4;
                var9 /= 4;
            }

            colorCode[var5] = (var7 & 255) << 16 | (var8 & 255) << 8 | var9 & 255;
        }
        stringCache = new StringCache(colorCode);
        stringCache.setDefaultFont(FontHandler.getInstance().getFontName(), FontHandler.getInstance().getFontSize(), true);
    }

    public static void color(final int par4) {
        final float var10 = ((par4 >> 24) & 255) / 255.0F;
        final float var6 = ((par4 >> 16) & 255) / 255.0F;
        final float var7 = ((par4 >> 8) & 255) / 255.0F;
        final float var8 = (par4 & 255) / 255.0F;
        glColor4f(var6, var7, var8, var10);
    }

    public static void drawRect(final double x, final double y, final double w, final double h, final int col) {
        prepareColorDraw();
        color(col);

        final Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();

        tess.addVertex(x, y, 0);
        tess.addVertex(x, y + h, 0);
        tess.addVertex(x + w, y + h, 0);
        tess.addVertex(x + w, y, 0);

        tess.draw();

        endColorDraw();
    }

    public static void drawBorderedRect(final double x, final double y, final double w, final double h,
                                        final int col1, final int col2) {
        drawRect(x, y, w, h, col1);

        glScaled(0.5, 0.5, 0.5);
        drawRect((x * 2) - 0.5, (y * 2) - 1, 0.5, (h * 2) + 2, col2);
        drawRect((x * 2) + (w * 2), (y * 2) - 1, 1, (h * 2) + 2, col2);
        drawRect(x * 2, (y * 2) - 1, w * 2, 0.5, col2);
        drawRect(x * 2, (y * 2) + (h * 2), w * 2, 0.5, col2);
        glScaled(2, 2, 2);
    }

    public static void drawBorderRect(final double x, final double y, final double w, final double h,
                                      final int col1) {
        drawBorderedRect(x, y, w, h, 0x0, col1);
    }

    public static void drawGradientRect(final double x, final double y, final double w, final double h,
                                        final int col1, final int col2) {
        final float var7 = ((col1 >> 24) & 255) / 255.0F;
        final float var8 = ((col1 >> 16) & 255) / 255.0F;
        final float var9 = ((col1 >> 8) & 255) / 255.0F;
        final float var10 = (col1 & 255) / 255.0F;
        final float var11 = ((col2 >> 24) & 255) / 255.0F;
        final float var12 = ((col2 >> 16) & 255) / 255.0F;
        final float var13 = ((col2 >> 8) & 255) / 255.0F;
        final float var14 = (col2 & 255) / 255.0F;
        prepareColorDraw();
        final Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(x + w, y, 0);
        var15.addVertex(x, y, 0);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(x, y + h, 0);
        var15.addVertex(x + w, y + h, 0);
        var15.draw();
        endColorDraw();
    }

    public static void drawBorderedGradientRect(final double x, final double y, final double w,
                                                final double h, final int col1, final int col2, final int col3) {
        drawGradientRect(x, y, w, h, col1, col2);
        drawBorderRect(x, y, w, h, col3);
    }

    public static void prepareColorDraw() {
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glDisable(GL_ALPHA_TEST);
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_POLYGON_SMOOTH);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_SAMPLE_ALPHA_TO_COVERAGE);
    }

    public static void endColorDraw() {
        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_FLAT);
        glEnable(GL_ALPHA_TEST);
        glDisable(GL_MULTISAMPLE);
        glDisable(GL_LINE_SMOOTH);
        glDisable(GL_POINT_SMOOTH);
        glDisable(GL_POLYGON_SMOOTH);
        glDisable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        glPopMatrix();
    }

    public static int getWidth() {
        final ScaledResolution sr = getScaledResolution();
        return sr.getScaledWidth();
    }

    public static int getHeight() {
        final ScaledResolution sr = getScaledResolution();
        return sr.getScaledHeight();
    }

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(Minecraft.getMinecraft(),
                Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }

    public static void enableScissoring() {
        glEnable(GL_SCISSOR_TEST);
    }

    public static void scissor(final double x, final double y, final double w, final double h) {
        final ScaledResolution sr = getScaledResolution();
        final int factor = sr.getScaleFactor();

        final double x2 = x + w, y2 = y + h;

        glScissor((int) (x * factor), (int) ((sr.getScaledHeight() - y2) * factor),
                (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    public static void disableScissoring() {
        glDisable(GL_SCISSOR_TEST);
    }

    public static void drawString(String s, int x, int y, int col, boolean shadow) {
        drawString(stringCache, s, x, y, col, shadow);
    }

    public static void drawCenteredString(final String s, final double x, final double w, final double y,
                                          final double h, final int col, final boolean shadow) {
        drawCenteredString(stringCache, s, x, w, y, h, col, shadow);
    }

    public static void drawString(final StringCache stringCache, String s, final double x,
                                  final double y, final int col, final boolean shadow) {
        s = "&f" + s;
        glPushMatrix();
        {
            glTranslated(x, y, 0);
            if (shadow) {
                stringCache.renderString(FormatUtil.formatMessage(s), 1, 1, col, true);
                stringCache.renderString(FormatUtil.formatMessage(s), 0, 0, col, false);
            } else {
                stringCache.renderString(FormatUtil.formatMessage(s), 0, 0, col, false);
            }
        }
        glPopMatrix();
    }

    public static void drawCenteredString(final StringCache stringCache, final String s, final double x,
                                          final double w, final double y, final double h, final int col,
                                          final boolean shadow) {
        glPushMatrix();
        glTranslated((x + (w / 2)) -
                (stringCache.getStringWidth(s) / 2), (y + (h / 2))
                - (stringCache.getStringHeight() / 2), 0);
        drawString(stringCache, s, 0, 0, col, shadow);
        glPopMatrix();
    }

    public static void drawTriangle(double x, double y, double a, int col) {
        glPushMatrix();
        prepareColorDraw();
        color(col);
        Tessellator tess = Tessellator.instance;
        tess.startDrawing(GL_TRIANGLES);
        tess.addVertex(x, y + (a / 2), 0);
        tess.addVertex(x + (a / 2), y - (a / 2), 0);
        tess.addVertex(x - (a / 2), y - (a / 2), 0);
        tess.draw();
        endColorDraw();
        glPopMatrix();
    }

    public static void drawCircle(double x, double y, double r, int c) {
        drawFilledArc(x, y, r, c, 360);
    }

    public static void drawCircleBorder(double x, double y, double r, int c, float w) {
        drawArc(x, y, r, c, 360, w);
    }

    public static void drawFilledArc(double x, double y, double r, int c, int degrees) {
        prepareColorDraw();
        color(c);

        Tessellator tess = Tessellator.instance;

        tess.startDrawing(GL_POLYGON);
        for (int i = 0; i <= MathUtil.wrapTo(degrees, 1, 360); i++) {
            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
            double y2 = Math.cos(((i * Math.PI) / 180)) * r;
            tess.addVertex(x + x2, y + y2, 0);
        }
        tess.draw();
        endColorDraw();
    }

    public static void drawArc(double x, double y, double r, int c, int degrees, float width) {
        prepareColorDraw();
        color(c);

        glLineWidth(width);

        Tessellator tess = Tessellator.instance;

        tess.startDrawing(GL_LINE_LOOP);
        for (int i = 0; i <= MathUtil.wrapTo(degrees, 1, 360); i++) {
            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
            double y2 = Math.cos(((i * Math.PI) / 180)) * r;
            tess.addVertex(x + x2, y + y2, 0);
        }
        for (int i = MathUtil.wrapTo(degrees, 1, 360); i > 0; i--) {
            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
            double y2 = Math.cos(((i * Math.PI) / 180)) * r;
            tess.addVertex(x + x2, y + y2, 0);
        }
        tess.draw();

        glLineWidth(1.0F);
        endColorDraw();
    }
}