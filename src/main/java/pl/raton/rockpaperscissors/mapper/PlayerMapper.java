package pl.raton.rockpaperscissors.mapper;

import org.springframework.stereotype.Component;
import pl.raton.rockpaperscissors.domain.PlayerDto;
import pl.raton.rockpaperscissors.entity.AppPlayer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerMapper {
    public AppPlayer mapToAppPlayer(final PlayerDto playerDto) {
            return new AppPlayer(
                    playerDto.getId(),
                    playerDto.getPlayerNick(),
                    playerDto.getPlayerPoints());
    }

    public List<PlayerDto> mapToPlayerDtoList(final List<AppPlayer> playerList) {

        return playerList.stream()
                .map(u -> new PlayerDto( u.getId(), u.getPlayerNick(), u.getPlayerPoints(), u.getDate()))
                .sorted(Comparator.comparing(PlayerDto::getPlayerPoints).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> mapToPlayerDtoFullList(final List<AppPlayer> playerList) {

        return playerList.stream()
                .map(u -> new PlayerDto( u.getId(), u.getPlayerNick(), u.getPlayerPoints(), u.getDate()))
                .sorted(Comparator.comparing(PlayerDto::getPlayerPoints).reversed())
                .collect(Collectors.toList());
    }
}
