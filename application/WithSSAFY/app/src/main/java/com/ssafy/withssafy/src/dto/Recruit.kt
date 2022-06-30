package com.ssafy.withssafy.src.dto

data class Recruit(
    val career: String,
    val company: String,
    val education: String,
    val employType: String,
    val endDate: String,
    val id: Int,
    val job: String,
    val location: String,
    val preferenceDescription: String,
    val salary: String,
    val startDate: String,
    val taskDescription: String,
    val userId: Int,
    val welfare: String,
    val workingHours: String,
    val photoPath : String
) {
//    constructor(career: String, company: String, education: String, employType: String, endDate: String, job: String, location: String, preferenceDescription: String,
//    salary: String, startDate: String, taskDescription: String, userId: Int, welfare: String, workingHours: String) : this(career = career, company = company,
//    education = education, employType = employType, endDate = endDate, id = 0, job = job, location = location, preferenceDescription = preferenceDescription,
//    salary = salary, startDate = startDate, taskDescription = taskDescription, userId = userId, welfare = welfare, workingHours = workingHours)

//    constructor(id: Int, career: String, company: String, education: String, employType: String, endDate: String, job: String, location: String, preferenceDescription: String,
//                salary: String, startDate: String, taskDescription: String, userId: Int, welfare: String, workingHours: String) : this(career = career, company = company,
//        education = education, employType = employType, endDate = endDate, id = id, job = job, location = location, preferenceDescription = preferenceDescription,
//        salary = salary, startDate = startDate, taskDescription = taskDescription, userId = userId, welfare = welfare, workingHours = workingHours)
}