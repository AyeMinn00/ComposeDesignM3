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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.reply.utils.DevicePosture
import com.example.reply.utils.ReplyNavigationType
import com.example.reply.utils.ReplyNavigationType.BOTTOM_NAVIGATION
import com.example.reply.utils.ReplyNavigationType.NAVIGATION_RAIL
import com.example.reply.utils.ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
import kotlinx.coroutines.launch


@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    replyHomeUIState: ReplyHomeUIState,
) {
    val navigationType = remember(windowSize) {
        when (windowSize) {
            WindowWidthSizeClass.Compact -> BOTTOM_NAVIGATION

            WindowWidthSizeClass.Medium -> NAVIGATION_RAIL

            WindowWidthSizeClass.Expanded -> if (foldingDevicePosture is DevicePosture.BookPosture) {
                NAVIGATION_RAIL
            } else {
                PERMANENT_NAVIGATION_DRAWER
            }

            else -> BOTTOM_NAVIGATION
        }
    }
    ReplyNavigationWrapperUI(
        replyHomeUIState = replyHomeUIState, navigationType = navigationType
    )
}

@Composable
fun ReplyNavigationWrapperUI(
    replyHomeUIState: ReplyHomeUIState, navigationType: ReplyNavigationType
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedDestination = ReplyRoute.INBOX

    if (navigationType == PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet {
                NavigationDrawContent(
                    selectedDestination = selectedDestination
                )
            }
        }) {
            ReplyAppContent(
                replyHomeUIState = replyHomeUIState, navigationType = navigationType
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawContent(
                        selectedDestination = selectedDestination,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        })
                }
            }, drawerState = drawerState
        ) {
            ReplyAppContent(
                replyHomeUIState = replyHomeUIState,
                navigationType = navigationType,
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                })
        }
    }
}

@Composable
fun ReplyAppContent(
    replyHomeUIState: ReplyHomeUIState,
    navigationType: ReplyNavigationType,
    onDrawerClicked: () -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NAVIGATION_RAIL) {
            ReplyNavigationRail(
                onDrawerClicked = onDrawerClicked
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Text(text = "Content" , modifier = Modifier.weight(1f))
            AnimatedVisibility(visible = navigationType == BOTTOM_NAVIGATION) {
                ReplyBottomNavigationBar(

                )
            }
        }
    }
}
