package com.br444n.dragonball

import com.br444n.dragonball.data.remote.models.*

/**
 * Utilidades para crear objetos de prueba
 */
object TestUtils {
    
    fun createTestCharacter(
        id: Int = 1,
        name: String = "Test Character",
        description: String? = "Test description"
    ) = Character(
        id = id,
        name = name,
        ki = "60000",
        maxKi = "90000",
        race = "Saiyan",
        gender = "Male",
        description = description,
        image = "test_image.jpg",
        affiliation = "Z Fighter",
        transformations = listOf(createTestTransformation())
    )
    
    fun createTestTransformation(
        id: Int = 1,
        name: String = "Test Transformation"
    ) = Transformation(
        id = id,
        name = name,
        image = "test_transformation.jpg",
        ki = "150000"
    )
    
    fun createTestPlanet(
        id: Int = 1,
        name: String = "Test Planet",
        description: String? = "Test planet description",
        isDestroyed: Boolean = false,
        deletedAt: String? = null
    ) = Planet(
        id = id,
        name = name,
        isDestroyed = isDestroyed,
        description = description,
        image = "test_planet.jpg",
        deletedAt = deletedAt
    )
    
    fun createTestCharactersResponse(
        characters: List<Character> = listOf(createTestCharacter()),
        totalItems: Int = characters.size
    ) = CharactersResponse(
        items = characters,
        meta = Meta(
            totalItems = totalItems,
            itemCount = characters.size,
            itemsPerPage = 10,
            totalPages = (totalItems + 9) / 10,
            currentPage = 1
        )
    )
    
    fun createTestPlanetsResponse(
        planets: List<Planet> = listOf(createTestPlanet()),
        totalItems: Int = planets.size
    ) = PlanetsResponse(
        items = planets,
        meta = Meta(
            totalItems = totalItems,
            itemCount = planets.size,
            itemsPerPage = 10,
            totalPages = (totalItems + 9) / 10,
            currentPage = 1
        ),
        links = createTestLinks()
    )
    
    fun createTestLinks(
        first: String = "https://api.example.com/planets?page=1",
        previous: String = "https://api.example.com/planets?page=1",
        next: String = "https://api.example.com/planets?page=2",
        last: String = "https://api.example.com/planets?page=10"
    ) = Links(
        first = first,
        previous = previous,
        next = next,
        last = last
    )
}