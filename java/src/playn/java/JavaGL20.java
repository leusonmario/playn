/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package playn.java;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL41;

/**
 * An implementation of the {@link GL20} interface based on Jogl. Note that Jogl
 * shaders and OpenGL ES shaders will not be 100% compatible. Some glGetXXX
 * methods are not implemented.
 *
 * Based on LIBGDX code by mzechner. Some code from LWJGL's GLES20.java
 *
 * @author mzechner
 */
final class JavaGL20 implements playn.core.gl.GL20 {

  // Sizes based on LWJGL's APIUtil
  private IntBuffer intBuffer = BufferUtils.createIntBuffer(32);
  private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(32);
  private ByteBuffer byteBuffer = BufferUtils.createByteBuffer(256);

  private void setIntBuffer(final int[] source, final int offset, final int length) {
    resizeIntBuffer(length);
    intBuffer.put(source, offset, length);
    intBuffer.rewind();
  }

  private void setFloatBuffer(final float[] source, final int offset, final int length) {
    resizeFloatBuffer(length);
    floatBuffer.put(source, offset, length);
    floatBuffer.rewind();
  }

  private void setByteBuffer(final byte[] source, final int offset, final int length) {
    resizeByteBuffer(length);
    byteBuffer.put(source, offset, length);
    byteBuffer.rewind();
  }

  private void resizeByteBuffer(final int length) {
    final int cap = byteBuffer.capacity();
    if (cap < length) {
      int newLength = cap << 1;
      while (newLength < length) {
        newLength <<= 1;
      }
      byteBuffer = BufferUtils.createByteBuffer(newLength);
    } else {
      byteBuffer.position(0);
    }
    byteBuffer.limit(length);
  }

  private void resizeIntBuffer(final int length) {
    final int cap = intBuffer.capacity();
    if (cap < length) {
      int newLength = cap << 1;
      while (newLength < length) {
        newLength <<= 1;
      }
      intBuffer = BufferUtils.createIntBuffer(newLength);
    } else {
      intBuffer.position(0);
    }
    intBuffer.limit(length);
  }

  private void resizeFloatBuffer(final int length) {
    final int cap = floatBuffer.capacity();
    if (cap < length) {
      int newLength = cap << 1;
      while (newLength < length) {
        newLength <<= 1;
      }
      floatBuffer = BufferUtils.createFloatBuffer(newLength);
    } else {
      floatBuffer.position(0);
    }
    floatBuffer.limit(length);
  }

  public void glActiveTexture(int texture) {
    GL13.glActiveTexture(texture);
  }

  @Override
  public void glAttachShader(int program, int shader) {
    GL20.glAttachShader(program, shader);
  }

  @Override
  public void glBindAttribLocation(int program, int index, String name) {
    GL20.glBindAttribLocation(program, index, name);
  }

  @Override
  public void glBindBuffer(int target, int buffer) {
    GL15.glBindBuffer(target, buffer);
  }

  @Override
  public void glBindFramebuffer(int target, int framebuffer) {
    EXTFramebufferObject.glBindFramebufferEXT(target, framebuffer);
  }

  @Override
  public void glBindRenderbuffer(int target, int renderbuffer) {
    EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
  }

  @Override
  public void glBindTexture(int target, int texture) {
    GL11.glBindTexture(target, texture);
  }

  @Override
  public void glBlendColor(float red, float green, float blue, float alpha) {
    GL14.glBlendColor(red, green, blue, alpha);
  }

  @Override
  public void glBlendEquation(int mode) {
    GL14.glBlendEquation(mode);
  }

  @Override
  public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
    GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
  }

  @Override
  public void glBlendFunc(int sfactor, int dfactor) {
    GL11.glBlendFunc(sfactor, dfactor);
  }

  @Override
  public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha,
                                  int dstAlpha) {
    GL14.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
  }

  @Override
  public void glBufferData(int target, int size, Buffer data, int usage) {
    if (data instanceof ByteBuffer)
      GL15.glBufferData(target, (ByteBuffer) data, usage);
    else if (data instanceof IntBuffer)
      GL15.glBufferData(target, (IntBuffer) data, usage);
    else if (data instanceof FloatBuffer)
      GL15.glBufferData(target, (FloatBuffer) data, usage);
    else if (data instanceof DoubleBuffer)
      GL15.glBufferData(target, (DoubleBuffer) data, usage);
    else if (data instanceof ShortBuffer) //
      GL15.glBufferData(target, (ShortBuffer) data, usage);
  }

  @Override
  public void glBufferSubData(int target, int offset, int size, Buffer data) {
    if (data instanceof ByteBuffer)
      GL15.glBufferSubData(target, offset, (ByteBuffer) data);
    else if (data instanceof IntBuffer)
      GL15.glBufferSubData(target, offset, (IntBuffer) data);
    else if (data instanceof FloatBuffer)
      GL15.glBufferSubData(target, offset, (FloatBuffer) data);
    else if (data instanceof DoubleBuffer)
      GL15.glBufferSubData(target, offset, (DoubleBuffer) data);
    else if (data instanceof ShortBuffer)
      GL15.glBufferSubData(target, offset, (ShortBuffer) data);
  }

  public int glCheckFramebufferStatus(int target) {
    return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
  }

  @Override
  public void glClear(int mask) {
    GL11.glClear(mask);
  }

  @Override
  public void glClearColor(float red, float green, float blue, float alpha) {
    GL11.glClearColor(red, green, blue, alpha);
  }

  @Override
  public void glClearDepthf(float depth) {
    GL11.glClearDepth(depth);
  }

  @Override
  public void glClearStencil(int s) {
    GL11.glClearStencil(s);
  }

  @Override
  public void glColorMask(boolean red, boolean green, boolean blue,
                          boolean alpha) {
    GL11.glColorMask(red, green, blue, alpha);
  }

  @Override
  public void glCompileShader(int shader) {
    GL20.glCompileShader(shader);
  }

  @Override public void glCompressedTexImage2D (int target, int level, int internalformat,
                                                int width, int height, int border,
                                                int imageSize, Buffer data) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override public void glCompressedTexSubImage2D (int target, int level, int xoffset, int yoffset,
                                                   int width, int height, int format,
                                                   int imageSize, Buffer data) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public void glCopyTexImage2D(int target, int level, int internalformat,
                               int x, int y, int width, int height, int border) {
    GL11.glCopyTexImage2D(target, level, internalformat, x, y, width,
                          height, border);
  }

  @Override
  public void glCopyTexSubImage2D(int target, int level, int xoffset,
                                  int yoffset, int x, int y, int width, int height) {
    GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width,
                             height);
  }

  @Override
  public int glCreateProgram() {
    return GL20.glCreateProgram();
  }

  @Override
  public int glCreateShader(int type) {
    return GL20.glCreateShader(type);
  }

  @Override
  public void glCullFace(int mode) {
    GL11.glCullFace(mode);
  }

  @Override
  public void glDeleteBuffers(int n, IntBuffer buffers) {
    GL15.glDeleteBuffers(buffers);
  }

  @Override
  public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
    EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffers);
  }

  @Override
  public void glDeleteProgram(int program) {
    GL20.glDeleteProgram(program);
  }

  @Override
  public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
    EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffers);
  }

  @Override
  public void glDeleteShader(int shader) {
    GL20.glDeleteShader(shader);
  }

  @Override
  public void glDeleteTextures(int n, IntBuffer textures) {
    GL11.glDeleteTextures(textures);
  }

  @Override
  public void glDepthFunc(int func) {
    GL11.glDepthFunc(func);
  }

  @Override
  public void glDepthMask(boolean flag) {
    GL11.glDepthMask(flag);
  }

  @Override
  public void glDepthRangef(float zNear, float zFar) {
    GL11.glDepthRange(zNear, zFar);
  }

  @Override
  public void glDetachShader(int program, int shader) {
    GL20.glDetachShader(program, shader);
  }

  @Override
  public void glDisable(int cap) {
    GL11.glDisable(cap);
  }

  @Override
  public void glDisableVertexAttribArray(int index) {
    GL20.glDisableVertexAttribArray(index);
  }

  @Override
  public void glDrawArrays(int mode, int first, int count) {
    GL11.glDrawArrays(mode, first, count);
  }

  @Override
  public void glDrawElements(int mode, int count, int type, Buffer indices) {
    if (indices instanceof ShortBuffer && type == GL_UNSIGNED_SHORT)
      GL11.glDrawElements(mode, (ShortBuffer) indices);
    else if (indices instanceof ByteBuffer && type == GL_UNSIGNED_SHORT)
      GL11.glDrawElements(mode, ((ByteBuffer) indices).asShortBuffer()); // FIXME
    // yay...
    else if (indices instanceof ByteBuffer && type == GL_UNSIGNED_BYTE)
      GL11.glDrawElements(mode, (ByteBuffer) indices);
    else
      throw new RuntimeException(
        "Can't use "
        + indices.getClass().getName()
        + " with this method. Use ShortBuffer or ByteBuffer instead. Blame LWJGL");
  }

  @Override
  public void glEnable(int cap) {
    GL11.glEnable(cap);
  }

  @Override
  public void glEnableVertexAttribArray(int index) {
    GL20.glEnableVertexAttribArray(index);
  }

  @Override
  public void glFinish() {
    GL11.glFinish();
  }

  @Override
  public void glFlush() {
    GL11.glFlush();
  }

  @Override
  public void glFramebufferRenderbuffer(int target, int attachment,
                                        int renderbuffertarget, int renderbuffer) {
    EXTFramebufferObject.glFramebufferRenderbufferEXT(
      target, attachment, renderbuffertarget, renderbuffer);
  }

  @Override
  public void glFramebufferTexture2D(int target, int attachment,
                                     int textarget, int texture, int level) {
    EXTFramebufferObject.glFramebufferTexture2DEXT(
      target, attachment, textarget, texture, level);
  }

  @Override
  public void glFrontFace(int mode) {
    GL11.glFrontFace(mode);
  }

  @Override
  public void glGenBuffers(int n, IntBuffer buffers) {
    GL15.glGenBuffers(buffers);
  }

  @Override
  public void glGenFramebuffers(int n, IntBuffer framebuffers) {
    EXTFramebufferObject.glGenFramebuffersEXT(framebuffers);
  }

  @Override
  public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
    EXTFramebufferObject.glGenRenderbuffersEXT(renderbuffers);
  }

  @Override
  public void glGenTextures(int n, IntBuffer textures) {
    GL11.glGenTextures(textures);
  }

  @Override
  public void glGenerateMipmap(int target) {
    EXTFramebufferObject.glGenerateMipmapEXT(target);
  }

  @Override
  public int glGetAttribLocation(int program, String name) {
    return GL20.glGetAttribLocation(program, name);
  }

  @Override
  public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
    GL15.glGetBufferParameter(target, pname, params);
  }

  @Override
  public int glGetError() {
    return GL11.glGetError();
  }

  @Override
  public void glGetFloatv(int pname, FloatBuffer params) {
    GL11.glGetFloat(pname, params);
  }

  @Override
  public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname,
                                                    IntBuffer params) {
    EXTFramebufferObject.glGetFramebufferAttachmentParameterEXT(
      target, attachment, pname, params);
  }

  @Override
  public void glGetIntegerv(int pname, IntBuffer params) {
    GL11.glGetInteger(pname, params);
  }

  @Override
  public String glGetProgramInfoLog(int program) {
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
    buffer.order(ByteOrder.nativeOrder());
    ByteBuffer tmp = ByteBuffer.allocateDirect(4);
    tmp.order(ByteOrder.nativeOrder());
    IntBuffer intBuffer = tmp.asIntBuffer();

    GL20.glGetProgramInfoLog(program, intBuffer, buffer);
    int numBytes = intBuffer.get(0);
    byte[] bytes = new byte[numBytes];
    buffer.get(bytes);
    return new String(bytes);
  }

  @Override
  public void glGetProgramiv(int program, int pname, IntBuffer params) {
    GL20.glGetProgram(program, pname, params);
  }

  @Override
  public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
    EXTFramebufferObject.glGetRenderbufferParameterEXT(
      target, pname, params);
  }

  @Override
  public String glGetShaderInfoLog(int shader) {
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
    buffer.order(ByteOrder.nativeOrder());
    ByteBuffer tmp = ByteBuffer.allocateDirect(4);
    tmp.order(ByteOrder.nativeOrder());
    IntBuffer intBuffer = tmp.asIntBuffer();

    GL20.glGetShaderInfoLog(shader, intBuffer, buffer);
    int numBytes = intBuffer.get(0);
    byte[] bytes = new byte[numBytes];
    buffer.get(bytes);
    return new String(bytes);
  }

  @Override
  public void glGetShaderPrecisionFormat(int shadertype, int precisiontype,
                                         IntBuffer range, IntBuffer precision) {
    glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
  }

  @Override
  public void glGetShaderiv(int shader, int pname, IntBuffer params) {
    GL20.glGetShader(shader, pname, params);
  }

  @Override
  public String glGetString(int name) {
    return GL11.glGetString(name);
  }

  @Override
  public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
    GL11.glGetTexParameter(target, pname, params);
  }

  @Override
  public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
    GL11.glGetTexParameter(target, pname, params);
  }

  @Override
  public int glGetUniformLocation(int program, String name) {
    return GL20.glGetUniformLocation(program, name);
  }

  @Override
  public void glGetUniformfv(int program, int location, FloatBuffer params) {
    GL20.glGetUniform(program, location, params);
  }

  @Override
  public void glGetUniformiv(int program, int location, IntBuffer params) {
    GL20.glGetUniform(program, location, params);
  }

  @Override
  public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
    GL20.glGetVertexAttrib(index, pname, params);
  }

  @Override
  public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
    GL20.glGetVertexAttrib(index, pname, params);
  }

  @Override
  public void glHint(int target, int mode) {
    GL11.glHint(target, mode);
  }

  @Override
  public boolean glIsBuffer(int buffer) {
    return GL15.glIsBuffer(buffer);
  }

  @Override
  public boolean glIsEnabled(int cap) {
    return GL11.glIsEnabled(cap);
  }

  @Override
  public boolean glIsFramebuffer(int framebuffer) {
    return EXTFramebufferObject.glIsFramebufferEXT(framebuffer);
  }

  @Override
  public boolean glIsProgram(int program) {
    return GL20.glIsProgram(program);
  }

  @Override
  public boolean glIsRenderbuffer(int renderbuffer) {
    return EXTFramebufferObject.glIsRenderbufferEXT(renderbuffer);
  }

  @Override
  public boolean glIsShader(int shader) {
    return GL20.glIsShader(shader);
  }

  @Override
  public boolean glIsTexture(int texture) {
    return GL11.glIsTexture(texture);
  }

  @Override
  public void glLineWidth(float width) {
    GL11.glLineWidth(width);
  }

  @Override
  public void glLinkProgram(int program) {
    GL20.glLinkProgram(program);
  }

  @Override
  public void glPixelStorei(int pname, int param) {
    GL11.glPixelStorei(pname, param);
  }

  @Override
  public void glPolygonOffset(float factor, float units) {
    GL11.glPolygonOffset(factor, units);
  }

  @Override
  public void glReadPixels(int x, int y, int width, int height, int format, int type,
                           Buffer pixels) {
    if (pixels instanceof ByteBuffer)
      GL11.glReadPixels(x, y, width, height, format, type,
                        (ByteBuffer) pixels);
    else if (pixels instanceof ShortBuffer)
      GL11.glReadPixels(x, y, width, height, format, type,
                        (ShortBuffer) pixels);
    else if (pixels instanceof IntBuffer)
      GL11.glReadPixels(x, y, width, height, format, type,
                        (IntBuffer) pixels);
    else if (pixels instanceof FloatBuffer)
      GL11.glReadPixels(x, y, width, height, format, type,
                        (FloatBuffer) pixels);
    else
      throw new RuntimeException(
        "Can't use " + pixels.getClass().getName() + " with this method. Use ByteBuffer, " +
        "ShortBuffer, IntBuffer or FloatBuffer instead. Blame LWJGL");
  }

  @Override public void glReleaseShaderCompiler () {
    // nothing to do here
  }

  @Override
  public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
    EXTFramebufferObject.glRenderbufferStorageEXT(target, internalformat, width, height);
  }

  @Override
  public void glSampleCoverage(float value, boolean invert) {
    GL13.glSampleCoverage(value, invert);
  }

  @Override
  public void glScissor(int x, int y, int width, int height) {
    GL11.glScissor(x, y, width, height);
  }

  @Override public void glShaderBinary (int n, IntBuffer shaders, int binaryformat, Buffer binary,
                                        int length) {
    throw new UnsupportedOperationException("unsupported, won't implement");
  }

  @Override
  public void glShaderSource(int shader, String string) {
    GL20.glShaderSource(shader, string);
  }

  @Override
  public void glStencilFunc(int func, int ref, int mask) {
    GL11.glStencilFunc(func, ref, mask);
  }

  @Override
  public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
    GL20.glStencilFuncSeparate(face, func, ref, mask);
  }

  @Override
  public void glStencilMask(int mask) {
    GL11.glStencilMask(mask);
  }

  @Override
  public void glStencilMaskSeparate(int face, int mask) {
    GL20.glStencilMaskSeparate(face, mask);
  }

  @Override
  public void glStencilOp(int fail, int zfail, int zpass) {
    GL11.glStencilOp(fail, zfail, zpass);
  }

  @Override
  public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
    GL20.glStencilOpSeparate(face, fail, zfail, zpass);
  }

  @Override
  public void glTexImage2D(int target, int level, int internalformat,
                           int width, int height, int border, int format, int type,
                           Buffer pixels) {
    if (pixels instanceof ByteBuffer || pixels == null)
      GL11.glTexImage2D(target, level, internalformat, width, height,
                        border, format, type, (ByteBuffer) pixels);
    else if (pixels instanceof ShortBuffer)
      GL11.glTexImage2D(target, level, internalformat, width, height,
                        border, format, type, (ShortBuffer) pixels);
    else if (pixels instanceof IntBuffer)
      GL11.glTexImage2D(target, level, internalformat, width, height,
                        border, format, type, (IntBuffer) pixels);
    else if (pixels instanceof FloatBuffer)
      GL11.glTexImage2D(target, level, internalformat, width, height,
                        border, format, type, (FloatBuffer) pixels);
    else if (pixels instanceof DoubleBuffer)
      GL11.glTexImage2D(target, level, internalformat, width, height,
                        border, format, type, (DoubleBuffer) pixels);
    else
      throw new RuntimeException(
        "Can't use " + pixels.getClass().getName() + " with this method. Use ByteBuffer, " +
        "ShortBuffer, IntBuffer, FloatBuffer or DoubleBuffer instead. Blame LWJGL");
  }

  @Override
  public void glTexParameterf(int target, int pname, float param) {
    GL11.glTexParameterf(target, pname, param);
  }

  @Override
  public void glTexParameterfv(int target, int pname, FloatBuffer params) {
    GL11.glTexParameter(target, pname, params);
  }

  @Override
  public void glTexParameteri(int target, int pname, int param) {
    GL11.glTexParameteri(target, pname, param);
  }

  @Override
  public void glTexParameteriv(int target, int pname, IntBuffer params) {
    GL11.glTexParameter(target, pname, params);
  }

  @Override
  public void glTexSubImage2D(int target, int level, int xoffset,
                              int yoffset, int width, int height, int format, int type,
                              Buffer pixels) {
    if (pixels instanceof ByteBuffer)
      GL11.glTexSubImage2D(target, level, xoffset, yoffset, width,
                           height, format, type, (ByteBuffer) pixels);
    else if (pixels instanceof ShortBuffer)
      GL11.glTexSubImage2D(target, level, xoffset, yoffset, width,
                           height, format, type, (ShortBuffer) pixels);
    else if (pixels instanceof IntBuffer)
      GL11.glTexSubImage2D(target, level, xoffset, yoffset, width,
                           height, format, type, (IntBuffer) pixels);
    else if (pixels instanceof FloatBuffer)
      GL11.glTexSubImage2D(target, level, xoffset, yoffset, width,
                           height, format, type, (FloatBuffer) pixels);
    else if (pixels instanceof DoubleBuffer)
      GL11.glTexSubImage2D(target, level, xoffset, yoffset, width,
                           height, format, type, (DoubleBuffer) pixels);
    else
      throw new RuntimeException(
        "Can't use " + pixels.getClass().getName() + " with this method. Use ByteBuffer, " +
        "ShortBuffer, IntBuffer, FloatBuffer or DoubleBuffer instead. Blame LWJGL");
  }

  @Override
  public void glUniform1f(int location, float x) {
    GL20.glUniform1f(location, x);
  }

  @Override
  public void glUniform1fv(int location, int count, FloatBuffer v) {
    GL20.glUniform1(location, v);
  }

  @Override
  public void glUniform1i(int location, int x) {
    GL20.glUniform1i(location, x);
  }

  @Override
  public void glUniform1iv(int location, int count, IntBuffer v) {
    GL20.glUniform1(location, v);
  }

  @Override
  public void glUniform2f(int location, float x, float y) {
    GL20.glUniform2f(location, x, y);
  }

  @Override
  public void glUniform2fv(int location, int count, FloatBuffer v) {
    GL20.glUniform2(location, v);
  }

  @Override
  public void glUniform2i(int location, int x, int y) {
    GL20.glUniform2i(location, x, y);
  }

  @Override
  public void glUniform2iv(int location, int count, IntBuffer v) {
    GL20.glUniform2(location, v);
  }

  @Override
  public void glUniform3f(int location, float x, float y, float z) {
    GL20.glUniform3f(location, x, y, z);
  }

  @Override
  public void glUniform3fv(int location, int count, FloatBuffer v) {
    GL20.glUniform3(location, v);
  }

  @Override
  public void glUniform3i(int location, int x, int y, int z) {
    GL20.glUniform3i(location, x, y, z);
  }

  @Override
  public void glUniform3iv(int location, int count, IntBuffer v) {
    GL20.glUniform3(location, v);
  }

  @Override
  public void glUniform4f(int location, float x, float y, float z, float w) {
    GL20.glUniform4f(location, x, y, z, w);
  }

  @Override
  public void glUniform4fv(int location, int count, FloatBuffer v) {
    GL20.glUniform4(location, v);
  }

  @Override
  public void glUniform4i(int location, int x, int y, int z, int w) {
    GL20.glUniform4i(location, x, y, z, w);
  }

  @Override
  public void glUniform4iv(int location, int count, IntBuffer v) {
    GL20.glUniform4(location, v);
  }

  @Override
  public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
    GL20.glUniformMatrix2(location, transpose, value);
  }

  @Override
  public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
    GL20.glUniformMatrix3(location, transpose, value);
  }

  @Override
  public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
    GL20.glUniformMatrix4(location, transpose, value);
  }

  @Override
  public void glUseProgram(int program) {
    GL20.glUseProgram(program);
  }

  @Override
  public void glValidateProgram(int program) {
    GL20.glValidateProgram(program);
  }

  @Override
  public void glVertexAttrib1f(int indx, float x) {
    GL20.glVertexAttrib1f(indx, x);
  }

  @Override
  public void glVertexAttrib1fv(int indx, FloatBuffer values) {
    GL20.glVertexAttrib1f(indx, values.get());
  }

  @Override
  public void glVertexAttrib2f(int indx, float x, float y) {
    GL20.glVertexAttrib2f(indx, x, y);
  }

  @Override
  public void glVertexAttrib2fv(int indx, FloatBuffer values) {
    GL20.glVertexAttrib2f(indx, values.get(), values.get());
  }

  @Override
  public void glVertexAttrib3f(int indx, float x, float y, float z) {
    GL20.glVertexAttrib3f(indx, x, y, z);
  }

  @Override
  public void glVertexAttrib3fv(int indx, FloatBuffer values) {
    GL20.glVertexAttrib3f(indx, values.get(), values.get(), values.get());
  }

  @Override
  public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
    GL20.glVertexAttrib4f(indx, x, y, z, w);
  }

  @Override
  public void glVertexAttrib4fv(int indx, FloatBuffer values) {
    GL20.glVertexAttrib4f(indx, values.get(), values.get(), values.get(),
                          values.get());
  }

  @Override
  public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride,
                                    Buffer ptr) {
    // GL20.glVertexAttribPointer(indx, size, type, normalized, stride,
    // BufferUtils.getOffset(ptr));
    if (ptr instanceof FloatBuffer) {
      GL20.glVertexAttribPointer(indx, size, normalized, stride, (FloatBuffer) ptr);
    } else if (ptr instanceof ByteBuffer) {
      switch (type) {
      case GL_BYTE:
        GL20.glVertexAttribPointer(indx, size, false, normalized, stride, ((ByteBuffer) ptr));
        break;
      case GL_FLOAT:
        GL20.glVertexAttribPointer(indx, size, normalized, stride,
                                   ((ByteBuffer) ptr).asFloatBuffer());
        break;
      case GL_UNSIGNED_BYTE:
        GL20.glVertexAttribPointer(indx, size, true, normalized, stride, (ByteBuffer) ptr);
        break;
      case GL_UNSIGNED_SHORT:
        GL20.glVertexAttribPointer(indx, size, true, normalized, stride, (ByteBuffer) ptr);
        break;
      case GL_SHORT:
        GL20.glVertexAttribPointer(indx, size, false, normalized, stride, (ByteBuffer) ptr);
        break;
      default:
        throw new RuntimeException("NYI for type " + Integer.toHexString(type));
      }
    } else if (ptr instanceof ShortBuffer) {
      throw new RuntimeException("LWJGL does not support short buffers in glVertexAttribPointer.");
    } else {
      throw new RuntimeException("NYI for " + ptr.getClass());
    }
  }

  @Override
  public void glViewport(int x, int y, int width, int height) {
    GL11.glViewport(x, y, width, height);
  }

  @Override
  public void glDrawElements(int mode, int count, int type, int indices) {
    GL11.glDrawElements(mode, count, type, indices);
  }

  @Override
  public void glVertexAttribPointer(int indx, int size, int type,
                                    boolean normalized, int stride, int ptr) {
    GL20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
  }

  @Override
  public String getPlatformGLExtensions() {
    throw new UnsupportedOperationException("NYI - not in LWJGL.");
  }

  @Override
  public int getSwapInterval() {
    throw new UnsupportedOperationException("NYI - not in LWJGL.");
  }

  @Override
  public void glClearDepth(double depth) {
    GL11.glClearDepth(depth);
  }

  @Override
  public void glCompressedTexImage2D(int target, int level, int internalformat,
                                     int width, int height, int border,
                                     int data_imageSize, int data) {
    GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, data_imageSize, data);
  }

  @Override
  public void glCompressedTexImage3D(int target, int level, int internalformat,
                                     int width, int height, int depth, int border,
                                     int imageSize, Buffer data) {
    GL13.glCompressedTexImage3D(target, level, internalformat, width, height, depth, border,
                                imageSize, MemoryUtil.getAddress((ByteBuffer) data));
  }

  @Override
  public void glCompressedTexImage3D(int target, int level, int internalformat,
                                     int width, int height, int depth, int border,
                                     int imageSize, int data) {
    GL13.glCompressedTexImage3D(
      target, level, internalformat, width, height, depth, border, imageSize, data);
  }

  @Override
  public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset,
                                        int width, int height, int format, int data_imageSize,
                                        int data) {
    GL13.glCompressedTexSubImage2D(
      target, level, xoffset, yoffset, width, height, format, data_imageSize, data);
  }

  @Override
  public void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset,
                                        int width, int height, int depth,
                                        int format, int imageSize, Buffer data) {
    // imageSize is calculated in glCompressedTexSubImage3D.
    GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset,
                                   width, height, depth, format, (ByteBuffer) data);
  }

  @Override
  public void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset,
                                        int width, int height, int depth,
                                        int format, int imageSize, int data) {
    final ByteBuffer dataBuffer = BufferUtils.createByteBuffer(4);
    dataBuffer.putInt(data);
    dataBuffer.rewind();
    // imageSize is calculated in glCompressedTexSubImage3D.
    GL13.glCompressedTexSubImage3D(
      target, level, xoffset, yoffset, zoffset, width, height, depth, format, dataBuffer);
  }

  @Override
  public void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset,
                                  int x, int y, int width, int height) {
    GL12.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
  }

  @Override
  public void glDeleteBuffers(int n, int[] buffers, int offset) {
    setIntBuffer(buffers, offset, n);
    GL15.glDeleteBuffers(intBuffer);
  }

  @Override
  public void glDeleteFramebuffers(int n, int[] framebuffers, int offset) {
    setIntBuffer(framebuffers, offset, n);
    EXTFramebufferObject.glDeleteFramebuffersEXT(intBuffer);
  }

  @Override
  public void glDeleteRenderbuffers(int n, int[] renderbuffers, int offset) {
    setIntBuffer(renderbuffers, offset, n);
    EXTFramebufferObject.glDeleteRenderbuffersEXT(intBuffer);
  }

  @Override
  public void glDeleteTextures(int n, int[] textures, int offset) {
    setIntBuffer(textures, offset, n);
    GL11.glDeleteTextures(intBuffer);
  }

  @Override
  public void glDepthRange(double zNear, double zFar) {
    GL11.glDepthRange(zNear, zFar);
  }

  @Override
  public void glFramebufferTexture3D(int target, int attachment, int textarget, int texture,
                                     int level, int zoffset) {
    GL30.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
  }

  @Override
  public void glGenBuffers(int n, int[] buffers, int offset) {
    resizeIntBuffer(n);
    GL15.glGenBuffers(intBuffer);
    intBuffer.get(buffers, offset, n);
  }

  @Override
  public void glGenFramebuffers(int n, int[] framebuffers, int offset) {
    resizeIntBuffer(n);
    EXTFramebufferObject.glGenFramebuffersEXT(intBuffer);
    intBuffer.get(framebuffers, offset, n);
  }

  @Override
  public void glGenRenderbuffers(int n, int[] renderbuffers, int offset) {
    resizeIntBuffer(n);
    EXTFramebufferObject.glGenRenderbuffersEXT(intBuffer);
    intBuffer.get(renderbuffers, offset, n);
  }

  @Override
  public void glGenTextures(int n, int[] textures, int offset) {
    resizeIntBuffer(n);
    GL11.glGenTextures(intBuffer);
    intBuffer.get(textures, offset, n);
  }

  @Override
  public void glGetActiveAttrib(int program, int index, int bufsize, int[] length, int lengthOffset,
                                int[] size, int sizeOffset, int[] type, int typeOffset,
                                byte[] name, int nameOffset) {
    // http://www.khronos.org/opengles/sdk/docs/man/xhtml/glGetActiveAttrib.xml
    // Returns length, size, type, name
    resizeIntBuffer(2);

    // Return name, length
    final String nameString = GL20.glGetActiveAttrib(program, index, bufsize, intBuffer);
    try {
      final byte[] nameBytes = nameString.getBytes("UTF-8");
      final int nameLength = nameBytes.length - nameOffset;
      setByteBuffer(nameBytes, nameOffset, nameLength);
      byteBuffer.get(name, nameOffset, nameLength);
      length[lengthOffset] = nameLength;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    // Return size, type
    intBuffer.get(size, 0, 1);
    intBuffer.get(type, 0, 1);
  }

  @Override
  public void glGetActiveAttrib(int program, int index, int bufsize,
                                IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
    IntBuffer typeTmp = BufferUtils.createIntBuffer(2);
    GL20.glGetActiveAttrib(program, index, 256, typeTmp);
    type.put(typeTmp.get(0));
    type.rewind();
  }

  @Override
  public void glGetActiveUniform(int program, int index, int bufsize,
                                 int[] length, int lengthOffset, int[] size, int sizeOffset,
                                 int[] type, int typeOffset, byte[] name, int nameOffset) {
    resizeIntBuffer(2);

    // Return name, length
    final String nameString = GL20.glGetActiveUniform(program, index, 256, intBuffer);
    try {
      final byte[] nameBytes = nameString.getBytes("UTF-8");
      final int nameLength = nameBytes.length - nameOffset;
      setByteBuffer(nameBytes, nameOffset, nameLength);
      byteBuffer.get(name, nameOffset, nameLength);
      length[lengthOffset] = nameLength;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    // Return size, type
    intBuffer.get(size, 0, 1);
    intBuffer.get(type, 0, 1);
  }

  @Override
  public void glGetActiveUniform(int program, int index, int bufsize, IntBuffer length,
                                 IntBuffer size, IntBuffer type, ByteBuffer name) {
    IntBuffer typeTmp = BufferUtils.createIntBuffer(2);
    GL20.glGetActiveAttrib(program, index, 256, typeTmp);
    type.put(typeTmp.get(0));
    type.rewind();
  }

  @Override
  public void glGetAttachedShaders(int program, int maxcount, int[] count, int countOffset,
                                   int[] shaders, int shadersOffset) {
    final int countLength = count.length - countOffset;
    resizeIntBuffer(countLength);

    final int shadersLength = shaders.length - shadersOffset;
    final IntBuffer intBuffer2 = BufferUtils.createIntBuffer(shadersLength);
    GL20.glGetAttachedShaders(program, intBuffer, intBuffer2);

    // Return count, shaders
    intBuffer.get(count, countOffset, countLength);
    intBuffer2.get(shaders, shadersOffset, shadersLength);
  }

  @Override
  public void glGetAttachedShaders(int program, int maxcount, IntBuffer count, IntBuffer shaders) {
    GL20.glGetAttachedShaders(program, count, shaders);
  }

  @Override
  public boolean glGetBoolean(int pname) {
    resizeByteBuffer(16); // LWJGL wants room for 16 values
    GL11.glGetBoolean(pname, byteBuffer);
    return byteBuffer.get(0) != GL_FALSE;
  }

  @Override
  public void glGetBooleanv(int pname, byte[] params, int offset) {
    final int length = params.length - offset;
    resizeByteBuffer(length);
    GL11.glGetBoolean(pname, byteBuffer);
    byteBuffer.get(params, offset, length);
  }

  @Override
  public void glGetBooleanv(int pname, ByteBuffer params) {
    GL11.glGetBoolean(pname, params);
  }

  @Override
  public int glGetBoundBuffer(int arg0) {
    throw new UnsupportedOperationException("glGetBoundBuffer not supported in GLES 2.0 or LWJGL.");
  }

  @Override
  public void glGetBufferParameteriv(int target, int pname, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    GL15.glGetBufferParameter(target, pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public float glGetFloat(int pname) {
    resizeFloatBuffer(16); // LWJGL wants room for 16 values
    GL11.glGetFloat(pname, floatBuffer);
    return floatBuffer.get(0);
  }

  @Override
  public void glGetFloatv(int pname, float[] params, int offset) {
    final int length = params.length - offset;
    resizeFloatBuffer(length);
    GL11.glGetFloat(pname, floatBuffer);
    floatBuffer.get(params, offset, length);
  }

  @Override
  public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname,
                                                    int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    EXTFramebufferObject.glGetFramebufferAttachmentParameterEXT(
      target, attachment, pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public int glGetInteger(int pname) {
    resizeIntBuffer(16); // LWJGL wants room for 16 values
    GL11.glGetInteger(pname, intBuffer);
    return intBuffer.get(0);
  }

  @Override
  public void glGetIntegerv(int pname, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    GL11.glGetInteger(pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public void glGetProgramBinary(int program, int bufsize, int[] length, int lengthOffset,
                                 int[] binaryformat, int binaryformatOffset, Buffer binary) {
    final int lengthLength = bufsize - lengthOffset;
    resizeIntBuffer(lengthLength);

    final int binaryformatLength = bufsize - binaryformatOffset;
    final IntBuffer intBuffer2 = BufferUtils.createIntBuffer(binaryformatLength);
    GL41.glGetProgramBinary(program, intBuffer, intBuffer2, (ByteBuffer) binary);

    // Return length, binaryformat
    intBuffer.get(length, lengthOffset, lengthLength);
    intBuffer2.get(binaryformat, binaryformatOffset, binaryformatLength);
  }

  @Override
  public void glGetProgramBinary(int program, int bufSize, IntBuffer length,
                                 IntBuffer binaryFormat, Buffer binary) {
    GL41.glGetProgramBinary(program, length, binaryFormat, (ByteBuffer) binary);
  }

  @Override
  public void glGetProgramInfoLog(int program, int bufsize, int[] length, int lengthOffset,
                                  byte[] infolog, int infologOffset) {
    final int intLength = length.length - lengthOffset;
    resizeIntBuffer(intLength);

    final int byteLength = bufsize - infologOffset;
    resizeByteBuffer(byteLength);

    GL20.glGetProgramInfoLog(program, intBuffer, byteBuffer);
    // length is the length of the infoLog string being returned
    intBuffer.get(length, lengthOffset, intLength);
    // infoLog is the char array of the infoLog
    byteBuffer.get(infolog, byteLength, infologOffset);
  }

  @Override
  public void glGetProgramInfoLog(int program, int bufsize, IntBuffer length, ByteBuffer infolog) {
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);
    buffer.order(ByteOrder.nativeOrder());
    ByteBuffer tmp = ByteBuffer.allocateDirect(4);
    tmp.order(ByteOrder.nativeOrder());
    IntBuffer intBuffer = tmp.asIntBuffer();
    GL20.glGetProgramInfoLog(program, intBuffer, buffer);
  }

  @Override
  public void glGetProgramiv(int program, int pname, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    GL20.glGetProgram(program, pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public void glGetRenderbufferParameteriv(int target, int pname, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    EXTFramebufferObject.glGetRenderbufferParameterEXT(target, pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public void glGetShaderInfoLog(int shader, int bufsize, int[] length, int lengthOffset,
                                 byte[] infolog, int infologOffset) {
    final int intLength = length.length - lengthOffset;
    resizeIntBuffer(intLength);
    final int byteLength = bufsize - infologOffset;
    resizeByteBuffer(byteLength);
    GL20.glGetShaderInfoLog(shader, intBuffer, byteBuffer);
    // length is the length of the infoLog string being returned
    intBuffer.get(length, lengthOffset, intLength);
    // infoLog is the char array of the infoLog
    byteBuffer.get(infolog, byteLength, infologOffset);
  }

  @Override
  public void glGetShaderInfoLog(int shader, int bufsize, IntBuffer length, ByteBuffer infolog) {
    GL20.glGetShaderInfoLog(shader, length, infolog);
  }

  @Override
  public void glGetShaderiv(int shader, int pname, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    GL20.glGetShader(shader, pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public void glGetShaderPrecisionFormat(int shadertype, int precisiontype,
                                         int[] range, int rangeOffset,
                                         int[] precision, int precisionOffset) {
    throw new UnsupportedOperationException("NYI");
  }

  @Override
  public void glGetShaderSource(int shader, int bufsize, int[] length, int lengthOffset,
                                byte[] source, int sourceOffset) {
    throw new UnsupportedOperationException("NYI");
  }

  @Override
  public void glGetShaderSource(int shader, int bufsize, IntBuffer length, ByteBuffer source) {
    throw new UnsupportedOperationException("NYI");
  }

  @Override
  public void glGetTexParameterfv(int target, int pname, float[] params, int offset) {
    final int length = params.length - offset;
    resizeFloatBuffer(length);
    GL11.glGetTexParameter(target, pname, floatBuffer);
    floatBuffer.get(params, offset, length);
  }

  @Override
  public void glGetTexParameteriv(int target, int pname, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    GL11.glGetTexParameter(target, pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public void glGetUniformfv(int program, int location, float[] params, int offset) {
    final int length = params.length - offset;
    resizeFloatBuffer(length);
    GL20.glGetUniform(program, location, floatBuffer);
    floatBuffer.get(params, offset, length);
  }

  @Override
  public void glGetUniformiv(int program, int location, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    GL20.glGetUniform(program, location, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public void glGetVertexAttribfv(int index, int pname, float[] params, int offset) {
    final int length = params.length - offset;
    resizeFloatBuffer(length);
    GL20.glGetVertexAttrib(index, pname, floatBuffer);
    floatBuffer.get(params, offset, length);
  }

  @Override
  public void glGetVertexAttribiv(int index, int pname, int[] params, int offset) {
    final int length = params.length - offset;
    resizeIntBuffer(length);
    GL20.glGetVertexAttrib(index, pname, intBuffer);
    intBuffer.get(params, offset, length);
  }

  @Override
  public boolean glIsVBOArrayEnabled() {
    throw new UnsupportedOperationException("NYI - not in LWJGL.");
  }

  @Override
  public boolean glIsVBOElementEnabled() {
    throw new UnsupportedOperationException("NYI - not in LWJGL.");
  }

  @Override
  public ByteBuffer glMapBuffer(int target, int access) {
    return GL15.glMapBuffer(target, access, null);
  }

  @Override
  public void glProgramBinary(int program, int binaryFormat, Buffer binary, int length) {
    // Length is calculated in glProgramBinary.
    GL41.glProgramBinary(program, binaryFormat, (ByteBuffer) binary);
  }

  @Override
  public void glReadPixels(int x, int y, int width, int height, int format, int type,
                           int pixelsBufferOffset) {
    GL11.glReadPixels(x, y, width, height, format, type, pixelsBufferOffset);
  }

  @Override
  public void glShaderBinary(int n, int[] shaders, int offset, int binaryformat,
                             Buffer binary, int length) {
    throw new UnsupportedOperationException("NYI");
  }

  @Override
  public void glShaderSource(int shader, int count, String[] strings, int[] length, int lengthOff) {
    for (final String str : strings)
      GL20.glShaderSource(shader, str);
  }

  @Override
  public void glShaderSource(int shader, int count, String[] strings, IntBuffer length) {
    for (final String str : strings)
      GL20.glShaderSource(shader, str);
  }

  @Override
  public void glTexImage2D(int target, int level, int internalformat, int width, int height,
                           int border, int format, int type, int pixels) {
    GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
  }

  @Override
  public void glTexImage3D(int arg0, int arg1, int arg2, int arg3, int arg4,
                           int arg5, int arg6, int arg7, int arg8, Buffer arg9) {
    if (!(arg9 instanceof ByteBuffer))
      throw new UnsupportedOperationException("Buffer must be a ByteBuffer.");
    GL12.glTexImage3D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, (ByteBuffer) arg9);
  }

  @Override
  public void glTexImage3D(int arg0, int arg1, int arg2, int arg3, int arg4,
                           int arg5, int arg6, int arg7, int arg8, int arg9) {
    GL12.glTexImage3D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }

  @Override
  public void glTexParameterfv(int target, int pname, float[] params, int offset) {
    final int length = params.length - offset;
    setFloatBuffer(params, offset, length);
    GL11.glTexParameter(target, pname, floatBuffer);
  }

  @Override
  public void glTexParameteriv(int target, int pname, int[] params, int offset) {
    final int length = params.length - offset;
    setIntBuffer(params, offset, length);
    GL11.glTexParameter(target, pname, intBuffer);
  }

  @Override
  public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format,
                              int type, int pixels) {
    GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
  }

  @Override
  public void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset,
                              int width, int height, int depth, int format, int type,
                              Buffer pixels) {
    GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format,
                         type, (ByteBuffer) pixels);
  }

  @Override
  public void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset,
                              int width, int height, int depth, int format, int type, int pixels) {
    final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(1);
    byteBuffer.putInt(pixels);
    byteBuffer.rewind();
    GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format,
                         type, byteBuffer);
  }

  @Override
  public void glUniform1fv(int location, int count, float[] v, int offset) {
    setFloatBuffer(v, offset, count);
    GL20.glUniform1(location, floatBuffer);
  }

  @Override
  public void glUniform1iv(int location, int count, int[] v, int offset) {
    setIntBuffer(v, offset, count);
    GL20.glUniform1(location, intBuffer);
  }

  @Override
  public void glUniform2fv(int location, int count, float[] v, int offset) {
    setFloatBuffer(v, offset, count);
    GL20.glUniform2(location, floatBuffer);
  }

  @Override
  public void glUniform2iv(int location, int count, int[] v, int offset) {
    setIntBuffer(v, offset, count);
    GL20.glUniform2(location, intBuffer);
  }

  @Override
  public void glUniform3fv(int location, int count, float[] v, int offset) {
    setFloatBuffer(v, offset, count);
    GL20.glUniform3(location, floatBuffer);
  }

  @Override
  public void glUniform3iv(int location, int count, int[] v, int offset) {
    setIntBuffer(v, offset, count);
    GL20.glUniform3(location, intBuffer);
  }

  @Override
  public void glUniform4fv(int location, int count, float[] v, int offset) {
    setFloatBuffer(v, offset, count);
    GL20.glUniform4(location, floatBuffer);
  }

  @Override
  public void glUniform4iv(int location, int count, int[] v, int offset) {
    setIntBuffer(v, offset, count);
    GL20.glUniform4(location, intBuffer);
  }

  @Override
  public void glUniformMatrix2fv(int location, int count, boolean transpose,
                                 float[] value, int offset) {
    setFloatBuffer(value, offset, count);
    GL20.glUniformMatrix2(location, transpose, floatBuffer);
  }

  @Override
  public void glUniformMatrix3fv(int location, int count, boolean transpose,
                                 float[] value, int offset) {
    setFloatBuffer(value, offset, count);
    // glUniformMatrix3 calls nglUniformMatrix3fv
    GL20.glUniformMatrix3(location, transpose, floatBuffer);
  }

  @Override
  public void glUniformMatrix4fv(int location, int count, boolean transpose,
                                 float[] value, int offset) {
    setFloatBuffer(value, offset, count);
    GL20.glUniformMatrix4(location, transpose, floatBuffer);
  }

  @Override
  public boolean glUnmapBuffer(int target) {
    return GL15.glUnmapBuffer(target);
  }

  @Override
  public void glVertexAttrib1fv(int indx, float[] values, int offset) {
    GL20.glVertexAttrib1f(indx, values[indx + offset]);
  }

  @Override
  public void glVertexAttrib2fv(int indx, float[] values, int offset) {
    GL20.glVertexAttrib2f(indx, values[indx + offset], values[indx + 1 + offset]);
  }

  @Override
  public void glVertexAttrib3fv(int indx, float[] values, int offset) {
    GL20.glVertexAttrib3f(indx, values[indx + offset], values[indx + 1 + offset],
                          values[indx + 2 + offset]);
  }

  @Override
  public void glVertexAttrib4fv(int indx, float[] values, int offset) {
    GL20.glVertexAttrib4f(indx, values[indx + offset], values[indx + 1 + offset],
                          values[indx + 2 + offset], values[indx + 3 + offset]);
  }

  @Override
  public boolean hasGLSL() {
    throw new UnsupportedOperationException("NYI - not in LWJGL.");
  }

  @Override
  public boolean isExtensionAvailable(String extension) {
    throw new UnsupportedOperationException("NYI - not in LWJGL.");
  }

  @Override
  public boolean isFunctionAvailable(String function) {
    throw new UnsupportedOperationException("NYI - not in LWJGL.");
  }
}
