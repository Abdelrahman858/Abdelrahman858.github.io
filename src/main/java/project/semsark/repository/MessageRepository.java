package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
