package com.example.test

import LoadingDialog
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.test.Modle.Article
import com.example.test.Modle.GeneralData
import com.example.test.Modle.Headlines
import com.example.test.databinding.FragmentHomeFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FragmentHomeFragment : Fragment(), Adapter.OnListItemClick {
    private lateinit var binding: FragmentHomeFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var searchView: SearchView
    private lateinit var textView: TextView
    private lateinit var imageView2: ImageView
    private lateinit var button: Button
    private val articleList = ArrayList<Article>()
    private val imageList = ArrayList<SlideModel>()
    private lateinit var imageSlider: ImageSlider
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        initializeRecyclerView()
        initializeSearchView()
        loadingDialog.startLoadingDialog()
        // Call the API to retrieve data
        retrieveJson(GeneralData.selectedLanguage) // Replace with your country code
    }

    private fun initializeViews() {
        searchView = binding.root.findViewById(R.id.seachView_id)
        imageSlider = binding.root.findViewById(R.id.iamgenews)
        textView = binding.root.findViewById(R.id.text_home_en_ar)
        loadingDialog = LoadingDialog(requireActivity())
    }

    private fun initializeRecyclerView() {
        recyclerView = binding.root.findViewById(R.id.recyclerview_id)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = Adapter(requireContext(), articleList)
        adapter.onListItemClick = this // Set the click listener here
        recyclerView.adapter = adapter
    }

    private fun initializeSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { fileList(it) }
                return true
            }
        })
    }

    private fun fileList(text: String) {
        val filteredList = ArrayList<Article>()
        for (item in articleList) {
            if (item.title?.lowercase()?.contains(text.lowercase()) == true) {
                filteredList.add(item)
            }
        }

        adapter.setFilterList(filteredList)
    }

    private fun retrieveJson(country: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val serviceAPI = retrofit.create(ServesAPI::class.java)
        val apiKey = "517c6e4f02a24c6f821dd5dbae4cc53e" // Replace with your actual API key

        val call: Call<Headlines> = serviceAPI.getPost(country, apiKey)
        call.enqueue(object : Callback<Headlines> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                if (response.isSuccessful) {
                    // Clear existing data
                    articleList.clear()
                    // Add new data
                    val headlines = response.body()
                    headlines?.articles?.let {
                        for (articleItem in it) {
                            if (articleItem.title != "[Removed]") {
                                Log.d("TAG", "Article Title: ${articleItem.title}")
                                articleList.add(articleItem)
                                loadingDialog.dismissDialog()
                            }
                        }
                        sliderImages()
                        // Notify the adapter of the data change
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Log.d("TAG", "onResponse: Request failed")
                }
            }

            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })
    }

    private fun sliderImages() {
        // Clear the existing image list
        imageList.clear()

        // Iterate through the articles and add them to the image list
        for (i in 0 until minOf(5, articleList.size)) {
            val imageUrl = articleList[i].urlToImage
            val title = articleList[i].title

            // Check if imageUrl is not null or empty before adding it to the list
            if (!imageUrl.isNullOrEmpty()) {
                imageList.add(SlideModel(imageUrl, title))
            } else {
                // If imageUrl is null or empty, set a default image from your drawable resources
                val defaultImageResourceId = R.drawable.placeholder // Replace 'placeholder' with your drawable resource name
                val defaultTitle = "Default Title" // You can customize the default title

                imageList.add(SlideModel(defaultImageResourceId, defaultTitle))
            }
        }

        // Set the image list in the image slider
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    override fun onItemClick(article: Article) {
        // Create an instance of ClickedNewsFragment
        val fragment = ClickedNewsFragment()

        // Pass data to the fragment using Bundle
        val bundle = Bundle()
        bundle.putString("titleNews", article.title)
        bundle.putString("description", article.description)
        bundle.putString("urlToImage", article.urlToImage)
        bundle.putString("publishedAt", article.publishedAt)
        bundle.putString("content", article.content)
        fragment.arguments = bundle

        // Replace the current fragment with ClickedNewsFragment and commit the transaction
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_fragment_id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
