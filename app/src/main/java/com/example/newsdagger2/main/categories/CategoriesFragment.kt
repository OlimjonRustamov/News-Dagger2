package com.example.newsdagger2.main.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.R
import com.example.newsdagger2.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categories:ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        loadData()

        val adapter = CategoryAdapter(requireContext(), categories)
        binding.categoryRv.adapter = adapter

        adapter.categoryItemClickListener = object : CategoryAdapter.CategoryItemClickListener {
            override fun onLick(category: String) {
                val bundle = Bundle()
                bundle.putString("category", category)
                findNavController().navigate(R.id.newsListFragment, bundle)
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback{
            findNavController().navigate(R.id.homeFragment)
        }
        return binding.root
    }

    private fun loadData() {
        categories = ArrayList()
        categories.add(requireContext().getString(R.string.category_sport))
        categories.add(requireContext().getString(R.string.category_politics))
        categories.add(requireContext().getString(R.string.category_life))
        categories.add(requireContext().getString(R.string.category_gaming))
        categories.add(requireContext().getString(R.string.category_animals))
        categories.add(requireContext().getString(R.string.category_nature))
        categories.add(requireContext().getString(R.string.category_food))
        categories.add(requireContext().getString(R.string.category_art))
        categories.add(requireContext().getString(R.string.category_history))
        categories.add(requireContext().getString(R.string.category_fashion))
        categories.add(requireContext().getString(R.string.category_covid19))
        categories.add(requireContext().getString(R.string.category_middle_east))
    }

}