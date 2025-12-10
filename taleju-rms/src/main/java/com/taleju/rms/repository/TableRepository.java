package com.taleju.rms.repository;

import com.taleju.rms.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<TableEntity,Long> {
    List<TableEntity> findByRestaurantId(Long restaurantId);
    Optional<TableEntity> findByTableNumber(String tableNumber);

}
