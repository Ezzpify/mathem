package com.example.mathemtest.viewmodels

import com.example.mathemtest.api.DeliveryTime
import com.example.mathemtest.api.MathemRepository
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
class DeliveryViewModelTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var repository: MathemRepository

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DeliveryViewModel

    @Before
    fun setup()  {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = DeliveryViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchDeliveryDates fetches data from repository`() = runTest {
        // Arrange
        val deliveryDates = listOf("2023-05-08", "2023-05-09", "2023-05-10")
        `when`(repository.getDeliveryDates()).thenReturn(deliveryDates)

        // Act
        advanceUntilIdle()

        // Assert
        val result = viewModel.deliveryData.value
        assertEquals(deliveryDates, result.deliveryDates)
        verify(repository).getDeliveryDates()
    }

    @Test
    fun `fetchDeliveryTimes fetches data from repository`() = runTest {
        // Arrange
        val deliveryDates = listOf("2023-05-08", "2023-05-09", "2023-05-10")
        `when`(repository.getDeliveryDates()).thenReturn(deliveryDates)

        val datestamp = "2023-05-08"
        val deliveryTimes = listOf(
            DeliveryTime("1", datestamp, "08:00", "10:00", inHomeAvailable = true),
            DeliveryTime("2", datestamp, "10:00", "12:00", inHomeAvailable = true),
            DeliveryTime("3", datestamp, "12:00", "14:00", inHomeAvailable = false)
        )
        `when`(repository.getDeliveryTimes(datestamp)).thenReturn(deliveryTimes)

        // Act
        advanceUntilIdle()
        viewModel.fetchDeliveryTimes(datestamp)
        advanceUntilIdle()

        // Assert
        val result = viewModel.deliveryData.value
        assertEquals(deliveryTimes, result.deliveryTimes)
        verify(repository).getDeliveryTimes(datestamp)
    }
}
