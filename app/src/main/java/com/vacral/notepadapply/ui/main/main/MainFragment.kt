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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.auth.FirebaseAuth
import com.vacral.notepadapply.App
import com.vacral.notepadapply.R
import com.vacral.notepadapply.data.local.local.Pref
import com.vacral.notepadapply.data.local.model.NoteModel
import com.vacral.notepadapply.databinding.FragmentMainActivityBinding
import com.vacral.notepadapply.ui.main.add_note.adapter.NotesAdapter

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainActivityBinding
    private val adapter = NotesAdapter(::onLongClick, ::onClick)

    private var isGrid = false
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
        binding.ivTypeNotes.setOnClickListener {
            toggleLayout()
        }
        binding.tvLeave.setOnClickListener {
            logout()
        }
        binding.etSearchNotes.addTextChangedListener { text ->
            adapter.addNotes(App.database.dao().searchByTitle(text.toString()))
        }
    setUserAvatar()
        setUserName()
    }

    private fun setUserName() {
        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName

        if(userName != null){
            binding.tvAllNotes.text = userName
        }

    }

    private fun setUserAvatar() {
        val user = FirebaseAuth.getInstance().currentUser
        val photoUrl = user?.photoUrl

        if(photoUrl != null){
            Glide.with(this)
                .load(photoUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_menu)
                .error(R.drawable.ic_menu)
                .into(binding.ivMenu)
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

    }
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)
        googleSignInClient.signOut().addOnCompleteListener {
            Pref(requireContext()).clearRegister()
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToRegisterFragment()
            )

        }

    }
    private fun setupRecycler(){
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())


    }
    private fun toggleLayout() {
        isGrid = !isGrid

        binding.rvNotes.layoutManager =
            if (isGrid) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }

        binding.ivTypeNotes.setImageResource(
            if (isGrid) R.drawable.ic_list else R.drawable.ic_type_notes
        )
    }
}



