package pl.raton.rockpaperscissors.repo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.raton.rockpaperscissors.domain.PlayerDto;
import pl.raton.rockpaperscissors.entity.AppPlayer;
import pl.raton.rockpaperscissors.mapper.PlayerMapper;
import pl.raton.rockpaperscissors.service.PlayerService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppPlayerRepoTestSuite {

    @Autowired
    private AppPlayerRepo appPlayerRepo;
    @Autowired
    private PlayerService playerService;

//    @Test
//    public void testAppPlayerRepoSave() {
//        //Given
//        AppPlayer appPlayer = new AppPlayer((long) 1,"testNick", 5);
//
//        //When
//        appPlayerRepo.save(appPlayer);
//
//        //Then
//        long id = appPlayer.getId();
//        Optional<AppPlayer> readAppPlayer = appPlayerRepo.findById(id);
//        Assert.assertTrue(readAppPlayer.isPresent());
//
//        //CleanUp
//        appPlayerRepo.deleteById(id);
//    }

    @Test
    public void testAppPlayerRepoSave() {
        //Given
        //AppPlayer appPlayer = new AppPlayer();
        AppPlayer appPlayer = new AppPlayer((long) 1 ,"testAddPlayer", 888);
        //PlayerDto playerDto = new PlayerDto((long) 1,"testNick", 5, LocalDate.now());

        long countsPlayers = appPlayerRepo.count();
        System.out.println("Liczba ID przed zapisem appPlayerRepo.count(): " + appPlayerRepo.count());

        //PlayerMapper playerMapper = new PlayerMapper();
        PlayerService playerService = new PlayerService(appPlayerRepo);

        //When
        appPlayerRepo.save(appPlayer);

        //playerService.savePlayer(playerMapper.mapToAppPlayer(playerDto));

        //Then
        long countsPlayersAfterSave = appPlayerRepo.count();
        System.out.println("Liczba ID po zapisie appPlayerRepo.count(): " + appPlayerRepo.count());
        long id =  appPlayer.getId();
        System.out.println("ID po zapisie : " + id);
        playerService.deletePlayer(id);
        System.out.println("Liczba ID po zapisie appPlayerRepo.count(): " + appPlayerRepo.count());
        //Optional<AppPlayer> readAppPlayer = appPlayerRepo.findById(id);
        //System.out.println("ID z appPlayerRepo: " + appPlayerRepo.findById(id));
        //Assert.assertTrue(readAppPlayer.isPresent());
        Assert.assertTrue(countsPlayersAfterSave > countsPlayers);

        //CleanUp
        //Optional<AppPlayer> ostatni = appPlayerRepo);

        //System.out.println("ostatni index " + ostatni);
        //appPlayerRepo.deleteById(id);
    }
}