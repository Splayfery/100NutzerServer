package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class TrustCommand extends ListenerAdapter {

    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {

        if (event.getName().equals("trust")) {

            if (event.getMember().hasPermission(event.getGuildChannel(), Permission.MANAGE_PERMISSIONS)) {

                List<Permission> trust = List.of(Permission.MESSAGE_MANAGE, Permission.MANAGE_CHANNEL, Permission.MESSAGE_SEND);

                Member member = event.getOptionsByName("nutzer").get(0).getAsMember();

                    //permissionoverride

                    switch (event.getChannelType()) {

                        case TEXT:

                            event.getGuild().getTextChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(trust).queue();

                            break;

                        case VOICE:

                            event.getGuild().getVoiceChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(trust).queue();

                            break;

                        case CATEGORY:

                            event.getGuild().getCategoryById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(trust).queue();

                            break;

                        case STAGE:

                            event.getGuild().getStageChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(trust).queue();

                            break;

                        case NEWS:

                            event.getGuild().getNewsChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(trust).queue();

                            break;

                    }

                    //building embed

                    EmbedBuilder bannerEmbed = new EmbedBuilder();
                    bannerEmbed.setColor(0x43b480);
                    bannerEmbed.setImage("https://cdn.discordapp.com/attachments/880725442481520660/914518380088819742/banner_erfolg.png");

                    EmbedBuilder reply = new EmbedBuilder();
                    reply.setColor(0x43b480);
                    reply.setTitle("✅ **NUTZER ERFOLGREICH HINZUGEFÜGT!**");
                    reply.setDescription("> Der Nutzer " + member.getAsMention() + " wurde erfolgreich zum Kanal hinzugefügt!");
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
