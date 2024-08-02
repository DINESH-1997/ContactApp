package com.dinesh.contactbook.utils

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
    fun getTitle(): String
}

class ContactPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Please grant permission in app settings for accessing contacts.\n\n" +
                    "To enable this, click App Settings below and activate this feature under the permission menu"
        } else {
            "Contact Book needs access to show all the contacts saved in your phone"
        }
    }

    override fun getTitle(): String {
        return "Contact Book needs to access contacts"
    }
}