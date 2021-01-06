package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 70;
    public static final int HEIGHT = 50;
    private static final int HUD_HEIGHT = 5;
    private TERenderer ter;
    private HeadsUpDisplay hud;
    private TETile[][] world;
    private RoomAndHallwayGen rah;
    private Avatar you;
    private String seedString;
    private StringBuilder savedState = new StringBuilder();
    private StringBuilder yourName;
    private boolean seedInitialized = false;
    private boolean seedProcessed = false;
    private boolean startedQuit = false;
    private boolean hasQuit = false;
    private boolean isFirst = true;
    private boolean interactWithKeyBoardGameLoopBoolean = true;
    private boolean enterName = false;
    private long seed;
    private java.util.List<Position> lightSources;
    private java.util.List<Position> stationaryLights;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        if (interactWithKeyBoardGameLoopBoolean) {
            interactWithKeyBoardGameLoopBoolean = false;
            initRendererAndHUD();
            gameLoop();
        }
        if (StdDraw.hasNextKeyTyped()) {
            char in = StdDraw.nextKeyTyped();
            processChar(in);
        }
    }

    private void readAvatarMovement() {
        if (StdDraw.hasNextKeyTyped()) {
            char in = StdDraw.nextKeyTyped();
            moveAvatar(in);
        }
    }

    private void moveAvatar(char in) {
        switch (in) {
            case 'w':
                you.moveUp();
                break;
            case 's':
                you.moveDown();
                break;
            case 'a':
                you.moveLeft();
                break;
            case 'd':
                you.moveRight();
                break;
            default:
                break;
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        //initRendererAndHUD();
        interactWithKeyBoardGameLoopBoolean = false;
        process(input);
        //gameLoop();
        return world;
    }

    private void initRendererAndHUD() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + HUD_HEIGHT, 0, 0);
        hud = new HeadsUpDisplay(0, WIDTH, HEIGHT, HEIGHT + HUD_HEIGHT);
    }

    private TETile[][] newWorld(long seedIn, int width, int height) {
        rah = new RoomAndHallwayGen(seedIn, width, height);
        stationaryLights = rah.getStationaryLights();
        return rah.getWorld();
    }

    /**
     * @source DemoInputSource.java made by Hug in InputDemo package.
     * @source https://bit.ly/3f36t9J (Style).
     * @param input
     */
    private void process(String input) {
        InputSource inputSource = new StringInputDevice(input);
        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            /*if (hasQuit) {
                if (c == 'L' || c == 'l') {
                    hasQuit = false;
                } else {
                    throw new IllegalArgumentException("need to Load a string after quitting.");
                }
            }
            if (c == 'N' || c == 'n') {
                seedInitialized = true;
                seedString = "";
            } else if (c == 'S' || c == 's') {
                processSeed();
            } else if (c == ':') {
                c = inputSource.getNextKey();
                if (c == 'q' || c == 'Q') {
                    hasQuit = true;
                    return;
                } else {
                    throw new IllegalArgumentException("a 'Q' must follow a ':'");
                }
            } else if (Character.isDigit(c) && seedInitialized) {
                seedString += c;
            } else {
                throw new IllegalArgumentException(input + " is not a valid input.");
            }*/
            processChar(c);
        }
    }

    private void processChar(char c) {
        if (startedQuit) {
            if (c == 'q' || c == 'Q') {
                quit();
            } else {
                startedQuit = false;
            }
        }
        if (enterName || c != 'q' && c != 'Q' && c != 'L' && c != 'l' && c != ':') {
            savedState.append(c);
        }
        if (Character.isLetter(c)) {
            c = Character.toLowerCase(c);
            processCharLetter(c);
        } else if (Character.isDigit(c)) {
            processCharDigit(c);
        } else {
            processCharSpecial(c);
        }
    }

    /**
     * @source https://www.w3schools.com/java/java_files_create.asp
     */
    private void quit() {
        hasQuit = true;
        try {
            File temp = new File("savedState.txt");
            temp.createNewFile();
            FileWriter temp2 = new FileWriter("savedState.txt");
            temp2.write(savedState.toString());
            temp2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createWorldAndAvatar() {
        world = newWorld(getSeed(), WIDTH, HEIGHT);
        you = new Avatar(world, rah.getRandomRoom().getDeterministicFloor());
    }

    private void processCharLetter(char c) {
<<<<<<< HEAD
        /*if (enterName) {
            if (yourName.isEmpty()) {
=======
        if (enterName) {
            if (yourName.length() == 0) {
>>>>>>> 1305e1d1a24aa5647d1c42e999c1eac00e54f42e
                c = Character.toUpperCase(c);
            }
            yourName.append(c);
            return;
        }*/
        switch (c) {
            case 'l':
                if (isFirst) {
                    load();
                }
                break;
            case 'n':
                if (isFirst) {
                    seedInitialized = true;
                    seedString = "";
                    isFirst = false;
                }
                break;
            case 'q':
                if (isFirst) {
                    isFirst = false;
                    hasQuit = true;
                }
                break;
            case 's':
                if (seedInitialized && !seedProcessed) {
                    processSeed();
                    createWorldAndAvatar();
                } else if (seedProcessed) {
                    moveAvatar(c);
                }
                break;
            case 'w':
            case 'a':
            case 'd':
                if (seedProcessed) {
                    moveAvatar(c);
                }
                break;
            case 'o':
                if (isFirst) {
                    yourName = new StringBuilder();
                    isFirst = false;
                    enterName = true;
                }
                break;
            case 't':
                ter.setLineOfSight();
                break;
            default:
                break;
        }
    }

    /**
     * @source https://www.w3schools.com/java/java_files_read.asp
     */
    private void load() {
        try {
            File temp = new File("savedState.txt");
            Scanner temp2 = new Scanner(temp);
            String in = temp2.nextLine();
            process(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processCharDigit(char c) {
        if (seedInitialized && !seedProcessed) {
            seedString += c;
        }
    }

    private void processCharSpecial(char c) {
        switch (c) {
            case ':':
                startedQuit = true;
                break;
            case '.':
                if (enterName) {
                    isFirst = true;
                    enterName = false;
                    hud.setName(yourName.toString());
                }
                break;
            default:
                break;
        }
    }

    private void processSeed() {
        seedProcessed = true;
        seed = Long.parseLong(seedString);
    }

    private Long getSeed() {
        if (seedProcessed) {
            return seed;
        }
        throw new NullPointerException();
    }

    private void gameLoop() {
        while (!seedInitialized) {
            renderMainMenu();
            while (isFirst) {
                this.interactWithKeyboard();
            }
            while (enterName) {
                renderOptionsMenu();
                this.interactWithKeyboard();
            }
        }
        while (seedInitialized && !seedProcessed) {
            renderSeedEnterScreen();
            this.interactWithKeyboard();
        }
        int x;
        int y;
        while (!hasQuit) {
            hud.drawName();
            x = (int) StdDraw.mouseX();
            y = (int) StdDraw.mouseY();
            if (y < HEIGHT - 1) {
                hud.update(world[x][y].description());
                hud.drawTileName();
            }
            this.interactWithKeyboard();
            remakeLightSource();
            ter.renderFrame(world);
        }
        renderEndScreen();
    }

    private void remakeLightSource() {
        lightSources = new ArrayList<>();
        lightSources.add(you.getCurrentPosition());
        //lightSources.addAll(stationaryLights);
        ter.setLightSource(lightSources);
    }

    private void renderMainMenu() {
        Font old = StdDraw.getFont();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font temp = new Font("AvantGarde", Font.BOLD, 30);
        StdDraw.setFont(temp);

        StdDraw.text(WIDTH / 2, HEIGHT - 10, "Build Your Own World");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 5, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 5, "Options (O)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 10, "Quit (Q)");
        StdDraw.show();
        StdDraw.setFont(old);
    }

    private void renderOptionsMenu() {
        Font old = StdDraw.getFont();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font temp = new Font("AvantGarde", Font.BOLD, 30);
        StdDraw.setFont(temp);

        StdDraw.text(WIDTH / 2, HEIGHT - 10, "Please enter letters for a name:");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 5, yourName.toString());
        StdDraw.text(WIDTH / 2, HUD_HEIGHT + 10, "Type Period(.) to stop entering.");

        StdDraw.show();
        StdDraw.setFont(old);
    }

    private void renderSeedEnterScreen() {
        Font old = StdDraw.getFont();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font temp = new Font("AvantGarde", Font.BOLD, 30);
        StdDraw.setFont(temp);

        StdDraw.text(WIDTH / 2, HEIGHT - 10, "Please enter numbers for Seed:");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 5, seedString);
        StdDraw.text(WIDTH / 2, HUD_HEIGHT + 10, "Type S to stop entering.");

        StdDraw.show();
        StdDraw.setFont(old);
    }

    private void renderEndScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font temp = new Font("AvantGarde", Font.BOLD, 30);
        StdDraw.setFont(temp);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 5, "World has been saved successfully. Please close.");
        StdDraw.show();
    }

    public static void main(String[] args) {
        /*Engine engine = new Engine();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + HUD_HEIGHT, 0, 0);
        TETile[][] world = engine.interactWithInputString(args[0]);
        //ter.renderFrame(world);

        HeadsUpDisplay hud = new HeadsUpDisplay(0, WIDTH, HEIGHT, HEIGHT + HUD_HEIGHT);

        int x;
        int y;
        while (true) {
            ter.renderFrame(world);
            x = (int) StdDraw.mouseX();
            y = (int) StdDraw.mouseY();
            if (y < HEIGHT - 1) {
                hud.update(world[x][y].description());
                hud.draw();
            }
            engine.interactWithKeyboard();
        }*/
    }
}
