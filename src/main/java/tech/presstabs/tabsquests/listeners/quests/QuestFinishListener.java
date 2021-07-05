package tech.presstabs.tabsquests.listeners.quests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tech.presstabs.tabsquests.events.QuestGivenEvent;
import tech.presstabs.tabsquests.quests.QuestHandler;
import tech.presstabs.tabsquests.quests.QuestManager;

import java.lang.reflect.InvocationTargetException;

public class QuestFinishListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFinish(QuestGivenEvent event) throws InvocationTargetException, IllegalAccessException {
        QuestHandler handler = QuestManager.getHandler(event.getQuest());
        event.getPlayer().sendMessage("§c&lQUEST: §r§6" + handler.questName() + " §c§lDIFFICULTY: §r§6" + handler.difficulty.name().substring(15) + " §chas been completed!");
        event.getPlayer().sendMessage("&7You should be recieving your rewards shortly...");
        QuestManager.reward(event.getQuest(), event.getPlayer());
    }
}
