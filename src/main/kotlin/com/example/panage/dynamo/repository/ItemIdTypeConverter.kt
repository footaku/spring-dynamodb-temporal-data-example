package com.example.panage.dynamo.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter

/**
 * @author fu-taku
 */
class ItemIdTypeConverter : DynamoDBTypeConverter<String, ItemId> {
    override fun unconvert(value: String): ItemId = ItemId(
            Regex("""code='(.*)',""").find(value)!!.groupValues.last(),
            Regex("""createdAt='(.*)'\)""").find(value)!!.groupValues.last()
    )

    override fun convert(itemId: ItemId): String = itemId.toString()
}
