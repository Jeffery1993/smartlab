package com.jeffery.smartlab.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jeffery.smartlab.consts.CommandEnum;
import com.jeffery.smartlab.core.AbstractCommand;
import com.jeffery.smartlab.core.Workspace;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@Parameters(commandDescription = "Clear one or more variables in the workspace")
public class ClearCommand extends AbstractCommand {

    @Parameter(names = {"--all", "-a"}, description = "Clear all variables in the workspace")
    private boolean all;

    @Parameter(names = {"--name", "-n"}, description = "Clear the specified variable")
    private List<String> names;

    @Override
    public CommandEnum getType() {
        return CommandEnum.CLEAR;
    }

    @Override
    public void run(JCommander jCommander) {
        if (all) {
            Workspace.getEnv().clear();
            jCommander.getConsole().println("Clear all successfully!");
        } else if (CollectionUtils.isNotEmpty(names)) {
            for (String name : names) {
                Object value = Workspace.getEnv().remove(name);
                if (value != null) {
                    jCommander.getConsole().println(String.format("Clear %s successfully!", name));
                } else {
                    jCommander.getConsole().println(String.format("Variable with name [%s] does not exist!", name));
                }
            }
        } else {
            usage(jCommander);
        }
    }

}
