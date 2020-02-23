name := "test-bigtable-grpc"

organization := "com.example"

//Do not add version in this file. It is maintained by sbt-dynver and tied to tags of the form vn.n.n for releases
// See: https://github.com/dwijnand/sbt-dynver
// version := "2.0.0-SNAPSHOT"

startYear := Some(2020)

organizationName := "Example.com"

scalaVersion in ThisBuild := "2.11.12"

crossScalaVersions := Seq("2.11.12")

val sparkVersion = "2.4.5"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding", "UTF-8",
//  "-optimise",
  "-Yclosure-elim",
  "-Yinline",
  "-Yno-adapted-args",
  "-Xlint", // equivalent to "-Ywarn-all" removed in 2.11
  "-Yinline-warnings",
//  "-Xfatal-warnings",  // Some inline function in Jerkson will not be inlined.
  "-Xverify",
  "-feature",
  "-language:higherKinds",
//"-Xprint:all",
  "-language:postfixOps"
)

scalacOptions in Test ++= Seq("-Yrangepos")

// Fix javadoc to not complain about Xlint. See following link on why:
//  https://github.com/sbt/sbt/issues/355#issuecomment-3817629
javacOptions in (Compile, compile) ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")


/* dependencies */
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion, // % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion, // % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion // % "provided"
)
//libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.3.27.v20190418"
//libraryDependencies += "org.eclipse.jetty" % "jetty-util" %  "9.3.27.v20190418"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

//  // https://github.com/grpc/grpc-java/blob/master/SECURITY.md#netty

// https://mvnrepository.com/artifact/com.google.cloud/google-cloud-bigtable
// works: libraryDependencies += "com.google.cloud" % "google-cloud-bigtable" % "1.7.1"
libraryDependencies += "com.google.cloud" % "google-cloud-bigtable" % "1.10.0"
// https://mvnrepository.com/artifact/com.google.guava/guava
// libraryDependencies += "com.google.guava" % "guava" % "28.1-jre"
// // https://mvnrepository.com/artifact/com.google.android/annotations
// libraryDependencies += "com.google.android" % "annotations" % "4.1.1.4" % "provided"
// // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
// libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.9"
// // https://mvnrepository.com/artifact/io.netty/netty-tcnative-boringssl-static
// libraryDependencies += "io.netty" % "netty-tcnative-boringssl-static" % "2.0.26.Final"

/* sbt behavior */
logLevel in compile := Level.Warn

traceLevel := 5

offline := false

