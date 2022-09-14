=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: hnxiao
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D array: I used a 2D array of Tiles to be the gameboard of my minesweeper
  game. This allowed me to easily access specific individual tiles to check their
  state and to check its adjacent tiles in some of my methods.

  2. Junit Testing: I wrote several JUnit tests to test the mechanics of my game.
  I checked to make sure the addBombs feature worked in that I would have exactly
  10 bombs initialized. I tested that uncovering all tiles would result in a win
  which would also end the game, and that clicking a bomb would also end the game.
  I tested that my recursive function flipped the right tiles. I also tested that
  initializing a board, flagging, and flipping increased the size of the LinkedList
  holding the game states, while undo decreased it in the expected ways.

  3. Collections: I used a LinkedList to keep track of the game state (the board of
  tiles) after each valid move (flipping or flagging a tile) so I could implement an
  undo feature that would restore the previous state of the game. A linked list was the
  best option because these game states must be ordered, with the most recent game
  state being appended to the end of the LinkedList. The LinkedList also made it easy
  for me to access certain game states while I was implementing my undo feature. I
  could not use an array because I do not know how many game states would be added
  in the game of minesweeper and arrays are fixed length. It also wouldn't make
  sense to use a set because it is unordered and I would not be able to define a way
  to sort separate game states. If I used a map, I wouldn't have a logical key to
  associate the game states with.

  4. Recursion: I used recursion to implement what happens when a tile with a value
  of 0 is clicked. Tiles adjacent to that tile are recursively flipped until nonzero
  tiles are flipped. This is better than iteration because I have a condition (stop
  at nonzero tiles), but I don't know exactly when that condition will occur while
  I am flipping the adjacent tiles.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
Tile: Tile holds the methods and fields of each Tile that is used in the game. Each
tile has getters and setters for if it is flipped, if it is flagged, if it is a bomb,
and its value. It also has a method for copying the state (flipped status, flagged
status, and value) of the tile to a new tile for when it is necessary to recreate
the game board as a different reference on the heap.

Minesweeper: This class holds the game board and the associated functions of a game
board with the applied minesweeper logic. It can initialize a game board, reset a game
board, add a designated number of bombs, add values, flip unflipped and unflagged tiles while
the game is still going on, flag unflipped tiles while the game is still going on, check if
the game is won or lost, check the number of bombs left (using the number of flags placed to
caluculate this), flip all tiles, retrieve the value of a given array position, retrieve the
tile of a given array position, save the game state, and reload the last game state.

Gameboard: The gameboard connects minesweeper to a Java Swing interface. Java Swing paints
a representation of the game board and keeps tracks of when and where clicks occur so it can
display the correct game board. It also serves as an intermediary between Minesweeper and
RunMinesweeper with its undo and reset methods.


RunMinesweeper: RunMinesweeper adds the reset and undo buttons to the interface and
organizes the layout.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
Because I started this project before Java Swing was covered in lecture, I struggled
with initially understanding what the role of Jpanel and Jframe was, and had trouble
looking for a component that I could use to store text like JTextArea.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
I think there is a good separation of functionality. Each function does only 1
 or 2 things. The private state is also well encapsulated because it is inaccessible
 to the user when the game is run. I think the game is pretty efficient, but some
 of my logic can be repetitive and overly complex, which would be good to refactor.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
