package com.jeffery.smartlab.consts;

public enum CommandEnum {

    HELP("help"),

    CAT("cat", "monitor"),

    LIST("list", "ls"),

    DUMP("dump", "write"),

    LOAD("load", "read"),

    CLEAR("clear", "clr"),

    EXIT("exit", "quit");

    private String name;
    private String[] alias;

    CommandEnum(String name, String... alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String[] getAlias() {
        return alias;
    }

}
