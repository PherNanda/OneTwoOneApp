package com.android.app.onetwoone.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app.onetwoone.R
import com.android.app.onetwoone.domain.model.Beer
import com.android.app.onetwoone.ui.adapters.BeerAdapter
import com.android.app.onetwoone.ui.utils.Data
import com.android.app.onetwoone.ui.utils.Status
import com.android.app.onetwoone.ui.viewmodel.BeerViewModel
import com.android.app.onetwoone.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

const val MINIMUM_LOADING_TIME = 1000L

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<BeerViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recycler beer list
        recyclerView = findViewById(R.id.recycler_beer)

        viewModel.mainStateList.observe(::getLifecycle, ::updateUI)
        viewModel.mainStateDetail.observe(::getLifecycle, ::updateDetailUI)

        callStartService()

        //search beer name
        val inputName = binding.beerInput.text
        binding.beerLayout.editText?.text = inputName

        var value: String
        binding.beerInput.doAfterTextChanged { input ->

            if(input != null && input.isNotEmpty()){
                value = binding.beerInput.text.toString()

                if(value.isNotEmpty()) {
                    viewModel.onSearchBeer(value.toLowerCase(),1,3)
                }
            }
            if (input != null) {
                if (input.isEmpty()){
                    callStartService()
                }
            }

        }

    }

    private fun updateUI(beersData: Data<List<Beer>>) {
        when (beersData.responseType) {
            Status.ERROR -> {
                beersData.error?.message?.let { showMessage(it) }
                beersData.data?.let { setBeerList(it) }
            }
            Status.LOADING -> {

            }
            Status.SUCCESSFUL -> {
                beersData.data?.let { setBeerList(it) }
            }
        }
    }

    private fun updateDetailUI(beersData: Data<List<Beer>>) {
        when (beersData.responseType) {
            Status.ERROR -> {
                beersData.error?.message?.let { showMessage(it) }
            }
            Status.LOADING -> {
            }
            Status.SUCCESSFUL -> {
                startActivity(Intent(this, DetailActivity::class.java))
            }
        }
    }

    private fun callStartService() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.onStartHome(1, 80)
        }, MINIMUM_LOADING_TIME)
    }

    private fun setBeerList(beerList: List<Beer>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val beerAdapter = BeerAdapter(beerList)
        beerAdapter.setOnItemClickListener(itemClickListener())
        recyclerView.adapter = beerAdapter
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun callClickDetailsService(item: Beer) {
        viewModel.onClickToBeerDetails(item.id, this@MainActivity.applicationContext)
    }

    private fun itemClickListener() = object : BeerAdapter.OnItemClickListener {
        override fun onItemClick(item: Beer) {
            callClickDetailsService(item)
        }
    }
}