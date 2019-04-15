package ru.innopolis.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.innopolis.demo.data.GoodRepository;
import ru.innopolis.demo.models.Good;
import ru.innopolis.demo.payloads.GoodPayload;
import ru.tinkoff.eclair.annotation.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class contains API for requests connected with goods
 */
@RestController
@RequestMapping("/goods")
public class GoodController {
    private GoodRepository goodRepository;

    @Autowired
    public GoodController(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Log(LogLevel.INFO)
    @GetMapping("/category/{category}")
    public List<Good> getGoodsByCategory(@PathVariable String category) {
        List<Good> result = new ArrayList<>(goodRepository.findAllByCategory(category));
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no goods in this category");
        }
        return result;
    }

    @PreAuthorize("hasAnyRole(T(ru.innopolis.demo.models.Role).ROLE_ADMIN.name())")
    @Log(LogLevel.INFO)
    @PatchMapping("/update/{name}")
    public void updateGoodPrice(@PathVariable String name, @RequestBody GoodPayload goodPayload) {
        Optional<Good> goodToCheck = goodRepository.findDistinctByName(name);
        if (!goodToCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no product with such a name");
        }
        Good good = goodToCheck.get();

        if (goodPayload.getName() != null) {
            if (goodRepository.existsByName(goodPayload.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with such name exists");
            }
            good.setName(goodPayload.getName());

        }
        if (goodPayload.getCategory() != null) {
            good.setCategory(goodPayload.getCategory());
        }
        if (goodPayload.getPrice() != 0) {
            good.setPrice(goodPayload.getPrice());
        }
        if (goodPayload.getDescription() != null) {
           good.setDescription(goodPayload.getDescription());
        }
        if (goodPayload.getQuantity() != 0) {
           good.setQuantity(goodPayload.getQuantity());
        }

        goodRepository.save(good);

    }


    @PreAuthorize("hasAnyRole(T(ru.innopolis.demo.models.Role).ROLE_ADMIN.name())")
    @Log(LogLevel.INFO)
    @DeleteMapping("/delete/category/{category}")
    public void deleteAllGoodsOfCategory(@PathVariable String category) {
        try {
            goodRepository.deleteAllByCategory(category);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no such category");
        }
    }

    @PreAuthorize("hasAnyRole(T(ru.innopolis.demo.models.Role).ROLE_ADMIN.name())")
    @Log(LogLevel.INFO)
    @DeleteMapping("/delete/name/{name}")
    public void deleteGoodByName(@PathVariable String name) {
        try {
            goodRepository.deleteByName(name);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no good with such name");
        }
    }

    @PreAuthorize("hasRole(T(ru.innopolis.demo.models.Role).ROLE_ADMIN.name())")
    @Log(LogLevel.INFO)
    @PostMapping("/add")
    public void addGoods(@RequestBody GoodPayload good) {
        if (goodRepository.existsByName(good.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Good with such name exists");
        }
        Good newGood = new Good();
        newGood.setCategory(good.getCategory());
        newGood.setDescription(good.getDescription());
        newGood.setName(good.getName());
        newGood.setPrice(good.getPrice());
        newGood.setQuantity(good.getQuantity());

        goodRepository.save(newGood);
    }
}
