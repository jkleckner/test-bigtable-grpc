package com.cphy.seq2bigtable

import java.io.IOException

import com.google.api.gax.batching.Batcher
import com.google.cloud.bigtable.data.v2.models.RowMutationEntry
import com.typesafe.config.{Config, ConfigFactory}

import com.google.cloud.bigtable.admin.v2.models.CreateTableRequest
import com.google.cloud.bigtable.admin.v2.{BigtableTableAdminClient, BigtableTableAdminSettings}
import com.google.cloud.bigtable.data.v2.{BigtableDataClient, BigtableDataSettings}
import com.google.cloud.bigtable.admin.v2.models.GCRules

import collection.JavaConversions._

import com.google.protobuf.ByteString

object Seq2Bigtable {

  // Usage: com.cphy.seq2bigtable.Seq2Bigtable <path-to-seq-file> <table-where-to-import> <column_family>
  // Start and set the bigtable emulator environment variable BIGTABLE_EMULATOR_HOST before running with:
  // gcloud beta emulators bigtable start &
  // $(gcloud beta emulators bigtable env-init)

  def toByteString(byteArray: Array[Byte]): ByteString = {
    ByteString.copyFrom(byteArray)
  }

  def main(args: Array[String]): Unit = {
    val appConfig: Config = ConfigFactory.load()
    val projectId = appConfig.getString("seq2bigtable.project")
    val instanceId = appConfig.getString("seq2bigtable.instance")
    val tableName = appConfig.getString("seq2bigtable.table")
    val columnFamily = appConfig.getString("seq2bigtable.column_family")
    val printData = appConfig.getBoolean("seq2bigtable.verbose")
    println(s"projectId: $projectId instanceId: $instanceId")
    val debugPrintTakeBytes: Int = 256


    try {
      val dataSettings: BigtableDataSettings = BigtableDataSettings
        .newBuilder
        .setProjectId(projectId)
        .setInstanceId(instanceId)
        .build()
      val dataClient: BigtableDataClient = BigtableDataClient.create(dataSettings)

      val adminSettings: BigtableTableAdminSettings = BigtableTableAdminSettings
        .newBuilder
        .setProjectId(projectId)
        .setInstanceId(instanceId)
        .build()
      val adminClient: BigtableTableAdminClient = BigtableTableAdminClient.create(adminSettings)

      val bulkMutationBatcher: Batcher[RowMutationEntry, Void] = dataClient.newBulkMutationBatcher(tableName)

      println(s"Data project: ${adminClient.getProjectId} admin instance: ${adminClient.getInstanceId}")
      println(s"Admin project: ${adminClient.getProjectId} admin instance: ${adminClient.getInstanceId}")

      try {
        if (adminClient.exists(tableName)) {
          println(s"Target table exists: $tableName")
        } else {
          println(s"Creating target table: $tableName and column family: $columnFamily with maxVersions=1")
          adminClient
            .createTable(
              CreateTableRequest
                .of(tableName)
                .addFamily(columnFamily, GCRules.GCRULES.maxVersions(1))
            )
        }
        /* */
        println(s"Deleting target table: $tableName")
        adminClient
          .deleteTable(tableName)
      } catch {
        case t: Throwable =>
          println(s"Caught Throwable: Message: ${t.getMessage}")
          t.printStackTrace()
          throw t
      } finally {
      }
    } catch {
      case _: InterruptedException =>
        // Ignore the InterruptedException
        ()
      case e: IOException =>
        e.printStackTrace()
      case t: Throwable =>
        t.printStackTrace()
    }
  }
}
