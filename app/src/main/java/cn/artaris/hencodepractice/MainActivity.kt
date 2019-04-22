package cn.artaris.hencodepractice

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import cn.artaris.hencodepractice.DashBoard.DashBoardFragment
import cn.artaris.hencodepractice.ProgressMark.ProgressMarkFragment

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager: ViewPager

    private var mPageModel = ArrayList<PageModel>()
    private var mPageFragment = ArrayList<PageFragment>()


    init {

        mPageFragment.add(PageFragment.newInstance(R.layout.view_round_image))
        mPageFragment.add(PageFragment.newInstance(R.layout.view_pie_chart))
        mPageFragment.add(DashBoardFragment())
        mPageFragment.add(ProgressMarkFragment())

        mPageModel.add(PageModel(R.layout.view_round_image, R.string.round_image))
        mPageModel.add(PageModel(R.layout.view_pie_chart, R.string.pie_chart))
        mPageModel.add(PageModel(R.layout.view_dash_board, R.string.dash_board))
        mPageModel.add(PageModel(R.layout.view_dash_board, R.string.progress_mark))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.view_paper)
        pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment {
                return mPageFragment[p0]
            }

            override fun getCount(): Int {
                return mPageModel.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return getString(mPageModel[position].title)
            }
        }

        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(pager)
    }


    open class PageFragment:Fragment(){
        @LayoutRes
        internal var layoutRes: Int = 0

        companion object{
            fun newInstance(@LayoutRes sampleLayoutRes: Int): PageFragment {
                val fragment = PageFragment()
                val args = Bundle()
                args.putInt("layoutRes", sampleLayoutRes)
                fragment.arguments = args
                return fragment
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val args = arguments
            if (args != null) {
                layoutRes = args.getInt("layoutRes")
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_page, container, false)

            val sampleStub = view.findViewById(R.id.view_stub) as ViewStub
            sampleStub.layoutResource = layoutRes
            sampleStub.inflate()

            return view
        }
    }

    class PageModel(@LayoutRes val layoutRes:Int,@StringRes val title:Int)

}
