package com.softuni.gameshop.repository;

import com.softuni.gameshop.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o ORDER BY o.orderDateTime DESC")
    List<Order> findAllByOrderDateTimeDesc();

    @Query("SELECT o FROM Order o WHERE o.user.id = :id ORDER BY o.orderDateTime DESC")
    List<Order> findAllByUserIdOrderByOrderDateTimeDesc(Long id);

    List<Order> findByUserId(Long id);
}
