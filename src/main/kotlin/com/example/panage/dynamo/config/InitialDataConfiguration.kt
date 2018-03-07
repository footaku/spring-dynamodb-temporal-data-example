package com.example.panage.dynamo.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.example.panage.dynamo.repository.Item
import com.example.panage.dynamo.repository.ItemId
import com.example.panage.dynamo.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

/**
 * @author fu-taku
 */
@Configuration
class InitialDataConfiguration {
    @Autowired
    lateinit var dynamoDB: AmazonDynamoDB
    @Autowired
    lateinit var itemRepository: ItemRepository

    @PostConstruct
    fun initItemTable() {
        val createItemTableRequest =
                DynamoDBMapper(dynamoDB).generateCreateTableRequest(Item::class.java)
                        .withProvisionedThroughput(ProvisionedThroughput(20L, 20L))
        dynamoDB.createTable(createItemTableRequest)

        val now = LocalDateTime.now()
        itemRepository.saveAll(listOf(
                Item(ItemId(("ABC"), now.toString()), UUID.randomUUID().toString(), "a-b-c", 101L),
                Item(ItemId(("BCD"), now.toString()), UUID.randomUUID().toString(), "b-c-d", 102L),
                Item(ItemId(("CDE"), now.toString()), UUID.randomUUID().toString(), "c-d-e", 103L),
                Item(ItemId(("DEF"), now.toString()), UUID.randomUUID().toString(), "d-e-f", 104L)
        ))
    }
}
