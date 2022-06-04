package com.example.newsdagger2.start.viewPager

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsdagger2.R
import com.example.newsdagger2.databinding.FragmentViewPagerItemBinding
import java.io.Serializable

class ViewPagerItemFragment : Fragment() {

    private var image: Int? = null
    private lateinit var binding :FragmentViewPagerItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getInt("image")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerItemBinding.inflate(layoutInflater)

        binding.viewPagerItemImageview.setImageResource(image!!)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(image: Int) =
            ViewPagerItemFragment().apply {
                arguments = Bundle().apply {
                    putInt("image", image)
                }
            }
    }
}