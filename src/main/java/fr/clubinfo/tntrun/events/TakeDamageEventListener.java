package fr.clubinfo.tntrun.events;

import fr.clubinfo.tntrun.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TakeDamageEventListener implements Listener {
    public TakeDamageEventListener(Main main) {

    }

    @EventHandler
    public void onTakeDamage(org.bukkit.event.entity.EntityDamageEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Player player) {
            event.setCancelled(true);
        }
    }
}
