package com.github.smartenergysystem.price.collector.priceCollector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<DayAheadPricePoint, Date> {

    @Query(value ="SELECT * FROM DAY_AHEAD_PRICE_POINT WHERE HOUR(TIME)  = :hour ORDER BY TIME DESC LIMIT 1", nativeQuery = true)
    Optional<DayAheadPricePoint> findOldPriceFromSameHour(int hour);
}
