package com.cabovianco.kis.data.remote.firebase

enum class Collection(val path: String) {
    USER("users"),
    INBOX("inboxes"),
    RECEIVED("received")
}
