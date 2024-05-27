/*
* Copyright (c) 2024, @Author Alban098
*
* Code licensed under MIT license.
*/
package simulation.emulation.constant;

public enum Flag {
  OVERFLOW(0b1000),
  ZERO(0b0100),
  CARRY(0b0010),
  NEGATIVE(0b0001);

  final int mask;

  Flag(int mask) {
    this.mask = mask;
  }

  public int getMask() {
    return mask;
  }
}
