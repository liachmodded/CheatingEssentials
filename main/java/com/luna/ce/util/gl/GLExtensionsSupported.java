package com.luna.ce.util.gl;

import org.lwjgl.opengl.GLContext;

public class GLExtensionsSupported {
    public static final boolean GL_EXT_FRAMEBUFFER_OBJECT_SUPPORTED = GLContext.getCapabilities().GL_EXT_framebuffer_object;
}
