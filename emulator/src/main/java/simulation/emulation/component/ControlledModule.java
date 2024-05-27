/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.component;

public abstract class ControlledModule extends Module {

  protected final ControlUnitModule controlUnit;

  protected ControlledModule(ControlUnitModule controlUnit) {
    this.controlUnit = controlUnit;
  }
}
