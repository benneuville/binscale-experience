package fr.unice.scale.latencyaware.e2e_analyzer.repository;

import fr.unice.scale.latencyaware.e2e_analyzer.entity.model.KafkaOffset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaOffsetRepository extends JpaRepository<KafkaOffset, String> {
}