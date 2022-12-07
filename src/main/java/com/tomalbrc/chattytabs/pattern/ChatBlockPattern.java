package com.tomalbrc.stm.pattern;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatBlockPattern {
    private String title;
    private Pattern pattern;

    private boolean autoExclusive;
    private boolean enabled;

    public ChatBlockPattern() {

    }

    public ChatBlockPattern(String title, Pattern pattern, boolean autoExclusive, boolean enabled) {
        this.title = title;
        this.pattern = pattern;
        this.enabled = enabled;
    }

    public boolean matches(String string) {
        if (pattern == null) // none = matches all
            return true;

        Matcher m = pattern.matcher(string);
        return m.results().count() > 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAutoExclusive() {
        return autoExclusive;
    }

    public void setAutoExclusive(boolean autoExclusive) {
        this.autoExclusive = autoExclusive;
    }

    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.add("title", new JsonPrimitive(this.getTitle()));
        obj.add("pattern", new JsonPrimitive(this.pattern.toString()));
        obj.add("enabled", new JsonPrimitive(this.isEnabled()));
        obj.add("autoexclusive", new JsonPrimitive(this.isAutoExclusive()));
        return obj;
    }

    public static ChatBlockPattern fromJson(JsonObject obj) {
        ChatBlockPattern blockPattern = new ChatBlockPattern();

        blockPattern.title = obj.get("title").getAsString();
        blockPattern.pattern = Pattern.compile(obj.get("pattern").getAsString());
        blockPattern.enabled = obj.get("enabled").getAsBoolean();
        blockPattern.autoExclusive = obj.get("autoexclusive").getAsBoolean();

        return blockPattern;
    }
}
