package com.travel.croatia

class RegisterModel {
    var id: String? = null
    var name: String? = null
    var email: String? = null
    var pass: String? = null

    constructor() {}
    constructor(id: String?, name: String?, email: String?, pass: String?) {
        this.id = id
        this.name = name
        this.email = email
        this.pass = pass
    }
}
