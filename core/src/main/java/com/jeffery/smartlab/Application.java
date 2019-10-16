package com.jeffery.smartlab;

import com.jeffery.smartlab.core.Processor;
import org.opencv.core.Core;

import java.util.Scanner;

public class Application {

    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (Throwable t) {
            Processor.CONSOLE.println(t.getMessage());
        }
    }

    public static void main(String[] args) {
        Processor.initialize();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                Processor.doProcess(line);
            }
        }
    }

}
