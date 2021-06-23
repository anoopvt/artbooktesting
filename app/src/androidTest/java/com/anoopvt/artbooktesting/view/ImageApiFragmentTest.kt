package com.anoopvt.artbooktesting.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.anoopvt.artbooktesting.R
import com.anoopvt.artbooktesting.adapter.ImageRecyclerAdapter
import com.anoopvt.artbooktesting.getOrAwaitValue
import com.anoopvt.artbooktesting.launchFragmentInHiltContainer
import com.anoopvt.artbooktesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class ImageApiFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()

    }

    @Test
    fun selectImageTest() {

        val navController = Mockito.mock(NavController::class.java)

        var viewModel: ArtViewModel? = null


        val selectedImageUrl = "google.com"


        launchFragmentInHiltContainer<ImageApiFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)

            val fragment = this as ImageApiFragment
            viewModel = fragment.viewModel
            fragment.imageRecyclerAdapter.images = listOf(selectedImageUrl)

        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        Mockito.verify(navController).popBackStack()

        GlobalScope.launch(Dispatchers.Main){
            assertThat(viewModel?.selectedImageUrl?.getOrAwaitValue())
                .isEqualTo(selectedImageUrl)
        }

    }

}