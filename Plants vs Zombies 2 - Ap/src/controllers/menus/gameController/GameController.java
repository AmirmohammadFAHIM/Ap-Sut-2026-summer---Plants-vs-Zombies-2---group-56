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
import models.games.NormalGame;
import models.games.specialGames.*;
import models.utils.Result;
import view.PlayView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameController implements Controller{
    private BaseGame game;
    private Level level;
    private Chapters chapter;

    public GameController(Chapters chapter , Level level){
        this.level = level;
        this.chapter = chapter;
        game = switch (level.getLevelType().toLowerCase()){
            case "night ops" -> new NightsOps();
            case "plant what you get" -> new PlantWhatYouGet();
            case "locked plants by category" -> new LockedPlants(LockedPlants.LockType.ByCategory);
            case "conveyor belt" -> new ConveyorBelt();
            case "deadline" -> new Deadline();
            case "save our seeds" -> new SaveOurSeeds();
            case "timed war"  -> new TimedWar();
            case "love your plants" -> new LoveYourPlants();
            default -> new NormalGame();
        };
        game.initGame(chapter , level.getId());
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
        String log = game.playGame(delta);
        Result end = game.check_endGame();
        if(end.success()){
            if(end.message().equals("Loss")) return "Brainzzzzzzzzzz!!!!! Deliciouzzzzzzz!!";
        }
        else if(game.isWon()){
            end();
            App.setScreen(new PlayView());
            return "Sometimes in the life , I'm too competitive , It's good to be competitive.";
        }
        return log;
    }

    private void end(){
        User user = Data.getCurrentUser();
        if(chapter == user.getChapter() && level.getId() == user.getLevelId()){
            user.setLevelId(user.getLevelId()+1);
            user.setLevelsPassed(user.getLevelsPassed()+1);
            for (PlantType x : level.getUnlockingPlants()){
                user.getUnlockedPlants().add(x);
                user.getUnreadNews().add("Congratulation , You've Unlocked new Plant " +
                        ", " + x.name() + " !");
            }
        }
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

        App.setScreen(new PlayView());
    }

    public String gameEndCheat(){
        end();
        return "game ended. you won!";
    }

    public String showSunAmount(){
        return "-->> Suns : " + game.getSunCount();
    }

    public String cheatSunAmount(int amount){
        game.setSunCount(game.getSunCount()+amount);
        return "==== >> Suns added by Cheat code : "  + amount + "\n now " + showSunAmount();
    }

    public String cheatZombieKiller(){
        for (Zombie z : game.getZombies()) {
            z.setHp(0);
        }
        return "What You Said goddamn niggaZombie???";
    }

    public String showPlantsStatus(){
        StringBuilder output = new StringBuilder();
        try {
            for (SeedPackage x : game.getAvailable_plants().values()) {
                output.append(x.getPlant().name()).append("\n")
                        .append("recharge remaining time = ").
                        append(x.getRecharge()).append("\n").
                        append("cost = ").append(x.getCost()).append("\n");
            }
        }catch (RuntimeException e){
            return "Something went wrong during showing plants! try again!...";
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
        output.append("Is this tile empty ? ").append(tile.isEmpty()).append("\n").append("Is it underwater ? ")
                .append(tile.isWater()).append("\n")
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
            case  "add-plant":
                break;
            case "add-sun":
                break;
            case "end":
                gameEndCheat();
                break;
        }
        return "Oh ma man , cheatttt , for real you nigga??? so bad , so bad , ain't tough ):";
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
                if(sun.isRadioActive()){
                    sun.dispose(game);
                    return "Boooooommmmmmmm !!!!! RadioActive Sun explode!";
                }
                game.setSunCount(game.getSunCount() + sun.getPrice());

                if (App.getCurrentuser() != null) {
                    App.getCurrentuser().updateQuestProgress("COLLECT_SUN", sun.getPrice());
                }

                sun.dispose(game);
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
                return game.getSelection().removePlant(game.getAvailable_plants() , t);
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

    public String showAllZombies() {
        if (game.getZombies().isEmpty()) {
            return "No active zombies in the game.";
        }
        StringBuilder sb = new StringBuilder("--- Active Zombies ---\n");
        for (Zombie z : game.getZombies()) {
            sb.append(game.formatZombieInfo(z)).append("\n");
        }
        return sb.toString().trim();
    }

    public String showZombie(String zombieName) {
        Zombie target = null;
        for (Zombie z : game.getZombies()) {
            if (z.getId().equalsIgnoreCase(zombieName) || z.getType().equalsIgnoreCase(zombieName)) {
                target = z;
                break;
            }
        }
        if (target == null) {
            return "Zombie \"" + zombieName + "\" not found in the current game.";
        }
        return game.formatZombieInfo(target);
    }
}