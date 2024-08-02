package com.dinesh.contactbook.utils

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

class ContactsObserver(
    handler: Handler
): ContentObserver(handler) {

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)

    }

}