# Chess
My personal chess engine project

To use: clone the project, compile and run MyChess.java

You may need to specify the project jdk/sdk and/or you may need to specify src as the root directory.

If you are using intelij, you can do this by attempting to build the project, if it prompts you that the SDK is not configured, then go ahead and click configure and choose whichever SDK you have on your system.

If you are still running into errors, go ahead and right click on the src directory and make sure that it is marked as the source directory by going to "mark directory as" -> "sources root"

One error I ran into after this was that intelij did not recognize my imports, but for whatever reason, after closing and reopening intelij, the imports were fine.


Project Overview:

There are three main packages for keeping track of the game state, and one additional package for the GUI
The three packages that keep track of game state are board, Pieces, and player.

Pieces package:

this contains all the different chess pieces, as well as the abstract Piece class. Pieces are immutable, so a new one needs to be constructed each time a move is made. The main job of the pieces are to calculate their legal moves, and to keep track of the state of the piece i.e. it's location, and whether it has moved yet.

Pieces are only constructed by the board class at the creation of the board, or by the previous piece when a move is executed, pieces have a special method called "public Piece movePiece(Move move)" that takes care of this movement.
