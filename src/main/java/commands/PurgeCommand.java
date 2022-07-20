package commands;

import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class PurgeCommand extends ListenerAdapter {

    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {

        if (event.getName().equals("purge")) {

            //retrieve roles

            List<AuditLogEntry> rolelist = event.getGuild().retrieveAuditLogs().type(ActionType.ROLE_CREATE).limit(100).complete();

            for (AuditLogEntry entry : rolelist) {

                if (entry.getUser().equals(event.getOptionsByName("nutzer").get(0).getAsUser())) {

                    event.getGuild().getRoleById(entry.getTargetId()).delete().queue();

                }

            }

            //retrieve channels

            List<AuditLogEntry> channellist = event.getGuild().retrieveAuditLogs().type(ActionType.CHANNEL_CREATE).limit(100).complete();

            for (AuditLogEntry entry : channellist) {

                if (entry.getUser().equals(event.getOptionsByName("nutzer").get(0).getAsUser())) {

                    event.getGuild().getGuildChannelById(entry.getTargetId()).delete().queue();

                }

            }

            event.getOptionsByName("nutzer").get(0).getAsMember().ban(7, "Purged by " + event.getUser().getAsTag()).queue();

        }

    }

}
