package com.example.panage.dynamo.controller

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.example.panage.dynamo.repository.Item
import com.example.panage.dynamo.repository.ItemRepository
import com.example.panage.dynamo.repository.OrderBy
import com.example.panage.dynamo.util.DateTimeHolder
import com.example.panage.dynamo.util.UUIDGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


/**
 * @author fu-taku
 */
@Controller
@RequestMapping("/items")
class Items {

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var dynamoDB: AmazonDynamoDB

    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
                .groupBy { it.getCode() }
                .map { it.value.maxBy { it.getCreatedAt() } }
                .toList()

        model.addAttribute("items", items)
        return "item-list"
    }

    @GetMapping("{code}")
    fun item(@PathVariable code: String, model: Model): String {
        val item = DynamoDBMapper(dynamoDB).query(
                Item::class.java,
                DynamoDBQueryExpression<Item>()
                        .withKeyConditionExpression("code = :code")
                        .withExpressionAttributeValues(mapOf(Pair(":code", AttributeValue(code))))
                        .withScanIndexForward(OrderBy.DESC.getValue())
        ).first()

        model.addAttribute("item", item)
        return "item"
    }

    @GetMapping("{code}/histories")
    fun histories(@PathVariable code: String, model: Model): String {
        val items = itemRepository.findByCode(code).sortedBy { it.getCreatedAt() }.reversed()
        model.addAttribute("items", items)
        return "history"
    }

    @PostMapping("{code}")
    fun updateItem(
            @PathVariable code: String,
            @Validated @ModelAttribute item: Item,
            bindingResult: BindingResult,
            model: Model
    ): String {
        val newItem = Item(UUIDGenerator.random().toString(), name = item.name, price = item.price)
                .setCode(code)
                .setCreatedAt(DateTimeHolder.get().toString())
        itemRepository.save(newItem)
        return "redirect:/items"
    }

    @PostMapping("new")
    fun addItem(model: Model): String {
        return "redirect:/"
    }
}
