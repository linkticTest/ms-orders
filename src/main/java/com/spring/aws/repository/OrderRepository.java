package com.spring.aws.repository;

import com.spring.aws.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    @Query(
            nativeQuery = true,
            value="Select * from orders where id_user = :idUser"
    )
    List<Order> findByUser(@Param("idUser") String idUser);

}
