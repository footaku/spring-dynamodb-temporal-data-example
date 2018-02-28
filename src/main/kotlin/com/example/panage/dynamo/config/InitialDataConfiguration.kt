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
        val createItemTableRequest =
                DynamoDBMapper(dynamoDB).generateCreateTableRequest(Item::class.java)
                        .withProvisionedThroughput(ProvisionedThroughput(20L, 20L))

        dynamoDB.createTable(createItemTableRequest)

//        LocalSecondaryIndex().withIndexName("itemCodeIndex")
//                .withKeySchema(
//                        listOf(
//                                KeySchemaElement().withAttributeName("code").withKeyType(KeyType.HASH),
//                                KeySchemaElement().withAttributeName("createdAt").withKeyType(KeyType.RANGE)
//                        )
//                )
//                .withProjection(
//
//                )

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
                Item(UUID.randomUUID().toString(), "a-b-c", 100L).setCode("ABC").setCreatedAt(now.toString()),
                Item(UUID.randomUUID().toString(), "b-c-d", 100L).setCode("BCD").setCreatedAt(now.toString()),
                Item(UUID.randomUUID().toString(), "c-d-e", 100L).setCode("CDE").setCreatedAt(now.toString()),
                Item(UUID.randomUUID().toString(), "d-e-f", 100L).setCode("DEF").setCreatedAt(now.toString())
        ))
    }
}
