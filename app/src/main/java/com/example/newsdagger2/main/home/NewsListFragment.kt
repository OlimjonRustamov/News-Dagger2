package com.example.newsdagger2.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.App.App
import com.example.newsdagger2.R
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.databinding.FragmentNewsListBinding
import com.example.newsdagger2.di.model.Article
import com.example.newsdagger2.di.model.NewsResponse
import com.example.newsdagger2.di.viewmodel.NewsViewModel
import com.example.newsdagger2.main.bookmark.BookmarkAdapter
import com.example.newsdagger2.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewsListFragment : Fragment(), CoroutineScope {
    private var category: String? = null
    private lateinit var binding:FragmentNewsListBinding
    private var news:ArrayList<NewsEntity>?=null

    @Inject
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(layoutInflater)
        App.appComponent.inject(this)
        binding.newsListToolbar.title = category ?: requireContext().getString(R.string.recommended)
        loadData()
        binding.newsListToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback{
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun loadData() {
        launch(Dispatchers.Main) {
            newsViewModel.getNews(category.toString(), "", "popularity")
                .observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.ERROR -> {
                            binding.newsListProgress.hide()
                            binding.newsRv.show()
                            binding.notFoundLottie.hide()
                            Log.d("TTTT", "loadData: ${it.message}")
                        }
                        Status.LOADING -> {
                            binding.newsListProgress.show()
                            binding.newsRv.hide()
                            binding.notFoundLottie.hide()
                        }
                        Status.SUCCESS -> {
                            news = it.data
                            binding.newsListProgress.hide()
                            binding.notFoundLottie.hide()
                            binding.newsRv.show()
                            Log.d("TTTT", "success loadData: ${news.toString()}")
                            if (news?.size != 0) {
                                loadAdapter()
                            } else {
                                binding.newsListProgress.hide()
                                binding.newsRv.hide()
                                binding.notFoundLottie.show()
                            }
                        }
                    }
                }
        }
    }
    private fun loadAdapter(){
        val recommendedAdapter =
            BookmarkAdapter(requireContext(), news!!)
        binding.newsRv.adapter = recommendedAdapter
        recommendedAdapter.bookMarkItemClickListener =
            object : BookmarkAdapter.BookMarkItemClickListener {
                override fun onClick(article: NewsEntity) {
                    val bundle = Bundle()
                    bundle.putSerializable("article", article)
                    findNavController().navigate(R.id.newsFragment, bundle)
                }
            }
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}