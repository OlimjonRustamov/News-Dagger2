package com.example.newsdagger2.start

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.R
import com.example.newsdagger2.cache.Cache
import com.example.newsdagger2.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding :FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.main_color)

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(object :Runnable{
            override fun run() {
                if(Cache.instance!!.getFirstEntrance())
                findNavController().navigate(R.id.homeFragment)
                else findNavController().navigate(R.id.getStartedFragment)

            }
        }, 1000)

        return binding.root
    }
    private fun loadUI() {
        when (requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
        }
    }

    override fun onPause() {
        super.onPause()
        loadUI()
    }
}