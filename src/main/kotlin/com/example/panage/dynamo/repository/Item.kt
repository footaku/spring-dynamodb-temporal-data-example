package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import org.springframework.data.annotation.Id
import java.io.Serializable


/**
 * @author fu-taku
 */
@DynamoDBTable(tableName = "item")
class Item(
        @get:DynamoDBAttribute
        var name: String = "",
        @get:DynamoDBAttribute
        var price: Long = 0L,
        @get:DynamoDBAttribute
        var createdAt: String = ""
) {
    @Id
    private lateinit var itemId: ItemId

    @DynamoDBHashKey(attributeName = "id")
    fun getId(): String = this.itemId.id

    fun setId(id: String): Item {
        if(!this::itemId.isInitialized) {
            this.itemId = ItemId()
        }
        itemId.id = id

        return this
    }

    @DynamoDBRangeKey(attributeName = "code")
    fun getCode(): String = this.itemId.code

    fun setCode(code: String): Item {
        if(!this::itemId.isInitialized) {
            this.itemId = ItemId()
        }
        itemId.code = code

        return this
    }
}

class ItemId(
        @get:DynamoDBHashKey
        var id: String = "",
        @get:DynamoDBRangeKey
        var code: String = ""
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 239
    }

    override fun toString(): String {
        return "ItemId(id='$id', code='$code')"
    }

}

