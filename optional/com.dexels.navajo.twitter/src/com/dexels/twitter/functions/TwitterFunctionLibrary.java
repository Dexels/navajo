/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.twitter.functions;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;

public class TwitterFunctionLibrary implements ExtensionDefinition {
    private static final long serialVersionUID = 8773461399384524479L;
    private transient ClassLoader extensionClassLoader = null;

    @Override
    public InputStream getDefinitionAsStream() {
        return getClass().getClassLoader()
                .getResourceAsStream("com/dexels/twitter/functions/twitterfunctions.xml");
    }

    @Override
    public String getConnectorId() {
        return null;
    }

    @Override
    public List<String> getDependingProjectUrls() {
        return null;
    }

    public String getDeploymentDescriptor() {
        return null;
    }

    @Override
    public String getDescription() {
        return "The Enterprise Navajo Function Library";
    }

    @Override
    public String getId() {
        return "NavajoAdapters";
    }

    @Override
    public String[] getIncludes() {
        return null;
    }

    public List<String> getLibraryJars() {
        return null;
    }

    public List<String> getMainJars() {
        return null;
    }

    @Override
    public String getProjectName() {
        return null;
    }

    @Override
    public List<String> getRequiredExtensions() {
        return null;
    }

    @Override
    public boolean isMainImplementation() {
        return false;
    }

    @Override
    public String requiresMainImplementation() {
        return null;
    }

    @Override
    public ClassLoader getExtensionClassloader() {
        return extensionClassLoader;
    }

    @Override
    public void setExtensionClassloader(ClassLoader extClassloader) {
        extensionClassLoader = extClassloader;
    }

}
