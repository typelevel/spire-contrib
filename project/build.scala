import sbt._
import Keys._

import sbtrelease._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.Utilities._

import com.typesafe.sbt.pgp.PgpKeys._

object SpireContribBuild extends Build {

  val spireVersion = "0.4.0-M4"


  lazy val publishSignedArtifacts = ReleaseStep(
    action = st => {
      val extracted = st.extract
      val ref = extracted.get(thisProjectRef)
      extracted.runAggregated(publishSigned in Global in ref, st)
    },
    check = st => {
      // getPublishTo fails if no publish repository is set up.
      val ex = st.extract
      val ref = ex.get(thisProjectRef)
      Classpaths.getPublishTo(ex.get(publishTo in Global in ref))
      st
    },
    enableCrossBuild = true
  )

  lazy val standardSettings = Defaults.defaultSettings ++ releaseSettings ++ Seq(
    organization := "org.typelevel",

    licenses := Seq("MIT" → url("http://www.opensource.org/licenses/mit-license.php")),
    homepage := Some(url("http://typelevel.org/")),

    scalaVersion := "2.10.1",
    scalacOptions ++= Seq(
      "-unchecked", "-deprecation",
      "-feature", "-language:implicitConversions", "-language:higherKinds"
    ),

    libraryDependencies ++= Seq(
      "org.spire-math" %% "spire" % spireVersion,
      "org.spire-math" %% "spire-scalacheck-binding" % spireVersion % "test",
      "org.scalatest"  %% "scalatest" % "1.9.1" % "test",
      "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
    ),

    resolvers += Resolver.sonatypeRepo("releases"),

    sourceDirectory <<= baseDirectory(identity),

    publishTo <<= (version).apply { v =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("Snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("Releases" at nexus + "service/local/staging/deploy/maven2")
    },
    credentials += Credentials(
      Option(System.getProperty("build.publish.credentials")) map (new File(_)) getOrElse (Path.userHome / ".ivy2" / ".credentials")
    ),

    // adapted from sbt-release defaults
    // * does not perform `pushChanges`
    // * performs `publish-signed` instead of `publish`
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      publishSignedArtifacts,
      setNextVersion,
      commitNextVersion
    ),

    pomIncludeRepository := Function.const(false),
    pomExtra := (
      <scm>
        <url>https://github.com/typelevel/spire-contrib</url>
        <connection>scm:git:git://github.com/typelevel/spire-contrib.git</connection>
      </scm>
      <developers>
        <developer>
          <id>larsrh</id>
          <name>Lars Hupel</name>
          <url>https://github.com/larsrh</url>
        </developer>
        <developer>
          <id>d_m</id>
          <name>Erik Osheim</name>
          <url>http://github.com/non/</url>
        </developer>
        <developer>
          <id>tixxit</id>
          <name>Tom Switzer</name>
          <url>http://github.com/tixxit/</url>
        </developer>
      </developers>
    )
  )

  lazy val spireContrib = Project(
    id = "spire-contrib",
    base = file("."),
    settings = standardSettings ++ Seq(
      publishArtifact := false
    ),
    aggregate = Seq(apfloat)
  )

  lazy val apfloat = Project(
    id = "apfloat",
    base = file("apfloat"),
    settings = standardSettings ++ Seq(
      name := "spire-apfloat",
      libraryDependencies += "org.apfloat" % "apfloat" % "1.7.1"
    )
  )

}

// vim: expandtab:ts=2:sw=2
