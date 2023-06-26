# Baldur's Bones - Game Rules
## *** Not For Players ***

## Game Start
The game begins with a tutorial fight which contains a brief rules explanation and some game exposition.
It is then followed by an introductory fight.

### Tutorial Fight
Regardless of the outcome of the fight, the players stats are not changed (**Player** and **Combat**).
The fight is meant to serve as an introduction and stats are reset after the fight.

### Tutorial Location
After finishing the tutorial fight the player will start in the tutorial position (currently 4,4 or bottom left).
If this location is returned to any time in the future the player will only be given the option to leave again.


## Maps
The game makes use of two maps, one that starts empty (*Player Map*), and one that starts filled (*Game Map*).
The two map system is used to have a set layout of locations while hiding information from the player.

### Player Map
This map updates as the player moves through the world. It is composed of 6 main elements.

The starting location (**0**) or the tutorial location is marked on the map from the beginning to give the player a reference.

The boss location (**X**) is also marked on the map from the beginning to show the player where to go to finish the game.

Undiscovered locations (**?**) are locations that the player has not yet been too.

Discovered / Beaten locations (**#**) are locations where there is either no fight or the fight has been beaten (see locations).

Fight locations (**!**) are locations where the player encountered a fight and either lost or skipped it.

Player location (**@**) is whatever square the player is currently located on.

### Game Map
The game map makes use of a 3 number system to indicate location information.

The first number indicates location **Area**: 

0 = Tutorial,  1 = Easy, w = Medium, 3 = Hard, 4 = End, 5 = Boss.

The second number indicates if the location **Type**: 

1 = Fight, 2 = Explore .

The third number indicates if the location is statues: 

1 = Unbeaten / Un-Found, 2 = Found, 3 = Beaten.



## Locations & Enemies
The game makes use of location types to decide what should happen when the player moves to that location. 
These location types determine what methods are called and what types of enemies to create.

### Location Area
There are 6 location types: *Easy*, *Medium*, *Hard*, *Tutorial*, *End* and *Boss*.
The game map has 1 *Tutorial* location, 1 *Boss* location, 3 *End* locations,
10 *Easy* locations, 15 *Medium* locations, and 19 *Hard* locations.

### Location Type 
*Easy*, *Medium*, and *Hard* locations can be either *explore* of *fight* locations. 
**Explore** locations have a location description and expand on the world,
while fight locations contain an enemy (**Enemy**) and start a combat.
The type of (**Enemy**) found in that location depends on the location type.

The *End* locations are a set of explore location surrounding the boss fight to give the player a chance to turn back.
The *Tutorial* and *Boss* locations have special properties (**Tutorial** & **Boss Fight**). 

### Enemies & Locations
When a player engages in combat the enemy roll is chosen between a static high and low roll (+/- 1).
The values of the hight and low rolls are determined based on the enemy difficulty: 
*Tutorial*, *Easy*, *Medium*, *Hard*, or *Boss*.


## Player Stats & Abilities
The player has 3 major groups of attributes that define its *actions*, *stats*, *abilities*, and *location*.

### Player Stats
The player has 3 primary stats, *health*, *experience*, and *level*. *Health* is called credibility in game
and represents how many fights the player can lose (**Losing**). *Experience* is called renown in game
and represents how many fights the player has won since leveling up (*Leveling*). *Level* is called reputation in game
and represents how close the player is to being able to fight the games boss (**Boss Fight**). 
The player starts with **3** health at level **1** with **0** experience.

### Leveling
As the player wins fights they gain experience and when they hit a certain number they level up. 
This resets the experience counter and increases they players abilities and health stat.
At level **1** they level up after **3** experience and at level **2** they level up after **5** experience.
Leveling up grants **1** additional health and increases add, subtract, and re-roll abilities by **1** (*Abilities*).

### Player Abilities
The player has access to three abilities to help them in games of Baldur's Bones (**Combat**):
*add*, *subtract*, and *re-roll*. 
The player starts with one use of *add*, one use of *subtract*, and no uses of *re-roll*.
The *Add* ability takes the players current **total** and adds **1**.
The *Subtract* ability takes the players current **total** and subtracts **1**.
The *Re-Roll* ability takes the players current **total** and subtracts their last **roll**. 
It then generates and adds a new **roll**.

### Player Location & Movement
The player *location* is recorded as a 2D array which is tracked by the **player**
and the last position is tracked by the **map**.
This is used to find the matching location on both the *player map* and the *game map*.
The player starts at 6,6 and finishes at 0,0. 
The player may *move* one spot in any cardinal direction (North, South, East, West) 
but cannot move above 6 or below 0 in either axis.


## Combat
Baldur's Bones primary gameplay feature is a game of dice similar to blackjack.
The player must roll 3 dice then take turns rolling more until they decide to stop.
At the end of the game the player with the highest total below 21 will win the game.

## Combat Start
The game starts with 3 dice being rolled and the player being given the total and the value of the last roll.
This is the players starting roll, though notably the last roll can be changed with the re-roll ability 
(**Player Abilities**).

### Using Player Abilities
Once the player has their roll they can choose to roll, use an **ability**, or hold. 
The abilities are *Add*, *Subtract*, or *Re-Roll* (**Player Abilities**).
All abilities have a number of uses that is shown to the player at start of combat.
Using an ability will remove one use and abilities with no uses cannot be chosen.

### Game Actions
In addition to abilities, the player may also add a *new roll* or *hold* their total.
If the player adds a *new roll* then a new roll value is added and the last roll value is updated.
When the player chooses to hold their rolls then the total is saved and the combat outcome is calculated.
If a player reaches a roll total of 31 or higher, then the combat automatically ends in a loss.
At a total of 31 no use of abilities can lower it to a winning value.

### Combat Calculation
Once the player has ended their rolling and has a final value 
the enemy will determine if they use their high roll value or their low roll value.
The player only wins if their total is **Greater** than the enemy. 
On a tie or lesser roll the player will lose the combat. 
The player will also always lose if their end value is "bust", or above 21.

### Outcome
If the player wins the combat, they gain **1** experience and update their stats (**Player Stats**). 
If the player instead lost the combat, then they will lose one health and check their health total (**Player Stats**).
This may result in either a level up (**Leveling**) or a game loss (**Losing**).


## Boss Fight
The boss fight is similar to regular combat but made to be more punishing. 
The player is already max level when they first start it, so they will only get weaker if they lose.

### Rounds
Instead of a normal combat the boss fight uses the best 2 out of 3 system to determine the winner.
If the boss is down a round he will roll higher and if he is up a round he will roll lower.
Other than this the boss fight functions the same as a regular fight 
with the player simply needing to win 2 be fore they lose 2.

### Outcomes
The outcomes of the boss fight also differ from a regular combat. 
On a loss the player will lose 2 health instead of 1 which limits the players attempts.
If the player does win then the player will be taken to the winning screen 
and the game will let them either restart or quit the game.


## Game Endings
The game will always end in either a win or a loss at which point the player may either restart or quit the game.

### Winning
The game ends in a win when a player successfully beats the boss fight with Volo at the end of the game.
In order to fight Volo, the player must be level 3. 
Therefore, the boss fight also acts as a way to check the player has won at least 8 previous battles as well.

### Losing
The game ends in a loss when the player loses a battle 
that causes their Health (**Player Stats**) to be reduced to 0 or below.