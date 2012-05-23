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

package org.jdesktop.jxlayer;

import javax.swing.*;

/**
 * The default glassPane for the {@link JXLayer}.<br/>
 * It is the non-opaque panel with overridden {@link #contains(int, int)}
 * to enable custom cursors for inner components,<br/> 
 * for more details, see:
 * <p/>
 * <a href="http://weblogs.java.net/blog/alexfromsun/archive/2006/09/a_wellbehaved_g.html">A well-behaved GlassPane</a>
 */
public class JXGlassPane extends JPanel {
    /**
     * Creates a new {@link JXGlassPane}
     */
    public JXGlassPane() {
        setOpaque(false);
    }

    /**
     * If neither any mouseListeners is attached to this component
     * nor custom cursor is set, returns <code>false</false>,
     * otherwise calls the super implementation of this method. 
     *  
     * @param x the <i>x</i> coordinate of the point
     * @param y the <i>y</i> coordinate of the point
     * @return true if this component logically contains x,y
     */
    public boolean contains(int x, int y) {
        if (getMouseListeners().length == 0
                && getMouseMotionListeners().length == 0
                && getMouseWheelListeners().length == 0
                && !isCursorSet()) {
            return false;
        }
        return super.contains(x, y);
    }
}
