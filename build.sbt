ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.12"

lazy val root = (project in file("."))
  .settings(
    name := "data-warehouses-spark"
  )

resolvers += "DataStax Repo" at "https://repo.datastax.com/public-repos/"
// Make sure that following DSE version matches your DSE cluster version.
val dseVersion = "6.8.9"
libraryDependencies += "com.datastax.dse" % "dse-spark-dependencies" % dseVersion % "provided" exclude(
  "com.azure", "azure-storage-blob")
