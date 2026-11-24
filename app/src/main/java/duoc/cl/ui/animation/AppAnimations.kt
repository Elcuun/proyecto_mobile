package duoc.cl.ui.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

/**
 * Utilidades y especificaciones de animación centralizadas para la aplicación.
 * Permite mantener un estilo visual coherente y reutilizable en transiciones (Fade, Slide, etc.).
 */
object AppAnimations {

    /**
     * Transición de entrada estándar para mensajes de error, alertas y tarjetas dinámicas.
     * Combina aparición gradual (fade in) y deslizamiento vertical suave.
     */
    val slideFadeIn: EnterTransition = fadeIn(animationSpec = tween(300)) +
            slideInVertically(animationSpec = tween(300), initialOffsetY = { -30 })

    /**
     * Transición de salida estándar para mensajes de error, alertas y tarjetas dinámicas.
     * Combina desvanecimiento (fade out) y deslizamiento vertical hacia arriba.
     */
    val slideFadeOut: ExitTransition = fadeOut(animationSpec = tween(300)) +
            slideOutVertically(animationSpec = tween(300), targetOffsetY = { -30 })

    /**
     * Transición de expansión para elementos multimedia o previsualizaciones de fotos y GPS.
     */
    val expandFadeIn: EnterTransition = fadeIn(animationSpec = tween(400)) +
            expandVertically(animationSpec = tween(400))

    /**
     * Transición de colapso para elementos multimedia o previsualizaciones de fotos y GPS.
     */
    val collapseFadeOut: ExitTransition = fadeOut(animationSpec = tween(400)) +
            shrinkVertically(animationSpec = tween(400))
}
