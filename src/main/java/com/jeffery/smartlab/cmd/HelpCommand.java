package com.jeffery.smartlab.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jeffery.smartlab.consts.CommandEnum;
import com.jeffery.smartlab.core.AbstractCommand;

@Parameters(commandDescription = "Show the help info")
public class HelpCommand extends AbstractCommand {

    @Parameter(names = {"--command", "-c"}, description = "Show the help info of the specified command")
    private String command;

    @Override
    public CommandEnum getType() {
        return CommandEnum.HELP;
    }

    @Override
    public void run(JCommander jCommander) {
        if (command != null) {
            JCommander specified = jCommander.getCommands().get(command);
            if (specified == null) {
                jCommander.getConsole().println(String.format("Command %s not found!", command));
            } else {
                specified.usage();
            }
        } else {
            usage(jCommander);
        }
    }

}
