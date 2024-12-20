package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Sale;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    @Query("SELECT SUM(s.quantity) FROM Sale s WHERE s.harvest.id = :harvestId")
    Optional<Double> findTotalQuantitySoldByHarvestId(@Param("harvestId") UUID harvestId);
}
