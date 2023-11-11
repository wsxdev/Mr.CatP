package com.wsxdev.mrcat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment


class FragmentHome : Fragment() {

    private lateinit var textViewHome: TextView

    private var notificationView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //textViewHome = view.findViewById(R.id.textViewHome)
        return view
    }

}
