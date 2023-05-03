package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.Favourites;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourites,Long> {
    Favourites findByUserId(long id);
}
