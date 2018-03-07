package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.datamodeling.*
import org.springframework.data.annotation.Id
import java.io.Serializable
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty


/**
 * @author fu-taku
 */
@DynamoDBTable(tableName = "item")
class Item(
        @Id
        @DynamoDBTypeConverted(converter = ItemIdTypeConverter::class)
        var itemId: ItemId = ItemId(),

        @get:DynamoDBAttribute
        var id: String = "",

        @get:NotEmpty
        @get:DynamoDBAttribute
        var name: String = "",

        @get:Min(10)
        @get:Max(1000)
        @get:DynamoDBAttribute
        var price: Long = 0L
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1L
    }

    @DynamoDBHashKey(attributeName = "code")
    fun getCode(): String = this.itemId.code

    fun setCode(code: String): Item {
        itemId.code = code
        return this
    }

    @DynamoDBRangeKey(attributeName = "createdAt")
    fun getCreatedAt(): String = this.itemId.createdAt

    fun setCreatedAt(createdAt: String): Item {
        itemId.createdAt = createdAt
        return this
    }
}
