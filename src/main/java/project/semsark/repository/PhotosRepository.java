package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.Photos;

@Repository
public interface PhotosRepository extends JpaRepository<Photos,Long> {
}
