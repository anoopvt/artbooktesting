package com.anoopvt.artbooktesting.viewmodel

import androidx.lifecycle.*
import com.anoopvt.artbooktesting.model.ImageResponse
import com.anoopvt.artbooktesting.repo.ArtRepositoryInterface
import com.anoopvt.artbooktesting.roomdb.ArtModel
import com.anoopvt.artbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface,
    private val savedStateHandle: SavedStateHandle?
) : ViewModel() , LifecycleObserver {

    //Art Fragment
    val artList = repository.getArt()

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

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }


}