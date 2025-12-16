package com.vacral.notepadapply.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.vacral.notepadapply.R
import com.vacral.notepadapply.data.local.Pref
import com.vacral.notepadapply.databinding.FragmentMainActivityBinding
import com.vacral.notepadapply.ui.main.on_board.adapter.NotesAdapter

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainActivityBinding
    private val adapter = NotesAdapter()
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
        binding.rvNotes.adapter = adapter
        adapter.addNotes(App.database.dao().getAllNotes())

        binding.fbAdd.setOnClickListener {
            findNavController().navigate(R.id.addNoteFragment)
        }

    }

}