package com.codenameflip.tags.objects;

import com.codenameflip.tags.Tags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author codenameflip
 * @since 4/30/17
 */

public class TagHolder {

    private UUID uuid;

    public TagHolder(UUID uuid) {
        this.uuid = uuid;
    }

    private List<Tag> tags;
    private Tag selectedTag;

    public UUID getUuid() {
        return uuid;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Tag getSelectedTag() {
        return selectedTag;
    }

    /**
     * Adds a specific Tag from a player's storage
     * @param tag The Tag you would like to add
     */
    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);

            Tags.get().getDataStrategy().saveTagData(this);
        }
    }

    /**
     * Removes a specific Tag from a player's storage
     * @param tag The Tag you would like to remove
     */
    public void removeTag(Tag tag) {
        if (tags.contains(tag)) {
            tags.remove(tag);

            Tags.get().getDataStrategy().saveTagData(this);
        }
    }

    /**
     * Resets a player's stored tags
     */
    public void resetTags() {
        tags.clear();

        Tags.get().getDataStrategy().saveTagData(this);
    }

    /**
     * Selects what tag will be displayed over the players head, in the tablist and in chat
     * @param tag The desired tag
     */
    public void selectTag(Tag tag) {
        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {
            // Set the player's name above their head
            tag.getScoreboardTeam().addEntry(uuid.toString());

            // Set the player's name on the tab list
            player.setPlayerListName(tag.getDisplayName() + " " + player.getDisplayName());

            if (Tags.get().isTagsInChat())
                player.setDisplayName(tag.getDisplayName() + " " + player.getDisplayName());

            // Set the variable
            selectedTag = tag;

            Tags.get().getDataStrategy().saveTagData(this);
        }
    }

    /**
     * Gets the player's saved tags in a store-able format (ex: "thisTag:thatTag:anotherTag"
     * @return The formatted string
     */
    public String getFormattedTags() {
        StringBuilder result = new StringBuilder();

        for (Tag tag : getTags()) {
            result.append(tag.getIdentifier()).append(":");
        }

        result.substring(0, result.length() -1); // Hacky work around to removing the last character from the string.

        return result.toString();
    }

}