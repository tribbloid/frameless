val sparkVersion = "4.0.1"
val spark40Version = "4.0.1"
val catsCoreVersion = "2.13.0"
val catsEffectVersion = "3.6.3"
val catsMtlVersion = "1.6.0"
val scalatest = "3.2.19"
val scalatestplus = "3.1.0.0-RC2"
val shapeless = "2.3.13"
val scalacheck = "1.19.0"
val scalacheckEffect = "1.0.4"
val refinedVersion = "0.11.3"
val nakedFSVersion = "0.1.0"

// Centralized Scala versions used in this build
val Scala213 = "2.13.16"

ThisBuild / tlBaseVersion := "0.16"

ThisBuild / crossScalaVersions := Seq(Scala213)
ThisBuild / scalaVersion := Scala213
ThisBuild / coverageScalacPluginVersion := "2.3.0"

lazy val root = project
  .in(file("."))
  .enablePlugins(NoPublishPlugin)
  .settings(crossScalaVersions := Nil)
  .aggregate(
    `root-spark40`,
    docs
  )

lazy val `root-spark40` = project
  .in(file(".spark40"))
  .enablePlugins(NoPublishPlugin)
  .aggregate(
    core,
    `cats-spark40`,
    `dataset-spark40`,
    `refined-spark40`,
    `ml-spark40`
  )

lazy val core =
  project.settings(name := "frameless-core").settings(framelessSettings)

lazy val cats = project
  .settings(name := "frameless-cats")
  .settings(crossScalaVersions := Seq(Scala213))
  .settings(catsSettings)
  .dependsOn(dataset % "test->test;compile->compile;provided->provided")

lazy val `cats-spark40` = project
  .settings(name := "frameless-cats-spark40")
  .settings(sourceDirectory := (cats / sourceDirectory).value)
  .settings(catsSettings)
  .settings(spark40Settings)
  .settings(crossScalaVersions := Seq(Scala213))
  .dependsOn(
    `dataset-spark40` % "test->test;compile->compile;provided->provided"
  )

lazy val dataset = project
  .settings(name := "frameless-dataset")
  .settings(crossScalaVersions := Seq(Scala213))
  .settings(
    Compile / unmanagedSourceDirectories += baseDirectory.value / "src" / "main" / "spark-3.4+"
  )
  .settings(
    Test / unmanagedSourceDirectories += baseDirectory.value / "src" / "test" / "spark-3.3+"
  )
  .settings(datasetSettings)
  .settings(sparkDependencies(sparkVersion))
  .dependsOn(core % "test->test;compile->compile")

lazy val `dataset-spark40` = project
  .settings(name := "frameless-dataset-spark40")
  .settings(sourceDirectory := (dataset / sourceDirectory).value)
  .settings(
    Compile / unmanagedSourceDirectories += (dataset / baseDirectory).value / "src" / "main" / "spark-3.4+"
  )
  .settings(
    Test / unmanagedSourceDirectories += (dataset / baseDirectory).value / "src" / "test" / "spark-3.3+"
  )
  .settings(datasetSettings)
  .settings(sparkDependencies(spark40Version))
  .settings(spark40Settings)
  .settings(crossScalaVersions := Seq(Scala213))
  .dependsOn(core % "test->test;compile->compile")

lazy val refined = project
  .settings(name := "frameless-refined")
  .settings(crossScalaVersions := Seq(Scala213))
  .settings(refinedSettings)
  .dependsOn(dataset % "test->test;compile->compile;provided->provided")

lazy val `refined-spark40` = project
  .settings(name := "frameless-refined-spark40")
  .settings(sourceDirectory := (refined / sourceDirectory).value)
  .settings(refinedSettings)
  .settings(spark40Settings)
  .settings(crossScalaVersions := Seq(Scala213))
  .dependsOn(
    `dataset-spark40` % "test->test;compile->compile;provided->provided"
  )

lazy val ml = project
  .settings(name := "frameless-ml")
  .settings(crossScalaVersions := Seq(Scala213))
  .settings(mlSettings)
  .settings(sparkMlDependencies(sparkVersion))
  .dependsOn(
    core % "test->test;compile->compile",
    dataset % "test->test;compile->compile;provided->provided"
  )

lazy val `ml-spark40` = project
  .settings(name := "frameless-ml-spark40")
  .settings(sourceDirectory := (ml / sourceDirectory).value)
  .settings(mlSettings)
  .settings(sparkMlDependencies(spark40Version))
  .settings(spark40Settings)
  .settings(crossScalaVersions := Seq(Scala213))
  .dependsOn(
    core % "test->test;compile->compile",
    `dataset-spark40` % "test->test;compile->compile;provided->provided"
  )

lazy val docs = project
  .in(file("mdocs"))
  .settings(framelessSettings)
  .settings(crossScalaVersions := Seq(Scala213))
  .settings(scalacOptions --= Seq("-Xfatal-warnings", "-Ywarn-unused-import"))
  .enablePlugins(TypelevelSitePlugin)
  .settings(sparkDependencies(sparkVersion, Compile))
  .settings(sparkMlDependencies(sparkVersion, Compile))
  .settings(
    addCompilerPlugin(
      "org.typelevel" % "kind-projector" % "0.13.3" cross CrossVersion.full
    ),
    scalacOptions += "-Ydelambdafy:inline",
    libraryDependencies += "org.typelevel" %% "mouse" % "1.3.2"
  )
  .dependsOn(dataset, cats, ml)

def sparkDependencies(
    sparkVersion: String,
    scope: Configuration = Provided
  ) = Seq(
  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % scope,
    "org.apache.spark" %% "spark-sql" % sparkVersion % scope
  )
)

def sparkMlDependencies(sparkVersion: String, scope: Configuration = Provided) =
  Seq(
    libraryDependencies += "org.apache.spark" %% "spark-mllib" % sparkVersion % scope
  )

lazy val catsSettings = framelessSettings ++ Seq(
  addCompilerPlugin(
    "org.typelevel" % "kind-projector" % "0.13.3" cross CrossVersion.full
  ),
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % catsCoreVersion,
    "org.typelevel" %% "cats-effect" % catsEffectVersion,
    "org.typelevel" %% "cats-mtl" % catsMtlVersion,
    "org.typelevel" %% "alleycats-core" % catsCoreVersion,
    "org.typelevel" %% "scalacheck-effect" % scalacheckEffect % Test
  )
)

lazy val datasetSettings =
  framelessSettings ++ framelessTypedDatasetREPL ++ Seq(
    mimaBinaryIssueFilters ++= {
      import com.typesafe.tools.mima.core._

      val imt = ProblemFilters.exclude[IncompatibleMethTypeProblem](_)
      val mc = ProblemFilters.exclude[MissingClassProblem](_)
      val dmm = ProblemFilters.exclude[DirectMissingMethodProblem](_)

      // TODO: Remove have version bump
      Seq(
        imt("frameless.TypedEncoder.mapEncoder"),
        imt("frameless.TypedEncoder.arrayEncoder"),
        imt("frameless.RecordEncoderFields.deriveRecordCons"),
        imt("frameless.RecordEncoderFields.deriveRecordLast"),
        mc("frameless.functions.FramelessLit"),
        mc(f"frameless.functions.FramelessLit$$"),
        dmm("frameless.functions.package.litAggr"),
        dmm("org.apache.spark.sql.FramelessInternals.column")
      )
    },
    coverageExcludedPackages := "org.apache.spark.sql.reflection",
    libraryDependencies += "com.globalmentor" % "hadoop-bare-naked-local-fs" % nakedFSVersion % Test exclude (
      "org.apache.hadoop",
      "hadoop-commons"
    )
  )

lazy val refinedSettings =
  framelessSettings ++ framelessTypedDatasetREPL ++ Seq(
    libraryDependencies += "eu.timepit" %% "refined" % refinedVersion
  )

lazy val mlSettings = framelessSettings ++ framelessTypedDatasetREPL

lazy val scalac212Options = Seq(
  "-Xlint:-missing-interpolator,-unused,_",
  "-target:jvm-1.8",
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard",
  "-language:existentials",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-Xfuture",
  "-Ypartial-unification"
)

lazy val scalac213Options = {
  val exclusions = Set(
    "-Yno-adapted-args",
    "-Ywarn-unused-import",
    "-Xfuture",
    // type TraversableOnce in package scala is deprecated, symbol literal is deprecated; use Symbol("a") instead
    "-Xfatal-warnings",
    "-Ypartial-unification"
  )

  // https://github.com/scala/bug/issues/12072
  val options = Seq("-Xlint:-byname-implicit")
  scalac212Options.filter(s => !exclusions.contains(s)) ++ options
}

lazy val scalacOptionSettings = Def.setting {
  def baseScalacOptions(scalaVersion: String) =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, 13)) => scalac213Options
      case _             => scalac212Options
    }

  baseScalacOptions(scalaVersion.value)
}

lazy val framelessSettings = Seq(
  scalacOptions ++= scalacOptionSettings.value,
  Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oDF"),
  libraryDependencies ++= Seq(
    "com.chuusai" %% "shapeless" % shapeless,
    "org.scalatest" %% "scalatest" % scalatest % Test,
    "org.scalatestplus" %% "scalatestplus-scalacheck" % scalatestplus % Test,
    "org.scalacheck" %% "scalacheck" % scalacheck % Test
  ),
  Test / javaOptions ++= Seq(
    "-Xmx4G",
    "-ea",
    "--add-opens=java.base/java.lang=ALL-UNNAMED",
    "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
    "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
    "--add-opens=java.base/java.io=ALL-UNNAMED",
    "--add-opens=java.base/java.net=ALL-UNNAMED",
    "--add-opens=java.base/java.nio=ALL-UNNAMED",
    "--add-opens=java.base/java.util=ALL-UNNAMED",
    "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED",
    "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED",
    "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
    "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED",
    "--add-opens=java.base/sun.security.action=ALL-UNNAMED",
    "--add-opens=java.base/sun.util.calendar=ALL-UNNAMED",
    "-Djdk.reflect.useDirectMethodHandle=false"
  ),
  Test / fork := true,
  Test / parallelExecution := false,
  mimaPreviousArtifacts ~= {
    _.filterNot(_.revision == "0.11.0") // didn't release properly
  },
  /**
   * The old Scala XML is pulled from Scala 2.12.x.
   *
   * [error] (update) found version conflict(s) in library dependencies; some are suspected to be binary incompatible:
   * [error]
   * [error] 	* org.scala-lang.modules:scala-xml_2.12:2.3.0 (early-semver) is selected over 1.0.6
   * [error] 	    +- org.scoverage:scalac-scoverage-reporter_2.12:2.0.7 (depends on 2.4.0)
   * [error] 	    +- org.scala-lang:scala-compiler:2.12.16              (depends on 1.0.6)
   */
  libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
) ++ consoleSettings

lazy val spark40Settings = Seq[Setting[_]](
  tlVersionIntroduced := Map("2.13" -> "0.16.0"),
  // New Spark 4.0 cross modules; no previous artifacts to compare for MiMa
  mimaPreviousArtifacts := Set.empty
)

lazy val consoleSettings = Seq(
  Compile / console / scalacOptions ~= {
    _.filterNot("-Ywarn-unused-import" == _)
  },
  Test / console / scalacOptions := (Compile / console / scalacOptions).value
)

lazy val framelessTypedDatasetREPL = Seq(
  initialize ~= { _ => // Color REPL
    val ansi = System.getProperty("sbt.log.noformat", "false") != "true"
    if (ansi) System.setProperty("scala.color", "true")
  },
  console / initialCommands :=
    """
      |import org.apache.spark.{SparkConf, SparkContext}
      |import org.apache.spark.sql.SparkSession
      |import frameless.functions.aggregate._
      |import frameless.syntax._
      |
      |val conf = new SparkConf().setMaster("local[*]").setAppName("frameless repl").set("spark.ui.enabled", "false")
      |implicit val spark = SparkSession.builder().config(conf).appName("REPL").getOrCreate()
      |
      |import spark.implicits._
      |
      |spark.sparkContext.setLogLevel("WARN")
      |
      |import frameless.TypedDataset
    """.stripMargin,
  console / cleanupCommands :=
    """
      |spark.stop()
    """.stripMargin
)

ThisBuild / organization := "org.typelevel"
ThisBuild / licenses := List(
  "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")
)
ThisBuild / developers := List(
  "OlivierBlanvillain" -> "Olivier Blanvillain",
  "adelbertc" -> "Adelbert Chang",
  "imarios" -> "Marios Iliofotou",
  "kanterov" -> "Gleb Kanterov",
  "non" -> "Erik Osheim",
  "jeremyrsmith" -> "Jeremy Smith",
  "cchantep" -> "Cédric Chantepie",
  "pomadchin" -> "Grigory Pomadchin"
).map {
  case (username, fullName) =>
    tlGitHubDev(username, fullName)
}

ThisBuild / tlCiReleaseBranches := Seq("master")
ThisBuild / tlSitePublishBranch := Some("master")

val roots = List("root-spark40")

ThisBuild / githubWorkflowBuildMatrixAdditions += "project" -> roots

ThisBuild / githubWorkflowEnv += "SBT_OPTS" -> "-Xms1g -Xmx4g"
