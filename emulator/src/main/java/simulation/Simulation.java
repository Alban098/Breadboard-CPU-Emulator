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
  private static final String PROGRAM_FILE = "sample.rom";
  private final Window window;
  private final Timer timer;
  private final Emulator emulator;
  private final UILayer uiLayer;

  public static void main(String[] args) throws IOException {
    Instructions.dumpInstructionSet("instruction_set");
    Instructions.dumpProgramROM(false, "ROM_dump.txt");
    Instructions.dumpProgramROM(true, "ROM_dump.bin");
    new Simulation();
  }

  public Simulation() throws IOException {
    window = new Window("Example", 480, 360);
    timer = new Timer();
    emulator = new Emulator();
    uiLayer = new UILayer(emulator);
    init();
    loop();
    window.cleanUp();
  }

  private void init() throws IOException {
    try (FileInputStream programFis = new FileInputStream(PROGRAM_FILE)) {
      int a = programFis.available();
      if (programFis.available() > 0x100) {
        throw new IOException("Program can not be more than 256 bytes");
      }
      emulator.writeMemory(programFis.readAllBytes());
    }
    uiLayer.decompile(emulator.getCurrentInstructionAddress(), false);
  }

  private void loop() {
    double accumulator = 0f;
    double interval = 1f / CLOCK_FREQUENCY;
    boolean down = false;

    // While running
    while (!window.windowShouldClose()) {
      window.newFrame();

      emulator.update();

      // Clock as many times as needed to respect the number of updates per second
      if (emulator.getState() == EmulatorState.RUN) {
        accumulator += timer.getElapsedTime();
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
      uiLayer.render();

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
