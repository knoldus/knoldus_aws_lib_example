import Dependencies._
import sbt.Test

lazy val commonSettings = Seq(
  version := "1.0",
  ThisBuild / scalaVersion := Versions.ScalaVersion,
  Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD"),
  libraryDependencies ++= Dependencies.Main.All ++ Dependencies.Test.All,
  scalafmtOnCompile := true,
  fork in run := false,
  parallelExecution in Test :=false
)

lazy val root = Project("knoldus-aws-lib-samples", file("."))
  .aggregate(dynamoDb)


lazy val dynamoDb = Project("dynamodb", file("dynamodb"))
  .settings(
    commonSettings,
    name := "dynamodb",
    description := "Dynamodb Sample",
    libraryDependencies ++= Seq(Main.Dynamo)
  )

lazy val kinesis = project.in(file("kinesis"))
  .settings(
    commonSettings,
    name := "kinesis",
    description := "Kinesis Sample",
    libraryDependencies ++= Seq(Main.Kinesis)
  )

lazy val s3 = project.in(file("s3"))
  .settings(
    commonSettings,
    name := "s3",
    description := "s3 Sample",
    libraryDependencies ++= Seq(Main.s3)
  )

lazy val sqs = project.in(file("sqs"))
  .settings(
    commonSettings,
    name := "sqs",
    description := "sqs Sample",
    libraryDependencies ++= Seq(Main.sqs)
  )
