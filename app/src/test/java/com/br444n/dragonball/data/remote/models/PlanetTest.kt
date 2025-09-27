package com.br444n.dragonball.data.remote.models

import org.junit.Test
import org.junit.Assert.*

class PlanetTest {

    @Test
    fun `Planet creation with all parameters works correctly`() {
        val planet = Planet(
            id = 1,
            name = "Tierra",
            isDestroyed = false,
            description = "Planeta natal de los humanos",
            image = "earth.jpg",
            deletedAt = null
        )

        assertEquals(1, planet.id)
        assertEquals("Tierra", planet.name)
        assertFalse(planet.isDestroyed)
        assertEquals("Planeta natal de los humanos", planet.description)
        assertEquals("earth.jpg", planet.image)
        assertNull(planet.deletedAt)
    }

    @Test
    fun `Planet creation with destroyed planet works correctly`() {
        val deletedDate = "2023-01-01T00:00:00Z"
        val planet = Planet(
            id = 2,
            name = "Vegeta",
            isDestroyed = true,
            description = "Planeta destruido por Frieza",
            image = "vegeta.jpg",
            deletedAt = deletedDate
        )

        assertEquals(2, planet.id)
        assertEquals("Vegeta", planet.name)
        assertTrue(planet.isDestroyed)
        assertEquals("Planeta destruido por Frieza", planet.description)
        assertEquals("vegeta.jpg", planet.image)
        assertEquals(deletedDate, planet.deletedAt)
    }

    @Test
    fun `Planet creation with null values works correctly`() {
        val planet = Planet(
            id = 3,
            name = "Namek",
            isDestroyed = false,
            description = null,
            image = null,
            deletedAt = null
        )

        assertEquals(3, planet.id)
        assertEquals("Namek", planet.name)
        assertFalse(planet.isDestroyed)
        assertNull(planet.description)
        assertNull(planet.image)
        assertNull(planet.deletedAt)
    }

    @Test
    fun `Links creation works correctly`() {
        val links = Links(
            first = "https://api.example.com/planets?page=1",
            previous = "https://api.example.com/planets?page=1",
            next = "https://api.example.com/planets?page=2",
            last = "https://api.example.com/planets?page=10"
        )

        assertEquals("https://api.example.com/planets?page=1", links.first)
        assertEquals("https://api.example.com/planets?page=1", links.previous)
        assertEquals("https://api.example.com/planets?page=2", links.next)
        assertEquals("https://api.example.com/planets?page=10", links.last)
    }

    @Test
    fun `PlanetsResponse creation works correctly`() {
        val planets = listOf(
            Planet(1, "Tierra", false, "Planeta natal", "earth.jpg", null),
            Planet(2, "Vegeta", true, "Planeta destruido", "vegeta.jpg", "2023-01-01T00:00:00Z")
        )
        
        val meta = Meta(2, 2, 10, 1, 1)
        val links = Links("first", "prev", "next", "last")
        
        val response = PlanetsResponse(planets, meta, links)

        assertEquals(2, response.items.size)
        assertEquals(planets, response.items)
        assertEquals(meta, response.meta)
        assertEquals(links, response.links)
    }
}