package com.ssafy.withssafy.src.dto

data class User(
    val id: Int,
    val name: String,
    val userId: String,
    val password: String,
    val studentId: String?,
    val classRoomId: Int,
    val state: Int?,
    val deviceToken: String?
) {
    constructor() : this(id = 0, name = "", userId = "", password = "", studentId = "", classRoomId = 0, state = 0, deviceToken = "")
    constructor(name: String) : this(id = 0, name = name, userId = "", password = "", studentId = "", classRoomId = 0, state = 0, deviceToken = "")
    constructor(id: Int, studentId: String?) : this(id = id, name = "", userId = "", password = "", studentId = studentId, classRoomId = 0, state = 0, deviceToken = "")
    constructor(id: Int, studentId: String?, classRoomId: Int) : this(id = id, name = "", userId = "", password = "", studentId = studentId, classRoomId = classRoomId, state = 0, deviceToken = "")
    constructor(userId: String, password: String) : this(id = 0, name = "", userId = userId, password = password, studentId = "", classRoomId = 0, state = 0, deviceToken = "")
    constructor(id: Int, password: String, studentId: String?) : this(id = id, name = "", userId = "", password = password, studentId = studentId, classRoomId = 0, state = 0, deviceToken = "")
    constructor(id: Int, password: String, deviceToken: String, studentId: String?) : this(id = id, name = "", userId = "", password = password, studentId = studentId, classRoomId = 0, state = 0, deviceToken = deviceToken)
    constructor(name: String, userId: String, password: String, studentId: String, classRoomId: Int) : this(id = 0, name = name, userId = userId, password = password, studentId = studentId, classRoomId = classRoomId, state = 0, deviceToken = "")
    constructor(name: String, userId: String, password: String, classRoomId: Int) : this(id = 0, name = name, userId = userId, password = password, studentId = null, classRoomId = classRoomId, state = null, deviceToken = "")
}