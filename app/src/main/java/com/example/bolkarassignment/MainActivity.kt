package com.example.bolkarassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bolkarassignment.adapters.PersonAdapter
import com.example.bolkarassignment.databinding.ActivityMainBinding
import com.example.bolkarassignment.response.ApiResponseBody
import com.example.bolkarassignment.utils.Result
import com.example.bolkarassignment.viewmodel.MainViewModel
import com.example.bolkarassignment.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }
    private val mainAdapter by lazy { PersonAdapter(true) }
    private val memberAdapter by lazy { PersonAdapter(false) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        observeData()
    }

    private fun observeData() {
        viewModel.information.observe(this){
            when(it){
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    populateRecyclerView(it.data)
                    setupToolbar(it.data)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvSpeakerHostMed.layoutManager = GridLayoutManager(this, 3)
        binding.rvSpeakerHostMed.adapter = mainAdapter

        binding.rvMembers.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvMembers.adapter = memberAdapter
    }

    private fun populateRecyclerView(response : ApiResponseBody) {
        val data = response.data
        mainAdapter.initialiseAdapter(data?.host, data?.speakers, data?.moderators)
        mainAdapter.submitList(data?.mainList)
        memberAdapter.submitList(data?.members)
    }

    private fun setupToolbar(response: ApiResponseBody) {

        binding.topicToolbar.title = response.data?.topic
        binding.tvTotal.text = response.data?.total_members.toString()
        response.data?.host?.u?.let { loadImage(it) }
        binding.tvVersion.text = response.data?.version.toString()

        binding.topicToolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Navigate to previous screen", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadImage(username: String) {
        val url = "https://cdn1.bolkarapp.com/uploads/dp/$username.jpg"
        Glide
            .with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .into(binding.ivAccountImg)
    }
}