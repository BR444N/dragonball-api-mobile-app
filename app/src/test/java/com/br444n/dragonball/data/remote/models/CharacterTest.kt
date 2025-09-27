package com.br444n.dragonball.data.remote.models

import org.junit.Test
import org.junit.Assert.*

class CharacterTest {

    @Test
    fun `Character creation with all parameters works correctly`() {
        val transformations = listOf(
            Transformation(1, "Super Saiyan", "image_url", "150000")
        )
        
        val character = Character(
            id = 1,
            name = "Goku",
            ki = "60000",
            maxKi = "90000",
            race = "Saiyan",
            gender = "Male",
            description = "Main protagonist",
            image = "goku_image_url",
            affiliation = "Z Fighter",
            transformations = transformations
        )

        assertEquals(1, character.id)
        assertEquals("Goku", character.name)
        assertEquals("60000", character.ki)
        assertEquals("90000", character.maxKi)
        assertEquals("Saiyan", character.race)
        assertEquals("Male", character.gender)
        assertEquals("Main protagonist", character.description)
        assertEquals("goku_image_url", character.image)
        assertEquals("Z Fighter", character.affiliation)
        assertEquals(1, character.transformations?.size)
    }

    @Test
    fun `Character creation with minimal parameters works correctly`() {
        val character = Character(
            id = 2,
            name = "Vegeta",
            ki = null,
            maxKi = null,
            race = null,
            gender = null,
            description = null,
            image = null,
            affiliation = null,
            transformations = null
        )

        assertEquals(2, character.id)
        assertEquals("Vegeta", character.name)
        assertNull(character.ki)
        assertNull(character.transformations)
    }

    @Test
    fun `Transformation creation works correctly`() {
        val transformation = Transformation(
            id = 1,
            name = "Super Saiyan",
            image = "ss_image_url",
            ki = "150000"
        )

        assertEquals(1, transformation.id)
        assertEquals("Super Saiyan", transformation.name)
        assertEquals("ss_image_url", transformation.image)
        assertEquals("150000", transformation.ki)
    }

    @Test
    fun `Meta creation works correctly`() {
        val meta = Meta(
            totalItems = 100,
            itemCount = 10,
            itemsPerPage = 10,
            totalPages = 10,
            currentPage = 1
        )

        assertEquals(100, meta.totalItems)
        assertEquals(10, meta.itemCount)
        assertEquals(10, meta.itemsPerPage)
        assertEquals(10, meta.totalPages)
        assertEquals(1, meta.currentPage)
    }
}