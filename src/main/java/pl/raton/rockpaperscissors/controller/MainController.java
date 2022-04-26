package pl.raton.rockpaperscissors.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.raton.rockpaperscissors.config.AdminConfig;
import pl.raton.rockpaperscissors.domain.Mail;
import pl.raton.rockpaperscissors.domain.PlayerDto;
import pl.raton.rockpaperscissors.mapper.PlayerMapper;
import pl.raton.rockpaperscissors.service.PlayerService;
import pl.raton.rockpaperscissors.service.SimpleEmailService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@Controller
public class MainController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public MainController(PlayerService playerService, PlayerMapper playerMapper, SimpleEmailService emailService, AdminConfig adminConfig) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
        this.emailService = emailService;
        this.adminConfig = adminConfig;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createPlayer", consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody void createUser(@RequestBody PlayerDto playerDto) {
        playerService.savePlayer(playerMapper.mapToAppPlayer(playerDto));
        emailService.send(new Mail(
                adminConfig.getAdminMail(),
                " New player: - " + playerDto.getPlayerNick() + " - ",
                playerDto.getPlayerNick() + " score: " + playerDto.getPlayerPoints()));
        log.info("dodany gracz: " + playerDto.getPlayerNick() + " maila wysłano");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletePlayer")
    public @ResponseBody void deletePlayer (@RequestParam Long id) {
        playerService.deletePlayer(id);
        log.info("Gracz usunięty!");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getPlayers")
    public @ResponseBody List<PlayerDto> getPlayers(HttpServletRequest request) {
            if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
                return playerMapper.mapToPlayerDtoFullList(playerService.findAll());
            }
        return playerMapper.mapToPlayerDtoList(playerService.findAll());
    }

    @GetMapping("/forUser")
    public String forUser() {
        return "user-page";
    }

    @GetMapping("/forAdmin")
    public  String forAdmin() {
        return "admin-page";
    }

    @GetMapping("/game")
        public  String game(Model model) {
        List<PlayerDto> players = playerMapper.mapToPlayerDtoList(playerService.findAll());
        model.addAttribute("getPlayers", players);
        return  "index";
    }

    @GetMapping("/gamePanel")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return forAdmin();
        } else if (request.isUserInRole("ROLE_USER")){
            return forUser();
        }
        return "index";
    }
}
