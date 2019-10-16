package com.jeffery.smartlab.core;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.internal.Console;
import com.googlecode.aviator.AviatorEvaluator;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {

    private Processor() {
    }

    private static final String ASSIGN_PREFIX_REGEX = "^(\\w+)\\s*=(.*)$";
    private static final Pattern ASSIGN_PREFIX_PATTERN = Pattern.compile(ASSIGN_PREFIX_REGEX);

    public static final Console CONSOLE = JCommander.newBuilder().build().getConsole();

    public static void initialize() {
        CONSOLE.println(">>> ========================================");
        CONSOLE.println(">>> SmartLab V1.0");
        CONSOLE.println(">>> ========================================");
        importFunctions(Mat.class, Core.class);
        prefix();
    }

    private static void importFunctions(Class<?>... clazzes) {
        for (Class<?> clazz : clazzes) {
            try {
                AviatorEvaluator.importFunctions(clazz);
            } catch (Exception e) {
                CONSOLE.println(e.getMessage());
            }
        }
    }

    public static void prefix() {
        CONSOLE.print(">>> ");
    }

    public static void doProcess(String line) {
        JCommander jCommander = CommandFactory.newJCommander();
        jCommander.parseWithoutValidation(line.split(" "));
        String parsedCommand = jCommander.getParsedCommand();
        if (parsedCommand != null) {
            JCommander parsedCommander = jCommander.getCommands().get(parsedCommand);
            for (Object command : parsedCommander.getObjects()) {
                if (command instanceof Command) {
                    ((Command) command).run(jCommander);
                }
            }
        } else {
            String key = null;
            Matcher matcher = ASSIGN_PREFIX_PATTERN.matcher(line);
            if (matcher.matches()) {
                key = matcher.group(1);
                line = matcher.group(2);
            }
            Object res = null;
            try {
                res = AviatorEvaluator.execute(line, Workspace.getEnv());
            } catch (Exception e) {
                jCommander.getConsole().println(e.getMessage());
                prefix();
                return;
            }
            if (key != null) {
                Workspace.getEnv().put(key, res);
            }
            jCommander.getConsole().println(String.valueOf(res));
        }
        prefix();
    }

}
