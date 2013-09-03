package spire.contrib.jscience

import org.jscience.mathematics.number._

import spire.macros.Auto
import spire.algebra._
import spire.implicits._

trait Instances {
  implicit val jsciComplexOrder = Auto.java.order[Complex]
  implicit val jsciComplexField = Auto.java.field[Complex](Complex.ZERO, Complex.ONE)

  implicit val float64Order = Auto.java.order[Float64]
  implicit val float64Field = Auto.java.field[Float64](Float64.ZERO, Float64.ONE)

  implicit val floatingPointOrder = Auto.java.order[FloatingPoint]
  implicit val floatingPointField = Auto.java.field[FloatingPoint](FloatingPoint.ZERO, FloatingPoint.ONE)

  implicit val jsciRationalOrder = Auto.java.order[Rational]
  implicit val jsciRationalField = Auto.java.field[Rational](Rational.ZERO, Rational.ONE)

  implicit val jsciRealOrder = Auto.java.order[Real]
  implicit val jsciRealField = Auto.java.field[Real](Real.ZERO, Real.ONE)

  implicit val largeIntegerOrder = Auto.java.order[LargeInteger]
  implicit val largeIntegerRing = Auto.java.euclideanRing[LargeInteger](LargeInteger.ZERO, LargeInteger.ONE)

  // Note: The other integer types don't provide mod operators, so we use ring.

  implicit val integer64Order = Auto.java.order[Integer64]
  implicit val integer64Ring = Auto.java.ring[Integer64](Integer64.ZERO, Integer64.ONE)

  implicit val moduloIntegerOrder = Auto.java.order[ModuloInteger]
  implicit val moduloIntegerRing = Auto.java.rig[ModuloInteger](ModuloInteger.ZERO, ModuloInteger.ONE)
}
