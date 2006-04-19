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
package com.dexels.navajo.functions;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class Zip extends FunctionInterface {

	public String remarks() {
		return "Zips a binary property";
	}

	public String usage() {
		return "Zip(Binary content, String filename)";
	}

	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 2) {
			return new TMLExpressionException(this, "Wrong number of arguments");
		}
		
		if (!(getOperand(0) instanceof Binary)) {
			return new TMLExpressionException(this, "Binary content expected");
		}
		
		if (!(getOperand(1) instanceof String)) {
			return new TMLExpressionException(this, "String expected");
		}
		
		Binary i = (Binary) getOperand(0);
		String f = (String) getOperand(1);
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zo = new ZipOutputStream(baos);
			ZipEntry entry = new ZipEntry(f);
			zo.putNextEntry(entry);
			zo.write( i.getData() );
			zo.closeEntry();
			zo.close();
			byte [] result = baos.toByteArray();
			return new Binary(result);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new TMLExpressionException(this, e.getMessage());
		}
	}

}
