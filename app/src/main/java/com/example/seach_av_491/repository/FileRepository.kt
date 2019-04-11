package com.example.seach_av_491.repository

import com.example.seach_av_491.model.File
import com.example.seach_av_491.model.FileDetail
import com.example.seach_av_491.service.NetworkEnvironment
import io.reactivex.Single
import timber.log.Timber

class FileRepository {

    private val networkEnvironment = NetworkEnvironment()

    fun getAllFiles(): Single<List<File>> {
        return networkEnvironment.provideAPIService()
                .getFiles()
                .map {
                    Timber.d(it.files[0].id)
                    return@map it.files
                }
                .onErrorReturn {
                    Timber.e(it)
                    return@onErrorReturn null
                }
    }

    fun getFileTranscript(fileId: String): Single<FileDetail> {
        return networkEnvironment.provideAPIService()
                .getFileDetail(fileId)
                .onErrorReturn {
                    Timber.e(it)
                    return@onErrorReturn null
                }
    }

    fun saveTranscript(versionId: String, newTranscription: String): Single<Boolean> {
        return networkEnvironment.provideAPIService()
                .postTranscription(versionId, newTranscription, "philippeinutile@gmail.com")
                .map {
                    return@map true
                }
                .onErrorReturn {
                    return@onErrorReturn false
                }
    }
}