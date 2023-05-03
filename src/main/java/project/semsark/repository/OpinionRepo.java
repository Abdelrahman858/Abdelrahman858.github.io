package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.semsark.model.entity.Opinion;

public interface OpinionRepo extends JpaRepository<Opinion, Long> {
    Opinion findAllByUserEmail(String email);
}
