package com.example.tugas5.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Notes App",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        HorizontalDivider()
        drawerItems.forEach { screen ->
            NavigationDrawerItem(
                label = { screen.title?.let { Text(it) } },
                icon = { screen.icon?.let { Icon(it, contentDescription = null) } },
                selected = currentRoute == screen.route,
                onClick = { onItemClick(screen.route) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
