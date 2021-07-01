package com.anoopvt.artbooktesting.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.anoopvt.artbooktesting.R
import com.anoopvt.artbooktesting.getOrAwaitValue
import com.anoopvt.artbooktesting.launchFragmentInHiltContainer
import com.anoopvt.artbooktesting.roomdb.ArtModel
import com.anoopvt.artbooktesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory





    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtToArtDetails() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(withId(R.id.artImageView)).perform(click())

        Mockito.verify(navController)
            .navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())

    }

    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).navigateUp()

    }

    @Test
    fun testSave(){
        val navController = Mockito.mock(NavController::class.java)

        var viewModel: ArtViewModel? =null


        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(), navController)
            viewModel = (this as ArtDetailsFragment).viewModel

        }
        Espresso.onView(withId(R.id.nameText)).perform(replaceText("anoop"),)
        Espresso.onView(withId(R.id.artistText)).perform(replaceText("test"))
        Espresso.onView(withId(R.id.yearText)).perform(replaceText("1969"))
        Espresso.onView(withId(R.id.saveButton)).perform(click())

        GlobalScope.launch(Dispatchers.Main){
            assertThat(viewModel?.artList?.getOrAwaitValue()).contains(ArtModel("anoop","test",1969,""))

        }


    }

}