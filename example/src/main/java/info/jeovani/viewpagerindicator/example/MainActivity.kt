package info.jeovani.viewpagerindicator.example

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import info.jeovani.viewpagerindicator.constants.PagerItemType
import info.jeovani.viewpagerindicator.constants.PagerOrientation
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.backgroundColor

class MainActivity : AppCompatActivity() {

    private val imageIdList: ArrayList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.library_name)

        imageIdList.add(R.drawable.imga)
        imageIdList.add(R.drawable.imgb)
        imageIdList.add(R.drawable.imgc)
        imageIdList.add(R.drawable.imgd)
        imageIdList.add(R.drawable.imge)
        imageIdList.add(R.drawable.imgf)

        val pageAdapter = ExamplePagerAdapter(this, imageIdList)
        mainViewPager.adapter = pageAdapter

        mainViewPagerIndicator.itemsCount = imageIdList.size
        mainViewPagerIndicatorVertical.itemsCount = imageIdList.size

        mainRb1.isChecked = true

        mainRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.mainRb1 -> {
                    example1()
                }
                R.id.mainRb2 -> {
                    example2()
                }
                R.id.mainRb3 -> {
                    example3()
                }
                R.id.mainRb4 -> {
                    example4()
                }
                R.id.mainRb5 -> {
                    example5()
                }
                R.id.mainRb6 -> {
                    example6()
                }
            }
        }

    }

    private fun example1(){
        mainViewPagerIndicator.visibility = View.VISIBLE
        mainViewPagerIndicatorVertical.visibility = View.GONE

        mainViewPagerIndicator.itemType = PagerItemType.OVAL
        mainViewPagerIndicator.itemSelectedColors = arrayListOf(Color.BLUE, Color.CYAN)
        mainViewPagerIndicator.itemsUnselectedColors = arrayListOf(Color.GRAY, Color.BLACK)
        mainViewPagerIndicator.itemElevation = 2
        mainViewPagerIndicator.itemWidth = 9
        mainViewPagerIndicator.itemHeight = 9
        mainViewPagerIndicator.itemMargin = 3

        mainViewPagerIndicator.backgroundColor = Color.TRANSPARENT
    }

    private fun example2(){
        mainViewPagerIndicator.visibility = View.VISIBLE
        mainViewPagerIndicatorVertical.visibility = View.GONE

        mainViewPagerIndicator.itemType = PagerItemType.RECTANGLE
        mainViewPagerIndicator.itemSelectedColors = arrayListOf(Color.parseColor("#1a237e"), Color.parseColor("#304ffe"))
        mainViewPagerIndicator.itemsUnselectedColors = arrayListOf(Color.parseColor("#f5f5f5"), Color.parseColor("#eeeeee"))
        mainViewPagerIndicator.itemElevation = 2
        mainViewPagerIndicator.itemWidth = 9
        mainViewPagerIndicator.itemHeight = 9
        mainViewPagerIndicator.itemMargin = 3

        mainViewPagerIndicator.backgroundColor = Color.TRANSPARENT
    }

    private fun example3(){
        mainViewPagerIndicator.visibility = View.VISIBLE
        mainViewPagerIndicatorVertical.visibility = View.GONE

        mainViewPagerIndicator.itemType = PagerItemType.OVAL
        mainViewPagerIndicator.itemSelectedColors = arrayListOf(Color.parseColor("#01579b"))
        mainViewPagerIndicator.itemsUnselectedColors = arrayListOf(Color.parseColor("#bdbdbd"))
        mainViewPagerIndicator.itemElevation = 0
        mainViewPagerIndicator.itemWidth = 9
        mainViewPagerIndicator.itemHeight = 9
        mainViewPagerIndicator.itemMargin = 3

        mainViewPagerIndicator.backgroundColor = Color.parseColor("#b2ebf2")
    }


    private fun example4(){
        mainViewPagerIndicator.visibility = View.VISIBLE
        mainViewPagerIndicatorVertical.visibility = View.GONE

        mainViewPagerIndicator.itemType = PagerItemType.RECTANGLE
        mainViewPagerIndicator.itemSelectedColors = arrayListOf(Color.parseColor("#bf360c"), Color.parseColor("#ff7043"))
        mainViewPagerIndicator.itemsUnselectedColors = arrayListOf(Color.parseColor("#cfd8dc"), Color.parseColor("#90a4ae"))
        mainViewPagerIndicator.itemElevation = 2
        mainViewPagerIndicator.itemWidth = 16
        mainViewPagerIndicator.itemHeight = 4
        mainViewPagerIndicator.itemMargin = 2

        mainViewPagerIndicator.backgroundColor = Color.TRANSPARENT
    }

    private fun example5(){
        mainViewPagerIndicator.visibility = View.GONE
        mainViewPagerIndicatorVertical.visibility = View.VISIBLE

        mainViewPagerIndicatorVertical.orientation = PagerOrientation.VERTICAL
        mainViewPagerIndicatorVertical.itemType = PagerItemType.OVAL
        mainViewPagerIndicatorVertical.itemSelectedColors = arrayListOf(Color.parseColor("#004d40"), Color.parseColor("#4db6ac"))
        mainViewPagerIndicatorVertical.itemsUnselectedColors = arrayListOf(Color.parseColor("#eeeeee"), Color.parseColor("#9e9e9e"))
        mainViewPagerIndicatorVertical.itemElevation = 2
        mainViewPagerIndicatorVertical.itemWidth = 9
        mainViewPagerIndicatorVertical.itemHeight = 9
        mainViewPagerIndicatorVertical.itemMargin = 3

        mainViewPagerIndicatorVertical.backgroundColor = Color.TRANSPARENT
    }

    private fun example6(){
        mainViewPagerIndicator.visibility = View.GONE
        mainViewPagerIndicatorVertical.visibility = View.VISIBLE

        mainViewPagerIndicatorVertical.orientation = PagerOrientation.VERTICAL
        mainViewPagerIndicatorVertical.itemType = PagerItemType.RECTANGLE
        mainViewPagerIndicatorVertical.itemSelectedColors = arrayListOf(Color.parseColor("#c62828"), Color.parseColor("#b71c1c"))
        mainViewPagerIndicatorVertical.itemsUnselectedColors = arrayListOf(Color.parseColor("#8d6e63"), Color.parseColor("#d7ccc8"))
        mainViewPagerIndicatorVertical.itemElevation = 2
        mainViewPagerIndicatorVertical.itemWidth = 16
        mainViewPagerIndicatorVertical.itemHeight = 5
        mainViewPagerIndicatorVertical.itemMargin = 3

        mainViewPagerIndicatorVertical.backgroundColor = Color.TRANSPARENT
    }

}
