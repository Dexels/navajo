import java.util.StringTokenizer;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

public class Replace extends FunctionInterface {

	public String remarks() {
		return "Replaces the content of a binary or a string with a specified character sequence";
	}

	public String usage() {
		return "Replace(String|Binary, String fromSequence, String toSequence): Binary|String";
	}

	public Object evaluate() throws TMLExpressionException {
		
		Binary b = null;
		String s = null;
		int [] fromSequence;
		int [] toSequence;
		
		if (getOperands().size() != 3) {
			return new TMLExpressionException(this, "Wrong number of arguments");
		}
		
		if (getOperand(0) instanceof Binary) {
			b = (Binary) getOperand(0);
		} else if (getOperand(0) instanceof String) {
			s = (String) getOperand(0);
		} else {
			return new TMLExpressionException(this, "Binary or String content expected");
		}
		
		if (!(getOperand(1) instanceof String && getOperand(2) instanceof String)) {
			return new TMLExpressionException(this, "String arguments expected");
		}
		
		StringTokenizer from = new StringTokenizer((String) getOperand(1), ";");
		fromSequence = new int[from.countTokens()];
		int index = 0;
		while ( from.hasMoreTokens() ) {
			fromSequence[index++] = Integer.parseInt(from.nextToken());
		}
		
		StringTokenizer to = new StringTokenizer((String) getOperand(2), ";");
		toSequence = new int[to.countTokens()];
		index = 0;
		while ( from.hasMoreTokens() ) {
			toSequence[index++] = Integer.parseInt(to.nextToken());
		}
		
		return null;
		
	}

}
