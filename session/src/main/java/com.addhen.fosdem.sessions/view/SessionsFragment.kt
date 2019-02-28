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
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addhen.fosdem.base.Resource
import com.addhen.fosdem.base.extension.snackbar
import com.addhen.fosdem.base.view.BaseFragment
import com.addhen.fosdem.sessions.databinding.SessionsFragmentBinding

class SessionsFragment : BaseFragment<SessionsViewModel, SessionsFragmentBinding>(
    clazz = SessionsViewModel::class.java
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SessionsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val sessionsAdapter = SessionsAdapter(requireContext())
        binding.sessionRecyclerView.adapter = sessionsAdapter
        val linearLayoutManager = object : LinearLayoutManager(context) {
            override fun getExtraLayoutSpace(state: RecyclerView.State) = 300
        }
        binding.sessionRecyclerView.layoutManager = linearLayoutManager
        viewModel.sessions.observe(this, Observer {
            when (it?.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    val list = viewModel.sessions.value?.data ?: emptyList()
                    binding.emptyViewHeader.isVisible = list.isEmpty()
                    sessionsAdapter.reset(list)
                }
                Resource.Status.LOADING -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                    binding.emptyViewHeader.isVisible = false
                }
                else -> {
                    binding.loadingProgressBar.isVisible = false
                    binding.emptyViewHeader.isVisible = false
                    binding.sessionsRootFramelayout.snackbar(viewModel.sessions.value?.message!!)
                }
            }
        })
        lifecycle.addObserver(viewModel)
    }

    companion object {

        const val TAG: String = "SessionsFragment"
        fun newInstance(): SessionsFragment = SessionsFragment()
    }
}
