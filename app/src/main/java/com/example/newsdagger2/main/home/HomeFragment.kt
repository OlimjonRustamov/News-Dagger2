package com.example.newsdagger2.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.example.newsdagger2.App.App
import com.example.newsdagger2.R
import com.example.newsdagger2.cache.Cache
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.databinding.FragmentHomeBinding
import com.example.newsdagger2.di.repository.NewsRepository
import com.example.newsdagger2.di.viewmodel.NewsViewModel
import com.example.newsdagger2.main.bookmark.BookmarkAdapter
import com.example.newsdagger2.main.home.adapters.HomeCategoryAdapter
import com.example.newsdagger2.main.home.adapters.HomeNewsAdapter
import com.example.newsdagger2.utils.Status
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeFragment: Fragment(), CoroutineScope {

    @Inject
    lateinit var newsViewModel: NewsViewModel

    @Inject
    lateinit var newsRepository: NewsRepository

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categories: ArrayList<String>
    private lateinit var news: ArrayList<NewsEntity>
    private var categoryAdapter: HomeCategoryAdapter? = null
    private var newsAdapter: HomeNewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        App.appComponent.inject(this)
        Cache.instance?.saveFirstEntrance()
        loadData()
        loadHomeCategoryAdapter()
        binding.seeAll.setOnClickListener {
            findNavController().navigate(R.id.newsListFragment)
        }
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("category", p0)
                findNavController().navigate(R.id.newsListFragment, bundle)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })

        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
        return binding.root
    }

    private fun loadData() {
        categories = ArrayList()
        categories.add(requireContext().getString(R.string.category_sport))
        categories.add(requireContext().getString(R.string.category_politics))
        categories.add(requireContext().getString(R.string.category_nature))
        categories.add(requireContext().getString(R.string.category_covid19))

        launch(Dispatchers.Main) {
            newsViewModel.getNews("Politics", "", "popularity")
                .observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.ERROR -> {
                            binding.homeNewsProgress.hide()
                            binding.homeNewsRv.show()
                            Log.d("TTTT", "loadData: ${it.message}")
                        }
                        Status.LOADING -> {
                            binding.homeNewsProgress.show()
                            binding.homeNewsRv.hide()
                        }
                        Status.SUCCESS -> {
                            news = it.data!!
                            binding.homeNewsProgress.hide()
                            binding.homeNewsRv.show()
                            loadHomeNewsAdapter()
                            loadRecommendedAdapter()
                        }
                    }
                }
        }
    }

    private fun loadHomeNewsAdapter() {
        newsAdapter = HomeNewsAdapter(requireContext(), news)

        binding.homeNewsRv.adapter = newsAdapter
        newsAdapter!!.homeNewsItemClickListener =
            object : HomeNewsAdapter.HomeNewsItemClickListener {
                override fun onItemClick(article: NewsEntity) {
                    val bundle = Bundle()
                    bundle.putSerializable("article", article)
                    findNavController().navigate(R.id.newsFragment, bundle)
                }

                override fun onBookmarkImageClick(article: NewsEntity) {
                    article.isSaved = article.isSaved != true
                    launch(Dispatchers.IO) {
                        newsRepository.updateArticle(article)
                    }
                }
            }
    }

    private fun loadHomeCategoryAdapter() {
        categoryAdapter = HomeCategoryAdapter(requireContext(), categories)
        binding.homeCategoriesRv.adapter = categoryAdapter
        categoryAdapter!!.homeCategoryClickListener =
            object : HomeCategoryAdapter.HomeCategoryClickListener {
                override fun onClick(category: String, position: Int) {
                    binding.homeCategoriesRv.children.forEach {
                        val view = it.findViewById<TextView>(R.id.home_category_name)
                        view.isSelected = false
                        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                    //TODO change function
                    Toast.makeText(requireContext(), category, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loadRecommendedAdapter() {
        val recommendedAdapter =
            BookmarkAdapter(requireContext(), news)
        binding.recommendedRv.adapter = recommendedAdapter
        recommendedAdapter.bookMarkItemClickListener =
            object : BookmarkAdapter.BookMarkItemClickListener {
                override fun onClick(article: NewsEntity) {
                    val bundle = Bundle()
                    bundle.putSerializable("article", article)
                    findNavController().navigate(R.id.newsFragment, bundle)
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }
}