package com.example.panage.dynamo.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * @author fu-taku
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = ["com.example.panage.dynamo.repository"])
class DynamoDBLocalConfiguration {

    @Bean
    @Profile("default")
    fun amazonDynamoDB(): AmazonDynamoDB {
        System.setProperty("sqlite4java.library.path", "target/dependencies")
        val server = DynamoDBEmbedded.create()
        return server.amazonDynamoDB()
    }
}
