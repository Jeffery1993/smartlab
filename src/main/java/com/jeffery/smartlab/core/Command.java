package com.jeffery.smartlab.core;

import com.beust.jcommander.JCommander;
import com.jeffery.smartlab.consts.CommandEnum;

public interface Command {

    CommandEnum getType();

    void run(JCommander jCommander);

}
