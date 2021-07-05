package tech.presstabs.tabsquests;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import tech.presstabs.tabsquests.listeners.quests.CreationListener;
import tech.presstabs.tabsquests.listeners.quests.QuestFailListener;
import tech.presstabs.tabsquests.listeners.quests.QuestFinishListener;
import tech.presstabs.tabsquests.listeners.quests.QuestGivenListener;
import tech.presstabs.tabsquests.quests.QuestManager;

public final class TabsQuests extends JavaPlugin {

    private QuestManager manager;
    private FileConfiguration config;

    private MongoDatabase database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        config.addDefault("database", "mongodb+srv://<user>:<password>@<ip>/<databaseName>?retryWrites=true&w=majority");
        config.addDefault("dbName", "databaseName");
        config.options().copyDefaults(true);
        this.saveConfig();
        this.config = this.getConfig();

        this.database = MongoClients.create(config.getString("database")).getDatabase(config.getString("dbName"));

        this.manager = new QuestManager(this.database);

        this.getServer().getPluginManager().registerEvents(new CreationListener(manager), this);
        this.getServer().getPluginManager().registerEvents(new QuestFailListener(), this);
        this.getServer().getPluginManager().registerEvents(new QuestFinishListener(), this);
        this.getServer().getPluginManager().registerEvents(new QuestGivenListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        manager = null;
        database = null;
    }
}
