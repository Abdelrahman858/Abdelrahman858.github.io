package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.TokenStore;

@Repository
public interface TokensRepository extends JpaRepository<TokenStore, Long> {
}
