package com.myproject.command.util;

public class Route {
    private RouteType route;
    private String pathOfThePage;

    public RouteType getRoute() {
        return route;
    }

    public void setRoute(RouteType route) {
        if (route == null){
            route = RouteType.REDIRECT;
        }
        this.route = route;
    }

    public String getPathOfThePage() {
        return pathOfThePage;
    }

    public void setPathOfThePage(String pathOfThePage) {
        this.pathOfThePage = pathOfThePage;
    }
    public enum RouteType{
        REDIRECT,FORWARD
    }
}