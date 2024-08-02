package com.dinesh.contactbook.view.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dinesh.contactbook.model.entity.Contact

@Composable
fun ContactsListView(
    modifier: Modifier = Modifier,
    contacts: List<Contact>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(contacts) { contact ->
            ContactView(
                contact = contact,
                onClick = {}
            )
        }
    }
}