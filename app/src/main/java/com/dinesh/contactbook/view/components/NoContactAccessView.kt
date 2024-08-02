package com.dinesh.contactbook.view.components

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dinesh.contactbook.R

@Composable
fun NoContactAccessView(
    modifier: Modifier = Modifier,
    onRequestAccess: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomImageView(
            image = R.drawable.smartphone,
            modifier = Modifier.size(120.dp)
        )
        Button(
            onClick = onRequestAccess
        ) {
            Text(stringResource(R.string.allow_access_to_contacts))
        }
    }
}