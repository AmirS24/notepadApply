package com.vacral.notepadapply.ui.main.reg_note

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GithubAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.vacral.notepadapply.R
import com.vacral.notepadapply.data.local.local.Pref
import com.vacral.notepadapply.databinding.FragmentRegisterBinding
import com.vacral.notepadapply.ui.main.on_board.OnBoardFragmentDirections


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var reg: FirebaseAuth
    private lateinit var signInOption: GoogleSignInClient
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                updateUi(null)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reg = Firebase.auth


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_client_id)
        ).requestEmail().build()
        signInOption = GoogleSignIn.getClient(requireContext(), gso)
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegGoogle.setOnClickListener {
            signInLauncher.launch(signInOption.signInIntent)
       }

    }
    private fun navigateToMain() {
        Pref(requireContext()).setRegister()
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToMainFragment()
        )
    }
    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        reg.signInWithCredential(credential).addOnCompleteListener {
            task -> if(task.isSuccessful){
                val user = reg.currentUser
            updateUi(user)
            }else{
                updateUi(null)
        }
        }
    }
    private fun updateUi(user: FirebaseUser?) {
        if (user != null) {
            Pref(requireContext()).setRegister()

            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToMainFragment()
            )
        } else {
            Toast.makeText(requireContext(),"Интернет подключи да!", Toast.LENGTH_SHORT).show()
        }
    }
}




