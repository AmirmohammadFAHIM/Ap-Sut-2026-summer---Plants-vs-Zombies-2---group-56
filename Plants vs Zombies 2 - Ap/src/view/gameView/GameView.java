package view.gameView;

import controllers.menus.gameController.GameController;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.games.BaseGame;
import models.games.specialGames.PlantWhatYouGet;
import models.utils.RegexHelper;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameView extends View {
    private GameController controller;

    public GameView(Chapters chapter , Level level) {
        this.controller = new GameController( chapter, level);
        this.menu = (controllers.menus.Menu) this.controller;
    }

    @Override
    public void input() {
        System.out.println("=== Plant vs Zombies: Game Interface ===");
        super.input();

        if (handleGlobalCommands(input)) {
            return;
        }
        Matcher showAllPlantsMatcher = Pattern.compile(RegexHelper.GAME_SHOW_ALL_PLANTS).matcher(input);
        Matcher showAvailPlantsMatcher = Pattern.compile(RegexHelper.GAME_SHOW_AVAILABLE_PLANTS).matcher(input);
        Matcher addPlantMatcher = Pattern.compile(RegexHelper.GAME_ADD_PLANT).matcher(input);
        Matcher removePlantMatcher = Pattern.compile(RegexHelper.GAME_REMOVE_PLANT).matcher(input);
        Matcher boostPlantMatcher = Pattern.compile(RegexHelper.GAME_BOOST_PLANT).matcher(input);
        Matcher startGameMatcher = Pattern.compile(RegexHelper.GAME_START_GAME).matcher(input);

        Matcher advanceTimeMatcher = Pattern.compile(RegexHelper.GAME_ADVANCE_TIME).matcher(input);
        Matcher collectSunMatcher = Pattern.compile(RegexHelper.GAME_COLLECT_SUN).matcher(input);
        Matcher showSunAmountMatcher = Pattern.compile(RegexHelper.GAME_SHOW_SUN_AMOUNT).matcher(input);
        Matcher cheatAddSunMatcher = Pattern.compile(RegexHelper.GAME_CHEAT_ADD_SUN).matcher(input);
        Matcher releaseNukeMatcher = Pattern.compile(RegexHelper.GAME_RELEASE_NUKE).matcher(input);
        Matcher plantPlantMatcher = Pattern.compile(RegexHelper.GAME_PLANT_PLANT).matcher(input);
        Matcher pluckPlantMatcher = Pattern.compile(RegexHelper.GAME_PLUCK_PLANT).matcher(input);
        Matcher feedPlantMatcher = Pattern.compile(RegexHelper.GAME_FEED_PLANT).matcher(input);
        Matcher cheatRemCooldownMatcher = Pattern.compile(RegexHelper.GAME_CHEAT_REMOVE_COOLDOWN).matcher(input);
        Matcher cheatAddFoodMatcher = Pattern.compile(RegexHelper.GAME_CHEAT_ADD_PLANT_FOOD).matcher(input);
        Matcher showMapMatcher = Pattern.compile(RegexHelper.GAME_SHOW_MAP).matcher(input);
        Matcher showPlantsStatMatcher = Pattern.compile(RegexHelper.GAME_SHOW_PLANTS_STATUS).matcher(input);
        Matcher showTileStatMatcher = Pattern.compile(RegexHelper.GAME_SHOW_TILE_STATUS).matcher(input);
        Matcher zombiesInfoMatcher = Pattern.compile(RegexHelper.GAME_ZOMBIES_INFO).matcher(input);
        Matcher cheatSpawnZombieMatcher = Pattern.compile(RegexHelper.GAME_CHEAT_SPAWN_ZOMBIE).matcher(input);
        Matcher startWavesMatcher = Pattern.compile(RegexHelper.GAME_START_ZOMBIE_WAVES).matcher(input);



        if(controller.getGame().getState() == BaseGame.GameState.STARTING){
            if (showAllPlantsMatcher.matches()) {
                System.out.println(controller.allPlants());
            } else if (showAvailPlantsMatcher.matches()) {
                System.out.println(controller.availablePlants());
            } else if (addPlantMatcher.matches()) {
                String type = addPlantMatcher.group("type");
                System.out.println(controller.addPlant(type));
            } else if (removePlantMatcher.matches()) {
                String type = removePlantMatcher.group("type");
                System.out.println(controller.removePlant(type));
            } else if (boostPlantMatcher.matches()) {
                String type = boostPlantMatcher.group("type");
                //controller.boostPlant(type);
                System.out.println("Boosting plant: " + type);
            } else if (startGameMatcher.matches()) {
                System.out.println(controller.GameStart(input));
            }
            else System.out.println("Invalid input according to starting state of the game.");

            return;
        }
        else if(input.matches("$start\\s+zombie\\s+waves^")){
            try {
                PlantWhatYouGet plantWhatYouGet = (PlantWhatYouGet) controller.getGame();
                plantWhatYouGet.startWaves();
            }catch (Exception e){
                System.out.println("Bro I'm sure you trippin af , zombies are in front of you!!!");
            }
        }
        else if (advanceTimeMatcher.matches()) {
            int ticks = Integer.parseInt(advanceTimeMatcher.group("count"));
            System.out.println(controller.playGame(ticks * 0.1f)); // پاس دادن زمان به کنترلر
        } else if (collectSunMatcher.matches()) {
            int x = Integer.parseInt(collectSunMatcher.group("x"));
            int y = Integer.parseInt(collectSunMatcher.group("y"));
            System.out.println(controller.collectSun(x,y));
        } else if (showSunAmountMatcher.matches()) {
            System.out.println(controller.showSunAmount());
        } else if (cheatAddSunMatcher.matches()) {
            int count = Integer.parseInt(cheatAddSunMatcher.group("count"));
            System.out.println(controller.cheatSunAmount(count));
        } else if (releaseNukeMatcher.matches()) {
            System.out.println(controller.cheatZombieKiller());
        } else if (plantPlantMatcher.matches()) {
            String type = plantPlantMatcher.group("type");
            int x = Integer.parseInt(plantPlantMatcher.group("x"));
            int y = Integer.parseInt(plantPlantMatcher.group("y"));
            System.out.println(controller.plant(type,x,y));

        } else if (pluckPlantMatcher.matches()) {
            int x = Integer.parseInt(pluckPlantMatcher.group("x"));
            int y = Integer.parseInt(pluckPlantMatcher.group("y"));
            System.out.println(controller.pluck(x , y));
        } else if (feedPlantMatcher.matches()) {
            int x = Integer.parseInt(feedPlantMatcher.group("x"));
            int y = Integer.parseInt(feedPlantMatcher.group("y"));
            // controller.feedPlant(x, y);
            System.out.println("Feeding plant at (" + x + ", " + y + ")");
        } else if (cheatRemCooldownMatcher.matches()) {
            System.out.println(controller.cheat("remove-cooldown"));
        } else if (cheatAddFoodMatcher.matches()) {
            System.out.println(controller.cheat("add-plant-food"));
        } else if (showMapMatcher.matches()) {
            // controller.showMap();
            System.out.println("Displaying map...");
        } else if (showPlantsStatMatcher.matches()) {
            System.out.println(controller.showPlantsStatus());
        } else if (showTileStatMatcher.matches()) {
            int x = Integer.parseInt(showTileStatMatcher.group("x"));
            int y = Integer.parseInt(showTileStatMatcher.group("y"));
            System.out.println(controller.tileStatus(x, y));
        } else if (zombiesInfoMatcher.matches()) {
            // controller.showZombiesInfo();
            System.out.println("Displaying zombies info...");
        } else if (cheatSpawnZombieMatcher.matches()) {
            String type = cheatSpawnZombieMatcher.group("type");
            int x = Integer.parseInt(cheatSpawnZombieMatcher.group("x"));
            int y = Integer.parseInt(cheatSpawnZombieMatcher.group("y"));
            // controller.spawnZombie(type, x, y);
            System.out.println("Spawning " + type + " at (" + x + ", " + y + ")");
        } else if (startWavesMatcher.matches()) {
            // controller.startZombieWaves();
            System.out.println("Starting zombie waves!");
        } else {
            System.out.println("Invalid command in Game Menu!");
        }
    }
}