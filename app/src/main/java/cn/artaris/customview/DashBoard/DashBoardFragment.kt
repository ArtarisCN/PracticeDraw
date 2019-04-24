package cn.artaris.customview.DashBoard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import cn.artaris.customview.MainActivity
import cn.artaris.customview.R

/**
 * cn.artaris.hencodepractice.DashBoard
 * CustomView
 * 2019.04.21  19:54
 * @author : artairs
 */
class DashBoardFragment:MainActivity.PageFragment() {

    private lateinit var mDashView: DashBoardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)
        mDashView = view.findViewById(R.id.dash_board_view)

        view.findViewById<Button> (R.id.btn_value_0).setOnClickListener(::onClick)
        view.findViewById<Button> (R.id.btn_value_5).setOnClickListener(::onClick)
        view.findViewById<Button> (R.id.btn_value_10).setOnClickListener(::onClick)
        view.findViewById<Button> (R.id.btn_value_15).setOnClickListener(::onClick)
        view.findViewById<Button> (R.id.btn_value_20).setOnClickListener(::onClick)

        view.findViewById<Button> (R.id.plus).setOnClickListener(::onClick)
        view.findViewById<Button> (R.id.minus).setOnClickListener(::onClick)

        return view
    }

    private fun onClick(view: View){
        when(view.id){
            R.id.btn_value_0->mDashView.setIndex(0)
            R.id.btn_value_5->mDashView.setIndex(5)
            R.id.btn_value_10->mDashView.setIndex(10)
            R.id.btn_value_15->mDashView.setIndex(15)
            R.id.btn_value_20->mDashView.setIndex(20)

            R.id.minus->mDashView.setIndex(mDashView.mIndex - 1)
            R.id.plus->mDashView.setIndex(mDashView.mIndex + 1)
        }
    }

}