package com.example.lab10.query.rest;

import com.example.lab10.query.FindProductsQuery;
import com.example.lab10.query.ProductRestModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<ProductRestModel> getProducts() {
        FindProductsQuery query = new FindProductsQuery();
        return queryGateway.query(
                query,
                ResponseTypes.multipleInstancesOf(ProductRestModel.class)
        ).join();
    }
}
