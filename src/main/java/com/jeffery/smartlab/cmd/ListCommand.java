package com.jeffery.smartlab.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jeffery.smartlab.consts.CommandEnum;
import com.jeffery.smartlab.core.AbstractCommand;
import com.jeffery.smartlab.core.Workspace;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@Parameters(commandDescription = "List one or more variables in the workspace")
public class ListCommand extends AbstractCommand {

    @Parameter(names = {"--all", "-a"}, description = "List all variables in the workspace")
    private boolean all;

    @Parameter(names = {"--name", "-n"}, description = "List the specified variable")
    private List<String> names;

    @Override
    public CommandEnum getType() {
        return CommandEnum.LIST;
    }

    @Override
    public void run(JCommander jCommander) {
        if (all) {
            if (Workspace.getEnv().isEmpty()) {
                jCommander.getConsole().println("Empty workspace!");
            } else {
                Workspace.getEnv().forEach((k, v) -> jCommander.getConsole().println(makeOneRow(k, v)));
            }
        } else if (CollectionUtils.isNotEmpty(names)) {
            for (String name : names) {
                Object value = Workspace.getEnv().get(name);
                if (value != null) {
                    jCommander.getConsole().println(makeOneRow(name, value));
                } else {
                    jCommander.getConsole().println(String.format("Variable with name [%s] does not exist!", name));
                }
            }
        } else {
            usage(jCommander);
        }
    }

    private String makeOneRow(String key, Object value) {
        String valueAsStr = (value != null && value.getClass().isArray()) ? Arrays.toString((Object[]) value) :
                String.valueOf(value);
        return String.format("%s = %s [%s]", key, valueAsStr, value == null ? "null" : value.getClass().getSimpleName());
    }

}
