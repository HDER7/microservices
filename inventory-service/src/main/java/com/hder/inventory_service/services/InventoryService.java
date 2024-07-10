package com.hder.inventory_service.services;

import com.hder.inventory_service.model.dtos.BaseResponse;
import com.hder.inventory_service.model.dtos.OrderItemsRequest;
import com.hder.inventory_service.model.entities.Inventory;
import com.hder.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemsRequest> orderItems) {
        var errorList = new ArrayList<>();
        List<String> skus = orderItems.stream().map(OrderItemsRequest::sku).toList();

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            var inventory = inventoryList.stream().filter(value -> value.getSku().equals(orderItem.sku())).findFirst();
            if (inventory.isEmpty()) {
                errorList.add("Product " + orderItem.sku() + " does not exist");
            } else if (inventory.get().getQuantity() < orderItem.quantity()){
                errorList.add("Product " + orderItem.sku() + " has insufficient stock");
            }
        });

        return !errorList.isEmpty() ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
}
