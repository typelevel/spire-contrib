package spire.contrib.jscience

import org.jscience.mathematics.number._

import spire.algebra.{ RingLaws, LawChecker }

import org.scalacheck.{ Gen, Arbitrary }
import org.scalacheck.Arbitrary._

class InstanceTests extends LawChecker {

  implicit def ComplexArbitrary = Arbitrary(
    arbitrary[(Double, Double)] map { case (r, i) => Complex.valueOf(r, i) }
  )

  implicit def Float64Arbitrary = Arbitrary(
    arbitrary[Double] map (Float64.valueOf(_))
  )

  implicit def FloatingPointArbitrary = Arbitrary(
    arbitrary[Double] map (FloatingPoint.valueOf(_))
  )

  implicit def RationalArbitrary = Arbitrary(Gen.oneOf(
    // we do not want to end up with very large numerators/denominators, so
    // generate rationals based on 32 bit integers
    arbitrary[Long] map { n => Rational.valueOf(n, 1) },
    arbitrary[(Long, Long)] filter (_._2 != 0) map { case (n, d) => Rational.valueOf(n, d) }
  ))

  implicit def RealArbitrary = Arbitrary(
    arbitrary[Double] map (Real.valueOf(_))
  )

  implicit def LargeIntegerArbitrary = Arbitrary(Gen.oneOf(
    arbitrary[Long] map (LargeInteger.valueOf(_)),
    arbitrary[math.BigInt] map (n => LargeInteger.valueOf(n.bigInteger))
  ))

  implicit def Integer64Arbitrary = Arbitrary(
    arbitrary[Long] map { n => Integer64.valueOf(n) }
  )

  // Ever the problem, floating point stuff doesn't follow the laws, so its
  // commented out for now.

  //checkAll("Complex", RingLaws[Complex].field)
  //checkAll("Float64", RingLaws[Float64].field)
  //checkAll("FloatingPoint", RingLaws[FloatingPoint].field)
  checkAll("Rational", RingLaws[Rational].field)
  //checkAll("Real", RingLaws[Real].field)
  checkAll("LargeInteger", RingLaws[LargeInteger].euclideanRing)
  checkAll("Integer64", RingLaws[Integer64].ring)
  // checkAll("ModuloInteger", RingLaws[ModuloInteger].rig)
}
