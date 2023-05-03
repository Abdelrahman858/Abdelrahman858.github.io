package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.Profile;

@Repository
public interface MainProfileRepository extends JpaRepository<Profile, Long> {
    Profile findProfileByName(String name);
}