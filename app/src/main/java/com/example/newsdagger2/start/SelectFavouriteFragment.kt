package com.example.newsdagger2.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.R
import com.example.newsdagger2.databinding.FragmentSelectFavouriteBinding
import com.example.newsdagger2.start.select_topic.SelectTopicAdapter

class SelectFavouriteFragment : Fragment() {

    private lateinit var binding:FragmentSelectFavouriteBinding
    private lateinit var topics : ArrayList<String>
    private var adapter :SelectTopicAdapter?=null
    private var selectedList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectFavouriteBinding.inflate(layoutInflater)

        loadData()
        adapter = SelectTopicAdapter(requireContext(), topics)
        binding.topicsRv.adapter = adapter

        adapter?.myItemClickListener = object: SelectTopicAdapter.MyItemClickListener{
            override fun itemClick(position: Int, isSelected: Boolean) {
                if (!isSelected) {
                    selectedList.add(topics[position])
                } else {
                    selectedList.remove(topics[position])
                }
                Log.d("TTTT", selectedList.toString())
            }
        }

        binding.nextBtn.setOnClickListener {
            Toast.makeText(requireContext(), selectedList.toString(), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.homeFragment)
        }

        return binding.root
    }

    private fun loadData() {
        topics = ArrayList()
        topics.add(requireContext().getString(R.string.category_sport))
        topics.add(requireContext().getString(R.string.category_politics))
        topics.add(requireContext().getString(R.string.category_life))
        topics.add(requireContext().getString(R.string.category_gaming))
        topics.add(requireContext().getString(R.string.category_animals))
        topics.add(requireContext().getString(R.string.category_nature))
        topics.add(requireContext().getString(R.string.category_food))
        topics.add(requireContext().getString(R.string.category_art))
        topics.add(requireContext().getString(R.string.category_history))
        topics.add(requireContext().getString(R.string.category_fashion))
        topics.add(requireContext().getString(R.string.category_covid19))
        topics.add(requireContext().getString(R.string.category_middle_east))
    }
}