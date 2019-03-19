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
import androidx.lifecycle.Observer
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.addhen.fosdem.base.view.BaseFragment
import com.addhen.fosdem.sessions.databinding.SessionBottomSheetDialogFragmentBinding
import com.addhen.fosdem.sessions.model.SessionScreen
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SessionBottomSheetDialogFragment :
    BaseFragment<SessionFilterViewModel, SessionBottomSheetDialogFragmentBinding>(
        clazz = SessionFilterViewModel::class.java
    ) {

    private val args: SessionBottomSheetDialogFragmentArgs by lazy {
        SessionBottomSheetDialogFragmentArgs.fromBundle(arguments ?: Bundle())
    }

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
    }

    private fun initFilterButton() {
        val onFilterButtonClick: (View) -> Unit = {
            viewModel.onAction(SessionAction.BottomSheetFilterToggled(SessionScreen.Saturday))
        }
        binding.sessionsBottomSheetShowFilterButton.setOnClickListener(onFilterButtonClick)
        binding.sessionsBottomSheetHideFilterButton.setOnClickListener(onFilterButtonClick)
    }

    private fun initView() {
        initFilterButton()
        observeViewStateChanges()
        lifecycle.addObserver(viewModel)
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
        })
    }

    companion object {

        const val TAG: String = "SessionBottomSheetDialogFragment"

        fun newInstance(
            args: SessionBottomSheetDialogFragmentArgs
        ): SessionBottomSheetDialogFragment {
            return SessionBottomSheetDialogFragment().apply {
                arguments = args.toBundle()
            }
        }
    }
}
