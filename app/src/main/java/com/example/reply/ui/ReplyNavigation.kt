package com.example.reply.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reply.R
import com.example.reply.ui.theme.ReplyTheme

object ReplyRoute {
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
}

@Composable
@Preview
fun ReplyNavigationRail(
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {}
) {
    NavigationRail(modifier) {
        NavigationRailItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.navigation_drawer)
                )
            }
        )
        NavigationRailItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Inbox,
                    contentDescription = stringResource(id = R.string.tab_inbox)
                )
            }
        )
        NavigationRailItem(
            selected = false,
            onClick = {/*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Article,
                    stringResource(id = R.string.tab_article)
                )
            }
        )
        NavigationRailItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(imageVector = Icons.Outlined.Chat, stringResource(id = R.string.tab_dm)) }
        )
        NavigationRailItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.People,
                    stringResource(id = R.string.tab_groups)
                )
            }
        )
    }
}


@Composable
@Preview
fun ReplyBottomNavigationBar(
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier) {
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Inbox,
                    contentDescription = stringResource(id = R.string.tab_inbox)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Article,
                    contentDescription = stringResource(id = R.string.tab_inbox)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Chat,
                    contentDescription = stringResource(id = R.string.tab_inbox)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Videocam,
                    contentDescription = stringResource(id = R.string.tab_inbox)
                )
            }
        )
    }
}


@Composable
fun NavigationDrawContent(
    modifier: Modifier = Modifier,
    selectedDestination: String,
    onDrawerClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(24.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = onDrawerClicked) {
                Icon(
                    imageVector = Icons.Default.MenuOpen,
                    contentDescription = stringResource(id = R.string.navigation_drawer)
                )
            }
        }

        NavigationDrawerItem(
            selected = selectedDestination == ReplyRoute.INBOX,
            label = {
                Text(
                    text = stringResource(id = R.string.tab_inbox),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Inbox,
                    contentDescription = stringResource(id = R.string.tab_inbox)
                )
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent
            ),
            onClick = {}
        )
        NavigationDrawerItem(
            selected = selectedDestination == ReplyRoute.ARTICLES,
            label = {
                Text(
                    text = stringResource(id = R.string.tab_article),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Article,
                    contentDescription = stringResource(id = R.string.tab_article)
                )
            },
            colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
            onClick = { /*TODO*/ }
        )
        NavigationDrawerItem(
            selected = selectedDestination == ReplyRoute.DM,
            label = {
                Text(
                    text = stringResource(id = R.string.tab_dm),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = stringResource(id = R.string.tab_dm)
                )
            },
            colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
            onClick = { /*TODO*/ }
        )
        NavigationDrawerItem(
            selected = selectedDestination == ReplyRoute.GROUPS,
            label = {
                Text(
                    text = stringResource(id = R.string.tab_groups),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Article,
                    contentDescription = stringResource(id = R.string.tab_groups)
                )
            },
            colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
fun NavigationDrawerContentPreview() {
    ReplyTheme {
        NavigationDrawContent(selectedDestination = ReplyRoute.DM)
    }
}