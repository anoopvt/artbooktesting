package com.anoopvt.artbooktesting.main.login.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.anoopvt.artbooktesting.repo.ArtRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel(), LifecycleObserver {


}