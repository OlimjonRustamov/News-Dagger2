package com.example.newsdagger2.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.App.App
import com.example.newsdagger2.R
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.databinding.FragmentNewsBinding
import com.example.newsdagger2.di.model.Article
import com.example.newsdagger2.di.repository.NewsRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewsFragment : Fragment(), CoroutineScope {
    private var article: NewsEntity? = null

    @Inject
    lateinit var repository: NewsRepository

    private lateinit var binding:FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = it.getSerializable("article") as NewsEntity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        App.appComponent.inject(this)

        requireActivity().setActionBar(binding.newsToolbar)
        binding.newsToolbar.title = article?.title
        Picasso.get().load(article?.urlToImage).into(binding.fragmentNewsImage)
        binding.newsTv.text = article?.content
        if (article?.isSaved==true) {
            binding.fragmentNewsBookmark.setImageResource(R.drawable.ic_bookmark_selected)
        }

        binding.fragmentNewsBookmark.setOnClickListener {
            article?.isSaved = article?.isSaved != true
            launch(Dispatchers.IO) {
                repository.updateArticle(article!!)
            }
            if (article?.isSaved == true) {
                binding.fragmentNewsBookmark.setImageResource(R.drawable.ic_bookmark_selected)
            } else {
                binding.fragmentNewsBookmark.setImageResource(R.drawable.ic_bookmark_unselected)
            }
        }

        binding.newsToolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}