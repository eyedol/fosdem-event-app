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
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.addhen.fosdem.base.view.BaseFragment
import com.addhen.fosdem.data.model.Session
import com.addhen.fosdem.sessions.databinding.SessionBottomSheetDialogFragmentBinding
import com.addhen.fosdem.sessions.model.ScreenTab
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SessionBottomSheetDialogFragment :
    BaseFragment<SessionsViewModel, SessionBottomSheetDialogFragmentBinding>(
        clazz = SessionsViewModel::class.java
    ) {

    private val args: SessionBottomSheetDialogFragmentArgs by lazy {
        SessionBottomSheetDialogFragmentArgs.fromBundle(arguments ?: Bundle())
    }

    private val sessionAdapter: SessionsAdapter by lazy { SessionsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SessionBottomSheetDialogFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.onAction(SessionAction.LoadSessions(args.index))
    }

    private fun initFilterButton() {
        val onFilterButtonClick: (View) -> Unit = {
            viewModel.onAction(SessionAction.BottomSheetFilterToggled(ScreenTab.Saturday))
        }
        binding.sessionsBottomSheetShowFilterButton.setOnClickListener(onFilterButtonClick)
        binding.sessionsBottomSheetHideFilterButton.setOnClickListener(onFilterButtonClick)
    }

    private fun initView() {
        initRecyclerView()
        initFilterButton()
        observeViewStateChanges()
        observeViewEffectChanges()
    }

    private fun observeViewStateChanges() {
        viewModel.viewState.observe(this, Observer {
            if (it.bottomSheetState == BottomSheetBehavior.STATE_EXPANDED ||
                it.bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED
            ) {
                TransitionManager.beginDelayedTransition(
                    binding.root as ViewGroup, AutoTransition().apply {
                        excludeChildren(binding.sessionsBottomSheetTitle, true)
                        excludeChildren(binding.sessionsRecycler, true)
                    })
                val isCollapsed = it.bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED
                viewModel.isBottomSheetCollapsed.set(isCollapsed)
            }
            setAdapterItems(it.sessions)
        })
    }

    private fun setAdapterItems(sessions: List<Session>) {
        sessionAdapter.reset(sessions)
        binding.sessionsRecycler.adapter = sessionAdapter
        //TODO replace with current date
        val title = sessions.asSequence().firstOrNull()?.day?.date
        binding.sessionsBottomSheetTitle.text = title.toString()
    }

    private fun observeViewEffectChanges() {
        viewModel.viewEffect.observe(this, Observer {
            Toast.makeText(requireContext(), "ok", Toast.LENGTH_LONG).show()
        })
    }

    private fun initRecyclerView() {
        binding.sessionsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = sessionAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    binding.sessionsListHeaderShadow.isVisible =
                        recyclerView.canScrollVertically(RecyclerView.NO_POSITION)
                }
            })
        }
    }

    companion object {

        fun newInstance(
            args: SessionBottomSheetDialogFragmentArgs
        ): SessionBottomSheetDialogFragment {
            return SessionBottomSheetDialogFragment().apply {
                arguments = args.toBundle()
            }
        }
    }
}
