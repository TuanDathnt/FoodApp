package com.example.foody.ui.fragments.profile

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.foody.R
import com.example.foody.databinding.FragmentConfirmSheetBinding
import com.example.foody.databinding.FragmentLanguageSettingBinding
import com.example.foody.databinding.FragmentProfileBinding
import com.example.foody.databinding.FragmentSignInBinding
import java.util.Locale

class LanguageSettingFragment : Fragment() {
    private var _binding: FragmentLanguageSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val radioButtonCheck = AppCompatDelegate.getApplicationLocales()
        _binding = FragmentLanguageSettingBinding.inflate(inflater, container, false)
        val sharedPreferences = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        val lang = sharedPreferences.getString("lang","en")
        if(lang=="en"){
            binding.radioButton1.isChecked=true
        }
        if(lang=="cn"){
            binding.radioButton2.isChecked=true
        }
        if(lang=="vn"){
            binding.radioButton3.isChecked=true
        }
        // Inflate the layout for this fragment
        binding.radioButton1.setOnClickListener{
            setLocal("en")
            setLang("en")
        }
        binding.radioButton2.setOnClickListener{
            setLocal("cn")
            setLang("cn")
        }
        binding.radioButton3.setOnClickListener{
            setLocal("vn")
            setLang("vn")
        }

        return binding.root
    }

    fun setLocal(langCode:String){
        val languageTag = langCode
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(languageTag))
        )
    }
    private fun setLang(lang:String){
        val sharedPreferences = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("lang",lang)
        editor.apply()
    }
}