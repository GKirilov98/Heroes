package app.data.repositories;

import app.data.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    boolean existsItemByName(String name);
    Item findItemByName(String name);
}
