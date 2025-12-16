package com.vacral.notepadapply.ui.main.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.vacral.notepadapply.databinding.FragmentAddNoteBinding
import com.vacral.notepadapply.model.NoteModel
import com.vacral.notepadapply.ui.main.App
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {

            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentTime = sdf.format(Date())

                            viewLifecycleOwner.lifecycleScope.launch {
                App.database.dao().addNote(noteModel = NoteModel(title = title, desc = desc, time = currentTime))
                findNavController().navigateUp()
            }
        }
    }
}
