package com.anoopvt.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anoopvt.artbooktesting.MainCoroutineRule
import com.anoopvt.artbooktesting.getOrAwaitValueTest
import com.anoopvt.artbooktesting.repo.FakeArtRepository
import com.anoopvt.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        viewModel = ArtViewModel(FakeArtRepository(), null)
    }

    @Test
    fun `insert art without year return error`() {
        viewModel.makeArt("Mona lisa", "Da vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name return error`() {
        viewModel.makeArt("", "Da vinci", "1965")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName return error`() {
        viewModel.makeArt("Mona lisa", "", "1964")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

}