package com.example.seach_av_491.ui.fileDetail

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.seach_av_491.R
import com.example.seach_av_491.ui.file.EXTRA_FILE_ID_PATH
import kotlinx.android.synthetic.main.activity_file_detail.*


class FileDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: FileDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_detail)

        viewModel = FileDetailViewModel()

        viewModel.fileDetailsLiveData().observe(this, Observer {
            it?.let { fileDetail ->
                transcription.setText(fileDetail.transcription)
            }
        })

        viewModel.confirmationDialogLiveData().observe(this, Observer {
            it?.let { isError ->
                showConfirmationDialog(isError)
            }
        })

        if (savedInstanceState == null) {
            val detailsPath = intent.getStringExtra(EXTRA_FILE_ID_PATH)
            detailsPath.let {
                viewModel.getFileTranscription(it)
            }
        }

        saveTranscription.setOnClickListener {
            viewModel.saveTranscription(transcription.text.toString())
        }
    }

    private fun showConfirmationDialog(isError: Boolean) {
        val builder = AlertDialog.Builder(this)
        var message: Int
        if (isError)
            message = R.string.ErrorSave
        else
            message = R.string.SuccessSave

        builder.setMessage(message)
                .setPositiveButton(R.string.ConfirmButton
                ) { dialog, id ->
                    dialog.dismiss()
                }
        builder.create()
    }
}