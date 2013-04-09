package spire.contrib.apfloat

import org.apfloat._

import spire.macros.Auto
import spire.algebra._
import spire.math._
import spire.ops._

trait Instances {

  // Apint

  implicit val apintOrder = Auto.java.order[Apint]
  implicit val apintEuclideanRing = Auto.java.euclideanRing[Apint](Apcomplex.ZERO, Apcomplex.ONE)

  // Apfloat

  implicit val apfloatIsReal = new ApfloatIsReal { }
  implicit val apfloatField = Auto.java.field[Apfloat](Apcomplex.ZERO, Apcomplex.ONE)
  implicit def apfloatTrig = ApfloatTrig(2048)
  implicit val apfloatNRoot = new ApfloatNRoot { }

  def ApfloatTrig(constants: Int): Trig[Apfloat] = new ApfloatTrig { val constantPrecision = constants }

  // Aprational

  implicit val aprationalOrder = Auto.java.order[Aprational]
  implicit val aprationalField = Auto.java.field[Aprational](Apcomplex.ZERO, Apcomplex.ONE)

  // Apcomplex

  implicit val apcomplexOrder = Auto.java.eq[Apcomplex]
  implicit val apcomplexField = Auto.java.field[Apcomplex](Apcomplex.ZERO, Apcomplex.ONE)

}

trait ApfloatIsReal extends IsReal[Apfloat] {
  private final val half = new Apfloat(0.5)

  def compare(a: Apfloat, b: Apfloat): Int = a.compareTo(b)
  override def eqv(a: Apfloat, b: Apfloat): Boolean = a == b
  def signum(a: Apfloat): Int = a.signum()
  def abs(a: Apfloat): Apfloat = ApfloatMath.abs(a)
  def ceil(a: Apfloat): Apfloat = a.ceil()
  def floor(a: Apfloat): Apfloat = a.floor()
  def round(a: Apfloat): Apfloat = (a + half).floor()
  def isWhole(a: Apfloat): Boolean = a.truncate() == a
  def toDouble(a: Apfloat): Double = a.doubleValue()
}

trait ApfloatNRoot extends NRoot[Apfloat] {
  override def sqrt(a: Apfloat): Apfloat = ApfloatMath.sqrt(a)
  def nroot(a: Apfloat, n: Int): Apfloat = ApfloatMath.root(a, n)
  def fpow(a: Apfloat, b: Apfloat): Apfloat = ApfloatMath.pow(a, b)
}

trait ApfloatTrig extends Trig[Apfloat] {
  private final val d180 = new Apfloat(180D)
  def constantPrecision: Int

  lazy val e: Apfloat = exp(new Apfloat(1, constantPrecision))
  lazy val pi: Apfloat = ApfloatMath.pi(constantPrecision)

  def exp(a: Apfloat): Apfloat = ApfloatMath.exp(a)
  def log(a: Apfloat): Apfloat = ApfloatMath.log(a)

  def sin(a: Apfloat): Apfloat = ApfloatMath.sin(a)
  def cos(a: Apfloat): Apfloat = ApfloatMath.cos(a)
  def tan(a: Apfloat): Apfloat = ApfloatMath.tan(a)

  def asin(a: Apfloat): Apfloat = ApfloatMath.asin(a)
  def acos(a: Apfloat): Apfloat = ApfloatMath.acos(a)
  def atan(a: Apfloat): Apfloat = ApfloatMath.atan(a)
  def atan2(y: Apfloat, x: Apfloat): Apfloat = ApfloatMath.atan2(y, x)

  def sinh(x: Apfloat): Apfloat = ApfloatMath.sinh(x)
  def cosh(x: Apfloat): Apfloat = ApfloatMath.cosh(x)
  def tanh(x: Apfloat): Apfloat = ApfloatMath.tanh(x)

  def toRadians(a: Apfloat): Apfloat = pi * (a / d180)
  def toDegrees(a: Apfloat): Apfloat = d180 * (a / pi)
}

// vim: expandtab:ts=2:sw=2
