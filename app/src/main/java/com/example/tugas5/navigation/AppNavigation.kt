package com.example.tugas5.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.tugas5.screens.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Check if we should show bottom bar and drawer toggle
    val isMainScreen = currentRoute in listOf(Screen.Notes.route, Screen.Favorites.route, Screen.Profile.route, Screen.Home.route, Screen.Settings.route)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                onItemClick = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        },
        gesturesEnabled = isMainScreen
    ) {
        Scaffold(
            topBar = {
                if (isMainScreen) {
                    TopAppBar(
                        title = { Text("Notes App") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (isMainScreen) {
                    BottomNavBar(navController = navController)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Notes.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Bottom Nav Screens
                composable(Screen.Notes.route) {
                    NoteListScreen(
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                        onAddNoteClick = { navController.navigate(Screen.AddNote.route) }
                    )
                }
                composable(Screen.Favorites.route) { FavoritesScreen() }
                composable(Screen.Profile.route) { ProfileScreen() }

                // Drawer Screens (Bonus)
                composable(Screen.Home.route) { HomeScreen() }
                composable(Screen.Settings.route) { SettingsScreen() }

                // Detail/Form Screens
                composable(
                    route = Screen.NoteDetail.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    NoteDetailScreen(
                        noteId = noteId,
                        onBackClick = { navController.popBackStack() },
                        onEditClick = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
                    )
                }
                composable(Screen.AddNote.route) {
                    AddNoteScreen(onBackClick = { navController.popBackStack() })
                }
                composable(
                    route = Screen.EditNote.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    EditNoteScreen(
                        noteId = noteId,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
