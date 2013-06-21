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
package com.dexels.navajo.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class ZipMap implements Mappable {

	public String name = "file.zip";
	public Binary content;
	public Binary zipped;
	
	private final static Logger logger = LoggerFactory.getLogger(ZipMap.class);
	
	ByteArrayOutputStream baos = null;
	ZipOutputStream zo = null;

	public void load(Access access) throws MappableException, UserException {
		baos = new ByteArrayOutputStream();
		zo = new ZipOutputStream(baos);
	}

	public void setName(String n) {
		this.name = n;
		ZipEntry entry = new ZipEntry(name);
		try {
			zo.putNextEntry(entry);
		} catch (IOException e) {
			logger.error("Zip problem: ", e);
		}
	}

	public void setContent(Binary b) {
		this.content = b;
		try {
			content.write( zo, false );
			zo.closeEntry();
		} catch (IOException e) {
			logger.error("Zip problem: ", e);
		}

	}

	public Binary getZipped() {

		try {
			zo.close();
			byte [] result = baos.toByteArray();
			return new Binary(result);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}

		return null;
	}

	public void store() throws MappableException, UserException {

	}

	public void kill() {

	}

	public static void main(String [] args) throws Exception {
		FileInputStream fi = new FileInputStream(new File("~/dbvis.license"));
		Binary b = new Binary(fi);
		ZipMap zm = new ZipMap();
		zm.load(null);
		zm.setName("dbvis.license");
		zm.setContent(b);
		fi = new FileInputStream(new File("~/INDEX"));
		b = new Binary(fi);
		zm.setName("INDEX");
		zm.setContent(b);

		Binary r = zm.getZipped();
		System.err.println(r.getData().length);
		FileOutputStream fo = new FileOutputStream(new File("~/aap.zip"));
		fo.write(r.getData());
		fo.close();
	}

}
