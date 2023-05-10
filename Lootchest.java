package lootchest.lootchest;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Lootchest extends JavaPlugin implements Listener {

    private final List<Material> availableItems = new ArrayList<>();

    @Override
    public void onEnable() {
        // Add all materials to availableItems except bedrock
        for (Material material : Material.values()) {
            if (material != Material.BEDROCK) {
                availableItems.add(material);
            }
        }

        // Register events
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Clean up
        availableItems.clear();
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        // Check if the opened inventory is a chest
        if (event.getInventory().getHolder() instanceof Chest) {
            Chest chest = (Chest) event.getInventory().getHolder();
            Inventory chestInventory = chest.getBlockInventory();

            // Randomize the items in the chest
            List<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < chestInventory.getSize(); i++) {
                // Choose a random item from availableItems
                Material randomMaterial = availableItems.get(new Random().nextInt(availableItems.size()));
                int randomStackSize = new Random().nextInt(64) + 1; // Choose a random stack size between 1 and 64
                ItemStack item = new ItemStack(randomMaterial, randomStackSize);

                items.add(item);
            }

            Collections.shuffle(items);
            for (int i = 0; i < chestInventory.getSize(); i++) {
                chestInventory.setItem(i, items.get(i));
            }
        }
    }
}






