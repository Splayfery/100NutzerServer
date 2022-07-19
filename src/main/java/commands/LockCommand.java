package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class LockCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("lock")) {

            if (event.getMember().hasPermission(event.getGuildChannel(), Permission.MANAGE_PERMISSIONS)) {

                Role everyone = event.getGuild().getRoleById("996368957441318923");

                List<Permission> allow = List.of(Permission.VIEW_CHANNEL);
                List<Permission> deny = List.of(Permission.MESSAGE_MANAGE, Permission.MANAGE_CHANNEL, Permission.MESSAGE_SEND, Permission.MANAGE_PERMISSIONS);

                //permissionoverride

                switch (event.getChannelType()) {

                    case TEXT:

                        event.getGuild().getTextChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();

                        break;

                    case VOICE:

                        event.getGuild().getVoiceChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();

                        break;

                    case CATEGORY:

                        event.getGuild().getCategoryById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();

                        break;

                    case STAGE:

                        event.getGuild().getStageChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();

                        break;

                    case NEWS:

                        event.getGuild().getNewsChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();

                        break;

                }

                //building embed

                EmbedBuilder bannerEmbed = new EmbedBuilder();
                bannerEmbed.setColor(0x43b480);
                bannerEmbed.setImage("https://cdn.discordapp.com/attachments/880725442481520660/914518380088819742/banner_erfolg.png");

                EmbedBuilder reply = new EmbedBuilder();
                reply.setColor(0x43b480);
                reply.setTitle("✅ **KANAL ERFOLGREICH GESPERRT!**");
                reply.setDescription("> Du hast den Kanal nun erfolgreich für alle Nutzer gesperrt!");
                reply.setImage("https://cdn.discordapp.com/attachments/880725442481520660/905443533824077845/auto_faqw.png");

                event.replyEmbeds(bannerEmbed.build(), reply.build()).setEphemeral(true).queue();

            } else {

                //building embed

                EmbedBuilder banner = new EmbedBuilder();
                banner.setColor(0xed4245);
                banner.setImage("https://cdn.discordapp.com/attachments/880725442481520660/914518380353040384/banner_fehler.png");

                EmbedBuilder reply = new EmbedBuilder();
                reply.setColor(0xed4245);
                reply.setTitle(":no_entry_sign: **KEINE BERECHTIGUNG!**");
                reply.setDescription("> Du kannst nur deine eigenen Kanäle verwalten!");
                reply.setImage("https://cdn.discordapp.com/attachments/880725442481520660/905443533824077845/auto_faqw.png");

                event.replyEmbeds(banner.build(), reply.build()).setEphemeral(true).queue();

            }

        }

    }

}
