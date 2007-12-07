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
package com.dexels.navajo.adapter.icalmap;

import java.util.Date;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class ICalEvent implements Mappable {

	public String organizer;
	public String location;
	public String summary;
	public String fromName;
	public String fromEmail;
	public Date startDate, endDate;
	public Date alarmDate;
	public int alarmMinutes;
	public Integer alarmMinutesObject = null;
	public String alarmDescription = null;
	public String alarmAction = "DISPLAY";
	public String description;
	public Attendee [] attendees;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {

	}

	/* Setters */
	public void setAlarmDescription(String s) {
		this.alarmDescription = s;
	}
	
	public void setAlarmMinutes(int m) {
		this.alarmMinutesObject = new Integer(m);
	}
	
	public void setAlarmDate(Date s) {
		this.alarmDate = s;
	}
	
	public void setFromEmail(String s) {
		this.fromEmail = s;
	}
	
	public void setFromName(String s) {
		this.fromName = s;
	}
	
	public void setOrganizer(String org) {
		organizer = org;
	}

	public void setLocation(String loc) {
		location = loc;
	}

	public void setSummary(String summ) {
		summary = summ;
	}

	public void setStartDate(java.util.Date start) {
		startDate = start;
	}

	public void setEndDate(java.util.Date end) {
		endDate = end;
	}
	
	/* Getters */
	public String getAlarmAction() {
		return alarmAction;
	}
	
	public String getAlarmDescription() {
		return alarmDescription;
	}
	
	public Integer getAlarmMinutesObject() {
		return alarmMinutesObject;
	}
	
	public Date getAlarmDate() {
		return alarmDate;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}
	
	public String getFromName() {
		return fromName;
	}
	
	public String getOrganizer() {
		return organizer;
	}

	public String getLocation() {
		return location;
	}

	public String getSummary() {
		return summary;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}
	
	public void store() throws MappableException, UserException {

	}

	public void kill() {
	
	}

	public Attendee[] getAttendees() {
		return attendees;
	}

	public void setAttendees(Attendee[] attendees) {
		this.attendees = attendees;
	}

}
