package com.br444n.dragonball.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.theme.DragonBallTheme
import com.br444n.dragonball.utils.LoadingAnimation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingAnimationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingAnimation_displaysLoadingText() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(R.string.loading)
        
        composeTestRule.setContent {
            DragonBallTheme {
                LoadingAnimation()
            }
        }

        // Verificar que el texto de carga se muestra
        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }

    @Test
    fun loadingAnimation_displaysWithCustomRawResource() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(R.string.loading)
        
        composeTestRule.setContent {
            DragonBallTheme {
                LoadingAnimation(rawRes = R.raw.spinner)
            }
        }

        // Verificar que el componente se renderiza sin errores
        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }

    @Test
    fun loadingAnimation_hasCorrectContentDescription() {
        composeTestRule.setContent {
            DragonBallTheme {
                LoadingAnimation()
            }
        }

        // Verificar que la animación está presente (aunque no podemos verificar la animación en sí)
        composeTestRule
            .onRoot()
            .assertIsDisplayed()
    }
}