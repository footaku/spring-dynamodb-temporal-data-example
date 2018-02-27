package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import org.springframework.data.annotation.Id
import java.io.Serializable
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull


/**
 * @author fu-taku
 */
@DynamoDBTable(tableName = "item")
class Item(
        @NotNull
        @get:DynamoDBAttribute
        var name: String = "",

        @NotNull
        @Min(10)
        @Max(1000)
        @get:DynamoDBAttribute
        var price: Long = 0L,

        @get:DynamoDBAttribute
        var createdAt: String = ""
) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 1L
    }

    @Id
    private lateinit var itemId: ItemId

    @DynamoDBHashKey(attributeName = "id")
    fun getId(): String = this.itemId.id

    fun setId(id: String): Item {
        if (!this::itemId.isInitialized) {
            this.itemId = ItemId()
        }
        itemId.id = id

        return this
    }

    @DynamoDBRangeKey(attributeName = "code")
    fun getCode(): String = this.itemId.code

    fun setCode(code: String): Item {
        if (!this::itemId.isInitialized) {
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
        private val serialVersionUID: Long = 1
    }

    override fun toString(): String {
        return "ItemId(id='$id', code='$code')"
    }

}

