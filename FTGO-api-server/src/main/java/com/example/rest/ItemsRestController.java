package com.example.rest;

import com.example.entity.Item;
import com.example.entity.Review;
import com.example.repository.ItemRepository;
import com.example.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/items")
public class ItemsRestController {

    private ItemRepository itemRepository;
    private ReviewRepository reviewRepository;

    public ItemsRestController(ItemRepository itemRepository, ReviewRepository reviewRepository) {
        this.itemRepository = itemRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public ResponseEntity<?> getItems() {
        Iterable<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{itemId}/reviews")
    public ResponseEntity<?> saveNewReview(
            @PathVariable int itemId,
            @RequestBody Review review
    ) {
        Optional<Item> optional = itemRepository.findById(itemId);
        Item item = optional.get();
        review.setItem(item);
        review = reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping("/{itemId}/reviews")
    public ResponseEntity<?> saveNewReview(
            @PathVariable int itemId
    ) {
        List<Review> reviews = reviewRepository.findByItemId(itemId);
        return ResponseEntity.ok(reviews);
    }

}
