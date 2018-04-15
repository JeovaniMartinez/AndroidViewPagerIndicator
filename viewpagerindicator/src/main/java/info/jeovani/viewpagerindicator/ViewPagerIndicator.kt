package info.jeovani.viewpagerindicator

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Build
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import info.jeovani.viewpagerindicator.constants.PagerOrientation
import info.jeovani.viewpagerindicator.constants.PagerItemType
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2dip

/**
 * Copyright 2018 Jeovani Martínez
 *
 * jeovani.info
 * github.com/jeovanimartinez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Class for generating and controlling the pager
 */
@Suppress("MemberVisibilityCanBePrivate")
class ViewPagerIndicator : ConstraintLayout {

    companion object {
        private const val LOG_TAG = "ViewPagerIndicator"

        private const val DEFAULT_RESOURCE_ID = -1

        private const val DEFAULT_COLOR = 1
    }

    //ViewPager to associate pager
    var viewPager: ViewPager? = null
        set(value) {
            field = value
            if (!isFromInit) {
                build()
            }
        }

    //Total ViewPager adapter elements
    var itemsCount: Int = 0
        set(value) {
            if (value >= 0) {
                field = value
                if (!isFromInit) {
                    build()
                }
            } else {
                Log.e(LOG_TAG, "The number of items cannot be negative")
            }
        }

    //Current position selected
    var itemSelected: Int = 0
        set(value) {
            if (value in 0..itemsCount) {
                field = value
                onPageChangeListener?.onPageSelected(value)
            } else {
                Log.w(LOG_TAG, "The selected item position is invalid")
            }
        }

    //@see PagerType
    var itemType: Int = PagerItemType.OVAL
        set(value) {
            if (value in PagerItemType.OVAL..PagerItemType.RECTANGLE) {
                field = value
                if (!isFromInit) {
                    updateDrawables()
                }
            } else {
                Log.w(LOG_TAG, "The PagerItemType is invalid")
            }
        }

    //@see PagerOrientation
    var orientation: Int = PagerOrientation.HORIZONTAL
        set(value) {
            if (value in PagerOrientation.HORIZONTAL..PagerOrientation.VERTICAL) {
                field = value
                if (!isFromInit) {
                    when (field) {
                        PagerOrientation.HORIZONTAL -> centralView?.orientation = LinearLayout.HORIZONTAL
                        PagerOrientation.VERTICAL -> centralView?.orientation = LinearLayout.VERTICAL
                    }
                    updateIndicatorViews()
                    invalidate()
                    requestLayout()
                }
            } else {
                Log.w(LOG_TAG, "The PagerOrientation is invalid")
            }
        }

    //Array of colors for the selected element of the pager, use one color for solid and two for gradient
    var itemSelectedColors: ArrayList<Int> = arrayListOf(Color.BLUE, Color.CYAN)
        set(value) {
            if (value.size in 1..2) {
                if (value.size == 2) {
                    field[0] = value[0]
                    field[1] = value[1]
                } else {
                    field[0] = value[0]
                    field[1] = value[0]
                }
                if (!isFromInit) {
                    updateDrawables()
                }
            } else {
                Log.w(LOG_TAG, "The selectedColors size must be between zero and one, use one for solid color, two for gradient")
            }
        }

    //Array of colors for the unselected elements of the pager, use one color for solid and two for gradient
    var itemsUnselectedColors: ArrayList<Int> = arrayListOf(Color.GRAY, Color.BLACK)
        set(value) {
            if (value.size in 1..2) {
                if (value.size == 2) {
                    field[0] = value[0]
                    field[1] = value[1]
                } else {
                    field[0] = value[0]
                    field[1] = value[0]
                }
                if (!isFromInit) {
                    updateDrawables()
                }
            } else {
                Log.w(LOG_TAG, "The itemsUnselectedColors size must be between zero and one, use one for solid color, two for gradient")
            }
        }

    //Elevation for each item, is a integer and the unity is converted to dp
    var itemElevation: Int = 2
        set(value) {
            field = value
            if (!isFromInit) {
                updateIndicatorViews()
            }
        }

    //Width for each item, is a integer and the unity is converted to dp
    var itemWidth: Int = 9
        set(value) {
            field = value
            if (!isFromInit) {
                updateIndicatorViews()
            }
        }

    //Height for each item, is a integer and the unity is converted to dp
    var itemHeight: Int = 9
        set(value) {
            field = value
            if (!isFromInit) {
                updateIndicatorViews()
            }
        }

    //Margin for each item, is a integer and the unity is converted to dp
    var itemMargin: Int = 3
        set(value) {
            field = value
            if (!isFromInit) {
                updateIndicatorViews()
            }
        }

    //Arraylist for contain all items
    private var pageIndicators: ArrayList<ImageView>? = null
    //Drawable to assign selected background
    private var selectedBackground: GradientDrawable? = null
    //Drawable to assign unselected background
    private var unselectedBackground: GradientDrawable? = null

    //Id of the ViewPager, from attribute set
    private var viewPagerId = DEFAULT_RESOURCE_ID
    //Listener from page changed
    private var onPageChangeListener: ViewPager.OnPageChangeListener? = null

    //View to contain all items
    private var centralView: LinearLayout? = null

    //Determine if the call is from init
    private var isFromInit = false

    //Use for keep selected item on properties changed
    private var currentPagerItemSelected = 0

    //To use from the class
    constructor(context: Context) : super(context, null)

    //To use from the xml
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        isFromInit = true

        //Convert the default values to dp
        itemElevation = context.dip(itemElevation)
        itemWidth = context.dip(itemWidth)
        itemHeight = context.dip(itemHeight)
        itemMargin = context.dip(itemMargin)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator, 0, 0)

        try {

            viewPagerId = typedArray.getResourceId(R.styleable.ViewPagerIndicator_vpi_view_pager, DEFAULT_RESOURCE_ID)
            itemsCount = typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_items_count, itemsCount)
            itemSelected = typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_item_selected, itemSelected)
            itemType = typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_item_type, itemType)
            orientation = typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_orientation, orientation)
            itemSelectedColors = arrayListOf(typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_selected_primary_color, DEFAULT_COLOR),
                    typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_selected_secondary_color, DEFAULT_COLOR))
            itemsUnselectedColors = arrayListOf(typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_unselected_primary_color, DEFAULT_COLOR),
                    typedArray.getInt(R.styleable.ViewPagerIndicator_vpi_unselected_secondary_color, DEFAULT_COLOR))
            itemElevation = context.px2dip(typedArray.getDimension(R.styleable.ViewPagerIndicator_vpi_item_elevation, itemElevation.toFloat()).toInt()).toInt()
            itemWidth = context.px2dip(typedArray.getDimension(R.styleable.ViewPagerIndicator_vpi_item_width, itemWidth.toFloat()).toInt()).toInt()
            itemHeight = context.px2dip(typedArray.getDimension(R.styleable.ViewPagerIndicator_vpi_item_height, itemHeight.toFloat()).toInt()).toInt()
            itemMargin = context.px2dip(typedArray.getDimension(R.styleable.ViewPagerIndicator_vpi_item_margin, itemMargin.toFloat()).toInt()).toInt()

            assignInitColors()

        } catch (ex: Exception) {
            Log.i(LOG_TAG, ex.toString())
        } finally {
            typedArray.recycle()
        }
    }

    /**
     * Assign the colors from init, if receive one or two colors, and combine if is necessary
     */
    private fun assignInitColors() {

        if (itemSelectedColors[0] == DEFAULT_COLOR && itemSelectedColors[1] == DEFAULT_COLOR) {
            //If not specify color, use the default colors
            itemSelectedColors[0] = Color.BLUE
            itemSelectedColors[1] = Color.CYAN
        } else if (itemSelectedColors[0] == DEFAULT_COLOR) {
            //If only specific primary color, assign it to secondary color
            itemSelectedColors[0] = itemSelectedColors[1]
        } else if (itemSelectedColors[1] == DEFAULT_COLOR) {
            //If only specific secondary color, assign it to primary color
            itemSelectedColors[1] = itemSelectedColors[0]
        }

        if (itemsUnselectedColors[0] == DEFAULT_COLOR && itemsUnselectedColors[1] == DEFAULT_COLOR) {
            itemsUnselectedColors[0] = Color.GRAY
            itemsUnselectedColors[1] = Color.BLACK
        } else if (itemsUnselectedColors[0] == DEFAULT_COLOR) {
            itemsUnselectedColors[0] = itemsUnselectedColors[1]
        } else if (itemsUnselectedColors[1] == DEFAULT_COLOR) {
            itemsUnselectedColors[1] = itemsUnselectedColors[0]
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        //If is called from xml and send the reference
        if (viewPagerId != DEFAULT_RESOURCE_ID) {
            try {
                viewPager = (this.parent as View).findViewById(viewPagerId) as ViewPager
                //If ViewPager is correct, generate the views
                build()
            } catch (ex: Exception) {
                Log.e(LOG_TAG, ex.toString())
            }
        }

        isFromInit = false
    }

    /**
     * Generate view and events for the pager
     */
    private fun build() {
        if (viewPager == null) {
            Log.e(LOG_TAG, "The viewPager cannot be null")
            return
        } else {

            if (itemSelected > itemsCount) {
                itemSelected = 0
                Log.w(LOG_TAG, "The selected item cannot bigger than items count")
            }

            generateDrawables()

            generateViews()

            pageChangeListener()

            invalidate()
            requestLayout()
        }
    }

    /**
     * Generate the drawables with the settings for the image view elements
     */
    private fun generateDrawables() {

        selectedBackground = GradientDrawable(Orientation.BOTTOM_TOP, intArrayOf(itemSelectedColors[0], itemSelectedColors[1]))

        unselectedBackground = GradientDrawable(Orientation.BOTTOM_TOP, intArrayOf(itemsUnselectedColors[0], itemsUnselectedColors[1]))

        when (itemType) {
            PagerItemType.OVAL -> {
                selectedBackground?.shape = GradientDrawable.OVAL
                unselectedBackground?.shape = GradientDrawable.OVAL
            }
            PagerItemType.RECTANGLE -> {
                selectedBackground?.shape = GradientDrawable.RECTANGLE
                unselectedBackground?.shape = GradientDrawable.RECTANGLE
            }
        }
    }

    /**
     * Update the drawables
     */
    private fun updateDrawables() {
        generateDrawables()
        pageIndicators?.forEachIndexed { i, it ->
            it.backgroundDrawable = if (i == currentPagerItemSelected) {
                selectedBackground
            } else {
                unselectedBackground
            }
        }
        invalidate()
        requestLayout()
    }

    /**
     * Generate a one image view foreach item adapter, and add the views to the parent
     */
    private fun generateViews() {

        super.removeAllViews()

        pageIndicators = arrayListOf()

        //Create a central view to centrate the elements
        centralView = LinearLayout(context)

        //This elevation is not visible, it is so that it can be above of other elements
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.setElevation(context.dip(20).toFloat())
        }

        val centralViewParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        centralView?.orientation = if (orientation == PagerOrientation.HORIZONTAL) {
            LinearLayout.HORIZONTAL //Ver
        } else {
            LinearLayout.VERTICAL //hor
        }

        centralViewParams.bottomToBottom = ConstraintSet.PARENT_ID
        centralViewParams.topToTop = ConstraintSet.PARENT_ID
        centralViewParams.startToStart = ConstraintSet.PARENT_ID
        centralViewParams.endToEnd = ConstraintSet.PARENT_ID

        //For each item, generate an image view and add to the linearlayout
        for (i in 0..(itemsCount - 1)) {

            val params = LinearLayout.LayoutParams(context.dip(itemWidth), context.dip(itemHeight))

            if (orientation == PagerOrientation.HORIZONTAL) {
                params.setMargins(context.dip(itemMargin), 1, context.dip(itemMargin), 1)
            } else {
                params.setMargins(1, context.dip(itemMargin), 1, context.dip(itemMargin))
            }

            val img = ImageView(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                img.elevation = context.dip(itemElevation).toFloat()
            }

            //Assign the selected and unselected background
            img.backgroundDrawable = if (i == itemSelected) {
                selectedBackground
            } else {
                unselectedBackground
            }

            pageIndicators?.add(img)

            //Add item to the view
            centralView?.addView(img, params)
        }

        //Add item container to the view
        super.addView(centralView, centralViewParams)
    }

    /**
     * Update views properties
     */
    private fun updateIndicatorViews() {

        pageIndicators?.forEach {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.elevation = context.dip(itemElevation).toFloat()
            }

            val params = LinearLayout.LayoutParams(context.dip(itemWidth), context.dip(itemHeight))

            if (orientation == PagerOrientation.HORIZONTAL) {
                params.setMargins(context.dip(itemMargin), 1, context.dip(itemMargin), 1)
            } else {
                params.setMargins(1, context.dip(itemMargin), 1, context.dip(itemMargin))
            }

            it.layoutParams = params

        }

        invalidate()
        requestLayout()
    }

    /**
     * Generate the callback for Viewpager and listener to the page change
     */
    private fun pageChangeListener() {

        if (onPageChangeListener != null) {
            viewPager?.removeOnPageChangeListener(onPageChangeListener!!)
        }

        onPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {

                if (position >= 0 && position < pageIndicators!!.size) {

                    pageIndicators?.forEach {
                        it.backgroundDrawable = unselectedBackground
                    }
                    pageIndicators!![position].backgroundDrawable = selectedBackground
                    currentPagerItemSelected = position
                } else {
                    Log.w(LOG_TAG, "Invalid position")
                }

            }
        }

        viewPager?.addOnPageChangeListener(onPageChangeListener!!)

    }

    /**
     * Save currentPagerItemSelected
     */
    override fun onSaveInstanceState(): Parcelable {
        val viewPagerIndicatorSavedState = ViewPagerIndicatorSavedState(super.onSaveInstanceState())
        viewPagerIndicatorSavedState.currentPagerItemSelected = currentPagerItemSelected
        return viewPagerIndicatorSavedState
    }

    /**
     * Restore currentPagerItemSelected
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        val viewPagerIndicatorSavedState = state as ViewPagerIndicatorSavedState
        itemSelected = viewPagerIndicatorSavedState.currentPagerItemSelected
        currentPagerItemSelected = itemSelected
        super.onRestoreInstanceState(viewPagerIndicatorSavedState.superState)
    }
}