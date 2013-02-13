package com.dexels.navajo.tipi.vaadin.touch.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.addon.touchkit.service.ApplicationIcon;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;
import com.vaadin.ui.Window;

public abstract class TouchServlet extends com.vaadin.terminal.gwt.server.AbstractApplicationServlet {

	private static final long serialVersionUID = -6650995696015481525L;
	private ThreadLocal<Window> window = new ThreadLocal<Window>();
    private Class<? extends Application> fallbackApplicationClass;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

    }

    @Override
    protected void writeAjaxPage(HttpServletRequest request,
            HttpServletResponse response, Window window, Application application)
            throws IOException, MalformedURLException, ServletException {
        /*
         * Temporary save window as we may need it if we write e.g. viewport
         * definitions.
         */
        this.window.set(window);
        String fallbackWidgetset = getWidgetset(request, window);
        if (fallbackWidgetset != null) {
            request.setAttribute(REQUEST_WIDGETSET, fallbackWidgetset);
        }

        super.writeAjaxPage(request, response, window, application);
        this.window.set(null);
    }

    /**
     * Return a possible custom widgetset for a window. The default behavior is
     * to return fallbackWidgetset init parameter for non TouchKitWindow's.
     * 
     * @param request
     * @param window
     * @return
     */
    protected String getWidgetset(HttpServletRequest request, Window window) {
        if (!(window instanceof TouchKitWindow)) {
            String widgetset = getApplicationProperty("fallbackWidgetset");
            return widgetset;
        }
        return null;
    }

    @Override
    protected void writeAjaxPageHtmlHeadStart(BufferedWriter page,
            HttpServletRequest request) throws IOException {
        // write html header
        page.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD "
                + "XHTML 1.0 Transitional//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/"
                + "DTD/xhtml1-transitional.dtd\">\n");

        Window w = window.get();
        String manifest;

        /*
         * Serve without cache manifest if "restartApplication" or if
         * TouchKitWindow is configured so
         */
        boolean disableCachManifest = request.getQueryString() != null
                && request.getQueryString().contains(
                        URL_PARAMETER_RESTART_APPLICATION);

        if (!disableCachManifest && (w instanceof TouchKitWindow)) {
            disableCachManifest = !((TouchKitWindow) w)
                    .isCacheManifestEnabled();
        }

        if (disableCachManifest) {
            manifest = "";
        } else {
            String staticFilesLocation = getStaticFilesLocation(request);

            String widgetset = getApplicationProperty(PARAMETER_WIDGETSET);
            widgetset = stripSpecialChars(widgetset);

            final String widgetsetFilePath = staticFilesLocation + "/"
                    + WIDGETSET_DIRECTORY_PATH + widgetset + "/";
            manifest = " manifest=\"" + widgetsetFilePath + "cache.manifest\"";

        }
        page.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"" + manifest
                + ">\n<head>\n");
    }

    @Override
    protected void writeAjaxPageHtmlHeader(BufferedWriter page, String title,
            String themeUri, HttpServletRequest request) throws IOException {
        super.writeAjaxPageHtmlHeader(page, title, themeUri, request);
        Window window = this.window.get();
        if (window != null && window instanceof TouchKitWindow) {
            TouchKitWindow w = (TouchKitWindow) window;

            boolean viewportOpen = false;
            if (w.getViewPortWidth() != null) {
                viewportOpen = prepareViewPort(viewportOpen, page);
                page.write("width=" + w.getViewPortWidth());
            }
            if (w.isViewPortUserScalable() != null) {
                viewportOpen = prepareViewPort(viewportOpen, page);
                page.write("user-scalable="
                        + (w.isViewPortUserScalable() ? "yes" : "no"));
            }
            if (w.getViewPortInitialScale() != null) {
                viewportOpen = prepareViewPort(viewportOpen, page);
                page.write("initial-scale=" + w.getViewPortInitialScale());
            }
            if (w.getViewPortMaximumScale() != null) {
                viewportOpen = prepareViewPort(viewportOpen, page);
                page.write("maximum-scale=" + w.getViewPortMaximumScale());
            }

            if (w.getViewPortMinimumScale() != null) {
                viewportOpen = prepareViewPort(viewportOpen, page);
                page.write("minimum-scale=" + w.getViewPortMinimumScale());
            }
            if (viewportOpen) {
                closeSingleElementTag(page);
            }

            boolean webAppCapable = w.isWebAppCapable();
            if (webAppCapable) {
                page.write("<meta name=\"apple-mobile-web-app-capable\" "
                        + "content=\"yes\" />\n");
            }

            if (w.getStatusBarStyle() != null) {
                page.append("<meta name=\"apple-mobile-web-app-status-bar-style\" "
                        + "content=\"" + w.getStatusBarStyle() + "\" />\n");
            }
            ApplicationIcon[] icons = w.getApplicationIcons();
            for (int i = 0; i < icons.length; i++) {
                ApplicationIcon icon = icons[i];
                page.write("<link rel=\"apple-touch-icon\" ");
                if (icon.getSizes() != null) {
                    page.write("sizes=\"");
                    page.write(icon.getSizes());
                    page.write("\"");
                }
                page.write(" href=\"");
                page.write(icon.getHref());
                closeSingleElementTag(page);
            }
            if (w.getStartupImage() != null) {
                page.append("<link rel=\"apple-touch-startup-image\" "
                        + "href=\"" + w.getStartupImage() + "\" />");
            }

        }
    }

    /**
     * Method detects whether the main application is supported by the TouchKit
     * application. It controls whether an optional fallback application should
     * be served for the end user. By default the method just ensures that the
     * browser is webkit based.
     * 
     * @param request
     * @return true if the normal application should be served
     */
    protected boolean isSupportedBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        /*
         * CFNetwork is reported by ios web apps in some cases (when updating
         * cache stuff ?). It is thus also accepted.
         */
        return !(header == null || !(header.toLowerCase().contains("webkit") || header
                .toLowerCase().contains("CFNetwork")));
    }

    /**
     * @param request
     * @return an application instance to be used for non touchkit compatible
     *         browsers
     * @throws ServletException
     */
    protected Application getNewFallbackApplication(HttpServletRequest request)
            throws ServletException {
        if (fallbackApplicationClass != null) {
            try {
                final Application application = getFallbackApplicationClass()
                        .newInstance();

                return application;
            } catch (final IllegalAccessException e) {
                throw new ServletException("getNewApplication failed", e);
            } catch (final InstantiationException e) {
                throw new ServletException("getNewApplication failed", e);
            }
        }
        return null;
    }

    protected Class<? extends Application> getFallbackApplicationClass() {
        return fallbackApplicationClass;
    }

    private void closeSingleElementTag(BufferedWriter page) throws IOException {
        page.write("\" />\n");
    }

    private boolean prepareViewPort(boolean viewportOpen, BufferedWriter page)
            throws IOException {
        if (viewportOpen) {
            page.write(", ");
        } else {
            page.write("\n<meta name=\"viewport\" content=\"");
        }
        return true;
    }

    /**
     * Disable query parameter for the host script to make cache manifests work
     * properly.
     * 
     * @see com.vaadin.terminal.gwt.server.AbstractApplicationServlet#createPreventCachingQueryString()
     */
    @Override
    protected String createPreventCachingQueryString() {
        return "";
    }

    @Override
    protected void writeStaticResourceResponse(HttpServletRequest request,
            HttpServletResponse response, URL resourceUrl) throws IOException {
        if (resourceUrl.getFile().endsWith(".manifest")) {
            response.setContentType("text/cache-manifest");
            response.setHeader("Cache-Control", "max-age=1, must-revalidate");
        }
        super.writeStaticResourceResponse(request, response, resourceUrl);
    }

}
