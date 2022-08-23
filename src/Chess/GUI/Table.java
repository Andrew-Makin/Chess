package Chess.GUI;

import Chess.Pieces.Piece;
import Chess.board.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Table {

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private final static String pieceIconPath = "art/simple/";

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board drawnBoard;
    private Orientation orientation;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(800, 800);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(300,300);
    private final static Dimension SQUARE_PANEL_DIMENSION = new Dimension(10,10);
    private Square sourceSquare;
    private Piece candidatePiece;
    private Square destSquare;
    private boolean legalHighlight;

    public Table() {
        this.legalHighlight = true;
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.drawnBoard = Board.createStandardBoard();
        this.boardPanel = new BoardPanel();
        this.orientation = Orientation.Normal;
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createPreferencesMenu() {
        final JMenu prefMenu = new JMenu("Preferences");
        final JMenuItem flipBoard = new JMenuItem("Flip Board");
        flipBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orientation = orientation.opposite();
                boardPanel.drawBoard(drawnBoard);
            }
        });
        final JMenuItem showMoves = new JMenuItem("Toggle Highlight");
        showMoves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                legalHighlight = !legalHighlight;
            }
        });
        prefMenu.add(showMoves);
        prefMenu.addSeparator();
        prefMenu.add(flipBoard);
        return prefMenu;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {

           public void actionPerformed(ActionEvent e) {
               System.out.println("open the pgn file");
           }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final java.util.List<SquarePanel> boardSquares;

        BoardPanel() {
            super(new GridLayout(BoardUtils.NUM_SQUARES_PER_ROW, BoardUtils.NUM_SQUARES_PER_ROW));
            this.boardSquares = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_SQUARES; i++) {
                final SquarePanel squarePanel = new SquarePanel(this, i);
                boardSquares.add(squarePanel);
                add(squarePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for(final SquarePanel squarePanel : orientation.traverse(boardSquares)) {
                squarePanel.drawSquare(board);
                add(squarePanel);
            }
            validate();
            repaint();
        }
    }

    private class SquarePanel extends JPanel {
        private final int squareID;

        SquarePanel (final BoardPanel boardPanel, final int squareID) {
            super(new GridBagLayout());
            this.squareID = squareID;
            setPreferredSize(SQUARE_PANEL_DIMENSION);
            assignSquareColor();
            assignSquarePiece(drawnBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(leftMouse(e)) {
                        if (sourceSquare  == null) {
                            sourceSquare = drawnBoard.getSquare(squareID);
                            if (sourceSquare.squareOccupied()) {
                                candidatePiece = sourceSquare.getPiece();
                            } else {
                                sourceSquare = null;
                            }
                        } else {
                            destSquare = drawnBoard.getSquare(squareID);
                            final Move move = Move.MoveFactory.createMove(drawnBoard, sourceSquare.getSquareID(), destSquare.getSquareID());
                            final MoveTransition transition = drawnBoard.getCurrentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                drawnBoard = transition.getTransitionBoard();
                            }
                            sourceSquare = null;
                            destSquare = null;
                            candidatePiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(drawnBoard);
                            }
                        });
                    } else if (rightMouse(e)) {
                        sourceSquare = null;
                        destSquare = null;
                        candidatePiece = null;
                    }
                }

                private boolean leftMouse(MouseEvent e) {
                    return true;
                }

                private boolean rightMouse(MouseEvent e) {
                    return true;
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            validate();
        }

        private void showLegals(final Board board) {
            if (legalHighlight) {
                for(Move move : pieceLegals(board)) {
                    if(move.getDestination() == this.squareID) {
                        try{
                            if(move.isCapture()) {
                                add(new JLabel(new ImageIcon(ImageIO.read(new File("art/other/capture_dot.png")))));
                            } else {
                                add(new JLabel(new ImageIcon(ImageIO.read(new File("art/other/regular_dot.png")))));
                            }
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegals(Board board) {
            if(candidatePiece!=null && candidatePiece.getColor() == board.getCurrentPlayer().getColor()) {
                return candidatePiece.getLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignSquarePiece(Board board) {
            this.removeAll();
            if(board.getSquare(this.squareID).squareOccupied()) {
                try {                       //ex file name: bishopWhite.gif
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath + board.getSquare(this.squareID).getPiece().getType() + board.getSquare(this.squareID).getPiece().getColor().toString() + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    System.out.println(this.squareID);
                    throw new RuntimeException(e);
                }
            }
        }

        public void drawSquare(Board board) {
            assignSquareColor();
            assignSquarePiece(board);
            showLegals(board);
            validate();
            repaint();
        }

        private void assignSquareColor() {
            if ((squareID + squareID / 8) % 2 == 0) {
                setBackground(lightTileColor);
            } else {
                setBackground(darkTileColor);
            }
        }
    }

    public enum Orientation {

        Normal {
            @Override
            List<SquarePanel> traverse(List<SquarePanel> boardSquares) {
                return boardSquares;
            }

            @Override
            Orientation opposite() {
                return Flipped;
            }
        },

        Flipped {
            @Override
            List<SquarePanel> traverse(List<SquarePanel> boardSquares) {
                List<SquarePanel> reversedList = new ArrayList<>();
                for(SquarePanel square : boardSquares) {
                    reversedList.add(square);
                }
                Collections.reverse(reversedList);
                return reversedList;
            }

            @Override
            Orientation opposite() {
                return Normal;
            }
        };


        abstract java.util.List<SquarePanel> traverse(final java.util.List<SquarePanel> boardSquares);
        abstract Orientation opposite();

    }
}
