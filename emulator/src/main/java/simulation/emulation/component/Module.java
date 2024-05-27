/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

public abstract class Module implements Formattable {

  public abstract boolean clock();

  public abstract void update();

  public abstract void reset();
}
