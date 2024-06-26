package com.example.foody.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.foody.R


class ThirdScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_third_screen, container, false)

        val next = view.findViewById<TextView>(R.id.nextButton3)
        next.setOnClickListener{
            onBoardingIsFinished()
            findNavController().navigate(R.id.action_viewPagerFragment_to_recipesBottomSheet)

        }
        return view
    }
    private fun onBoardingIsFinished(){
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding212", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("finisheda",true)
        editor.apply()
    }
}