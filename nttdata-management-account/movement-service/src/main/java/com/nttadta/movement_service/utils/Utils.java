package com.nttadta.movement_service.utils;

public class Utils {

    /**
     * Method to determine if the application is running in Docker
     * @return true if running on Docker, false if not
     */
    public static boolean dockerIsRunning() {
        String isDockerEnv = System.getenv("IS_DOCKER");
        return isDockerEnv != null && isDockerEnv.equalsIgnoreCase("true");
    }
}
