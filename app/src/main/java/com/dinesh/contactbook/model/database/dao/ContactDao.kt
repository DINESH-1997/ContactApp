package com.dinesh.contactbook.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dinesh.contactbook.model.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Query("Select * from contact")
    fun getContacts(): Flow<List<Contact>>

    @Query("Select count(*) from contact")
    suspend fun getContactsCount(): Int

    @Query("Select * from contact where id = :contactId")
    suspend fun getContact(contactId: String): Contact?
}