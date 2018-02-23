package com.example.panage.dynamo.repository

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

/**
 * @author fu-taku
 */
@EnableScan
interface ItemRepository : CrudRepository<Item, String> {
    fun findByCode(code: String): List<Item>
}
