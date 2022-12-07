package com.tomalbrc.stm.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tomalbrc.stm.pattern.ChatBlockPattern;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Data {

    private static final Data INSTANCE = new Data();

    private List<ChatBlockPattern> patternList = new ArrayList<>();

    public static void add(ChatBlockPattern pattern) {
        Data.INSTANCE.patternList.add(pattern);
    }

    public static void remove(ChatBlockPattern pattern) {
        Data.INSTANCE.patternList.remove(pattern);
    }

    public static void save() {
        List<ChatBlockPattern> list = Data.INSTANCE.patternList;

        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();

        for (ChatBlockPattern blockPattern: list) {
            array.add(blockPattern.toJson());
        }

        obj.add("list", array);

        File file = getStorageFile(true);
        JsonUtils.writeJsonToFile(obj, file);
    }

    public static void load() {
        List<ChatBlockPattern> list = new ArrayList<>();

        JsonElement element = JsonUtils.parseJsonFile(getStorageFile(true));

        if (element != null && element.isJsonObject())
        {
            JsonObject root = element.getAsJsonObject();

            JsonArray arr = root.get("patterns").getAsJsonArray();
            final int size = arr.size();

            for (int i = 0; i < size; ++i) {
                JsonElement el = arr.get(i);
                if (el.isJsonObject()) {
                    list.add(ChatBlockPattern.fromJson(el.getAsJsonObject()));
                }
            }
        }

        if (list.isEmpty()) {
            list.add(new ChatBlockPattern("has test", Pattern.compile(".*test.*"), true, true));
        }

        Data.INSTANCE.patternList = list;
    }

    private static Data getInstance()
    {
        return INSTANCE;
    }

    private static File getStorageFile(boolean globalData)
    {
        File dir = new File(FileUtils.getConfigDirectory(), "StopTheMadness");

        if (dir.exists() == false && dir.mkdirs() == false)
        {
            System.out.println("Failed to create the config directory " + dir.getAbsolutePath());
        }

        System.out.println(dir.toString());

        return new File(dir, StringUtils.getStorageFileName(globalData, "StopTheMadness" + "_", ".json", "default"));
    }

    public static List<ChatBlockPattern> getBlockPatternList() {
        return Data.INSTANCE.patternList;
    }

    public static void setBlockPatternList(List<ChatBlockPattern> list) {
        Data.INSTANCE.patternList = list;
    }
}
