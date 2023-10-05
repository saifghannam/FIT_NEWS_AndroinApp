package com.example.test

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.Modle.Article
import com.example.test.Modle.GeneralData
import com.example.test.databinding.FragmentFavartFragmentBinding
import java.util.ArrayList

class favart_fragment : Fragment(), Adapter.OnListItemClick, Adapter2.OnListItemClick {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter2
    private lateinit var searchView: SearchView
    private lateinit var emptyTextView: TextView
    private lateinit var favaritTextView: TextView


    private val articleList2 = ArrayList<Article>()
    private lateinit var binding: FragmentFavartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavartFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        initializeRecyclerView()
        initializeSearchView()

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initializeViews() {
        searchView = binding.root.findViewById(R.id.seachView_id)
        emptyTextView = binding.root.findViewById(R.id.empty_text_view)
        favaritTextView = binding.root.findViewById(R.id.favriet_text_id)

    }

    private fun initializeRecyclerView() {
        recyclerView = binding.root.findViewById(R.id.recyclerview_id)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = Adapter2(requireContext(), GeneralData.articlesList)
        adapter.onListItemClick = this // Set the click listener here
        recyclerView.adapter = adapter

        if (GeneralData.articlesList.isEmpty()) {
            emptyTextView.visibility = View.VISIBLE
        } else {
            emptyTextView.visibility = View.GONE
        }

        if (GeneralData.articlesList.isNotEmpty()) {
            favaritTextView.visibility = View.VISIBLE
        } else {
            favaritTextView.visibility = View.GONE
        }

    }

    val swipeToDeleteCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // Implement this if you want to support drag-and-drop reordering.
            return false
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            val builder = AlertDialog.Builder(requireContext())
            val Document = getString(R.string.Document_changed)
            val massage = getString(R.string.massage)

            val Yes = getString(R.string.Yes)
            val No = getString(R.string.No)
            builder.apply {
                setTitle(Document)
                setMessage(massage)
                setPositiveButton(Yes) { dialogInterface: DialogInterface, i: Int ->

                    GeneralData.articlesList.removeAt(position)
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.home_fragment_id,favart_fragment()).commit()
                }
                setNegativeButton(No) { dialogInterface: DialogInterface, i: Int ->

                }
            }

            val dialog = builder.create()
            dialog.show()


            recyclerView.adapter?.notifyItemRemoved(position)

        }
    }

    val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
// Attach the ItemTouchHelper to your RecyclerView



    private fun initializeSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                fileList(newText)
                return true
            }
        })
    }

    private fun fileList(text: String) {
        val filteredList = ArrayList<Article>()
        for (item in GeneralData.articlesList) {
            if (item.title?.lowercase()?.contains(text.lowercase()) == true) {
                filteredList.add(item)

            }
        }

        if (filteredList.isEmpty()) {
            // Handle case when filtered list is empty
        } else {
            adapter.setFilterList(filteredList)
        }

    }



    override fun onItemClick(article: Article) {
        // Create an instance of CleckedNewsFragment
        var fragment = ClickedNewsFragment()

        // Pass data to the fragment using Bundle
        val bundle = Bundle()
        bundle.putString("titleNews", article.title)
        bundle.putString("description", article.description)
        bundle.putString("urlToImage", article.urlToImage)
        bundle.putString("publishedAt", article.publishedAt)
        bundle.putString("content", article.content)
        fragment.arguments = bundle

        // Replace the current fragment with CleckedNewsFragment and commit the transaction
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_fragment_id, fragment) // Use the existing fragment instance
        transaction.addToBackStack(null) // Optional: Add transaction to the back stack
        transaction.commit()

    }


    private fun showMeDialog() {

        val builder = AlertDialog.Builder(requireContext())
        var Titale=getString(R.string.Document_changed)
        var message=getString(R.string.massage)
        var yes=getString(R.string.Yes)
        var no=getString(R.string.No)
         builder.apply {
            setTitle(Titale)
            setMessage(message)
            setPositiveButton(yes) { dialogInterface: DialogInterface, i: Int ->

            }
            setNegativeButton(no) { dialogInterface: DialogInterface, i: Int ->

            }
        }

        val dialog = builder.create()
        dialog.show()
    }



}
