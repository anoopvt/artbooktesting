package com.anoopvt.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.anoopvt.artbooktesting.api.RetrofitAPI
import com.anoopvt.artbooktesting.model.ImageResponse
import com.anoopvt.artbooktesting.roomdb.ArtDao
import com.anoopvt.artbooktesting.roomdb.ArtModel
import com.anoopvt.artbooktesting.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: ArtModel) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: ArtModel) {
        artDao.delete(art)
    }

    override fun getArt(): LiveData<List<ArtModel>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error(e.message ?: "Something went wrong", null)
        }
    }
}