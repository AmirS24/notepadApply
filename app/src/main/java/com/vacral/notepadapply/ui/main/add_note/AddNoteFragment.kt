package com.vacral.notepadapply.ui.main.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vacral.notepadapply.databinding.FragmentAddNoteBinding
import com.vacral.notepadapply.model.NoteModel
import com.vacral.notepadapply.ui.main.App
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val realTime = formatter.format(Date()).toString()

        binding.tvComplete.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()

            App.database.dao()
                .addNote(NoteModel(title = title, desc = desc, time = realTime))

            findNavController().navigateUp()
        }
        binding.timeDay.text = realTime.toString()
    }

}
