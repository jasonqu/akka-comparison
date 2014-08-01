package com.oracle.sec6.immutable

case class ImmutableRGBInScala(val red: Int, val green: Int, val blue: Int, val name: String) {
  require(red >= 0 && red <= 255)
  require(green >= 0 && green <= 255)
  require(blue >= 0 && blue <= 255)

  def getRGB(): Int = {
    (red << 16) | (green << 8) | blue;
  }

  def invert(): ImmutableRGBInScala = {
    ImmutableRGBInScala(255 - red,
      255 - green,
      255 - blue,
      "Inverse of " + name);
  }
}

object ImmutableRGBInScala extends App {
  val black = ImmutableRGBInScala(0, 0, 0, "black")
  val err = ImmutableRGBInScala(-1, 256, 0, "err")
}