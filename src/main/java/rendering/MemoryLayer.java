package rendering;

import emulation.component.MemoryModule;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;

/**
 * This class represent the DebugLayer in charge of displaying the current state of the MMU
 * alongside the state of all registers of the Game Boy
 */
public class MemoryLayer extends Layer {

    private static final int HIGHLIGH_DURATION = 64;
    private int highlight = -1;
    private int highlight_cooldown = 0;
    private final ImString go_to = new ImString();
    private final ImBoolean gradient = new ImBoolean();
    private final MemoryModule memoryModule;

    public MemoryLayer(MemoryModule memoryModule) {
        this.memoryModule = memoryModule;
    }

    /**
     * Render the layer to the screen
     */
    public void render() {
        ImGui.begin("Memory");
        ImGui.setWindowSize(540, 350);
        ImGui.pushItemWidth(100);
        if (ImGui.inputText(": Search Address", go_to)) {
            go_to.set(go_to.get().replaceAll("[^A-Fa-f0-9]*[ ]*", ""));
            if (go_to.get().isEmpty())
                go_to.set("0");
            highlight = Integer.decode("0x" + go_to.get());
            highlight_cooldown = HIGHLIGH_DURATION;
        }
        ImGui.sameLine(440);
        ImGui.checkbox("Gradient", gradient);
        ImGui.separator();
        ImGui.text("   ");
        for (int i = 0x0; i <= 0xF; i++) {
            ImGui.sameLine();
            ImGui.textColored(255, 255, 0, 255, String.format("%02X", i));
        }
        for (int i = 0x0; i <= 0xF; i++) {
            int addr = i << 4;
            ImGui.textColored(255, 255, 0, 255, String.format("%02X ", addr));
            for (int data = 0x0; data <= 0xF; data++) {
                ImGui.sameLine();
                addr = (i << 4) | data;
                int read = memoryModule.readMemory(addr);
                if (addr == highlight) {
                    if (read == 0x00)
                        ImGui.textColored(128 + 128 * highlight_cooldown / HIGHLIGH_DURATION, 128 - 128 * highlight_cooldown / HIGHLIGH_DURATION, 128 - 128 * highlight_cooldown / HIGHLIGH_DURATION, 255, String.format("%02X", read));
                    else
                        ImGui.textColored(255, 255 - 255 * highlight_cooldown / HIGHLIGH_DURATION, 255 - 255 * highlight_cooldown / HIGHLIGH_DURATION, 255, String.format("%02X", read));
                    if (highlight_cooldown-- == 0)
                        highlight = -1;
                } else if (!gradient.get()) {
                    if (read == 0x00)
                        ImGui.textColored(128, 128, 128, 255, String.format("%02X", read));
                    else
                        ImGui.text(String.format("%02X", read));
                } else
                    ImGui.textColored(read, read, read, 255, String.format("%02X", read));
            }
            ImGui.sameLine();
            ImGui.text(" | ");
            StringBuilder dataString = new StringBuilder();
            for (int data = 0x0; data <= 0xF; data++) {
                char read = (char) memoryModule.readMemory((i << 4) | data);
                dataString.append(read < 0x20 ? "." : read);
            }
            ImGui.sameLine();
            ImGui.text(dataString.toString());
        }
        ImGui.pushItemWidth(450);
        ImGui.end();
    }
}
