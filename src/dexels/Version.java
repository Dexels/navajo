package dexels;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public abstract class Version {

	public String RELEASEDATE;
	
	public abstract int getMajor();
	public abstract int getMinor();
	public abstract int getPatchLevel();
	public abstract String getVendor();
	
	public void setReleaseDate(String s) {
		this.RELEASEDATE = s;
	}
	
	public Date getReleaseDate() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(RELEASEDATE);
		} catch (Exception e) {
			return null;
		}
	}
	
	public abstract String getProductName();
	
	public String toString() {
		return getProductName() + " " + getMajor() + "." + getMinor() + "." + getPatchLevel() + "/" + getVendor() + " (" + getReleaseDate() + ")";
	}
	
	public String getVersion() {
		return getMajor() + "." + getMinor() + "." + getPatchLevel();
	}
	
}
