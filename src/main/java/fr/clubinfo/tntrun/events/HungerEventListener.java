package fr.clubinfo.tntrun.events;

import fr.clubinfo.tntrun.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerEventListener implements Listener {
    public HungerEventListener(Main main) {
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Player player) {
            event.setCancelled(true);
        }
    }
}
