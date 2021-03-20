package com.example.tnelection2021.views
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.ViewPager
import com.example.tnelection2021.utils.ViewUtils
import com.google.android.material.tabs.TabLayout

class CustomTabLayout : TabLayout
{
    private var mTypeface : Typeface? = null

    constructor(context : Context) : super(context)
    {
        init()
    }

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs)
    {
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr)
    {
        init()
    }

    private fun init()
    {
        mTypeface = ViewUtils.getRubikRegularTypeface(context)
    }

    override fun setupWithViewPager(viewPager : ViewPager?)
    {
        super.setupWithViewPager(viewPager)

        if (mTypeface != null)
        {
            this.removeAllTabs()
            val slidingTabStrip = getChildAt(0) as ViewGroup

            val adapter = viewPager?.adapter

            val count = adapter?.count ?: 0

            for (i in 0 until count)
            {
                val tab = this.newTab()
                this.addTab(tab.setText(adapter?.getPageTitle(i)))
                val view = (slidingTabStrip.getChildAt(i) as ViewGroup).getChildAt(1) as AppCompatTextView
                view.setTypeface(mTypeface, Typeface.NORMAL)
            }
        }
    }

}
