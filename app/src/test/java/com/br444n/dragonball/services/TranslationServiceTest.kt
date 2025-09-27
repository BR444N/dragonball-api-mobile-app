package com.br444n.dragonball.services

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

class TranslationServiceTest {

    @Test
    fun `translateTextIfNeeded returns original text when text is null`() = runTest {
        val result = TranslationService.translateTextIfNeeded(null, "en")
        assertNull(result)
    }

    @Test
    fun `translateTextIfNeeded returns original text when text is blank`() = runTest {
        val result = TranslationService.translateTextIfNeeded("", "en")
        assertEquals("", result)
    }

    @Test
    fun `translateTextIfNeeded returns original text for Spanish target language`() = runTest {
        val originalText = "Hola mundo"
        val result = TranslationService.translateTextIfNeeded(originalText, "es")
        assertEquals(originalText, result)
    }

    @Test
    fun `translateTextIfNeeded returns original text for unsupported language`() = runTest {
        val originalText = "Hello world"
        val result = TranslationService.translateTextIfNeeded(originalText, "fr")
        assertEquals(originalText, result)
    }

    @Test
    fun `translateToEnglish returns original text when input is blank`() = runTest {
        val result = TranslationService.translateToEnglish("")
        assertEquals("", result)
    }
}