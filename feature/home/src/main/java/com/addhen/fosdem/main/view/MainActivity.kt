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

package com.addhen.fosdem.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.addhen.fosdem.base.view.BaseActivity
import com.addhen.fosdem.main.R
import com.addhen.fosdem.main.databinding.MainActivityBinding
import com.addhen.fosdem.main.extension.disableShiftMode

class MainActivity : BaseActivity<MainActivityViewModel, MainActivityBinding>(
    R.layout.main_activity,
    0,
    MainActivityViewModel::class.java
) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        initView()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    private fun initView() {
        binding.bottomNavigation.disableShiftMode()
        navController = findNavController(this, R.id.main_nav_host_fragment)
        setupActionBarWithNavController(this, navController)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
