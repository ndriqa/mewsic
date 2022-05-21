package com.ndriqa.rootapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ndriqa.rootapp.R
import com.ndriqa.rootapp.adapters.MusicAdapter
import com.ndriqa.rootapp.base.BaseFragment
import com.ndriqa.rootapp.databinding.FragmentHomeBinding
import com.ndriqa.rootapp.models.Song
import com.ndriqa.rootapp.viewmodels.MainActivityViewModel

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainActivityViewModel by activityViewModels()

    private val musicAdapter = MusicAdapter(object : MusicAdapter.Listener {
        override fun playSong(song: Song) {
            viewModel.nowPlaying.postValue(song)
            Log.i("mytag", song.audioDisplayName.toString())
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseFunctions()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.musicListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.musicListRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        binding.musicListRecyclerView.adapter = musicAdapter
    }

    override fun onLoad() {
        viewModel.getAllAudioFromDevice(requireContext())
    }

    override fun setToolbar() {  }

    override fun onClickListeners() {  }

    override fun observers() {
        viewModel.allSongs.observe(this) {
            it?.let { musicAdapter.data = it }
        }
    }
}