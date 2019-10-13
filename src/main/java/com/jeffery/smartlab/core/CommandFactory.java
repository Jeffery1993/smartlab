package com.jeffery.smartlab.core;

import com.beust.jcommander.JCommander;
import com.jeffery.smartlab.cmd.*;
import com.jeffery.smartlab.consts.CommandEnum;

import java.util.ArrayList;
import java.util.List;

public class CommandFactory {

    public static JCommander newJCommander() {
        JCommander.Builder builder = JCommander.newBuilder();
        List<Command> commands = getCommands();
        for (Command command : commands) {
            CommandEnum type = command.getType();
            builder.addCommand(type.getName(), command, type.getAlias());
        }
        return builder.build();
    }

    private static List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new HelpCommand());
        commands.add(new CatCommand());
        commands.add(new ListCommand());
        commands.add(new DumpCommand());
        commands.add(new LoadCommand());
        commands.add(new ClearCommand());
        commands.add(new ExitCommand());
        return commands;
    }

}
