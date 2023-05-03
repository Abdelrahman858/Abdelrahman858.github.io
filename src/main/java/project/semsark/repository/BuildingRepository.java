package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Long> {
    Building getBuildingByLangAndLat(Double lang,Double lat);
}
