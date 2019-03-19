package com.dexels.navajo.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.NavajoConfigInterface;
/**
 * @deprecated
 * @author frank
 *
 */
@Deprecated
public class AdminMap implements Mappable {

    private static final String UNDEFINED = "Undefined, check OSGi";
	public int openConnections;
    public int requestCount;
    public float requestRate;
    public Date startTime;
    public String scriptPath;
    public AccessMap[] users;
    public AsyncProxy[] asyncThreads;
    public ClassCount[] classCounts;
    public String accessId;
    public String vendor;
    public String productName;
    public String version;
    public String configPath;
    public String adapterPath;
    public String compiledScriptPath;
    public String rootPath;
    public String webservice;
    public String documentClass;
    public boolean supportsHotCompile;
    public boolean supportsAsync;
    public boolean supportsStore;
    public boolean supportsIntegrity;
    public boolean supportsStatistics;
    public boolean supportsLocks;
    public boolean aliveConnection;
    public boolean collectGarbage;
    public boolean resetAdapters;

    /**
     * Monitor parameters.
     */
    public boolean monitorOn;
    public String monitorUsers;
    public String monitorWS;
    public int monitorTotaltime;

    public int classLoaderInstances;
    public int scriptClassInstances;
    public String serverId;

    public String instanceName;
    public String instanceGroup;
    public String clientId;

    // RequestRate windowSize
    public int requestRateWindowSize;

    private static final Logger logger = LoggerFactory.getLogger(AdminMap.class);

    private Access myAccess = null;

    @Override
    public void load(Access access) throws MappableException, UserException {
        NavajoConfigInterface nc = DispatcherFactory.getInstance().getNavajoConfig();
        scriptPath = ensurePathEndsWithSeparator(nc.getScriptPath());
        configPath = ensurePathEndsWithSeparator(nc.getConfigPath());
        adapterPath = ensurePathEndsWithSeparator(nc.getAdapterPath());
        compiledScriptPath = ensurePathEndsWithSeparator(nc.getCompiledScriptPath());
        rootPath = ensurePathEndsWithSeparator(nc.getRootPath());
        supportsAsync = nc.isAsyncEnabled();
        supportsStore = (nc.getAsyncStore() != null);
        supportsIntegrity = nc.isIntegrityWorkerEnabled();
        supportsStatistics = nc.isEnableStatisticsRunner();
        supportsLocks = nc.isLockManagerEnabled();

        serverId = DispatcherFactory.getInstance().getApplicationId();

        myAccess = access;

        instanceName = nc.getInstanceName();
        instanceGroup = nc.getInstanceGroup();
        clientId = access.getClientToken();

    }

    private String ensurePathEndsWithSeparator(String path) {
        if (path.endsWith(File.separator)) {
            return path;
        } else {
            return path + File.separator;
        }
    }

    /**
     * Some admin functions.
     *
     * @param datasourceName
     * @throws MappableException
     * @throws UserException
     */
    public final int getOpenConnections(String datasource) {
        try {
            SQLMap sql = new SQLMap();
            sql.setDatasource(datasource);

            if (SQLMap.fixedBroker == null || SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password) == null) {
                logger.error("Could not create connection to datasource {}, using username {}",sql.datasource, sql.username);
                return 0;
            }
            int c = SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password).getUseCount();

            sql.store();

            return c;
        } catch (Throwable e) {
            logger.error("Error: ", e);
        }
        return 0;
    }

    public final boolean getAliveConnection(String datasource) {
        boolean b = true;
        SQLMap sql = new SQLMap();
        try {

            sql.load(myAccess);
            sql.setDatasource(datasource);
            sql.createConnection();
            if (sql.con == null) {
                b = false;
            }

        } catch (Throwable e1) {
            b = false;
        } finally {
            try {
                sql.store();
            } catch (Exception e) {
                logger.error("Error: ", e);
            }
        }

        return b;
    }

    /**
     * Some admin functions.
     * 
     * @param datasourceName
     * @throws MappableException
     * @throws UserException
     */
    public final int getMaxConnections(String datasource) {
        SQLMap sql = new SQLMap();
        sql.setDatasource(datasource);

        if (SQLMap.fixedBroker == null || SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password) == null) {
            logger.error("Could not create connection to datasource {}, using username {}",sql.datasource, sql.username);
            return 0;
        }
        return SQLMap.fixedBroker.get(sql.datasource, sql.username, sql.password).getSize();
    }

    public int getRequestCount() {
        return (int) com.dexels.navajo.server.DispatcherFactory.getInstance().getRequestCount();
    }

    @SuppressWarnings("rawtypes")
    public AsyncProxy[] getAsyncThreads() {

        Map all = com.dexels.navajo.mapping.AsyncStore.getInstance().objectStore;
        Iterator iter = all.values().iterator();
        List<AsyncProxy> l = new ArrayList<>();
        while (iter.hasNext()) {
            AsyncMappable am = (AsyncMappable) iter.next();
            Access ac = com.dexels.navajo.mapping.AsyncStore.getInstance().accessStore.get(am.pointer);
            AsyncProxy o = new AsyncProxy();
            o.user = ac.rpcUser;
            o.rpcName = ac.rpcName;
            o.setPointer(am.getPointer());
            o.startDate = am.getStartDate();
            o.name = am.getName();
            o.startTime = am.getStartTime();
            o.running = am.getRunning();
            o.interrupt = am.getInterrupt();
            o.accessId = ac.accessID;
            o.totaltime = (int) (System.currentTimeMillis() - am.getStartDate().getTime());
            o.ipAddress = ac.ipAddress;
            o.host = ac.hostName;
            o.kill = am.kill;
            o.stackTrace = am.getStackTrace();
            o.lockClass = am.getLockClass();
            o.lockName = am.getLockName();
            o.lockOwner = am.getLockOwner();
            o.waiting = am.isWaiting();
            try {
                o.percReady = am.getPercReady();
            } catch (Exception e) {
                logger.error("Error: ", e);
            }
            l.add(o);
        }
        AsyncProxy[] objects = new AsyncProxy[l.size()];
        return l.toArray(objects);
    }

    public AccessMap[] getUsers() {
        Set<Access> all = new HashSet<>(com.dexels.navajo.server.DispatcherFactory.getInstance().getAccessSet());
        Iterator<Access> iter = all.iterator();
        List<AccessMap> d = new ArrayList<>();
        while (iter.hasNext()) {
            Access a = iter.next();
            d.add(new AccessMap(a));
        }
        AccessMap[] ams = new AccessMap[d.size()];
        return d.toArray(ams);
    }

    @Override
    public void store() throws MappableException, UserException {
    }

    @Override
    public void kill() {

    }

    public void setAsyncThreads(AsyncProxy[] asyncThreads) {
        this.asyncThreads = asyncThreads;
    }

    public Date getStartTime() {
        return com.dexels.navajo.server.DispatcherFactory.getInstance().getStartTime();
    }

    public float getRequestRate() {
        return DispatcherFactory.getInstance().getRequestRate();

    }

    public String getProductName() {
        return UNDEFINED;
    }

    public String getVendor() {
        return UNDEFINED;
    }

    public String getVersion() {
        return UNDEFINED;
    }

    public String getConfigPath() {
        return configPath;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public String getAdapterPath() {
        return adapterPath;
    }

    public String getCompiledScriptPath() {
        return compiledScriptPath;
    }

    public String getRootPath() {
        return rootPath;
    }

    public double getCPULoad() {
        return DispatcherFactory.getInstance().getNavajoConfig().getCurrentCPUload();
    }

    public boolean getSupportsAsync() {
        return supportsAsync;
    }

    public boolean getSupportsIntegrity() {
        return supportsIntegrity;
    }

    public boolean getSupportsStatistics() {
        return supportsStatistics;
    }

    public void setSupportsStatistics(boolean b) {
        DispatcherFactory.getInstance().getNavajoConfig().setStatisticsRunnerEnabled(b);
    }

    public boolean getSupportsLocks() {
        return supportsLocks;
    }

    public boolean getSupportsHotCompile() {
        return supportsHotCompile;
    }

    public boolean getSupportsStore() {
        return supportsStore;
    }

    public int getScriptClassInstances() {
        return GenericHandler.getLoadedClassesSize();
    }

    public String getServerId() {
        return serverId;
    }

    public int getRequestRateWindowSize() {
        return DispatcherFactory.getInstance().getRateWindowSize();
    }

    public boolean getMonitorOn() {
        return false;
    }

    /**
     * Sets the full log monitor to on for all requests.
     *
     * @param monitorOn
     */
    public void setMonitorOn(boolean monitorOn) {

    }

    public int getMonitorTotaltime() {
        return -1;
    }

    public String getMonitorUsers() {
        return "";
    }

    public String getMonitorWS() {
        return "";
    }

    public void setMonitorWS(String monitorWS) {

    }

    public void setMonitorUsers(String monitorUsers) {

    }

    public void setMonitorTotaltime(int monitorTotaltime) {

    }

    public void setWebservice(String w) {
        this.webservice = w;
    }

    public String getDocumentClass() {
        return NavajoFactory.getInstance().getClass().getName();
    }

    public void setResetAdapters(boolean b) {
        this.resetAdapters = b;
        if (resetAdapters) {
            logger.info("Resetting adapters...");
            DispatcherFactory.getInstance().getNavajoConfig().doClearCache();
        }
    }

    public void setDocumentClass(String s) throws UserException {
        try {
            Class.forName(s);
        } catch (Exception e) {
            throw new UserException(-1, "Could not find document implementation: " + s);
        }
        System.setProperty("com.dexels.navajo.DocumentImplementation", s);
        NavajoFactory.resetImplementation();
        NavajoFactory.getInstance().setTempDir(DispatcherFactory.getInstance().getTempDir());
        NavajoFactory.getInstance().setExpressionEvaluator(new CachedExpressionEvaluator());
        logger.debug("Document class is now: {}", getDocumentClass());
    }

    @Deprecated
    public ClassCount[] getClassCounts() {
        ClassCount[] cc = new ClassCount[1];
        cc[0].className = "com.dexels.navajo.server.Dispatcher";
        cc[0].count = -1;
        return cc;
    }

    /**
     * @param b
     */
    public void setCollectGarbage(boolean b) {
        logger.info("Calling garbage collect");
        System.gc();
        logger.info("Finished collecting garbage");
    }

    public String getInstanceName() {
        return instanceName;
    }

    public String getInstanceGroup() {
        return instanceGroup;
    }

    public String getClientId() {
        return clientId;
    }

}
