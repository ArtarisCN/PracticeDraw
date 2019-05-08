package cn.artaris.customview.ProgressMarkView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import cn.artaris.customview.MainActivity
import cn.artaris.customview.R
import cn.artaris.omview.dialog.ProgressMarkView

/**
 * cn.artaris.hencodepractice.DashBoard
 * CustomView
 * 2019.04.21  19:54
 * @author : artairs
 */
class ProgressMarkFragment:MainActivity.PageFragment() {

    private lateinit var mProgressMarkView: ProgressMarkView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_progress_view, container, false)
        mProgressMarkView = view.findViewById(R.id.progress_mark_view)

        view.findViewById<Button> (R.id.btn_success).setOnClickListener(::onClick)
        view.findViewById<Button> (R.id.btn_failed).setOnClickListener(::onClick)
        view.findViewById<Button> (R.id.btn_loading).setOnClickListener(::onClick)

        return view
    }

    private fun onClick(view: View){
        when(view.id){
            R.id.btn_success->mProgressMarkView.loadSuccess(null)
            R.id.btn_failed->mProgressMarkView.loadFailure(null)
            R.id.btn_loading->mProgressMarkView.loadLoading()
        }
    }

}