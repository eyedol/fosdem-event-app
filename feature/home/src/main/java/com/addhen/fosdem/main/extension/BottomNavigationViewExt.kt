/*
 * MIT License
 *
 * Copyright (c) 2017 - 2018 Henry Addo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.addhen.fosdem.main.extension

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.forEach
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

// Credits
// https://github.com/DroidKaigi/conference-app-2018/blob/master/app/src/main/java/io/github/droidkaigi/confsched2018/util/ext/BottomNavigationViewExt.kt
@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = this.getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        menuView.forEach {
            val item = it as BottomNavigationItemView
            // NOTE: This removing the BottomNavigationItem labels including small and large text container.
            item.removeViewAt(1)

            // set the gravity of item icon to Center.
            val lp = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            lp.gravity = Gravity.CENTER
            item.getChildAt(0).layoutParams = lp

            // remove the default top margin.
            val topMargin = item.javaClass.getDeclaredField("mDefaultMargin")
            topMargin.isAccessible = true
            topMargin.setInt(item, 0)
            topMargin.isAccessible = false

            // item.setShiftingMode(false)
            // set once again checked value, so view will be updated

            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Timber.e(e, "Unable to get shift mode field")
    } catch (e: IllegalAccessException) {
        Timber.e(e, "Unable to change value of shift mode")
    }
}
