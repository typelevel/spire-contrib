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

  implicit val apfloatOrder = Auto.java.order[Apfloat]
  implicit val apfloatField = Auto.java.field[Apfloat](Apcomplex.ZERO, Apcomplex.ONE)

  // Aprational

  implicit val aprationalOrder = Auto.java.order[Aprational]
  implicit val aprationalField = Auto.java.field[Aprational](Apcomplex.ZERO, Apcomplex.ONE)

  // Apcomplex

  implicit val apcomplexOrder = Auto.java.eq[Apcomplex]
  implicit val apcomplexField = Auto.java.field[Apcomplex](Apcomplex.ZERO, Apcomplex.ONE)

}

// vim: expandtab:ts=2:sw=2
