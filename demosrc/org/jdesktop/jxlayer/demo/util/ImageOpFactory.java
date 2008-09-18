/**
 * Copyright (c) 2006-2008, Alexander Potochkin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the JXLayer project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jdesktop.jxlayer.demo.util;

import java.awt.color.*;
import java.awt.image.*;
import java.util.*;

public class ImageOpFactory {

    private ImageOpFactory() {
    }

    public static LookupOp getInvertColorOp() {
        return getInvertColorOp(true, true, true);
    }

    public static LookupOp getInvertColorOp(boolean red, boolean green, boolean blue) {
        byte[] invert = new byte[256];
        byte[] straight = new byte[256];
        for (int i = 0; i < invert.length; i++) {
            invert[i] = (byte) (255 - i);
            straight[i] = (byte) i;
        }
        byte[][] result = new byte[4][];
        Arrays.fill(result, straight);
        if (red) {
            result[0] = invert;
        }
        if (green) {
            result[1] = invert;
        }
        if (blue) {
            result[2] = invert;
        }
        result[3] = straight;
        // Wrapper is used as workaround for the buggy color convertion in *nix
        LookupTable table = new LookupTableWrapper(new ByteLookupTable(0, result));
        return new LookupOp(table, null);
    }

    public static LookupOp getPosterizeOp() {
        byte[] posterize = new byte[256];
        for (int i = 0; i < posterize.length; i++) {
            posterize[i] = (byte) (i - (i % 32));
//            posterize[i] = (byte) (i - (i % 256));
        }
        // Wrapper is used as workaround for the buggy color convertion in *nix
        LookupTable table = new LookupTableWrapper(new ByteLookupTable(0, posterize));
        return new LookupOp(table, null);
    }


    public static ColorConvertOp getGrayScaleOp() {
        return new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
    }

    public static RescaleOp getRescaleOp(float scaleFactor) {
        return new RescaleOp(scaleFactor, 0, null);
    }

    public static RescaleOp getRescaleOp(float scaleFactor, float offset) {
        return new RescaleOp(scaleFactor, offset, null);
    }

    public static RescaleOp getRescaleOp(float[] scaleFactor) {
        float offset[] = new float[scaleFactor.length];
        Arrays.fill(offset, 0);
        return new RescaleOp(scaleFactor, offset, null);
    }

    public static RescaleOp getRescaleOp(float[] scaleFactor, float[] offset) {
        return new RescaleOp(scaleFactor, offset, null);
    }

    public static ConvolveOp getConvolveOp(int i) {
        float[] kernel = new float[i * i];
        Arrays.fill(kernel, 1f / (i * i));
        return new ConvolveOp(new Kernel(i, i, kernel), ConvolveOp.EDGE_NO_OP, null);
    }
}
