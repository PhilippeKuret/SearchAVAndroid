package com.example.seach_av_491.ui.fileDetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.seach_av_491.model.FileDetail
import com.example.seach_av_491.repository.FileRepository
import com.example.seach_av_491.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.regex.Pattern


class FileDetailViewModel : BaseViewModel() {

    private val fileRepository = FileRepository()

    private val transcriptionLiveData = MutableLiveData<FileDetail>()
    private val confirmationDialog = MutableLiveData<Boolean>()

    private lateinit var versionId : String

    fun fileDetailsLiveData(): LiveData<FileDetail> {
        return transcriptionLiveData
    }

    fun confirmationDialogLiveData(): LiveData<Boolean> {
        return confirmationDialog
    }

    fun getFileTranscription(fileId: String) {
        fileRepository.getFileTranscript(fileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    versionId = it.id
                    val removedHTMLFileDetail = it.copy(transcription = clearHTML(it.transcription))
                    transcriptionLiveData.postValue(removedHTMLFileDetail)
                }) {
                    Timber.e(it)
                }.addTo(disposable)
    }

    fun saveTranscription(transcription: String) {
        fileRepository.saveTranscript(versionId, transcription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    confirmationDialog.postValue(it)
                }) {
                    Timber.e(it)
                    confirmationDialog.postValue(false)
                }.addTo(disposable)
    }

    private fun clearHTML(transcript : String) : String {
        if (transcript.isEmpty()) {
            return transcript
        }

        val regex = Pattern.compile("<.+?>")

        val m = regex.matcher(transcript)
        return m.replaceAll("")
    }
}