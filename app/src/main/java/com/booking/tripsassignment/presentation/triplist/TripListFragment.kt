package com.booking.tripsassignment.presentation.triplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.booking.tripsassignment.databinding.TripsListScreenBinding
import com.booking.tripsassignment.presentation.adapter.TripsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Trip list fragment
 *
 * @author Dima Balash on 12.06.2022
 */
@AndroidEntryPoint
class TripListFragment : Fragment() {

    private val viewModel: TripListViewModel by viewModels()
    private var _binding: TripsListScreenBinding? = null
    private val binding get() = _binding!!
    private val adapter = TripsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TripsListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNoteRecyclerView()
        initObserver()
        initClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    showProgress(state.loading)
                    if (state.error.isNotEmpty()) {
                        showError(state.error)
//                        viewModel.errorShown()
                    }
                    if (state.data.isNotEmpty()) {
                        adapter.setData(state.data)
                    }
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.btnRetry.setOnClickListener {
            viewModel.retryClick()
        }
    }

    private fun initNoteRecyclerView() {
        with(binding) {
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter
        }
    }

    private fun showProgress(show: Boolean) {
        with(binding) {
            progress.visibility = if (show) View.VISIBLE else View.GONE
            tvError.visibility = View.GONE
            btnRetry.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        with(binding) {
            tvError.visibility = View.VISIBLE
            tvError.text = message
            btnRetry.visibility = View.VISIBLE
        }
    }
}
