package com.example.foody.ui.fragments.profile

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button

import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foody.R
import com.example.foody.databinding.FragmentLanguageSettingBinding
import com.example.foody.databinding.FragmentPersonalScreenBinding
import com.example.foody.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
class PersonalScreenFragment : Fragment() {
    private var _binding: FragmentPersonalScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private var storageRef = Firebase.storage

    private lateinit var uri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding= FragmentPersonalScreenBinding.inflate(inflater, container, false)
        storageRef = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val imageRef = storageRef.getReference("images/"+userId)
        val image = binding.imageView2
        val btnBrowse = binding.buttonThayDoi
        val btnUpLoad = binding.buttonLuu
        binding.textView10.text=currentUser?.email
        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                // Thành công, sử dụng uri để hiển thị ảnh
                Glide.with(this)
                    .load(uri).circleCrop()
                    .into(image)
            }.addOnFailureListener { exception ->
                // Xử lý thất bại, log lỗi, hiển thị thông báo
            }
        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                image.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            }
        )
        btnBrowse.setOnClickListener{
            galleryImage.launch("image/*")
        }
        binding.buttonLuu.setOnClickListener{
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            storageRef.getReference("images").child(userId)
                .putFile(uri)
                .addOnSuccessListener {

                    task->
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        Toast.makeText(activity,"SUCCESS",Toast.LENGTH_LONG).show()
                        val userId = FirebaseAuth.getInstance().currentUser!!.uid
                        Log.d("OK",userId)
                        val mapImage = mapOf(
                            "url" to it.toString()
                        )
                        Log.d("OK2",userId)
                        val databaseReference = FirebaseDatabase.getInstance().getReference("usersImage")
                        Log.d("OK3",userId)
                        databaseReference.child(userId).setValue(mapImage)
                            .addOnSuccessListener {
                                Log.d("OK",userId)
                            }
                            .addOnFailureListener{
                                Log.d("NoOK",userId)
                            }
                    }
                }
        }
        binding.btnChangePass.setOnClickListener{
            showCustomDialogBox("OK")
        }
        return binding.root
    }
    private fun saveData(){}
    private fun showCustomDialogBox(message: String?) {
        val dialog = Dialog(activity as Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)


        var result = false
        btnYes.setOnClickListener {
            val pass1:TextView = dialog.findViewById(R.id.pass1)
            val pass2:TextView = dialog.findViewById(R.id.pass2)
            if(pass1.text.toString() == pass2.text.toString()){
                FirebaseAuth.getInstance().currentUser?.updatePassword(pass1.text.toString())?.addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){

                        Toast.makeText(this.context,"DONE",Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }else{
                        Toast.makeText(this.context ,"FAIL",Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                }
            }else{
                Toast.makeText(activity as Activity,"Passwords do not match",Toast.LENGTH_LONG).show()
            }

        }


        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}