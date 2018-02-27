package com.example.panage.dynamo.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.example.panage.dynamo.repository.Item
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
        dynamoDB.createTable(
                DynamoDBMapper(dynamoDB).generateCreateTableRequest(Item::class.java)
                        .withProvisionedThroughput(ProvisionedThroughput(20L, 20L))
        )

//        dynamoDB.updateTable(
//                UpdateTableRequest()
//                        .withTableName("item")
//                        .withAttributeDefinitions(
//                                listOf(
//                                        AttributeDefinition("name", ScalarAttributeType.S),
//                                        AttributeDefinition("price", ScalarAttributeType.S)
//                                )
//                        )
//        )

        val now = LocalDateTime.now()
        itemRepository.saveAll(listOf(
                Item("a-b-c", 100L, now.toString()).setId(UUID.randomUUID().toString()).setCode("ABC"),
                Item("b-c-d", 100L, now.toString()).setId(UUID.randomUUID().toString()).setCode("BCD"),
                Item("c-d-e", 100L, now.toString()).setId(UUID.randomUUID().toString()).setCode("CDE"),
                Item("d-e-f", 100L, now.toString()).setId(UUID.randomUUID().toString()).setCode("DEF")
        ))
    }
}
