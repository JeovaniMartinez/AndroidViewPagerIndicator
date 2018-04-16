# Android View Pager Indicator
[![](https://jitpack.io/v/JeovaniMartinez/AndroidViewPagerIndicator.svg)](https://jitpack.io/#JeovaniMartinez/AndroidViewPagerIndicator) [![](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://developer.android.com/about/versions/android-4.0.html)
##

Pager indicator view for android, compatible with support v4 ViewPager

## Demo

[![](https://github.com/JeovaniMartinez/AndroidViewPagerIndicator/blob/develop/assets/images/demo_img.png)](https://youtu.be/wrjiFcLXh1Q)

[![](https://github.com/JeovaniMartinez/AndroidViewPagerIndicator/blob/develop/assets/images/demo_video.png)](https://youtu.be/wrjiFcLXh1Q)

### Usage (Dependencies)

-  Add the following to your project level `build.gradle`:

```gradle
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```
-  Add this to your app `build.gradle`:
 
```gradle
dependencies {

  implementation "com.github.JeovaniMartinez:AndroidViewPagerIndicator:0.1.0"
  
  //The library need this dependencies, use the latest version
  implementation 'com.android.support:appcompat-v7:27.1.1'
  implementation 'com.android.support.constraint:constraint-layout:1.1.0'
  implementation "org.jetbrains.kotlin:kotlin-stdlib-jre7:1.2.31"
  implementation "org.jetbrains.anko:anko:0.10.4"
}
```

### Getting Started

-  In your `layout`:
 
```xml
<info.jeovani.viewpagerindicator.ViewPagerIndicator
        android:id="@+id/mViewPagerIndicator"
        android:layout_width="match_parent"
        android:layout_height="30dp"
        app:vpi_items_count="5"
        app:vpi_view_pager="@+id/mViewPager" />

<!--Note: vpi_items_count is necessary to show a preview in the xml design-->
```

-  In your `java class`:
```java
 ViewPagerIndicator mViewPagerIndicator;
 
 mViewPagerIndicator = findViewById(R.id.mViewPagerIndicator);
 mViewPagerIndicator.setItemsCount(5); //Total elements of your ViewPager adapter
```
-  Or in your `kotlin class`:
```kotlin
 mViewPagerIndicator.itemsCount = 5 //Total elements of your ViewPager adapter
```

That's all you need for basic the implementation, the library will react by changing the selected item when ViewPager page change

### Customize

| Methods Java/Kotlin  | XML attribute | Description |
| ------------- | ------------- |  ------------- |
| setViewPager()  <br/> viewPager| vpi_view_pager  |  ViewPager to associate pager  |
| setItemsCount() <br/>  itemsCount| vpi_items_count  | Total ViewPager adapter elements  |
| setItemSelected()  <br/> itemSelected| vpi_item_selected  |  Current position selected  |
| setItemType() <br/> itemType| vpi_item_type  | Item shape, use PagerItemType.OVAL or PagerItemType.RECTANGLE, in XML use oval or rectangle  |
| setOrientation()  <br/>  orientation| vpi_orientation  | Items orientation, use PagerOrientation.HORIZONTAL or PagerOrientation.VERTICAL, in XML use horizontal or vertical |
| setItemSelectedColors() <br/>  itemSelectedColors| vpi_selected_primary_color <br/> vpi_selected_secondary_color | Array of colors for the selected element of the pager, use one color for solid and two for gradient, in XML can define one or both colors separately  |
| setItemsUnselectedColors()  <br/> itemsUnselectedColors | vpi_unselected_primary_color  <br/> vpi_unselected_secondary_color | Array of colors for the unselected elements of the pager, use one color for solid and two for gradient, in XML can define one or both colors separately  |
| setItemElevation() <br/> itemElevation | vpi_item_elevation  | Elevation for each item, in code use a integer and the unity is converted to dp, in XML use dp |
| setItemWidth()  <br/> itemWidth | vpi_item_width  | Width for each item, in code use a integer the library convert the unity to dp, in XML use dp  |
| setItemHeight()  <br/> itemHeight | vpi_item_height  | Height for each item, in code use a integer the library convert the unity to dp, in XML use dp  |
| setItemMargin() <br/> itemMargin | vpi_item_margin  | Margin for each item, in code use a integer the library convert the unity to dp, in XML use dp |


