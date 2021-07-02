package com.anoopvt.artbooktesting.main.login.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anoopvt.artbooktesting.R
import com.anoopvt.artbooktesting.databinding.FragmentLoginBinding
import com.anoopvt.artbooktesting.view.BaseFragment
import com.anoopvt.artbooktesting.viewmodel.ArtViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var fragmentBinding: FragmentLoginBinding? = null

    val viewModel: ArtViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        fragmentBinding = binding

        binding.button.setOnClickListener {
            viewModel.login("fdhgfshgdh")
        }

    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()

    }

}