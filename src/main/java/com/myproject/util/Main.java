package com.myproject.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        logger.fatal("FATAL LOGGER");
        logger.error("LOGGER WARNING");
        logger.info("LOGGER INFO");

    }
}
