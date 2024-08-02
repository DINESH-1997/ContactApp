package com.dinesh.contactbook.model.repository

import com.dinesh.contactbook.model.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun insertContact(contact: Contact)

    fun getContacts(): Flow<List<Contact>>

    suspend fun getContact(id: String): Contact?

    suspend fun storeContactsFromDevice()
}