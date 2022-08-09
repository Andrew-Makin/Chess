package Chess;

import Chess.player.Player;

public enum Team {
    White {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return whitePlayer;
        }
    },
    Black {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract Player choosePlayer(final Player whitePlayer, final Player blackPlayer);
}
