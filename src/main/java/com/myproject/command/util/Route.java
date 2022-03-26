package com.myproject.command.util;

/**
 * The Route class defines the route where the response will be sent after the processing the request
 */
public class Route {
    private RouteType route;
    private String pathOfThePage;

    public RouteType getRoute() {
        return route;
    }

    public void setRoute(RouteType route) {
        if (route == null) {
            route = RouteType.REDIRECT;
        }
        this.route = route;
    }

    public String getPathOfThePage() {
        return pathOfThePage;
    }

    public void setPathOfThePage(String pathOfThePage) {
        if (this.pathOfThePage == null) {
            this.pathOfThePage = pathOfThePage;
        }
    }

    public enum RouteType {
        REDIRECT, FORWARD
    }
}