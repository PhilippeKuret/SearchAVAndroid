package com.example.seach_av_491.ui.file

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.seach_av_491.R
import com.example.seach_av_491.model.File
import com.example.seach_av_491.ui.file.adapter.FileAdapter
import com.example.seach_av_491.ui.fileDetail.FileDetailActivity
import kotlinx.android.synthetic.main.activity_file.*

const val EXTRA_FILE_ID_PATH = "EXTRA_FILE_ID_PATH"
const val REQUEST_CODE_HISTORY_DETAILS = 100

class FileActivity : AppCompatActivity(), FileAdapter.OnFileItemSelectedListener {

    private lateinit var viewModel: FileViewModel
    private lateinit var adapter: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        initRecyclerView()

        viewModel = FileViewModel()

        viewModel.fileLiveData().observe(this, Observer { files ->
            files.let {
                adapter.submitList(it)
            }
        })

        viewModel.startActivityLiveData().observe(this, Observer { fileItem ->
            fileItem?.let { fileId ->
                val intent = Intent(this, FileDetailActivity::class.java)
                intent.putExtra(EXTRA_FILE_ID_PATH, fileId)
                startActivityForResult(intent, REQUEST_CODE_HISTORY_DETAILS)
            }
        })
    }

    override fun onFileItemSelectedListener(file: File) {
        viewModel.fileItemClicked(file)
    }

    private fun initRecyclerView() {
        adapter = FileAdapter(this)
        fileRecyclerView.adapter = adapter
        fileRecyclerView.addItemDecoration(DividerItemDecoration(fileRecyclerView.context, DividerItemDecoration.VERTICAL))
        fileRecyclerView.apply {
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
    }
}