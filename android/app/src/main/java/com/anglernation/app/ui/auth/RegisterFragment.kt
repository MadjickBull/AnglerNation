package com.anglernation.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var registerJob: kotlinx.coroutines.Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phoneNumber = binding.phoneEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() &&
                username.isNotEmpty() && email.isNotEmpty() &&
                phoneNumber.isNotEmpty() && password.isNotEmpty()) {

                registerJob = lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val token = AuthRepository.register(
                            firstName, lastName, username, email, phoneNumber, password
                        )
                        // Navigate to main screen or login success
                        requireActivity().onBackPressed()
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(requireContext(), "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registerJob?.cancel()
        _binding = null
    }
}