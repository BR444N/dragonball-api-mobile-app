package com.br444n.dragonball.data.remote.repository

import com.br444n.dragonball.data.remote.DragonBallApiService
import com.br444n.dragonball.data.remote.models.*
import com.br444n.dragonball.managers.language.UnifiedLanguageManager
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class CharacterRepositoryTest {

    private lateinit var repository: CharacterRepository
    private val mockApi = mockk<DragonBallApiService>()

    @Before
    fun setup() {
        // Mock del ApiClient
        mockkObject(com.br444n.dragonball.data.remote.ApiClient)
        every { com.br444n.dragonball.data.remote.ApiClient.api } returns mockApi
        
        // Mock del UnifiedLanguageManager
        mockkObject(UnifiedLanguageManager)
        every { UnifiedLanguageManager.currentLanguage } returns MutableStateFlow("es")
        
        repository = CharacterRepository()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getCharacters returns response from API when language is Spanish`() = runTest {
        // Given
        val expectedResponse = CharactersResponse(
            items = listOf(
                Character(
                    id = 1,
                    name = "Goku",
                    ki = "60000",
                    maxKi = "90000",
                    race = "Saiyan",
                    gender = "Male",
                    description = "Protagonista principal",
                    image = "goku.jpg",
                    affiliation = "Z Fighter"
                )
            ),
            meta = Meta(1, 1, 10, 1, 1)
        )
        
        coEvery { mockApi.getCharacters(58, 0) } returns expectedResponse
        every { UnifiedLanguageManager.currentLanguage.value } returns "es"

        // When
        val result = repository.getCharacters()

        // Then
        assertEquals(expectedResponse, result)
        coVerify { mockApi.getCharacters(58, 0) }
    }

    @Test
    fun `getCharacters with custom parameters calls API with correct values`() = runTest {
        // Given
        val limit = 20
        val offset = 10
        val expectedResponse = CharactersResponse(
            items = emptyList(),
            meta = Meta(0, 0, 20, 0, 1)
        )
        
        coEvery { mockApi.getCharacters(limit, offset) } returns expectedResponse
        every { UnifiedLanguageManager.currentLanguage.value } returns "es"

        // When
        val result = repository.getCharacters(limit, offset)

        // Then
        assertEquals(expectedResponse, result)
        coVerify { mockApi.getCharacters(limit, offset) }
    }

    @Test
    fun `getCharacterById returns character from API when language is Spanish`() = runTest {
        // Given
        val characterId = "1"
        val expectedCharacter = Character(
            id = 1,
            name = "Goku",
            ki = "60000",
            maxKi = "90000",
            race = "Saiyan",
            gender = "Male",
            description = "Protagonista principal",
            image = "goku.jpg",
            affiliation = "Z Fighter"
        )
        
        coEvery { mockApi.getCharacterById(characterId) } returns expectedCharacter
        every { UnifiedLanguageManager.currentLanguage.value } returns "es"

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        assertEquals(expectedCharacter, result)
        coVerify { mockApi.getCharacterById(characterId) }
    }

    @Test
    fun `getCharacterById with explicit Spanish language returns original character`() = runTest {
        // Given
        val characterId = "1"
        val expectedCharacter = Character(
            id = 1,
            name = "Goku",
            ki = null,
            maxKi = null,
            race = null,
            gender = null,
            description = "Protagonista principal",
            image = null,
            affiliation = null
        )
        
        coEvery { mockApi.getCharacterById(characterId) } returns expectedCharacter

        // When
        val result = repository.getCharacterById(characterId, "es")

        // Then
        assertEquals(expectedCharacter, result)
        coVerify { mockApi.getCharacterById(characterId) }
    }
}