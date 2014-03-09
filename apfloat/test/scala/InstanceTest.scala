package spire.contrib.apfloat

import spire.laws._

import org.scalacheck.{Gen, Arbitrary}
import org.scalacheck.Arbitrary._

import org.apfloat._

import org.scalatest.FunSuite

import org.typelevel.discipline.scalatest.Discipline

class InstanceTests extends FunSuite with Discipline {

  implicit def ApintArbitrary = Arbitrary(Gen.oneOf(
    arbitrary[Int] map (new Apint(_)),
    arbitrary[math.BigInt] map (bi => new Apint(bi.bigInteger))
  ))

  implicit def AprationalArbitrary = Arbitrary(Gen.oneOf(
    // we do not want to end up with very large numerators/denominators, so
    // generate rationals based on 32 bit integers
    arbitrary[Int] map (bi => new Aprational(new Apint(bi))),
    arbitrary[(Int, Int)] filter (_._2 != 0) map { case (num, den) => new Aprational(new Apint(num), new Apint(den)) }
  ))

  implicit def ApcomplexArbitrary = Arbitrary(
    arbitrary[(Apint, Apint)] map { case (real, imag) => new Apcomplex(real, imag) }
  )

  checkAll("Apint",      RingLaws[Apint].euclideanRing)
  checkAll("Aprational", RingLaws[Aprational].field)
  checkAll("Apcomplex",  RingLaws[Apcomplex].euclideanRing)

  // field tests for Apreal and Apcomplex are left as an exercise to the reader

}

// vim: expandtab:ts=2:sw=2
