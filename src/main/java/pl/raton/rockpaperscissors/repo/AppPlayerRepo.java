package pl.raton.rockpaperscissors.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.raton.rockpaperscissors.entity.AppPlayer;
import java.util.List;

@Repository
public interface AppPlayerRepo extends CrudRepository<AppPlayer, Long> {

    @Override
    List<AppPlayer> findAll();

    @Override
    void deleteById(Long id);
}
