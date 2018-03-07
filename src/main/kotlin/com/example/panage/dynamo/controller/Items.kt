package com.example.panage.dynamo.controller

import com.example.panage.dynamo.repository.Item
import com.example.panage.dynamo.repository.ItemId
import com.example.panage.dynamo.service.ItemService
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
class Items(
        @Autowired private val itemService: ItemService
) {
    @GetMapping
    fun items(model: Model): String {
        model.addAttribute("items", itemService.findAllDistinctItems())
        return "item-list"
    }

    @GetMapping("{code}")
    fun item(@PathVariable code: String, model: Model): String {
        model.addAttribute("item", itemService.newestByCode(code))
        return "item"
    }

    @GetMapping("{code}/histories")
    fun histories(@PathVariable code: String, model: Model): String {
        model.addAttribute("items", itemService.historiesByCode(code))
        return "history"
    }

    @PostMapping("{code}")
    fun updateItem(
            @PathVariable code: String,
            @Validated @ModelAttribute item: Item,
            bindingResult: BindingResult,
            model: Model
    ): String {
        if(bindingResult.hasErrors()) {
            return "item"
        }

        itemService.saveItem(
                Item(
                        ItemId(code, DateTimeHolder.get().toString()),
                        UUIDGenerator.random().toString(),
                        item.name,
                        item.price
                )
        )
        return "redirect:/items"
    }

    @PostMapping("new")
    fun addItem(model: Model): String {
        return "redirect:/"
    }
}
