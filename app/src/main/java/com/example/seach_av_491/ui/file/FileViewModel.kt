package com.example.seach_av_491.ui.file

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.seach_av_491.model.File
import com.example.seach_av_491.repository.FileRepository
import com.example.seach_av_491.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FileViewModel : BaseViewModel() {

    private val fileRepository = FileRepository()

    private val fileLiveData = MutableLiveData<List<File>>()
    private val startActivityLiveData = MutableLiveData<String>()

    private var fileList = arrayListOf<File>()

    fun fileLiveData(): LiveData<List<File>> {
        return fileLiveData
    }

    init {
        getFiles()
    }

    fun fileItemClicked(file: File) {
        startActivityLiveData.value = file.id
    }

    fun startActivityLiveData(): LiveData<String> {
        return startActivityLiveData
    }

    private fun getFiles() {
        fileRepository.getAllFiles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                fileList.clear()
                fileLiveData.postValue(it)
            }) { throwable ->
                Timber.e(throwable)
            }.addTo(disposable)
    }
}