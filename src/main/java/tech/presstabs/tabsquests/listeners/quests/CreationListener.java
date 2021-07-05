package tech.presstabs.tabsquests.listeners.quests;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tech.presstabs.tabsquests.events.QuestCreationEvent;
import tech.presstabs.tabsquests.quests.QuestHandler;
import tech.presstabs.tabsquests.quests.QuestManager;

public class CreationListener implements Listener {
    private final QuestManager manager;

    public CreationListener(QuestManager manager) {
        this.manager = manager;
    }

    //We need to do this last so that other apis and plugins have time to cancel it if needed
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreation(QuestCreationEvent event) {
        if (manager.registerQuest(event.getQuest())) {
            QuestHandler handler = QuestManager.getHandler(event.getQuest());
            for (Player p : Bukkit.getOnlinePlayers()) {
                //QuestDificulty.{DIFF}
                //0123456789ABCDE
                p.sendMessage("§c&lQUEST: §r§6" + handler.questName() + " §c§lDIFFICULTY: §r§6" + handler.difficulty.name().substring(15) + " §chas been created!");
            }
            return;
        }

        Bukkit.getConsoleSender().sendMessage("Quest created improperly");
    }
}
