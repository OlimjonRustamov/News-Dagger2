package com.example.newsdagger2.main.bookmark

import android.database.Observable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.App.App
import com.example.newsdagger2.R
import com.example.newsdagger2.database.NewsDao
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.databinding.FragmentBookmarkBinding
import com.example.newsdagger2.di.model.Article
import com.example.newsdagger2.di.model.Source
import com.example.newsdagger2.di.repository.NewsRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BookmarkFragment : Fragment(), CoroutineScope {

    @Inject
    lateinit var repository: NewsRepository

    private lateinit var binding: FragmentBookmarkBinding
    private var list=ArrayList<NewsEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(layoutInflater)
        App.appComponent.inject(this)

        launch(Dispatchers.IO) {
            list = repository.getAllSavedNews() as ArrayList<NewsEntity>
            launch(Dispatchers.Main) {
                if (list.size == 0) {
                    binding.bookmarkRv.visibility = View.GONE
                    binding.noBookmarkLayout.visibility = View.VISIBLE
                } else {
                    binding.bookmarkRv.visibility = View.VISIBLE
                    binding.noBookmarkLayout.visibility = View.GONE
                    val adapter = BookmarkAdapter(requireContext(), list as ArrayList<NewsEntity>)
                    binding.bookmarkRv.adapter = adapter
                    adapter.bookMarkItemClickListener =
                        object : BookmarkAdapter.BookMarkItemClickListener {
                            override fun onClick(article: NewsEntity) {
                                val bundle = Bundle()
                                bundle.putSerializable("article", article)
                                findNavController().navigate(R.id.newsFragment, bundle)
                            }
                        }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.homeFragment)
        }
        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}