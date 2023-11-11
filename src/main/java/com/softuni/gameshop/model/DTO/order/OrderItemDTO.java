package com.softuni.gameshop.model.DTO.order;

import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.Game;

import java.math.BigDecimal;

public class OrderItemDTO extends CartItemDTO {

    private BigDecimal total;

    public OrderItemDTO() {
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderItemDTO setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }
}
