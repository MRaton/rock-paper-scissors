package pl.raton.rockpaperscissors.domain;

import java.time.LocalDate;

public class PlayerDto {

    private Long id;
    private String playerNick;
    private Integer playerPoints;
    private LocalDate date;

    public PlayerDto(Long id, String playerNick, Integer playerPoints, LocalDate date) {
        this.id = id;
        this.playerNick = playerNick;
        this.playerPoints = playerPoints;
        this.date = date;
    }

    public PlayerDto() {
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

    public void setPlayerNick(String playerNick) {
        this.playerNick = playerNick;
    }

    public Integer getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(Integer playerPoints) {
        this.playerPoints = playerPoints;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
