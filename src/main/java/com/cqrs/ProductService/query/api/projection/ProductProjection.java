package com.cqrs.ProductService.query.api.projection;


import com.cqrs.ProductService.command.api.data.Product;
import com.cqrs.ProductService.command.api.data.ProductRepo;
import com.cqrs.ProductService.command.api.model.ProductRestModel;
import com.cqrs.ProductService.query.api.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {
    private ProductRepo productRepo;

    public ProductProjection(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @QueryHandler
    public List<ProductRestModel>handle(GetProductsQuery getProductsQuery){
        List<Product>products=productRepo.findAll();
        List<ProductRestModel>productRestModels=products.stream().map(product -> ProductRestModel.builder()
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .name(product.getName())
                .build()).collect(Collectors.toList());
        return productRestModels;
    }
}
