package com.cqrs.ProductService.command.api.aggrecate;


import com.cqrs.ProductService.command.api.commands.CreateProductCommand;
import com.cqrs.ProductService.command.api.events.ProductCreatedEvents;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggrecate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;


    @CommandHandler
    public ProductAggrecate(CreateProductCommand createProductCommand) {
        //You can perform all the validation
        ProductCreatedEvents productCreatedEvents=new ProductCreatedEvents();

        BeanUtils.copyProperties(createProductCommand,productCreatedEvents);

        AggregateLifecycle.apply(productCreatedEvents);

    }

    public ProductAggrecate() {
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvents productCreatedEvents){
        this.quantity=productCreatedEvents.getQuantity();
        this.productId=productCreatedEvents.getProductId();
        this.price=productCreatedEvents.getPrice();
        this.name=productCreatedEvents.getName();
    }
}
