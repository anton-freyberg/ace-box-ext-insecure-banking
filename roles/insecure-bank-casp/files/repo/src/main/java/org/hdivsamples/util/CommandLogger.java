package org.hdivsamples.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLogger {

    private static final Logger logger = LoggerFactory.getLogger(CommandLogger.class);

    public static String executeCommand(String command) throws IOException, InterruptedException {
        if ((command == null) || command.isEmpty())
            return "";

        logger.info("Executing command: " + command);

        StringBuilder output = new StringBuilder();

        ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            output.append(line);
            output.append("<br>");
        }
        process.waitFor();
        in.close();

        return output.toString();
    }

}
