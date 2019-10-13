package com.jeffery.smartlab.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import com.jeffery.smartlab.consts.CommandEnum;
import com.jeffery.smartlab.core.AbstractCommand;

@Parameters(commandDescription = "Exit the program")
public class ExitCommand extends AbstractCommand {

    @Override
    public CommandEnum getType() {
        return CommandEnum.EXIT;
    }

    @Override
    public void run(JCommander jCommander) {
        jCommander.getConsole().println("Exit!");
        System.exit(0);
    }

}
