package commands;

import net.dv8tion.jda.api.EmbedBuilder;
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

            int rolecount = 0;

            for (AuditLogEntry entry : rolelist) {

                if (entry.getUser().equals(event.getOptionsByName("nutzer").get(0).getAsUser())) {

                    event.getGuild().getRoleById(entry.getTargetId()).delete().queue();

                    rolecount++;

                }

            }

            //retrieve channels

            List<AuditLogEntry> channellist = event.getGuild().retrieveAuditLogs().type(ActionType.CHANNEL_CREATE).limit(100).complete();

            int channelcount = 0;

            for (AuditLogEntry entry : channellist) {

                if (entry.getUser().equals(event.getOptionsByName("nutzer").get(0).getAsUser())) {

                    event.getGuild().getGuildChannelById(entry.getTargetId()).delete().queue();

                    channelcount++;

                }

            }

            event.getOptionsByName("nutzer").get(0).getAsMember().ban(7, "Purged by " + event.getUser().getAsTag()).queue();

            //building embed

            EmbedBuilder bannerEmbed = new EmbedBuilder();
            bannerEmbed.setColor(0x43b480);
            bannerEmbed.setImage("https://cdn.discordapp.com/attachments/880725442481520660/914518380088819742/banner_erfolg.png");

            EmbedBuilder reply = new EmbedBuilder();
            reply.setColor(0x43b480);
            reply.setTitle("✅ **PURGE ERFOLGREICH!**");
            reply.setDescription("> Du hast die Aktionen des Nutzers " + event.getOptionsByName("nutzer").get(0).getAsUser().getAsTag() + " erfolgreich rückgänig gemacht!");
            reply.addField("Gelöschte Rollen", String.valueOf(rolecount), false);
            reply.addField("Gelöschte Kanäle", String.valueOf(channelcount), false);
            reply.setImage("https://cdn.discordapp.com/attachments/880725442481520660/905443533824077845/auto_faqw.png");

            event.replyEmbeds(bannerEmbed.build(), reply.build()).setEphemeral(true).queue();

        }

    }

}
