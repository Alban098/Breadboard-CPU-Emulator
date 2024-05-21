package rendering;

import imgui.ImGui;

/**
 * This class represent the common behaviour of all ImGui Layer
 */
public abstract class Layer {

    private boolean visible;

    /**
     * Create a new Layer
     * setting it as non-visible
     */
    public Layer() {
        visible = false;
    }

    /**
     * Placeholder render method
     * called to render the layer to the screen
     */
    public abstract void render();

    /**
     * Return whether the layer is visible or not
     * @return is the layer visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the layer as visible or non-visible
     * @param visible should the layer be visible or not
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Print a register to the screen as hex and binary
     * @param addr the address of the register in memory
     * @param name the name of the register to print
     * @param value the value of the register
     */
    protected void inlineRegister(int addr, String name, int value) {
        ImGui.textColored(0, 255, 255, 255, String.format("    $%04X", addr));
        ImGui.sameLine();
        ImGui.textColored(255, 0, 255, 255, name);
        ImGui.sameLine();
        ImGui.text(String.format("$%02X", value) + "(" + Utils.binaryString(value, 8) + ")");
    }


}
