package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
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
        @NotEmpty
        @get:DynamoDBAttribute
        var id: String = "",

        @NotEmpty
        @get:DynamoDBAttribute
        var name: String = "",

        @NotEmpty
        @Min(10)
        @Max(1000)
        @get:DynamoDBAttribute
        var price: Long = 0L
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1L
    }

    @Id
    private lateinit var itemId: ItemId

    @DynamoDBHashKey(attributeName = "code")
    fun getCode(): String = this.itemId.code

    fun setCode(code: String): Item {
        if (!this::itemId.isInitialized) {
            this.itemId = ItemId()
        }
        itemId.code = code

        return this
    }

    @DynamoDBRangeKey(attributeName = "createdAt")
    fun getCreatedAt(): String = this.itemId.createdAt

    fun setCreatedAt(createdAt: String): Item {
        if (!this::itemId.isInitialized) {
            this.itemId = ItemId()
        }
        itemId.createdAt = createdAt

        return this
    }
}

class ItemId(
        @get:DynamoDBHashKey
        var code: String = "",
        @get:DynamoDBRangeKey
        var createdAt: String = ""
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1
    }

    override fun toString(): String {
        return "ItemId(code='$code', createdAt='$createdAt')"
    }

}

