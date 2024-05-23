package simulation.emulation.component;

public abstract class Module implements Formattable {

    public abstract boolean clock();

    public abstract void update();

    public abstract void reset();
}
