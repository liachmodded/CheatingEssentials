package com.luna.ce.util.gl.framebuffer;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.luna.lib.datastructures.Tuple;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FBOUtil {
    private static final Multimap<String, List<Integer>> fboNameIdMap = LinkedHashMultimap.create();
    private static int oldWidth = 0, oldHeight = 0;

    public static void generateNewFBO(String name) {
        int framebufferID, colorTextureID, depthRenderBufferID;
        GL11.glViewport(0, 0, 512, 512);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0F, 1.0F, 1.0F, 100.0F);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.5F);
        GL11.glClearDepth(1.0D);
        GL11.glDepthFunc(515);
        GL11.glEnable(2929);
        GL11.glShadeModel(7425);
        GL11.glHint(3152, 4354);
        framebufferID = EXTFramebufferObject.glGenFramebuffersEXT();
        colorTextureID = GL11.glGenTextures();
        depthRenderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        /*EXTFramebufferObject.glBindFramebufferEXT('\u8d40', framebufferID);
        GL11.glBindTexture(3553, colorTextureID);
        GL11.glTexParameterf(3553, 10241, 9729.0F);
        GL11.glTexImage2D(3553, 0, '\u8058', 512, 512, 0, 6408, 5124, (ByteBuffer) null);
        EXTFramebufferObject.glFramebufferTexture2DEXT('\u8d40', '\u8ce0', 3553, colorTextureID, 0);
        EXTFramebufferObject.glBindRenderbufferEXT('\u8d41', depthRenderBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT('\u8d41', '\u81a6', 512, 512);
        EXTFramebufferObject.glFramebufferRenderbufferEXT('\u8d40', '\u8d00', '\u8d41', depthRenderBufferID);
        EXTFramebufferObject.glBindFramebufferEXT('\u8d40', 0);*/
        EXTFramebufferObject.glBindFramebufferEXT(0x8d40, framebufferID);
        GL11.glBindTexture(3553, colorTextureID);
        GL11.glTexParameterf(3553, 10241, 9729.0F);
        GL11.glTexImage2D(3553, 0, 0x8058, 512, 512, 0, 6408, 5124, (ByteBuffer) null);
        EXTFramebufferObject.glFramebufferTexture2DEXT(0x8d40, 0xce0, 3553, colorTextureID, 0);
        EXTFramebufferObject.glBindRenderbufferEXT(0x8d41, depthRenderBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(0x8d41, 0x81a6, 512, 512);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(0x8d40, 0x8d00, 0x8d41, depthRenderBufferID);
        EXTFramebufferObject.glBindFramebufferEXT(0x8d40, 0);

        List<Integer> ids = new ArrayList<Integer>();
        ids.add(framebufferID);
        ids.add(colorTextureID);
        ids.add(depthRenderBufferID);

        fboNameIdMap.put(name, ids);
    }

    public static Tuple<String, List<Integer>> getFBOByName(String name) {
        List<Integer> ids = new ArrayList<Integer>();
        for (Map.Entry<String, List<Integer>> entry : fboNameIdMap.entries()) {
            if (entry.getKey().equals(name)) {
                return new Tuple<String, List<Integer>>(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }

    public static void bindFBO(String name) {
        EXTFramebufferObject.glBindFramebufferEXT(0x8d40, getFBOByName(name).getValue().get(0));
        oldWidth = Minecraft.getMinecraft().displayWidth;
        oldHeight = Minecraft.getMinecraft().displayHeight;
        GL11.glViewport(0, 0, 512, 512);
        GL11.glLoadIdentity();
    }

    public static void unbindFBO() {
        EXTFramebufferObject.glBindFramebufferEXT(0x8d40, 0);
        GL11.glViewport(0, 0, oldWidth, oldHeight);
    }

    public static int getFBOTexture(String name) {
        return getFBOByName(name).getValue().get(1);
    }
}
