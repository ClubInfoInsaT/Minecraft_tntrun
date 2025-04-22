package fr.clubinfo.tntrun;

public enum GameStatus {
    WAITING,
    STARTING,
    RUNNING,
    ENDING,
    ENDED;

    public static GameStatus fromString(String status) {
        try {
            return GameStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
