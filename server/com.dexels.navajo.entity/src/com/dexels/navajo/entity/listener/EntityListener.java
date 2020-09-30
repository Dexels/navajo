package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dexels.navajo.api.util.NavajoRequestConfig;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.continuations.EntityContinuationRunner;
import com.dexels.navajo.entity.continuations.EntityDispatcher;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.DispatcherInterface;

public class EntityListener extends HttpServlet {

    private static final long serialVersionUID = -6681359881499760460L;

    private final static Logger logger = LoggerFactory.getLogger(EntityListener.class);

    private TmlScheduler tmlScheduler;
    private EntityDispatcher entityDispatcher;
    private DispatcherInterface dispatcherInterface;
	private long requestTimeout;

    public void activate() {

        logger.info("Entity servlet component activated");
        requestTimeout = NavajoRequestConfig.getRequestTimeout(5 * 60 * 1000L); // 5 minutes
        logger.info("Using timeout in continuation: {}", requestTimeout);
    }

    public void deactivate() {
        logger.info("Entity servlet component deactivated");
    }

    /**
     * entity/Match?Match/MatchId=2312321
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("GET".equals(request.getMethod()) && request.getParameter("getDefinition") != null) {
            performHeadRequest(request, response);
        } else {
            EntityContinuationRunner runner = new EntityContinuationRunner(request, response,
                    requestTimeout);
            if (!runner.isAborted()) {
                runner.setEntityDispatcher(entityDispatcher);
                runner.setDispatcher(dispatcherInterface);
                tmlScheduler.submit(runner, false);
            }
        }
    }

    public void setTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = scheduler;
    }

    public void clearTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = null;
    }

    public void setEntityDispatcher(EntityDispatcher ed) {
        this.entityDispatcher = ed;
    }

    public void clearEntityDispatcher(EntityDispatcher scheduler) {
        this.entityDispatcher = null;
    }

    public void setDispatcher(DispatcherInterface di) {
        this.dispatcherInterface = di;
    }

    public void clearDispatcher(DispatcherInterface di) {
        this.dispatcherInterface = null;
    }

    private void performHeadRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Entity e = getEntityFromRequest(request);
        if (e == null) {
            response.sendError(302, "Entity Not Found");
        }
        try (OutputStream out = response.getOutputStream()) {
                e.getMyNavajo().write(out);
        }
    }

    private Entity getEntityFromRequest(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (path.startsWith("/entity")) {
            path = path.substring(7);
        }
        String entityName = path.substring(1);
        if (entityName.indexOf('.') > 0) {
            // Remove .<format> from entityName
            entityName = entityName.substring(0, entityName.indexOf('.'));
        }
        entityName = entityName.replace("/", ".");
        String mappedEntity = null;

        String entitySubName = entityName.substring(entityName.lastIndexOf('.') + 1);
        String folder;
        if (entityName.equals(entitySubName)) {
            folder = ""; // Root folder
        } else {
            folder = path.substring(1, path.lastIndexOf("/"));
        }
        Set<String> entities = entityDispatcher.getMyMapper().getEntities(folder);

        for (String s : entities) {
            String anEntity = s.substring(s.lastIndexOf('.') + 1);
            if (anEntity.equals(entitySubName)) {
                mappedEntity = s;
                break;
            }
        }

        Entity e = entityDispatcher.getMyManager().getEntity(mappedEntity);
        return e;
    }

}
