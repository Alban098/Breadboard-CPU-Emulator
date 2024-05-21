package org.example;


import emulation.Emulator;
import emulation.EmulatorState;
import emulation.component.MemoryModule;
import rendering.CpuLayer;
import rendering.MemoryLayer;
import rendering.Timer;
import rendering.Window;

import java.awt.event.KeyEvent;

public class Main {

    private static final int FPS = 60;
    private static final int CLOCK_FREQUENCY = 5;
    private final Window window;
    private final Timer timer;
    private final Emulator emulator;

    private final MemoryLayer memoryLayer;
    private final CpuLayer cpuLayer;


    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        window = new Window("Example", 480, 360);
        timer = new Timer();
        emulator = new Emulator();
        memoryLayer = new MemoryLayer(emulator.getModule("RAM", MemoryModule.class));
        cpuLayer = new CpuLayer(emulator);
        init();
        loop();
        window.cleanUp();
    }

    private void init() {}

    private void loop() {
        double accumulator = 0f;
        double interval;
        boolean down = false;

        // While running
        while (!window.windowShouldClose()) {
            window.newFrame();

            // Calculate an update duration and get the elapsed time since last loop
            interval = 1f / CLOCK_FREQUENCY;

            emulator.update();

            // Update as many times as needed to respect the number of updates per second
            if (emulator.getState() == EmulatorState.RUN) {
                accumulator += timer.getElapsedTime();
                while (accumulator >= interval) {
                    clock();
                    accumulator -= interval;
                }
            }
            if (emulator.getState() == EmulatorState.DEBUG) {
                timer.getElapsedTime();
                if (window.isKeyPressed(KeyEvent.VK_SPACE) && !down) {
                    clock();
                    down = true;
                } else if (down && !window.isKeyPressed(KeyEvent.VK_SPACE)) {
                    down = false;
                }
            }

            // Render the frame
            memoryLayer.render();
            cpuLayer.render();

            // Draw the frame
            window.endFrame();

            sync();
        }
    }

    private void clock() {
        emulator.clock();
        if (emulator.hasMemoryChanged()) {
            cpuLayer.decompile();
        }
    }

    private void sync() {
        float loopSlot = 1f / FPS;
        double endTime = timer.getLastFrameTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
        }
    }
}
