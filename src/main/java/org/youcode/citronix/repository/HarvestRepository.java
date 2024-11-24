package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Field;
import org.youcode.citronix.domain.Harvest;
import org.youcode.citronix.domain.enums.Saison;

import java.util.List;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    List<Harvest> findBySaison(Saison saison);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Harvest h " +
            "JOIN h.harvestDetails hd " +
            "JOIN hd.tree t " +
            "WHERE t.field = :field " +
            "AND h.saison = :season " +
            "AND YEAR(h.date) = :year")
    boolean existsByFieldAndSeasonAndHarvestDateYear(@Param("field") Field field,
                                                     @Param("season") Saison season,
                                                     @Param("year") int year);
}
