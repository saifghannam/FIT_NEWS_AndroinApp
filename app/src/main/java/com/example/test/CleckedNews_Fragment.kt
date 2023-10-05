package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.test.databinding.FragmentCleckedNewsBinding


class ClickedNewsFragment : Fragment() {
    private lateinit var binding: FragmentCleckedNewsBinding
    private lateinit var textView: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCleckedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()

        val description = arguments?.getString("description")
        val publishedAt = arguments?.getString("publishedAt")
        val content = arguments?.getString("content")
        val urlToImage = arguments?.getString("urlToImage")

        // Set the retrieved data to your views
        textView.text = description
        textView2.text = publishedAt
        textView3.text = content

        // Load image using Glide
        Glide.with(this)
            .load(urlToImage)
            .into(imageView)
    }

    private fun initializeViews() {
        textView = binding.descriptionId
        textView2 = binding.publishedAtId
        textView3 = binding.contentId
        imageView = binding.imageView
    }
}
