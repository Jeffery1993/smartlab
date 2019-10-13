package com.jeffery.smartlab.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jeffery.smartlab.consts.CommandEnum;
import com.jeffery.smartlab.core.AbstractCommand;
import com.jeffery.smartlab.core.Processor;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

@Parameters(commandDescription = "Cat the performance of smart lab")
public class CatCommand extends AbstractCommand {

    @Parameter(names = {"--all", "-a"}, description = "Cat all the status")
    private boolean all;

    @Parameter(names = {"--memory", "-m"}, description = "Cat the memory usage")
    private boolean memory;

    @Parameter(names = {"--gc", "-gc"}, description = "Cat the gc status")
    private boolean gc;

    @Override
    public CommandEnum getType() {
        return CommandEnum.CAT;
    }

    @Override
    public void run(JCommander jCommander) {
        if (all) {
            catMemory();
            catGc();
        } else if (memory) {
            catMemory();
        } else if (gc) {
            catGc();
        } else {
            usage(jCommander);
        }
    }

    public void catMemory() {
        Processor.CONSOLE.println("=== Memory usage ===");
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        long heap = memoryMXBean.getHeapMemoryUsage().getUsed() >> 20;
        long nonHeap = memoryMXBean.getNonHeapMemoryUsage().getUsed() >> 20;
        Processor.CONSOLE.println(String.format("Heap memory used %d MB", heap));
        Processor.CONSOLE.println(String.format("Non-heap memory used %d MB", nonHeap));
    }

    public void catGc() {
        Processor.CONSOLE.println("=== Garbage collection ===");
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            Processor.CONSOLE.println(String.format("%s, GC=%d, GCT=%d ms", garbageCollectorMXBean.getName(), garbageCollectorMXBean.getCollectionCount(), garbageCollectorMXBean.getCollectionTime()));
        }
    }

}
