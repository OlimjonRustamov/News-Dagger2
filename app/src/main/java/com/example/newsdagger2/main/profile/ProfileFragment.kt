package com.example.newsdagger2.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.R
import com.example.newsdagger2.cache.Cache
import com.example.newsdagger2.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.nightModeSwitch.isChecked = Cache.instance!!.getNightMode()

        binding.nightModeSwitch.setOnCheckedChangeListener { switch, b ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Cache.instance!!.saveNightMode(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Cache.instance!!.saveNightMode(false)
            }
        }

        binding.languagesRoot.setOnClickListener {
            findNavController().navigate(R.id.languagesFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback{
            findNavController().navigate(R.id.homeFragment)
        }
        return binding.root
    }
}