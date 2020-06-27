package com.weaponoid.uiguru.model

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import com.weaponoid.uiguru.util.failedToast
import com.weaponoid.uiguru.util.successToast

class Download(private val url: String, private val fileName: String, val context: Context) {
    private lateinit var downloadManager: DownloadManager
    private var downloadReference: Long = 0

    fun downloadFile() {

        try {
            val downloadRequest = DownloadManager.Request(Uri.parse(url))
            downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadRequest.apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                setTitle(fileName)
                setDescription("Downloading UI Resource...")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

            }
            context.registerReceiver(
                receiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
            downloadReference = downloadManager.enqueue(downloadRequest)
        } catch (e: Exception) {
            println(e)
        }


    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadId != downloadReference) {
                    context.unregisterReceiver(this)
                    return
                }
                val query = DownloadManager.Query()
                query.setFilterById(downloadReference)
                val cursor = downloadManager.query(query)
                cursor?.let {
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                            context.successToast("File saved in Downloads Folder")

                        } else if (DownloadManager.STATUS_FAILED == cursor.getInt(columnIndex)) {
                            context.failedToast("Download Failed")
                        }
                    }
                    cursor.close()
                }

                context.unregisterReceiver(this)

            }
        }
    }

}