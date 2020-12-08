/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.statistics;

import java.util.Map;
import java.util.Set;

import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.events.types.WorkflowEvent;
import com.dexels.navajo.events.types.WorkflowLog;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.statistics.impl.TodoItem;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
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

public interface StoreInterface {

  /**
   * Method to store an access object in the (persistent) Navajo store.
   *
   *It doesn't seem to be used. Is it deprecated?
   * @param a
   */
  public void storeAccess(final Access a, final AsyncMappable am);
  
  /**
   * Method to store multiple access objects in the (persistent) Navajo store.
   * Returns number of written full access logs.
   *
   * @param a
   */
  public int storeAccess(final Map<String,TodoItem> accessMap);
  
  /**
   * Method to store multiple access objects in the (persistent) Navajo store.
   *
   * @param a
   */
  public void storeAuditLogs(final Set<AuditLogEvent> auditLogSet);

  public void storeWorkflowEvents(final Set<WorkflowEvent> workflowEventSet);
  
  public void storeWorkflowLogs(final Set<WorkflowLog> copyOfWorkflowLogs);

  /**
   * Clean up and consolidate action - to be called after completing inserting statistics data.
   */
  public void consolidate();
  
  /**
   * Set the url for the database.
   *
   * @param url
   */
  public void setDatabaseUrl(String url);

  /**
   * Pass database specific parameters in a map object.
   *
   * @param m
   */
  public void setDatabaseParameters(Map<String,String> m);

  public void initialize();
  
  public void shutdown();

}