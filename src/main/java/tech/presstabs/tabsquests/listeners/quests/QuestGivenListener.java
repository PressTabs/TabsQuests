package tech.presstabs.tabsquests.listeners.quests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tech.presstabs.tabsquests.events.QuestGivenEvent;
import tech.presstabs.tabsquests.quests.QuestHandler;
import tech.presstabs.tabsquests.quests.QuestManager;

public class QuestGivenListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuest(QuestGivenEvent event) {
        QuestHandler handler = QuestManager.getHandler(event.getQuest());
        event.getPlayer().sendMessage("§c&lQUEST: §r§6" + handler.questName() + " §c§lDIFFICULTY: §r§6" + handler.difficulty.name().substring(15) + " §chas been accepted!");
    }
}
