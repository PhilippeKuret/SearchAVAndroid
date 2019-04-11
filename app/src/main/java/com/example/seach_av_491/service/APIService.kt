package com.example.seach_av_491.service

import com.example.seach_av_491.model.FileDetail
import com.example.seach_av_491.model.FileResponse
import io.reactivex.Single
import retrofit2.http.*

interface APIService {

    @GET("file/GetAllWithUsernames/")
    fun getFiles(
    ): Single<FileResponse>

    @GET("version/GetActivebyFileId/{fileId}")
    fun getFileDetail(
            @Path("fileId") fileId: String
    ): Single<FileDetail>

    @FormUrlEncoded
    @POST("Transcription/SaveTranscript/{versionId}")
    fun postTranscription(
            @Path("versionId") versionId: String,
            @Field("newTranscript") newTranscript : String,
            @Field("email") email : String
    ): Single<FileDetail>
}