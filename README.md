# Board-game
A personal board game project which is run from the console

Run program with 2 arguments divided a space
First argument must contain the number of players.
Second argument builds the board and must contain 1 S at the beginning for start and a combination of 
H(hen house), M(mill), C(cowhouse)
Eg. 2 S;M;H;C

Each player has an inventory where he keeps his money and his ingredients. At the start
of the game they each have 20 coins and 0 of each ingredient. This is displayed in game as
20;0;0;0
The first slot shows the money, the second shows the flour, the third the eggs and the last the milk
Each square contains an ingredient which can be harvested by players once per turn
H - eggs, M - flour, C - milk


Once the program starts the game begins and player one must play out their turn. There 
are several things a player can do

Player functions
roll [1-6] - the number of spaces the player will move. A player can move only once per turn.

harvest - depending on which square the player is they will harvest an ingredient which will
in turn be added to the market. A player can only harvest once per turn and only if he 
hasn't bought anything in the market. Harvest also isn't possible if the market doesn't have
anymore space for the ingredient a player is trying to harvest. If harvesting is successful the
player gains 1 coin.

buy "ingredient" - the player can buy an ingredient from the shop for a certain price.
The player can as many times as he likes within a turn so long as he has enough money
and hasn't harvested.

prepare "dish" - the player can use one of the available recipees to prepare a dish which 
will earn him a certain amount of money at the cost of the ingredients the player has.

can-prepare? - tells the player what he can prepare with the ingredient he urrently has.

show-market - shows the player the available items in the market as well as their price.

show-player - shows information about a player, namely the amount of money he has and 
the ingredient in his possesion.

turn - ends the turn of the current player

quit - quits the game

The market

The market can contain up to 5 of each ingridients. If the market is full the player can't harvest
that turn.

Recipees


The game ends automatically once a player obtains 100 coins 
and it declares that player as the winner
