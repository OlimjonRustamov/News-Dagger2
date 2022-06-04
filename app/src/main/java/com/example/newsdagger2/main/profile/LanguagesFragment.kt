package com.example.newsdagger2.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.MainActivity
import com.example.newsdagger2.R
import com.example.newsdagger2.databinding.FragmentLanguagesBinding
import java.util.*

class LanguagesFragment : Fragment() {

    lateinit var binding: FragmentLanguagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguagesBinding.inflate(layoutInflater)
        clr()
        val config = requireContext().resources.configuration
        when (config.locale.language) {
            "uz" -> {
                binding.uzbekLan.isSelected = false
                binding.uzbekLan.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                binding.uzbekLanImageView.visibility = View.VISIBLE
            }
            "ru" -> {
                binding.russianLan.isSelected = false
                binding.russianLan.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                binding.russianImageView.visibility = View.VISIBLE
            }
            "de" -> {
                binding.germanLan.isSelected = false
                binding.germanLan.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                binding.germanImageView.visibility = View.VISIBLE
            }
            "sp" -> {
                binding.spanishLan.isSelected = false
                binding.spanishLan.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                binding.spanishImageView.visibility = View.VISIBLE
            }
            "en" -> {
                binding.englishLan.isSelected = false
                binding.englishLan.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                binding.englishImageView.visibility = View.VISIBLE
            }
        }



        binding.uzbekLan.setOnClickListener { setLanguage("uz") }
        binding.spanishLan.setOnClickListener { setLanguage("sp") }
        binding.germanLan.setOnClickListener { setLanguage("de") }
        binding.russianLan.setOnClickListener { setLanguage("ru") }
        binding.englishLan.setOnClickListener { setLanguage("en") }


        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack(R.id.profileFragment, false)
        }
        return binding.root
    }

    private fun clr() {
        binding.englishLan.isSelected = true
        binding.russianLan.isSelected = true
        binding.uzbekLan.isSelected = true
        binding.germanLan.isSelected = true
        binding.spanishLan.isSelected = true
    }

    private fun setLanguage(language: String) {
        setAppLocale(requireContext(), language)
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }
}