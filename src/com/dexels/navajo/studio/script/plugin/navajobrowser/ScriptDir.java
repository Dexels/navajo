package com.dexels.navajo.studio.script.plugin.navajobrowser;

import java.util.*;

import org.eclipse.core.runtime.*;

import com.dexels.navajo.document.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class ScriptDir implements ScriptNode {

    private final List fileList = new ArrayList();
    private final List dirList = new ArrayList();
    private final Map dirMap = new HashMap();
    private final ScriptDir parent;
    private final String name;
    private final String parentPath;
    private final ServerConnection myConnection;
    private final boolean isConnection;

    public ScriptDir(ScriptDir parent, String parentPath, String name, ServerConnection sc, boolean isConnection) {
        this.parent = parent;
        this.name = name;
        this.parentPath = parentPath;
        this.isConnection = isConnection;
        myConnection = sc;
    }

    public void clear() {
        fileList.clear();
        dirList.clear();
        dirMap.clear();
    }

    public String getConnectionName() {
        if (myConnection != null) {
            return myConnection.getName();
        }
        return null;
    }

    public void addEntry(String connectionName, ServerConnection sc, IProgressMonitor monitor) {
        ScriptDir sd = new ScriptDir(this, "", sc.getName(), sc, true);
        dirMap.put(connectionName, sd);
        dirList.add(sd);
        Message init = sc.getInitScripts();
        monitor.worked(4);
        Message process = sc.getProcessScripts();
        monitor.worked(4);
        ArrayList al = init.getAllMessages();
        for (int i = 0; i < al.size(); i++) {
            Message current = (Message) al.get(i);
            sd.addEntry(current);
        }
        ArrayList al2 = process.getAllMessages();
        for (int i = 0; i < al2.size(); i++) {
            Message current = (Message) al2.get(i);
            sd.addEntry(current);
        }
    }

    public void addEntry(Message m) {
        Property name = m.getProperty("Name");
        String nameValue = name.getValue();
        if (!nameValue.startsWith(parentPath)) {
            return;
        }

        String relPath;
        if (parentPath.length() == 0) {
            relPath = nameValue;
        } else {
            relPath = nameValue.substring(parentPath.length() + 1);
        }
        if (relPath.indexOf('/') == -1) {
            addFile(relPath, m);
            return;
        }
        StringTokenizer st = new StringTokenizer(relPath, "/");
        String dir = st.nextToken();
        ScriptDir dd = (ScriptDir) dirMap.get(dir);
        if (dd == null) {
            String newPath;
            if (parentPath.endsWith(":")) {
                newPath = "".equals(parentPath) ? dir : parentPath + dir;
            } else {
                newPath = "".equals(parentPath) ? dir : parentPath + "/" + dir;
            }
            dd = new ScriptDir(this, newPath, dir, myConnection, false);
            dirMap.put(dir, dd);
            dirList.add(dd);
        }
        dd.addEntry(m);
    }

    private void addFile(String name, Message m) {
        ScriptFile sf = new ScriptFile(this, name, m);
        fileList.add(sf);
    }

    public int getChildCount() {
        return dirList.size() + fileList.size();
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public boolean isLeaf() {
        return false;
    }

    public Enumeration children() {
        return null;
    }

    public Object getParent() {
        return parent;
    }

    public Object getChildAt(int childIndex) {
        if (childIndex >= dirList.size()) {
            return fileList.get(childIndex - dirList.size());
        }
        return dirList.get(childIndex);
    }

    public ArrayList getRecursiveChildren() {
        ArrayList al = new ArrayList();
        for (Iterator iter = dirList.iterator(); iter.hasNext();) {
            ScriptDir element = (ScriptDir) iter.next();
            al.addAll(element.getRecursiveChildren());
        }
        for (Iterator iter = fileList.iterator(); iter.hasNext();) {
            ScriptFile element = (ScriptFile) iter.next();
            al.add(element);
        }
        return al;
    }

    public ArrayList getChildren() {
        ArrayList al = new ArrayList();
        al.addAll(dirList);
        al.addAll(fileList);
        return al;
    }

    public int getIndex(Object node) {
        int i = dirList.indexOf(node);
        if (i == -1) {
            return fileList.indexOf(node);
        }
        return i;
    }

    public String toString() {
        return name;
    }

    public String getFullPath() {
        if (parent == null) {
            return "";
        }

        if (isConnection) {
            return name + ":";
        }
        if (parentPath.endsWith(":")) {
            return parent.getFullPath() + name;
        }
        if (name.startsWith("/") || parent.getFullPath().endsWith("/")) {
            return parent.getFullPath() + name;
        }
        return parent.getFullPath() + "/" + name;
    }

    public ScriptFile getFileByFullPath(String name) {
        int xx = name.indexOf("/");
        if (xx == -1) {
            for (int i = 0; i < getChildCount(); i++) {
                Object tn = getChildAt(i);
                if (ScriptFile.class.isInstance(tn)) {
                    ScriptFile sf = (ScriptFile) tn;
                    if (sf.toString().equals(name)) {
                        return sf;
                    }
                }
            }
            return null;
        }
        String dir = name.substring(0, xx);
        ScriptDir sd = (ScriptDir) dirMap.get(dir);
        if (sd != null) {
            return sd.getFileByFullPath(name.substring(xx + 1));
        }
        return null;
    }

    public String getName() {
        return name;
    }

}