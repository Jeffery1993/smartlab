package com.jeffery.smartlab.cmd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jeffery.smartlab.consts.CommandEnum;
import com.jeffery.smartlab.core.AbstractCommand;
import com.jeffery.smartlab.core.Processor;
import com.jeffery.smartlab.core.Workspace;
import org.apache.commons.collections.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Parameters(commandDescription = "Load one or more variables from disk")
public class LoadCommand extends AbstractCommand {

    @Parameter(names = {"--all", "-a"}, description = "Load all variables to the workspace")
    private boolean all;

    @Parameter(names = {"--name", "-n"}, description = "Load the specified variable")
    private List<String> names;

    @Parameter(names = {"--filename", "-f"}, description = "Specify the file name")
    private String fileName = "dump.json";

    @Override
    public CommandEnum getType() {
        return CommandEnum.LOAD;
    }

    @Override
    public void run(JCommander jCommander) {
        if (all || CollectionUtils.isNotEmpty(names)) {
            load();
            jCommander.getConsole().println("Load successfully!");
        } else {
            usage(jCommander);
        }
    }

    private void load() {
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.forName("utf-8")))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                Map<String, Object> row = JSON.parseObject(line, new TypeReference<Map<String, Object>>() {
                });
                if (all || (CollectionUtils.isNotEmpty(names) && names.contains(row.keySet().iterator().next()))) {
                    Workspace.getEnv().putAll(row);
                }
            }
        } catch (IOException e) {
            Processor.CONSOLE.println(e.getMessage());
        }
    }

}
