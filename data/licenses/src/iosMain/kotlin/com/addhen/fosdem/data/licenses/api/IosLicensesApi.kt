package com.addhen.fosdem.data.licenses.api

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.posix.memcpy

class IosLicensesApi(
  private val dispatchers: AppCoroutineDispatchers,
) : LicensesApi {
  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
    val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }
    json.decodeFromString(readBundleFile("licenses.json").decodeToString())
  }

  @OptIn(ExperimentalForeignApi::class)
  private fun readBundleFile(path: String): ByteArray {
    val fileManager = NSFileManager.defaultManager()
    val composeResourcesPath = NSBundle.mainBundle.resourcePath + "/" + path
    val contentsAtPath: NSData? = fileManager.contentsAtPath(composeResourcesPath)
    if (contentsAtPath != null) {
      val byteArray = ByteArray(contentsAtPath.length.toInt())
      byteArray.usePinned {
        memcpy(it.addressOf(0), contentsAtPath.bytes, contentsAtPath.length)
      }
      return byteArray
    }
    error("File $path not found in Bundle")
  }
}
