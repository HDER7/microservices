package com.hder.inventory_service.model.dtos;

public record BaseResponse(String[] errorMessages) {
    public boolean hasError(){
        return errorMessages.length > 0;
    }
}
