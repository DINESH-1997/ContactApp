package com.dinesh.contactbook.view

import android.Manifest
import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dinesh.contactbook.R
import com.dinesh.contactbook.utils.ContactPermissionTextProvider
import com.dinesh.contactbook.utils.checkPermissionStatus
import com.dinesh.contactbook.utils.openAppSettings
import com.dinesh.contactbook.view.components.ContactsListView
import com.dinesh.contactbook.view.components.CustomImageView
import com.dinesh.contactbook.view.components.NoContactAccessView
import com.dinesh.contactbook.view.components.dialogues.PermissionDialog
import com.dinesh.contactbook.viewmodel.ContactViewModel
import com.dinesh.contactbook.viewmodel.PermissionStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    viewModel: ContactViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val contactsPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.onPermissionStatusChanged(
                    PermissionStatus.ON_PERMISSION_GRANTED
                )
            } else if (
                !shouldShowRequestPermissionRationale(
                    context, Manifest.permission.READ_CONTACTS
                )
            ) {
                viewModel.onPermissionStatusChanged(
                    PermissionStatus.ON_PERMISSION_REVOKED
                )
            } else {
                viewModel.onPermissionStatusChanged(
                    PermissionStatus.ON_SHOW_RATIONALE
                )
            }
        }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (context.checkPermissionStatus(Manifest.permission.READ_CONTACTS)) {
                    viewModel.onPermissionStatusChanged(
                        PermissionStatus.ON_PERMISSION_GRANTED
                    )
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        when {
            context.checkPermissionStatus(Manifest.permission.READ_CONTACTS) -> {
                viewModel.onPermissionStatusChanged(
                    PermissionStatus.ON_PERMISSION_GRANTED
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.contacts),
                        style = typography.headlineLarge
                    )
                }
            )
        }
    ) { padding ->
        when {
            viewModel.contacts.isNotEmpty() -> {
                ContactsListView(
                    contacts = viewModel.contacts,
                    modifier = Modifier.padding(padding)
                )
            }

            !viewModel.hasContactPermission -> {
                NoContactAccessView(
                    modifier = Modifier.padding(padding),
                    onRequestAccess = {
                        contactsPermissionResultLauncher.launch(
                            Manifest.permission.READ_CONTACTS
                        )
                    }
                )
            }
        }

        if (viewModel.showDialogue) {
            PermissionDialog(
                permissionTextProvider = ContactPermissionTextProvider(),
                isPermanentlyDeclined = viewModel.isPermissionPermanentlyDeclined,
                onDismiss = { viewModel.onDismissDialogue() },
                onOkClick = {
                    contactsPermissionResultLauncher.launch(
                        Manifest.permission.READ_CONTACTS
                    )
                },
                onAppSettingsClick = { context.openAppSettings() }
            )
        }
    }
}