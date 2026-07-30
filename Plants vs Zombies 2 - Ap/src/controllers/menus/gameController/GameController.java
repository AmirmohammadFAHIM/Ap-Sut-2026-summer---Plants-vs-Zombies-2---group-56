package controllers.menus.gameController;

import controllers.datacontroller.Data;
import controllers.datacontroller.SeedPackage;
import models.App;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.User;
import models.entity.Plant;
import models.entity.Sun;
import models.entity.Zombie;
import models.factory.builder.PlantType;
import models.gamePanes.Tile;
import models.games.BaseGame;
import models.utils.Result;
import view.PlayView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameController implements Controller{/// Main Brain of the game
    private BaseGame game;
    private Level level;
    private Chapters chapter;
public GameController(Chapters chapter , Level level){
    this.level = level;
    this.chapter = chapter;
}

    public String plant(String name , int x , int y){
        return game.plant(name,x,y);
    }
    public String pluck( int x , int y){
        return game.pluck(x,y);
    }

    @Override
    public String GameStart(String input) {
        boolean start = game.startGame(input);
        if(start){
            game.setState(BaseGame.GameState.PLAYING);
            return "Ay Yoooooo ma homie , Game on baby!!!! Vamooosss!";
        }
        return "Cannot Start Game ... You ain't ready mate.";
    }


    @Override
    public String playGame(float delta) {
            game.playGame(delta);
            Result end = game.check_endGame();
            if(end.success()){
                if(end.message().equals("Loss")) return "Brainzzzzzzzzzz!!!!! Deliciouzzzzzzz!!";
            }
            else if(game.isWon()){
                end();
                App.setScreen(new PlayView());
                return "Sometimes in the life , I'm too competitive , It's good to be competitive.";
            }
            return null;
            // TODO: return the log
    }

    private void end(){
        User user = Data.getCurrentUser();
        user.setLevelId(user.getLevelId()+1); /// you've unlocked new level!
        user.setLevelsPassed(user.getLevelsPassed()+1);
        if(user.getLevelId() == 5){
            user.setLevelId(1);
            Chapters newChapter = switch (user.getChapter()){
                case AncientEgypt -> Chapters.FrozenCaves;
                case FrozenCaves -> Chapters.BigWaveBeach;
                case BigWaveBeach -> Chapters.DarkAge;
                default -> user.getChapter();
            };
            user.setChapter(newChapter);
        }
        for (PlantType x : level.getUnlockingPlants()){
            user.getUnlockedPlants().add(x);
            user.getUnreadNews().add("Congratulation , You've Unlocked new Plant " +
                    ", " + x.name() + " !");
        }

        // TODO : unlocking new zombies



    }

    public String showSunAmount(){
        return "-->> Suns : " + game.getSunCount();
    }

    public String cheatSunAmount(int amount){
        game.setSunCount(game.getSunCount()+amount);
        return "==== >> Suns added by Cheat code : "  + amount;
    }

    public String cheatZombieKiller(){
        for (Zombie z : game.getZombies()) {
            z.setHp(0);
        }
        return "What You Said goddamn niggaZombie???";
    }


    public String showPlantsStatus(){
        StringBuilder output = new StringBuilder();
        for (SeedPackage x : game.getAvailable_plants().values()) {
            output.append(x.getPlant().name()).append("\n")
                    .append("recharge remaining time = ").
                    append(x.getRecharge()).append("\n").
                    append("cost = ").append(x.getCost()).append("\n");
        }
        return output.toString();
    }

    public String tileStatus(int x , int y){
        ArrayList<Plant> plants = new ArrayList<>();
        ArrayList<Zombie> zombies = new ArrayList<>();
        for (Plant p : game.getPlants_inField()){
            if(p.getLine() == y && p.getTileIndex() == x){
                plants.add(p);
            }
        }
        for (Zombie z : game.getZombies()){
            if(z.getLine() == y && z.getTileIndex() == x){
                zombies.add(z);
            }
        }
        Tile tile = game.getField().getTiles().get(y).get(x);
        StringBuilder output = new StringBuilder("═════════════════TILE STATUS════════════════════");
        output.append("Tile Type : ").append(tile.getTileType().name()).append("\n");
        output.append("hp : ").append(tile.getHp()).append("\n");
        output.append("Is this tile empty ? ").append(tile.isEmpty()).append("\n").append("Is it underwater ? ").append(tile.isWater()).append("\n")
                .append("Is it plantable ? " + tile.isPlantable()).append("\n");
        boolean lilyPad = tile.isWater() && tile.isPlantable();
        output.append("══════\nIs there a lily pad here? ").append(lilyPad).append("\n");
        return  output.toString();

    }


    public String cheat(String content){
        switch (content){
            case "remove-cooldown":
                removeCooldown();
                break;
            case "add-plant-food":
                addPlantFood();
                break;
        }
        return "Oh ma man , cheatt , for real you nigga??? so bad , so bad , ain't tough ):";
    }


    private void removeCooldown(){
        for (SeedPackage x : game.getAvailable_plants().values()){
            x.setRecharge(0);
            x.setAvailable(true);
        }
    }



    public String collectSun(int x , int y){
        Iterator<Sun> iterator = game.getSuns().iterator();
        while(iterator.hasNext()){
            Sun sun = iterator.next();
            if(sun.getTileIndex() == x && sun.getLine() == y){
                game.setSunCount(game.getSunCount() + sun.getPrice());
                iterator.remove();
                return "Sun collected , you got " + sun.getPrice() + " suns!";
            }
        }
        return null;
    }


    public String availablePlants(){
        StringBuilder output = new StringBuilder();
        for (PlantType x : game.getSelection().getPlantsToChoose()){
            output.append(x.name()).append("\n");
        }
        return output.toString();
    }

    public String allPlants(){
        StringBuilder output = new StringBuilder();
        for (PlantType x : App.getCurrentuser().getUnlockedPlants()){
            output.append(x.name()).append("\n");
        }
        return output.toString();
    }

    private void addPlantFood(){
        game.setPlantFoodsCount(game.getPlantFoodsCount()+1);
    }

    public String removePlant(String name){
        try {
            PlantType t = PlantType.valueOf(name);
            if(game.getState() != BaseGame.GameState.STARTING){
                return game.getSelection().removePlant(game.getAvailable_plants() ,
                        t);
            }
            return "Invalid command now.";
        } catch (RuntimeException e) {
            return "Plant not found.";
        }


    }

    public String addPlant(String name){
        try {
            SeedPackage seedPackage = game.getSelection().selectPlant(name);
            return "added plant " + seedPackage.getPlant();
        } catch (RuntimeException e) {
            return "Plant not found.";
        }
    }

    public String boost(int x , int y){
        Plant p = game.findByCoordinates(x, y);
        p.setPlantFood(true);
        return "Suiiiiiiiiiiiiiiiiii , we waz kangz at Africaaaaa";
    }

    public BaseGame getGame() {
        return game;
    }
}

