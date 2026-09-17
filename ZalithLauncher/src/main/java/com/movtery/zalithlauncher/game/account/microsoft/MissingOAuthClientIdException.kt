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

package com.movtery.zalithlauncher.game.account.microsoft

import com.movtery.zalithlauncher.R
import com.movtery.zalithlauncher.ui.AndroidStringText
import com.movtery.zalithlauncher.ui.androidText

/**
 * 构建时未提供 OAuth 客户端 ID
 * 微软设备代码端点会因此返回 400 (AADSTS900144)，直接说明原因比展示裸 HTTP 错误更有帮助
 */
class MissingOAuthClientIdException : RuntimeException("OAUTH_CLIENT_ID is not configured")

fun MissingOAuthClientIdException.toLocal(): AndroidStringText {
    return androidText(R.string.account_logging_missing_client_id)
}
