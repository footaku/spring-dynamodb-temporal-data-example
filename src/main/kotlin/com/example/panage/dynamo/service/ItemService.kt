package com.example.panage.dynamo.service

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.example.panage.dynamo.repository.Item
import com.example.panage.dynamo.repository.ItemRepository
import com.example.panage.dynamo.repository.OrderBy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author fu-taku
 */
@Service
class ItemService(
        @Autowired private val dynamoDB: AmazonDynamoDB,
        @Autowired private val itemRepository: ItemRepository
) {
    fun newestByCode(code: String) = DynamoDBMapper(dynamoDB).query(
            Item::class.java,
            DynamoDBQueryExpression<Item>()
                    .withKeyConditionExpression("code = :code")
                    .withExpressionAttributeValues(mapOf(Pair(":code", AttributeValue(code))))
                    .withScanIndexForward(OrderBy.DESC.getValue())
    ).requireNoNulls().first()

    fun historiesByCode(code: String): List<Item> = itemRepository.findByCode(code)
            .sortedBy { it.getCreatedAt() }
            .reversed()

    fun findAllDistinctItems(): List<Item> = findAll()
            .groupBy { it.getCode() }
            .map { it.value.maxBy { it.getCreatedAt() } }
            .requireNoNulls()
            .toList()

    fun findAll(): List<Item> = itemRepository.findAll().requireNoNulls().toList()

    fun saveItem(item: Item) {
        itemRepository.save(item)
    }
}
