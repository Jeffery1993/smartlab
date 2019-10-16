package com.jeffery.smartlab.core;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.jeffery.smartlab.consts.CommandEnum;

public abstract class AbstractCommand implements Command {

    @Parameter()
    private String main;

    protected void usage(JCommander jCommander) {
        CommandEnum type = getType();
        if (type != null) {
            if (CommandEnum.HELP == type) {
                jCommander.setProgramName("SmartLab");
                jCommander.usage();
            } else {
                jCommander.getCommands().get(getType().getName()).usage();
            }
        }
    }

}
