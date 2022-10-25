package com.example.lab10.command.rest;

import com.example.lab10.core.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    // State
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    // Command Handlers
    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        // Business Logic
        if (command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less than or equal 0");
        }
        if (command.getTitle() == null || command.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        // Event
        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        // Update state
        this.productId = event.getProductId();
        this.title = event.getTitle();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
    }
}
