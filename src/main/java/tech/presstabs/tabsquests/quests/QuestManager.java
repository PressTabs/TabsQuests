package tech.presstabs.tabsquests.quests;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class QuestManager {
    private final MongoDatabase db;

    //Probably should use database instead of MongoClient
    public QuestManager(MongoDatabase db) {
        this.db = db;
    }

    public static QuestHandler getHandler(Class<? extends AbstractQuest> quest) {
        return quest.getAnnotation(QuestHandler.class);
    }

    public static void reward(Class<? extends AbstractQuest> quest, Player p) throws InvocationTargetException, IllegalAccessException {
        for (final Method method : quest.getMethods()) {
            if (method.getAnnotation(Reward.class) != null) {
                method.invoke(quest, p);
            }
        }

        for (final Method method : quest.getDeclaredMethods()) {
            if (method.getAnnotation(Reward.class) != null) {
                method.invoke(quest, p);
            }
        }
    }

    public void removeQuest(String questName) {
        MongoCollection<Document> quests = db.getCollection("quests");

        Bson filter = Filters.eq("questName", questName);
        quests.findOneAndDelete(filter);
    }

    public boolean registerQuest(Class<? extends AbstractQuest> quest) {
        QuestHandler questHandler = quest.getAnnotation(QuestHandler.class);
        if (questHandler == null) {
            return false;
        }

        String questName = questHandler.questName();
        QuestDifficulty difficulty = questHandler.difficulty;

        //This is similar to the JavaPluginLoader in paper/spigot
        Set<Method> methods;
        try {
            Method[] publicMethods = quest.getMethods();
            Method[] privateMethods = quest.getDeclaredMethods();
            methods = new HashSet<>(publicMethods.length + privateMethods.length, 1.0f);
            Collections.addAll(methods, publicMethods);
            Collections.addAll(methods, privateMethods);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return false;
        }

        boolean hasStart = false;
        boolean hasQuit = false;
        boolean hasFinish = false;
        boolean hasReward = false;
        for (final Method method : methods) {
            final QuestStart start = method.getAnnotation(QuestStart.class);
            final QuestQuit quit = method.getAnnotation(QuestQuit.class);
            final QuestFinish finish = method.getAnnotation(QuestFinish.class);
            final Reward reward = method.getAnnotation(Reward.class);

            if (start != null) {
                hasStart = true;
            } else if (quit != null) {
                hasQuit = true;
            } else if (finish != null) {
                hasFinish = true;
            } else if (reward != null) {
                hasReward = true;
            }

            if (hasStart || hasQuit || hasFinish || hasReward) {
                break;
            }
        }

        if (!(hasStart || hasQuit || hasFinish || hasReward)) {
            return false;
        }
        MongoCollection<Document> quests = db.getCollection("quests");

        Bson filter = Filters.eq("questName", questName);
        FindIterable<Document> docIter = quests.find(filter);
        if (docIter.first() == null) {
            return false;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("questName", questName);
        map.put("difficulty", difficulty);
        map.put("players", new HashSet<UUID>());
        Document document = new Document(map);

        quests.insertOne(document);
        quests.createIndex(Indexes.text("questName"));

        return true;
    }

    public void addPlayer(String questName, Player p) throws Exception {
        UUID uid = p.getUniqueId();

        MongoCollection<Document> quests = db.getCollection("quests");

        Bson filter = Filters.eq("questName", questName);
        FindIterable<Document> docIter = quests.find(filter);
        Document doc = docIter.first();
        if (doc == null) {
            throw new Exception(questName + " not found in QUESTS database");
        }
        @SuppressWarnings("unchecked")
        ArrayList<UUID> UUIDList = (ArrayList<UUID>) Objects.requireNonNull(doc.get("players"));
        if (!UUIDList.contains(uid)) {
            UUIDList.add(uid);
        }
        doc.put("players", UUIDList);

        //Unsure to use FindOneAndUpdate or do this.
        quests.insertOne(doc);
    }

    public void removePlayer(String questName, Player p) throws Exception {
        UUID uid = p.getUniqueId();

        MongoCollection<Document> quests = db.getCollection("quests");

        Bson filter = Filters.eq("questName", questName);
        FindIterable<Document> docIter = quests.find(filter);
        Document doc = docIter.first();
        if (doc == null) {
            throw new Exception(questName + " not found in QUESTS database");
        }
        @SuppressWarnings("unchecked")
        ArrayList<UUID> UUIDList = (ArrayList<UUID>) Objects.requireNonNull(doc.get("players"));
        UUIDList.remove(uid);
        doc.put("players", UUIDList);

        //Unsure to use FindOneAndUpdate or do this.
        quests.insertOne(doc);
    }
}
