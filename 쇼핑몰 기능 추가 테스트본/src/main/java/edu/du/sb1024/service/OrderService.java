package edu.du.sb1024.service;


import edu.du.sb1024.entity.Products;
import edu.du.sb1024.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private ProductsRepository productsRepository;

    public Products saveOrder(Products order) {
        return productsRepository.save(order);
    }

    public List<Products> getAllOrders() {
        return productsRepository.findAll();
    }

    public Optional<Products> getOrderById(Long id) {
        return productsRepository.findById(id);
    }

    public void deleteOrder(Long id) {
        productsRepository.deleteById(id);
    }

}
