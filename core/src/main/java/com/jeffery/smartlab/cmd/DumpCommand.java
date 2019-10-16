package com.jeffery.smartlab.cmd;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jeffery.smartlab.consts.CommandEnum;
import com.jeffery.smartlab.core.AbstractCommand;
import com.jeffery.smartlab.core.Processor;
import com.jeffery.smartlab.core.Workspace;
import org.apache.commons.collections.CollectionUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Parameters(commandDescription = "Dump one or more variables to disk")
public class DumpCommand extends AbstractCommand {

    @Parameter(names = {"--all", "-a"}, description = "Dump all variables in the workspace")
    private boolean all;

    @Parameter(names = {"--name", "-n"}, description = "Dump the specified variable")
    private List<String> names;

    @Parameter(names = {"--filename", "-f"}, description = "Specify the file name")
    private String fileName = "dump.json";

    @Override
    public CommandEnum getType() {
        return CommandEnum.DUMP;
    }

    @Override
    public void run(JCommander jCommander) {
        if (all) {
            if (Workspace.getEnv().isEmpty()) {
                jCommander.getConsole().println("Empty workspace!");
            } else {
                save(Workspace.getEnv());
                jCommander.getConsole().println("Dump successfully!");
            }
        } else if (CollectionUtils.isNotEmpty(names)) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (String name : names) {
                Object value = Workspace.getEnv().get(name);
                if (value != null) {
                    map.put(name, value);
                } else {
                    jCommander.getConsole().println(String.format("Variable with name [%s] does not exist!", name));
                }
            }
            if (!map.isEmpty()) {
                save(map);
                jCommander.getConsole().println("Dump successfully!");
            }
        } else {
            usage(jCommander);
        }
    }

    private void save(Map<String, Object> variables) {
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), Charset.forName("utf-8")))) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                writer.write(JSON.toJSONString(Collections.singletonMap(entry.getKey(), entry.getValue())));
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            Processor.CONSOLE.println(e.getMessage());
        }
    }

}
