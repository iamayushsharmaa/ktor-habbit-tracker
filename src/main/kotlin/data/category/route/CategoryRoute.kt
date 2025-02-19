package com.example.data.habits.route

//import com.example.data.habits.repository.CategoryRepository
//import com.example.data.habits.response.CategoryResponse
//import io.ktor.http.*
//import io.ktor.server.request.*
//import io.ktor.server.response.*
//import io.ktor.server.routing.*

//fun Route.category(
//    categoryRepository: CategoryRepository
//) {
//    get("categories") {
//        val categories = categoryRepository.getAllCategory()
//        call.respond(categories)
//    }
//
//    post("categories") {
//        val category = call.receive<CategoryResponse>()
//        categoryRepository.insertAllCategory(listOf(category))
//        call.respond(HttpStatusCode.Created)
//    }
//}