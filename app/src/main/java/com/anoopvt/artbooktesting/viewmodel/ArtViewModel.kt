package com.anoopvt.artbooktesting.viewmodel

import androidx.lifecycle.*
import com.anoopvt.artbooktesting.data.UserRepository
import com.anoopvt.artbooktesting.model.ImageResponse
import com.anoopvt.artbooktesting.model.ImageResult
import com.anoopvt.artbooktesting.repo.ArtRepositoryInterface
import com.anoopvt.artbooktesting.roomdb.ArtModel
import com.anoopvt.artbooktesting.util.Resource
import com.anoopvt.artbooktesting.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel(), LifecycleObserver {

    private val _uiState = MutableLiveData(ActionCenterState())
    val uiState: LiveData<ActionCenterState> = _uiState

    //Art Fragment
    val artList = repository.getArt()

    val token = userRepository.getToken().asLiveData()

    // Image api fragment

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    // Art Details fragment

    private var insertArtMsg = MutableLiveData<Resource<ArtModel>>()
    val insertArtMessage: LiveData<Resource<ArtModel>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<ArtModel>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(art: ArtModel) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: ArtModel) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Enter name,artist,year", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("year should be number", null))
            return
        }

        val art = ArtModel(name, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    suspend fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        withContext(Main) {
            _uiState.value = ActionCenterState(loading = true)
        }
        val response = repository.searchImage(searchString)

        when (response.status) {
            Status.SUCCESS -> {
//                images.value = response
                val urls = response.data?.hits?.map { imageResult ->
                    imageResult.previewURL
                } as ArrayList<String>

                withContext(Main) {
                    _uiState.value = ActionCenterState(mainList = urls)
                }
            }

            Status.ERROR -> {
                withContext(Main) {
                    _uiState.value =
                        ActionCenterState(error = true, errorMessage = response.message ?: "Error")
                }
            }
        }


    }

    fun login(string: String) {
        viewModelScope.launch { userRepository.saveToken(string) }

    }


    data class ActionCenterState(
        val mainList: ArrayList<String> = arrayListOf(),
        val loading: Boolean = false,
        val error: Boolean = false,
        val errorMessage: String = "",

        )


}