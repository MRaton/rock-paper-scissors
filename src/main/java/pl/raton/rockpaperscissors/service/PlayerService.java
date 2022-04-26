package pl.raton.rockpaperscissors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.raton.rockpaperscissors.entity.AppPlayer;
import pl.raton.rockpaperscissors.repo.AppPlayerRepo;

import java.util.List;
import java.util.Optional;

//@Primary
@Service
public class PlayerService {

    private AppPlayerRepo appPlayerRepo;

    @Autowired
    public PlayerService(AppPlayerRepo appPlayerRepo) {
        this.appPlayerRepo = appPlayerRepo;
    }

    public List<AppPlayer> findAll() {
        return appPlayerRepo.findAll();
    }

    public AppPlayer savePlayer(final AppPlayer appPlayer) {
        return appPlayerRepo.save(appPlayer);
    }

    public void deletePlayer(final Long appPlayerId) {
         appPlayerRepo.deleteById(appPlayerId);
    }
}
