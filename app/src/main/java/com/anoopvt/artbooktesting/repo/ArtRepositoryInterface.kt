package com.anoopvt.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.anoopvt.artbooktesting.model.ImageResponse
import com.anoopvt.artbooktesting.roomdb.ArtModel
import com.anoopvt.artbooktesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: ArtModel)

    suspend fun deleteArt(art: ArtModel)

    fun getArt(): LiveData<List<ArtModel>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>

}