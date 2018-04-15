package info.jeovani.viewpagerindicator.example

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_pager_item.view.*

class ExamplePagerAdapter(private val context: Context, private val imageIdList: ArrayList<Int>): PagerAdapter(){

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_pager_item, container, false)
        view.itemImage.setImageResource(imageIdList[position])
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return imageIdList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

}