/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import simulation.emulation.Emulator;
import simulation.emulation.constant.EmulatorState;
import simulation.emulation.execution.Instructions;
import simulation.rendering.Timer;
import simulation.rendering.UILayer;
import simulation.rendering.Window;

public class Simulation {

  private static final int CLOCK_FREQUENCY = 100;
  private final Window window;
  private final Timer timer;
  private final Emulator emulator;
  private final UILayer uiLayer;

  public static void main(String[] args) throws IOException {
    if (args[0] != null) {
      Instructions.dumpInstructionSet("dump/instruction_set.md");
      Instructions.dumpProgramROM(false, "dump/ROM_dump.md");
      Instructions.dumpProgramROM(true, "dump/ROM_dump.bin");
      new Simulation(args[0]);
    }
  }

  public Simulation(String file) throws IOException {
    timer = new Timer();
    emulator = new Emulator();
    uiLayer = new UILayer(emulator);
    window = new Window("Example", 480, 360);
    init(file);
    loop();
    window.cleanUp();
  }

  private void init(String file) throws IOException {
    try (FileInputStream programFis = new FileInputStream(file)) {
      if (programFis.available() > 0xFFFF) {
        throw new IOException("Program can not be more than 1KB");
      }
      emulator.writeMemory(programFis.readAllBytes());
    }
    uiLayer.decompile(emulator.getCurrentInstructionAddress(), false);
  }

  private void loop() {
    double accumulator = 0f;
    double frametime;
    double interval = 1f / CLOCK_FREQUENCY;
    boolean down = false;

    // While running
    while (!window.windowShouldClose()) {
      window.newFrame();

      emulator.update();
      frametime = timer.getElapsedTime();
      // Clock as many times as needed to respect the number of updates per second
      if (emulator.getState() == EmulatorState.RUN) {
        accumulator += frametime;
        if (accumulator >= interval) {
          clock();
          accumulator = 0;
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
      uiLayer.render(frametime);

      // Draw the frame
      window.endFrame();

      // sync();
    }
  }

  private void clock() {
    if (emulator.clock()) {
      uiLayer.decompile(emulator.getCurrentInstructionAddress(), true);
    }
  }
}
