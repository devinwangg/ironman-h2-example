package com.ironman.demo

import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.util.*


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@RestController
class HomeController(@Autowired environment: Environment) {
    private final val url = environment.getProperty("spring.datasource.url");
    private final val username = environment.getProperty("spring.datasource.username");
    private final val password = environment.getProperty("spring.datasource.password");
    val connection: Connection = DriverManager.getConnection(url, username, password)

    @GetMapping("/students")
    fun getStudentData(): ArrayList<MutableMap<String, Any>> {
        // 建立 Statement 進行資料庫操作
        val statement: Statement = connection.createStatement()

        // 取得 Student 資料表所有資料
        val record: ResultSet = statement.executeQuery("SELECT * FROM Student")

        // 將 Student 資料取出並儲存在一個集合進行輸出
        val result: ArrayList<MutableMap<String, Any>> = ArrayList()
        while (record.next()) {
            val item = mutableMapOf<String, Any>()
            item["id"] = record.getInt("id")
            item["name"] = record.getString("name")
            item["email"] = record.getString("email")
            result.add(item)
        }

        return result
    }
}