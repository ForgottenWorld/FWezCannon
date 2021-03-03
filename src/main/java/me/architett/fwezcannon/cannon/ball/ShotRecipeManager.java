package me.architett.fwezcannon.cannon.ball;

import me.architett.fwezcannon.FWezCannon;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class ShotRecipeManager {

    private static ShotRecipeManager shotRecipeManager;

    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<List<Material>, ShotType> shotTypeContainer;

    private ShotRecipeManager() {
        if(shotRecipeManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.shotTypeContainer = new HashMap<>();
        buildContainer();

    }

    public static ShotRecipeManager getInstance() {
        if(shotRecipeManager == null) {
            shotRecipeManager = new ShotRecipeManager();
        }
        return shotRecipeManager;
    }

    public void buildContainer() {

        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();

        if (fileConfiguration.getBoolean("blind_shot.enable")) {
            String string = fileConfiguration.getString("blind_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
             for (String material : string.split(",")) {
                 Material m = Material.getMaterial(material);
                 if (m == null)
                     break;
                 recipe.add(m);             }
             if (recipe.size() == 9)
                 this.shotTypeContainer.put(recipe,ShotType.BLIND);
            }
        }

        if (fileConfiguration.getBoolean("slow_shot.enable")) {
            String string = fileConfiguration.getString("slow_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
                for (String material : string.split(",")) {
                    Material m = Material.getMaterial(material);
                    if (m == null)
                        break;
                    recipe.add(m);
                }
                if (recipe.size() == 9)
                    this.shotTypeContainer.put(recipe,ShotType.SLOW);
            }
        }

        if (fileConfiguration.getBoolean("poison_shot.enable")) {
            String string = fileConfiguration.getString("poison_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
                for (String material : string.split(",")) {
                    Material m = Material.getMaterial(material);
                    if (m == null)
                        break;
                    recipe.add(m);
                }
                if (recipe.size() == 9)
                    this.shotTypeContainer.put(recipe,ShotType.POISON);
            }
        }

        if (fileConfiguration.getBoolean("fire_shot.enable")) {
            String string = fileConfiguration.getString("fire_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
                for (String material : string.split(",")) {
                    Material m = Material.getMaterial(material);
                    if (m == null)
                        break;
                    recipe.add(m);
                }
                if (recipe.size() == 9)
                    this.shotTypeContainer.put(recipe,ShotType.FIRE);
            }
        }

        if (fileConfiguration.getBoolean("nogravity_shot.enable")) {
            String string = fileConfiguration.getString("nogravity_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
                for (String material : string.split(",")) {
                    Material m = Material.getMaterial(material);
                    if (m == null)
                        break;
                    recipe.add(m);                }
                if (recipe.size() == 9)
                    this.shotTypeContainer.put(recipe,ShotType.NOGRAVITY);
            }
        }

        if (fileConfiguration.getBoolean("huge_shot.enable")) {
            String string = fileConfiguration.getString("huge_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
                for (String material : string.split(",")) {
                    Material m = Material.getMaterial(material);
                    if (m == null)
                        break;
                    recipe.add(m);                }
                if (recipe.size() == 9)
                    this.shotTypeContainer.put(recipe,ShotType.HUGE);
            }
        }

        if (fileConfiguration.getBoolean("multi_shot.enable")) {
            String string = fileConfiguration.getString("multi_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
                for (String material : string.split(",")) {
                    Material m = Material.getMaterial(material);
                    if (m == null)
                        break;
                    recipe.add(m);                }
                if (recipe.size() == 9)
                    this.shotTypeContainer.put(recipe,ShotType.MULTI);
            }
        }

        if (fileConfiguration.getBoolean("bounce_shot.enable")) {
            String string = fileConfiguration.getString("bounce_shot.recipe");
            if (string != null && !string.isEmpty()) {
                List<Material> recipe = new ArrayList<>();
                for (String material : string.split(",")) {
                    Material m = Material.getMaterial(material);
                    if (m == null)
                        break;
                    recipe.add(m);                }
                if (recipe.size() == 9)
                    this.shotTypeContainer.put(recipe,ShotType.BOUNCE);
            }
        }

    }

    public void clearContainer() {
        this.shotTypeContainer.clear();
    }

    public HashMap<List<Material>, ShotType> getShotTypeContainer() {
        return this.shotTypeContainer;
    }

    public List<Material> getShotRecipe(ShotType shotType) {
        for (Map.Entry<List<Material>, ShotType> entry : this.shotTypeContainer.entrySet()) {
            if (Objects.equals(shotType, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public ShotType getShotType(List<Material> recipe) {

        if (this.shotTypeContainer.get(recipe) == null)
            return ShotType.NORMAL;
        else
            return this.shotTypeContainer.get(recipe);

    }

}
