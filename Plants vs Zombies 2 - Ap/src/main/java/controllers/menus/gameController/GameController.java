package controllers.menus.gameController;

import controllers.datacontroller.Data;
import controllers.datacontroller.SeedPackage;
import controllers.menus.Menu;
import models.App;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.User;
import models.entity.*;
import models.factory.builder.PlantType;
import models.gamePanes.Tile;
import models.games.BaseGame;
import models.games.NormalGame;
import models.games.specialGames.*;
import models.utils.Result;
import view.PlayView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameController implements Controller , Menu {
    private final BaseGame game;
    private final Level level;
    private final Chapters chapter;

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
        game.initGame(chapter , level);
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
            if(end.message().equals("Loss")) {
                App.setScreen(new PlayView());
                return "Brainzzzzzzzzzz!!!!! Deliciouzzzzzzz!!";
            }
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

        Data.saveUser();
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
        if(game instanceof ConveyorBelt){
            return belt();
        }
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

    private String belt(){
        ConveyorBelt  conveyorBelt = (ConveyorBelt) game;
        StringBuilder output = new StringBuilder();
        for (PlantType x : conveyorBelt.getBelt()){
            output.append(x.name()).append(" is ready on the belt\n");
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
        String output = null;
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
                output = gameEndCheat();
                break;
        }
        return "Oh ma man , cheatttt , for real you nigga??? so bad , so bad , ain't tough ):\n" + output;
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
                //iterator.remove();
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
            if(game.getAvailable_plants().containsKey(seedPackage.getPlant())){
                return "The plant is already selected , can't select twice.";
            }
            game.getAvailable_plants().put(seedPackage.getPlant(), seedPackage);
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
        List<Zombie> zombies = game.getZombies();
        if (zombies.isEmpty()) {
            return "No active zombies in the game.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Active Zombies (").append(zombies.size()).append(") ---\n");

        for (int i = 0; i < zombies.size(); i++) {
            Zombie z = zombies.get(i);
            sb.append(i + 1).append(". ");
            sb.append(formatZombieInfo(z));
            if (i < zombies.size() - 1) {
                sb.append("\n");
            }
        }

        return sb.toString().trim();
    }

    public String showZombie(String zombieName) {
        Zombie target = null;
        for (Zombie z : game.getZombies()) {
            if (z.getId().equalsIgnoreCase(zombieName) ||
                    z.getType().equalsIgnoreCase(zombieName)) {
                target = z;
                break;
            }
        }

        if (target == null) {
            return "Zombie \"" + zombieName + "\" not found in the current game.";
        }

        return formatZombieInfo(target);
    }

    private String formatZombieInfo(Zombie zombie) {
        StringBuilder sb = new StringBuilder();

        // 1. Name
        sb.append(zombie.getType());

        // 2. Position (tile position)
        int col = zombie.getTileIndex();
        int row = zombie.getRow();
        sb.append("  position: (").append(row).append(", ").append(col).append(")\n");
        sb.append("x : ").append(zombie.getX()).append(" , y : ").append(zombie.getY()).append("\n");

        // 3. Health
        sb.append("  health: ").append(zombie.getHp()).append("/").append(zombie.getMaxHp());

        // 4. Armors
        List<Armor> armors = zombie.getArmors();
        if (!armors.isEmpty()) {
            sb.append("  armors: ");
            for (int i = 0; i < armors.size(); i++) {
                Armor armor = armors.get(i);
                sb.append(armor.getType()).append(": ").append(armor.getHealth());
                if (armor.isBroken()) {
                    sb.append("(BROKEN)");
                }
                if (i < armors.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        // 5. Effects
        List<Effect> effects = zombie.getEffects();
        if (!effects.isEmpty()) {
            sb.append("  effects: ");
            for (int i = 0; i < effects.size(); i++) {
                Effect effect = effects.get(i);
                sb.append(effect.getType().name().toLowerCase());
                float remaining = effect.getRemainingTime();
                if (remaining > 0) {
                    sb.append(": ").append(String.format("%.1f", remaining)).append("s");
                }
                if (i < effects.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        // 6. State flags
        if (zombie.isHypnotized()) {
            sb.append("  hypnotized: YES");
        }
        if (zombie.isFrozen()) {
            sb.append("  frozen: YES");
        }

        return sb.toString();
    }


    public String showPlants(){
        StringBuilder sb = new StringBuilder();
        for (Plant x : game.getPlants_inField()){
            sb.append("=====\n").append("type : " + x.getType()).append("\n")
                    .append("hp : " + x.getHp()).append("\n")
                    .append("location : x = " + x.getTileIndex() + " , y = " + x.getLine());
        }
        return  sb.toString();
    }


    public String showBullets(){
        StringBuilder sb = new StringBuilder();
        for (Bullet bullet : game.getBullets()){
            sb.append("=====\n").append("type : " + bullet.getType()).append("\n")
                    .append("location " + "(" + bullet.getX() + "," + bullet.getY() + ")" + "\n");
        }
        return  sb.toString();
    }

    public String showSuns(){
        if(game.getSuns().isEmpty()){
            return "No suns in the game.";
        }
        StringBuilder sb = new StringBuilder();
        for (Sun sun : game.getSuns()){
            sb.append(" price : " + sun.getPrice()).append("\n")
                    .append("remainingTime : " + sun.getRemainingTime()).append("\n")
                    .append(" is radio active ? " + sun.isRadioActive() ).append("\n");
        }
        return  sb.toString();
    }

    public String nuke(){
        return ((NormalGame) game).nuke();
    }
    @Override
    public String ChangeMenu(String menuName) {
        return "";
    }
}