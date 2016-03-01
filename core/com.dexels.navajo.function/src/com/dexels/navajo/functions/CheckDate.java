package com.dexels.navajo.functions;

import java.util.Calendar;

import com.dexels.navajo.parser.FunctionInterface;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version $Id$
 *
 *          DISCLAIMER
 *
 *          THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *          WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *          MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *          IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE LIABLE FOR ANY
 *          DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *          DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *          GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *          INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 *          IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *          OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 *          IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *          ====================================================================
 * 
 */

public final class CheckDate extends FunctionInterface {

    public CheckDate() {
    }

    @Override
    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperand(0);
        
        if (o instanceof java.util.Date) {
            // Year can't be greater than 9999 by default
            boolean limitYear = true;
            if (this.getOperand(1) != null) {
                limitYear = (Boolean) this.getOperand(1);
            }
            
            if (limitYear) {
                Calendar c = Calendar.getInstance();
                c.setTime((java.util.Date) o);
                return c.get(Calendar.YEAR) <= 9999;
            } 
            
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;

        }

    }

    @Override
    public String usage() {
        return "";
    }

    @Override
    public String remarks() {
        return "";
    }
}