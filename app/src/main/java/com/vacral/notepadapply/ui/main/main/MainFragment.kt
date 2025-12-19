package com.vacral.notepadapply.ui.main.main

import android.R.attr.text
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vacral.notepadapply.App
import com.vacral.notepadapply.R
import com.vacral.notepadapply.data.local.local.Pref
import com.vacral.notepadapply.data.local.model.NoteModel
import com.vacral.notepadapply.databinding.FragmentMainActivityBinding
import com.vacral.notepadapply.ui.main.main.adapter.NotesAdapter

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainActivityBinding
    private val adapter = NotesAdapter(::onLongClick, ::onClick)
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
        binding.rvNotes.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter
        adapter.addNotes(App.database.dao().searchByTitle(""))

        binding.fbAdd.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAddNoteFragment(
                    null
                )
            )
        }
        binding.etSearchNotes.addTextChangedListener { text ->
            adapter.addNotes(App.database.dao().searchByTitle(text.toString()))
        }

    }

    private fun onLongClick(model: NoteModel) {
        Log.d("ololo", "onLongClick: ${model}")
        App.database.dao().deleteNote(model)
        adapter.addNotes(App.database.dao().searchByTitle(""))

    }

    private fun onClick(model: NoteModel) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToAddNoteFragment(
                model
            )
        )
    }

    override fun onResume() {
        super.onResume()
        adapter.addNotes(App.Companion.database.dao().searchByTitle(""))
        Log.d("TEST_NOTES", "notes size = ${adapter.notesList.size}")

    }
}