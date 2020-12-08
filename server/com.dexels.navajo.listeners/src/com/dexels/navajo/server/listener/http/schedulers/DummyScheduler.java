/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.listener.http.schedulers;

import javax.servlet.ServletContext;

import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.script.api.TmlScheduler;

public class DummyScheduler implements TmlScheduler {
    @Override
    public void initialize(ServletContext servlet) {
    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public void run(TmlRunnable myRunner) {
        myRunner.run();
    }

    @Override
    public void submit(TmlRunnable myRunner, boolean retry) {
        throw new UnsupportedOperationException("Can not schedule using DummyScheduler");
    }

    @Override
    public String getSchedulingStatus() {
        return "DummyScheduler: I know nothing";
    }

    @Override
    public void shutdownScheduler() {
    }

    @Override
    public RequestQueue getDefaultQueue() {
        throw new UnsupportedOperationException("Can not getGenericPool using DummyScheduler");
    }

    @Override
    public RequestQueue getQueue(String queueid) {
        throw new UnsupportedOperationException("Can not getQueue using DummyScheduler");
    }

    @Override
    public void submit(TmlRunnable myRunner, String queueid) {
        throw new UnsupportedOperationException("Can not schedule using DummyScheduler");
    }
}
