# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: Orginally, our hexagon implementation tried to pass an array of coordinates to fill and we would add the hexagons one by one. However, the lab implementation decided to add all the Hexagons to the rendered and include the logic in the renderer. It makes sense to put more logic in these subclasses so we don't need to figure out bounds in the main class and are able just to run adding with a few constraints rather than parsing through outputs to verify correctness.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: We suppose the hallway and rooms are like the hexagon, tesselations are where you place the rooms. Hallways connect the rooms. But the hexagons want to be tightly packed while the rooms are loosely packed.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: A generate room method, and a placer room method. Eventually after the rooms are generated and placed, we connect them with hallways.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A room is "2-D dimensional" and we place it first. Then a hallway is more 1 dimensional and used to connect rooms. Hallways are determined by the rooms. Both are not walls, can walk though them, but will use different logic to generate.
