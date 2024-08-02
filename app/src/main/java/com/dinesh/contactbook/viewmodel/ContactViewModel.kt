package com.dinesh.contactbook.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.contactbook.model.entity.Contact
import com.dinesh.contactbook.model.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository
): ViewModel() {
    var contacts: List<Contact> by mutableStateOf(emptyList())
        private set

    var hasContactPermission: Boolean by mutableStateOf(false)
        private set

    var isPermissionPermanentlyDeclined: Boolean by mutableStateOf(false)
        private set

    var showDialogue: Boolean by mutableStateOf(false)
        private set

    init {
        getContactsFromDb()
    }

    fun onPermissionStatusChanged(permissionStatus: PermissionStatus) {
        when (permissionStatus) {
            PermissionStatus.ON_PERMISSION_GRANTED -> {
                if (contacts.isEmpty()) {
                    storeContactsFromDevice()
                }
                hasContactPermission = true
                isPermissionPermanentlyDeclined = false
                showDialogue = false
            }
            PermissionStatus.ON_PERMISSION_REVOKED -> {
                hasContactPermission = false
                isPermissionPermanentlyDeclined = true
                showDialogue = true
            }
            PermissionStatus.ON_SHOW_RATIONALE -> {
                hasContactPermission = false
                isPermissionPermanentlyDeclined = false
                showDialogue = true
            }
        }
    }

    fun onDismissDialogue() { showDialogue = false }

    private fun storeContactsFromDevice() {
        viewModelScope.launch {
            contactRepository.storeContactsFromDevice()
        }
    }

    private fun getContactsFromDb() {
        contactRepository.getContacts().onEach { result ->
            contacts = result.toMutableList()
        }.launchIn(viewModelScope)
    }
}