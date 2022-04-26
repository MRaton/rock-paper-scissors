package pl.raton.rockpaperscissors.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "players")
public class AppPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "player_nick")
    private String playerNick;

    @NotNull
    @Column(name = "player_points")
    private Integer playerPoints;

    @NotNull
    @Column
    private LocalDate date = LocalDate.now();

    public AppPlayer(Long id, String playerNick, Integer playerPoints) {
        this.id = id;
        this.playerNick = playerNick;
        this.playerPoints = playerPoints;
    }

    public AppPlayer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerNick() {
        return playerNick;
    }

    private void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public Integer getPlayerPoints() {
        return playerPoints;
    }

    private void setPlayerPoints(Integer playerPoints) {
        this.playerPoints = playerPoints;
    }

    public LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }
}
