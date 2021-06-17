package com.anoopvt.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anoopvt.artbooktesting.model.ImageResponse
import com.anoopvt.artbooktesting.roomdb.ArtModel
import com.anoopvt.artbooktesting.util.Resource

class FakeArtRepositoryTest :ArtRepositoryInterface {

    private val arts = mutableListOf<ArtModel>()
    private val artLiveData = MutableLiveData<List<ArtModel>>(arts)

    override suspend fun insertArt(art: ArtModel) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: ArtModel) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<ArtModel>> {
        return artLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artLiveData.postValue(arts)
    }
}