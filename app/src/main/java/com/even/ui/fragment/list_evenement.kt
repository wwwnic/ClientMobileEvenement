package com.even.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.even.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [liste_evenement.newInstance] factory method to
 * create an instance of this fragment.
 */
class liste_evenement : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


            val listView = view?.findViewById<ListView>(R.id.listEvenement_listEvenement) as ListView
//        val redColor = Color.parseColor("#FF0000")
//        listView.setBackgroundColor(redColor)

            listView.adapter = MyCustomAdapter(this.context as Context) // this needs to be my custom adapter telling my list what to render
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_evenement, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Liste_evenement.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            liste_evenement().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext: Context

        init {
            mContext = context
        }

        // responsible for how many rows in my list
        override fun getCount(): Int {
            return 5
        }

        // you can also ignore this
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        // you can ignore this for now
        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        // responsible for rendering out each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val textView = TextView(mContext)
            textView.text = "HERE is my ROW for my LISTVIEW"
            return textView
        }

    }
}