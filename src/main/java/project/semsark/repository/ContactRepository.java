package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.semsark.model.entity.ContactUs;

public interface ContactRepository extends JpaRepository<ContactUs, Long> {
}
