package com.br444n.dragonball.data.remote.models

import com.br444n.dragonball.services.TranslationService
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class ExtensionsTest {

    @Before
    fun setup() {
        mockkObject(TranslationService)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Character translateTo returns same character when language is Spanish`() = runTest {
        // Given
        val character = Character(
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

        // When
        val result = character.translateTo("es")

        // Then
        assertEquals(character, result)
        verify { TranslationService wasNot Called }
    }

    @Test
    fun `Character translateTo calls translation service for English`() = runTest {
        // Given
        val originalDescription = "Protagonista principal"
        val translatedDescription = "Main protagonist"
        val character = Character(
            id = 1,
            name = "Goku",
            ki = null,
            maxKi = null,
            race = null,
            gender = null,
            description = originalDescription,
            image = null,
            affiliation = null
        )

        coEvery { 
            TranslationService.translateTextIfNeeded(originalDescription, "en") 
        } returns translatedDescription

        // When
        val result = character.translateTo("en")

        // Then
        assertEquals(translatedDescription, result.description)
        assertEquals(character.id, result.id)
        assertEquals(character.name, result.name)
        coVerify { TranslationService.translateTextIfNeeded(originalDescription, "en") }
    }

    @Test
    fun `Character translateTo handles null description`() = runTest {
        // Given
        val character = Character(
            id = 1,
            name = "Goku",
            ki = null,
            maxKi = null,
            race = null,
            gender = null,
            description = null,
            image = null,
            affiliation = null
        )

        coEvery { 
            TranslationService.translateTextIfNeeded(null, "en") 
        } returns null

        // When
        val result = character.translateTo("en")

        // Then
        assertNull(result.description)
        coVerify { TranslationService.translateTextIfNeeded(null, "en") }
    }

    @Test
    fun `Planet translateTo returns same planet when language is Spanish`() = runTest {
        // Given
        val planet = Planet(
            id = 1,
            name = "Tierra",
            isDestroyed = false,
            description = "Planeta natal de los humanos",
            image = "earth.jpg",
            deletedAt = null
        )

        // When
        val result = planet.translateTo("es")

        // Then
        assertEquals(planet, result)
        verify { TranslationService wasNot Called }
    }

    @Test
    fun `Transformation translateTo returns same transformation when language is Spanish`() = runTest {
        // Given
        val transformation = Transformation(
            id = 1,
            name = "Super Saiyan",
            image = "ss.jpg",
            ki = "150000"
        )

        // When
        val result = transformation.translateTo("es")

        // Then
        assertEquals(transformation, result)
        verify { TranslationService wasNot Called }
    }
}