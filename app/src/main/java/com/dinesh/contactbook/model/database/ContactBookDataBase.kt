package com.dinesh.contactbook.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dinesh.contactbook.model.database.dao.ContactDao
import com.dinesh.contactbook.model.entity.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactBookDataBase: RoomDatabase() {
    abstract val contactDao: ContactDao

    companion object {
        const val DATABASE_NAME = "contact_book_db"
    }
}