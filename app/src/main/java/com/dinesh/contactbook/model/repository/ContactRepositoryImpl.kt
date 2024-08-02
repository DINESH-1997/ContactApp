package com.dinesh.contactbook.model.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.dinesh.contactbook.model.database.dao.ContactDao
import com.dinesh.contactbook.model.entity.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val context: Context
): ContactRepository {
    override suspend fun insertContact(contact: Contact) {
        contactDao.insertContact(contact)
    }

    override fun getContacts(): Flow<List<Contact>> {
        return contactDao.getContacts()
    }

    override suspend fun getContact(id: String): Contact? {
        return contactDao.getContact(id)
    }

    override suspend fun storeContactsFromDevice() {
        if (contactDao.getContactsCount() == 0) {
            val uniquePhoneNumbers = HashSet<String>()
            val contentResolver: ContentResolver = context.contentResolver

            val cursor: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )

            cursor?.use {
                val contactIdIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
                val contactNameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val imageUriIndex = it.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)

                while (it.moveToNext()) {
                    val phoneNumber  = getPhoneNumber(contentResolver, it.getString(contactIdIndex))
                    if (!phoneNumber.isNullOrEmpty() && uniquePhoneNumbers.add(phoneNumber)) {
                            val contact = Contact(
                                id = it.getString(contactIdIndex),
                                name = it.getString(contactNameIndex),
                                mobileNumber = phoneNumber,
                                imageUri = it.getString(imageUriIndex)
                            )
                            contactDao.insertContact(contact)
                    }
                }
            }
        }
    }

    /**
     * Get phone number using contact id
     */
    private fun getPhoneNumber(contentResolver: ContentResolver, contactId: String): String? {
        val phoneCursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )
        var phoneNumber: String? = null
        phoneCursor?.use {
            val phone = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            if (it.moveToFirst()) {
                phoneNumber = it.getString(phone)
            }
        }
        return phoneNumber
    }
}