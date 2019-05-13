package com.butterfly.klepto.faildragon.utils

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.butterfly.klepto.faildragon.BaseApplication
import com.butterfly.klepto.faildragon.apimanagers.FileCacheApiManager
import com.butterfly.klepto.faildragon.extensions.path
import com.butterfly.klepto.faildragon.modal.Feed
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.Exception


class FileCacher:KoinComponent {
    private val fileCacheApiManager by inject<FileCacheApiManager>()
    private val filesInQue:Set<String> = setOf()


    private fun downloadFile(url:String){

        if(!isFileInCache(url))
        if (!filesInQue.contains(url.path())) {
            filesInQue.plus(url.path())
            fileCacheApiManager.service.cacheFile(url).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        object : AsyncTask<Void, Void, Void>() {
                            override fun doInBackground(vararg voids: Void): Void? {
                                val writtenToDisk =
                                    writeResponseBodyToDisk(response.body()!!, BaseApplication.applicationContext(), url)

                                Log.d("JUDE", "file download was a success? $writtenToDisk")
                                return null
                            }
                        }.execute()
                    }
                }

            })


        }
    }

    private fun writeResponseBodyToDisk(body: ResponseBody,context: Context,url:String): Boolean {
        try {
            val futureStudioIconFile = File(context.cacheDir,url.path())

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    Log.d("JUDE", "file download: $fileSizeDownloaded of $fileSize")

                }

                outputStream.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()
                filesInQue.minus(url.path())
                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }


    }
    @Throws(Exception::class)
    fun cacheFile(feeds: MutableList<Feed>?) {
        feeds?.forEach {feed->
            downloadFile(feed.videoUrl?:"")
        }

    }


}

