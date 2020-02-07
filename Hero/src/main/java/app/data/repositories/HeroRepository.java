package app.data.repositories;

import app.data.models.Hero;
import app.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeroRepository extends JpaRepository<Hero, String> {
    Optional<Hero> findHeroByName(String name);
}
