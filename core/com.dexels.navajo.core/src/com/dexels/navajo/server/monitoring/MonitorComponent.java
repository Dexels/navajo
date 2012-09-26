package com.dexels.navajo.server.monitoring;

import java.util.StringTokenizer;

import com.dexels.navajo.server.Access;

public class MonitorComponent implements ServiceMonitor {
	
    
    public boolean monitorOn;
    public String monitorUsers = null;
    public String [] monitorUsersList = null;
    public String monitorWebservices = null;
    public String [] monitorWebservicesList = null;
    public int monitorExceedTotaltime = -1;

    /**
    *
    * BELOW WILL FOLLOW LOGIC FOR MONITORING WEBSERVICES.
    *
    */

   /*
    * Determine if a value matches any of the regexps in a list.
    *
    * @param value
    * @param regExplist
    * @return
    */
   private final boolean matchesRegexp(String value, String [] regExplist) {
     if (regExplist == null) {
       return true;
     }

     for (int i = 0; i < regExplist.length; i++) {
       if (value.matches(regExplist[i])) {
         return true;
       }
     }
     return false;
   }

   /*
    * Determine if access object needs full access log.
    *
    * @param a the full access log candidate
    * @return whether full access log is required for access object.
    */
   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#needsFullAccessLog(com.dexels.navajo.server.Access)
 */
@Override
   public final boolean needsFullAccessLog(Access a) {
     // Check whether compiledscript has debugAll set or whether access object has debug all set.
     if ( a.isDebugAll() || ( a.getCompiledScript() != null && a.getCompiledScript().isDebugAll() ) ) {
   	  return true;
     }
     
     if (!monitorOn) {
       return false;
     }
     if (
          (monitorUsersList == null || matchesRegexp(a.rpcUser, this.monitorUsersList ) )&&
          (monitorWebservicesList == null || matchesRegexp(a.rpcName, monitorWebservicesList) ) &&
          (monitorExceedTotaltime == -1 || a.getTotaltime() >= monitorExceedTotaltime)
         )
     {
       return true;
     }
     return false;
   }

   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#isMonitorOn()
 */
   @Override
   public final boolean isMonitorOn() {
     return monitorOn;
   }

   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#setMonitorOn(boolean)
 */
@Override
   public final void setMonitorOn(boolean monitorOn) {
     this.monitorOn = monitorOn;
   }

   /*
    * Get r.e. for user monitor filter. If null is returned all users should be logged.
    *
    * @return the current filter
    */
   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#getMonitorUsers()
 */
@Override
   public final String getMonitorUsers() {
     return monitorUsers;
   }

   /*
    * Set r.e. for user monitor filter. Null or empty string means no filter.
    *
    * @param monitorUsers
    */
   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#setMonitorUsers(java.lang.String)
 */
@Override
   public final void setMonitorUsers(String monitorUsers) {
     System.err.println("in setMonitorUsers(" + monitorUsers + ")");
     if (monitorUsers == null || monitorUsers.equals("")) {
       this.monitorUsersList = null;
       this.monitorUsers = null;
       return;
     }
     this.monitorUsers = monitorUsers;
     StringTokenizer list = new StringTokenizer(monitorUsers, ",");
     System.err.println("Found " + list.countTokens() + " regexp elements");
     monitorUsersList = new String[list.countTokens()];
     int i = 0;
     while (list.hasMoreTokens()) {
       monitorUsersList[i++] = list.nextToken();
     }
   }

   /*
     * Set r.e. for webservice monitor filter. Null or empty string means no filter.
     *
     * @param monitorWebservices
     */

   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#setMonitorWebservices(java.lang.String)
 */
@Override
   public final void setMonitorWebservices(String monitorWebservices) {
     System.err.println("in setMonitorWebservices(" + monitorWebservices + ")");
     if (monitorWebservices == null || monitorWebservices.equals("")) {
       this.monitorWebservicesList = null;
       this.monitorWebservices = null;
       return;
     }
     this.monitorWebservices = monitorWebservices;
     StringTokenizer list = new StringTokenizer(monitorWebservices, ",");
     monitorWebservicesList = new String[list.countTokens()];
     System.err.println("Found " + list.countTokens() + " regexp elements");
     int i = 0;
     while (list.hasMoreTokens()) {
       monitorWebservicesList[i++] = list.nextToken();
     }
   }

   /*
    * Get r.e. for webservice monitor filter. If null is returned all users should be logged.
    *
    * @return the current filter
    */
   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#getMonitorWebservices()
 */
@Override
   public final String getMonitorWebservices() {
     return monitorWebservices;
   }
   
   /*
    * Get the time in millis over which an access needs to be fully logged.
    *
    * @return
    */
   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#getMonitorExceedTotaltime()
 */
@Override
   public final int getMonitorExceedTotaltime() {
     return monitorExceedTotaltime;
   }

   /* (non-Javadoc)
 * @see com.dexels.navajo.server.monitoring.ServiceMonitor#setMonitorExceedTotaltime(int)
 */
@Override
   public final void setMonitorExceedTotaltime(int monitorExceedTotaltime) {
     this.monitorExceedTotaltime = monitorExceedTotaltime;
   }

}
