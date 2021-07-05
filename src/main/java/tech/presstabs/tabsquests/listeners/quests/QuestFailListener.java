package tech.presstabs.tabsquests.listeners.quests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tech.presstabs.tabsquests.events.QuestFailEvent;

public class QuestFailListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFail(QuestFailEvent event) {
        event.getPlayer().sendMessage("Why are you so bad at the game?");
    }
}
