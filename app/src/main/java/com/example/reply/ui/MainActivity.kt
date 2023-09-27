/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.reply.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.example.reply.data.LocalEmailsDataProvider
import com.example.reply.ui.theme.ReplyTheme
import com.example.reply.utils.DevicePosture
import com.example.reply.utils.isBookPosture
import com.example.reply.utils.isSeparating
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Flow of [DevicePosture] that emits everytime there is a change in the windowLayoutInfo
         */
        val devicePostureFlow = WindowInfoTracker.getOrCreate(this)
            .windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.SeparatingPosture(
                            foldingFeature.bounds,
                            foldingFeature.orientation
                        )

                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            val windowSize = calculateWindowSizeClass(activity = this)
            ReplyTheme {
                Surface(tonalElevation = 2.dp) {
                    ReplyApp(
                        windowSize = windowSize.widthSizeClass,
                        foldingDevicePosture = devicePostureFlow.collectAsState().value,
                        replyHomeUIState = uiState,
                        closeDetailScreen = {
                            viewModel.closeDetailScreen()
                        },
                        navigateToDetail = { emailId ->
                            viewModel.setSelectedEmail(emailId)
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ReplyAppPreview() {
    ReplyApp(
        replyHomeUIState = ReplyHomeUIState(
            emails = LocalEmailsDataProvider.allEmails
        ),
        windowSize = WindowWidthSizeClass.Compact,
        foldingDevicePosture = DevicePosture.NormalPosture
    )
}

@Preview(
    showBackground = true,
    widthDp = 700
)
@Composable
fun ReplyAppPreviewTablet() {
    ReplyApp(
        replyHomeUIState = ReplyHomeUIState(
            emails = LocalEmailsDataProvider.allEmails
        ),
        windowSize = WindowWidthSizeClass.Medium,
        foldingDevicePosture = DevicePosture.NormalPosture
    )
}

@Preview(
    showBackground = true,
    widthDp = 1000
)
@Composable
fun ReplyAppPreviewDesktop() {
    ReplyApp(
        replyHomeUIState = ReplyHomeUIState(
            emails = LocalEmailsDataProvider.allEmails
        ),
        windowSize = WindowWidthSizeClass.Expanded,
        foldingDevicePosture = DevicePosture.NormalPosture
    )
}