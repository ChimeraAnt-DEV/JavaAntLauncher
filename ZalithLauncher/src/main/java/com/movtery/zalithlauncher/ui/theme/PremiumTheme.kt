/*
 * Zalith Launcher 2
 * Copyright (C) 2025 MovTery <movtery228@qq.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */

package com.movtery.zalithlauncher.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movtery.zalithlauncher.R

/**
 * Obsidian VIP — 深色、高对比的“会员级”配色
 * 以曜石黑为底，辅以香槟金主色，紫罗兰与极光青作为点缀色
 */
private val obsidianPrimary = Color(0xFFF6D06B)
private val obsidianOnPrimary = Color(0xFF231A00)
private val obsidianPrimaryContainer = Color(0xFF4A3A00)
private val obsidianOnPrimaryContainer = Color(0xFFFFE9A8)

private val obsidianSecondary = Color(0xFFC4B5FD)
private val obsidianOnSecondary = Color(0xFF21174A)
private val obsidianSecondaryContainer = Color(0xFF3A2E6E)
private val obsidianOnSecondaryContainer = Color(0xFFE9E2FF)

private val obsidianTertiary = Color(0xFF7FE3FF)
private val obsidianOnTertiary = Color(0xFF00323D)
private val obsidianTertiaryContainer = Color(0xFF004E5E)
private val obsidianOnTertiaryContainer = Color(0xFFB9F1FF)

val obsidianVipDark = darkColorScheme(
    primary = obsidianPrimary,
    onPrimary = obsidianOnPrimary,
    primaryContainer = obsidianPrimaryContainer,
    onPrimaryContainer = obsidianOnPrimaryContainer,
    secondary = obsidianSecondary,
    onSecondary = obsidianOnSecondary,
    secondaryContainer = obsidianSecondaryContainer,
    onSecondaryContainer = obsidianOnSecondaryContainer,
    tertiary = obsidianTertiary,
    onTertiary = obsidianOnTertiary,
    tertiaryContainer = obsidianTertiaryContainer,
    onTertiaryContainer = obsidianOnTertiaryContainer,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF07070B),
    onBackground = Color(0xFFEDEAF2),
    surface = Color(0xFF0B0B11),
    onSurface = Color(0xFFEDEAF2),
    surfaceVariant = Color(0xFF2A2833),
    onSurfaceVariant = Color(0xFFCBC6D6),
    outline = Color(0xFF8B8798),
    outlineVariant = Color(0xFF3C3A47),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFEDEAF2),
    inverseOnSurface = Color(0xFF1B1A20),
    inversePrimary = Color(0xFF7A5D00),
    surfaceDim = Color(0xFF07070B),
    surfaceBright = Color(0xFF3A3846),
    surfaceContainerLowest = Color(0xFF040407),
    surfaceContainerLow = Color(0xFF0E0E15),
    surfaceContainer = Color(0xFF13131B),
    surfaceContainerHigh = Color(0xFF1B1A24),
    surfaceContainerHighest = Color(0xFF24222E),
)

/**
 * 当前是否处于高级（VIP）主题
 */
val LocalPremiumTheme = compositionLocalOf { false }

/**
 * VIP 金色渐变，用于强调“会员级”视觉
 */
fun premiumGoldGradient(): Brush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFE9A8),
        Color(0xFFF6D06B),
        Color(0xFFB98F2E),
        Color(0xFFF6D06B)
    )
)

/**
 * 反应式流光渐变：颜色沿对角线循环流动
 * @param reverse 是否反向流动
 */
@Composable
fun rememberPremiumSheenBrush(
    reverse: Boolean = false,
    durationMillis: Int = 4200
): Brush {
    val transition = rememberInfiniteTransition(label = "PremiumSheen")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "PremiumSheenProgress"
    )

    val t = if (reverse) 1f - progress else progress
    return remember(t) {
        val start = -600f + t * 1800f
        Brush.linearGradient(
            colors = listOf(
                Color(0x00F6D06B),
                Color(0x66F6D06B),
                Color(0xCCFFE9A8),
                Color(0x66C4B5FD),
                Color(0x00C4B5FD)
            ),
            start = Offset(start, 0f),
            end = Offset(start + 700f, 700f)
        )
    }
}

/**
 * VIP 金色描边（采用 [BorderStroke]，可传给 Card）
 * 使用静态金色渐变以控制开销；动态流光由 [premiumAmbience] 统一提供
 */
@Composable
fun premiumBorderStroke(
    enabled: Boolean = LocalPremiumTheme.current
): BorderStroke? {
    if (!enabled) return null
    return BorderStroke(1.5.dp, premiumGoldGradient())
}

/**
 * 反应式高级主题氛围：
 * 在多主题切换/深色模式下保持稳定，仅当处于 [LocalPremiumTheme] 时绘制极光辉光
 */
@Composable
fun Modifier.premiumAmbience(
    enabled: Boolean = LocalPremiumTheme.current
): Modifier {
    if (!enabled) return this
    val brush = rememberPremiumSheenBrush(durationMillis = 9000)
    return this
        .fillMaxSize()
        .drawWithContent {
            drawContent()
            //左上角的紫罗兰辉光
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x33C4B5FD), Color(0x00C4B5FD)),
                    center = Offset(size.width * 0.15f, size.height * 0.1f),
                    radius = size.minDimension * 0.75f
                ),
                radius = size.minDimension * 0.75f,
                center = Offset(size.width * 0.15f, size.height * 0.1f)
            )
            //右下角的极光青辉光
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x267FE3FF), Color(0x007FE3FF)),
                    center = Offset(size.width * 0.9f, size.height * 0.9f),
                    radius = size.minDimension * 0.7f
                ),
                radius = size.minDimension * 0.7f,
                center = Offset(size.width * 0.9f, size.height * 0.9f)
            )
        }
        .drawWithContent {
            drawContent()
            drawRect(brush = brush, alpha = 0.06f)
        }
}

/**
 * VIP 标识徽章
 */
@Composable
fun PremiumBadge(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(6.dp)
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(premiumGoldGradient())
            .padding(PaddingValues(horizontal = 6.dp, vertical = 1.dp))
    ) {
        Text(
            text = stringResource(R.string.premium_badge_label),
            color = Color(0xFF231A00),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}