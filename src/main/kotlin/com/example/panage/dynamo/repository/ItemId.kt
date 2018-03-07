package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import java.io.Serializable

/**
 * @author fu-taku
 */
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
