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

package com.addhen.fosdem.sessions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.addhen.fosdem.base.view.BaseFragment
import com.addhen.fosdem.sessions.R
import com.addhen.fosdem.sessions.databinding.SessionsFragmentBinding
import com.addhen.fosdem.sessions.model.SessionScreen

class SessionsFragment : BaseFragment<SessionsViewModel, SessionsFragmentBinding>(
    clazz = SessionsViewModel::class.java
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SessionsFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewStateChanges()
        viewModel.onAction(SessionAction.LoadSessions)
    }

    override fun onDestroyView() {
        binding.sessionsTabLayout.setupWithViewPager(null)
        super.onDestroyView()
    }

    private fun initView() {
        binding.sessionsTabLayout.setupWithViewPager(binding.sessionsViewpager)
        binding.sessionsViewpager.pageMargin = resources.getDimensionPixelSize(R.dimen.space_16dp)
        binding.sessionsViewpager.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return SessionFilterFragment.newInstance(SessionFilterFragmentArgs(position))
            }

            override fun getPageTitle(position: Int) = SessionScreen.tabs[position].title

            override fun getCount(): Int = SessionScreen.tabs.size
        }
        binding.sessionsViewpager.addOnPageChangeListener(
            object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    // TODO set selection
                }
            }
        )

        (0 until binding.sessionsTabLayout.tabCount).forEach { tabIndex ->
            val tab = binding.sessionsTabLayout.getTabAt(tabIndex)
            tab?.let {
                val view = layoutInflater.inflate(
                    R.layout.tab_item, binding.sessionsTabLayout, false
                ) as? TextView
                view?.let { textView ->
                    textView.text = tab.text
                    it.customView = textView
                }
            }
        }
    }

    private fun observeViewStateChanges() {
        viewModel.viewState.observe(this, Observer {
            binding.sessionsProgressBar.isVisible = it.isLoading
            viewModel.isEmptyViewShown.set(it.isEmptyViewShown)
        })
    }
}
