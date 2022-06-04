package com.example.newsdagger2.start

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.R
import com.example.newsdagger2.databinding.FragmentGetStartedBinding
import com.example.newsdagger2.start.viewPager.ViewPagerAdapter

class GetStartedFragment : Fragment() {

    private lateinit var binding:FragmentGetStartedBinding
    private var imageList = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGetStartedBinding.inflate(layoutInflater)

        loadData()
        val adapter = ViewPagerAdapter(childFragmentManager, imageList)
        binding.viewPager.adapter = adapter

        binding.nextBtn.setOnClickListener{
            val currentItem = binding.viewPager.currentItem
            if (currentItem == binding.viewPager.size) {
                findNavController().navigate(R.id.selectFavouriteFragment)
            }
            binding.viewPager.setCurrentItem(currentItem+1, true)
            if (currentItem + 1 == binding.viewPager.size) {
                binding.nextBtn.setText("Get Started")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback{
            requireActivity().finish()
        }

        return binding.root
    }

    private fun loadData() {
        imageList.add(R.drawable.news)
        imageList.add(R.drawable.news)
        imageList.add(R.drawable.news)
    }
}