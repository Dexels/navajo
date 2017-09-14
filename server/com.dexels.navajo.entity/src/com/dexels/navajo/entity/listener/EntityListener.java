package com.dexels.navajo.entity.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void activate() {
        logger.info("Entity servlet component activated");
    }

    public void deactivate() {
        logger.info("Entity servlet component deactivated");
    }

    /**
     * entity/Match?Match/MatchId=2312321
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityContinuationRunner runner = new EntityContinuationRunner(request, response);
        runner.setEntityDispatcher(entityDispatcher);
        runner.setDispatcher(dispatcherInterface);
        tmlScheduler.submit(runner, false);
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

}
