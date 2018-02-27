package com.example.panage.dynamo.controller

import com.example.panage.dynamo.repository.Item
import com.example.panage.dynamo.repository.ItemRepository
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

    @GetMapping
    fun items(model: Model): String {
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        return "item-list"
    }

    @GetMapping("{code}")
    fun item(@PathVariable code: String, model: Model): String {
        val item = itemRepository.findByCode(code)
                .sortedBy { it -> it.createdAt }
                .reversed()
                .first()
        model.addAttribute("item", item)
        return "item"
    }

    @PostMapping("{code}")
    fun updateItem(
            @PathVariable code: String,
            @Validated item: Item,
            bindingResult: BindingResult,
            model: Model
    ): String {
        val newItem = Item(name = item.name, price = item.price)
                .setId(UUIDGenerator.random().toString())
                .setCode(code)
        newItem.price = item.price
        newItem.createdAt = DateTimeHolder.get().toString()
        itemRepository.save(newItem)
        return "redirect:/items"
    }

    @PostMapping("new")
    fun addItem(model: Model): String {
        return "redirect:/"
    }
}
