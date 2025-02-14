package com.example

import io.ktor.server.application.*

fun Application.configureDatabases() {
//    val mongoDatabase = connectToMongoDB()
//    val userDataSource = UserDataSourceImpl(mongoDatabase)
//    GlobalScope.launch {
//        val user = User(
//            username = "test_username",
//            password = "test_password",
//            salt = "salt"
//        )
//        userDataSource.insertUser(user)
//    }
}


//fun Application.connectToMongoDB(): MongoDatabase {
//
//    val connectionString = "mongodb+srv://ayushs9468:ayushs9468@cluster0.xvkqh.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
//    val serverApi = ServerApi.builder()
//        .version(ServerApiVersion.V1)
//        .build()
//    val mongoClientSettings = MongoClientSettings.builder()
//        .applyConnectionString(ConnectionString(connectionString))
//        .serverApi(serverApi)
//        .build()
//
//    val mongoClient = MongoClients.create(mongoClientSettings)
//    val database = mongoClient.getDatabase("Habits")
//
//    monitor.subscribe(ApplicationStopped) {
//        mongoClient.close()
//    }
//
//    return database
//}
