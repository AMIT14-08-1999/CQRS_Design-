package com.cqrs.ProductService.command.api.events;


import com.cqrs.ProductService.command.api.data.Product;
import com.cqrs.ProductService.command.api.data.ProductRepo;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
public class ProductsEventHandler {

    private ProductRepo productRepo;

    public ProductsEventHandler(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @EventHandler
    public void on(ProductCreatedEvents event) throws Exception{
        Product product=new Product();
        BeanUtils.copyProperties(event,product);
        productRepo.save(product);
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
