# Android View Pager Indicator
[![](https://jitpack.io/v/JeovaniMartinez/AndroidViewPagerIndicator.svg)](https://jitpack.io/#JeovaniMartinez/AndroidViewPagerIndicator) [![](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://developer.android.com/about/versions/android-4.0.html)
##

Pager indicator view for android, compatible with support v4 ViewPager

## Demo

![](https://github.com/JeovaniMartinez/AndroidViewPagerIndicator/blob/develop/assets/images/demo_img.png)

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

### To implement in your project

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
