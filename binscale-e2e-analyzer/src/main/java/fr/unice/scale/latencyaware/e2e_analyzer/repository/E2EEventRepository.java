package fr.unice.scale.latencyaware.e2e_analyzer.repository;

import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.E2EEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface E2EEventRepository extends JpaRepository<E2EEvent, Long> {

    @Query(value = """
            SELECT
                tracker_id as trackerId,
                jsonb_agg(
                    jsonb_build_object(
                        'id', id,
                        'nodeOrigin', node_origin,
                        'timestamp', timestamp
                    ) ORDER BY timestamp
                ) as events
            FROM e2e_event
            GROUP BY tracker_id
            """, nativeQuery = true)
    List<Object[]> findAllGroupedByTrackerIdAsJson();
}