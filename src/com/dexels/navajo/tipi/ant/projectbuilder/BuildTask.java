/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.dexels.navajo.tipi.ant.projectbuilder;



import org.apache.tools.ant.BuildException;



public class BuildTask extends AbstractCatalinaTask {

    protected String application = null;

    public String getApplication() {
        return (this.application);
    }

    public void setApplication(String application) {
        this.application = application;
    }




    // --------------------------------------------------------- Public Methods


    /**
     * Execute the requested operation.
     *
     * @exception BuildException if an error occurs
     */
    public void execute() throws BuildException {

        super.execute();
        if (getApplication() == null) {
            throw new BuildException
                ("Must specify 'application' attribute");
        }
        String currentUrl = "?app="+getApplication()+"&cmd=build";
        super.execute(currentUrl, null, null, -1);

    }

    public static void main(String[] args) {
   	 BuildTask d = new BuildTask();
   	 d.setUsername("ad");
   	 d.setPassword("pw");
   	 d.setApplication("mujahedin");
   	 d.setUrl("http://spiritus.dexels.nl:8080/TipiServer/TipiAdminServlet");
   	 d.execute();
    }

}
