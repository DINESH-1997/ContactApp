package com.dinesh.contactbook.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dinesh.contactbook.R
import com.dinesh.contactbook.model.entity.Contact
import com.dinesh.contactbook.ui.theme.ContactBookTheme

@Composable
fun ContactView(
    contact: Contact,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (contact.imageUri != null) {
            CustomImageView(
                image = contact.imageUri,
                modifier = Modifier.size(45.dp)
                .clip(CircleShape)
            )
        } else {
            CircularTextView(contact.name?.get(0) ?: 'U')
        }
        Column {
            Text(
                contact.name ?: stringResource(R.string.unknown),
                style = typography.titleMedium,
                color = colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Text(
                contact.mobileNumber ?: "-",
                style = typography.bodyMedium,
                color = colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactViewPreview() {
    ContactBookTheme {
        ContactView(
            contact = Contact(
                id = "1",
                name = "Will Smith",
                mobileNumber = "+42 444 444 85",
                imageUri = null
            ),
            onClick = {}
        )
    }
}