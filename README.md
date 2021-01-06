# Build Your Own World Design Document

## Final project for CS 61B (data structures). Publication of this semester's project has been allowed.

**Partner 1:**
Dylan Chow
**Partner 2:**
Aaron Fu
## Classes and Data Structures

## Algorithms
0) Initialize everything as NOTHING.
1) Take in a seed to use as a PRNG.
2) While we are ~ less ~ than a load factor, generate a randomly sized rectangular room with walls.
3) Place the room randomly, check if its within the bounds of the world and no walls intersect.
3b) When placing the room, add to the load factor. Load factor can be rng as well.
4) Add the room to a list of rooms.
5) Hallways:
    aa) Mark one room as the starting room
    a) While there is a room not connected (using DJ sets)
    b) Draw a hallway from a connected room to an unconnected room.
    c) Hallway logic: Overwrite everything to draw the hallway, then if there nothing surrounding, mark with wall.
(If the wall is on a path, don't write the wall. If the wall is on nothing, create the wall).
6) Place the golden lock on a wall. Find a walkable path, locate the nearest accessible wall.
