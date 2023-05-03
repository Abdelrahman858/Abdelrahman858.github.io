package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.Emails;

import java.util.Optional;

@Repository
public interface EmailsRepository extends JpaRepository<Emails,Long> {
    Optional<Emails> findByEmail(String email);
}
