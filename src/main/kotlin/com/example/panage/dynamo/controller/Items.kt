package com.example.panage.dynamo.controller

import com.example.panage.dynamo.repository.Item
import com.example.panage.dynamo.repository.ItemId
import com.example.panage.dynamo.repository.ItemRepository
import com.example.panage.dynamo.util.DateTimeHolder
import com.example.panage.dynamo.util.UUIDGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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
            @ModelAttribute name: String,
            @ModelAttribute price: String,
            model: Model
    ): String {
        val item = Item(name = name).setId(UUIDGenerator.random().toString()).setCode(code)
        item.createdAt = DateTimeHolder.get().toString()
        itemRepository.save(item)
        return "redirect:/items"
    }

    @PostMapping("new")
    fun addItem(model: Model): String {
        return "redirect:/"
    }
}
