package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = view?.findViewById<ProgressBar>(R.id.progress)
        val home = File(requireContext().filesDir, "home.txt")
        var list: ArrayList<Item> = ArrayList()

        thread(start = true) {

            if (home.exists()) {
                var idSongs = home.readText().trim().split(" ")

                for (i in 0 until idSongs.size) {
                    println(idSongs.get(i))
                }
                for (i in 0 until idSongs.size) {
                    list.add(
                        Item(
                            Parse.parsingSong(idSongs.get(i)).name,
                            Parse.parsingSong(idSongs.get(i)).artist.name,
                            Parse.parsingSong(idSongs.get(i)).imgURL,
                            Parse.parsingSong(idSongs.get(i)).id.toString()
                        )
                    )
                }
            }
            activity?.runOnUiThread {
                progressBar?.visibility = ProgressBar.INVISIBLE
                recyclerView?.adapter = MyRecyclerViewAdapter(list, object : MyOnClickListener {
                    override fun onClicked(id: String) {
                        val intent = Intent(context, SongActivity::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }

                })
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}