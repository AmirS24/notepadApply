package com.vacral.notepadapply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import com.vacral.notepadapply.databinding.FragmentMainActivityBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainActivityBinding
    var Text: String ="text"
    private lateinit var pref: Pref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainActivityBinding.inflate(inflater, container, false)

        pref = Pref(requireContext())
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            save()
        }
        Text = pref.getText().toString()

    }
    private fun save() {
        Text = binding.editText.text.toString()
        binding.text.setText(Text)

    }

}
