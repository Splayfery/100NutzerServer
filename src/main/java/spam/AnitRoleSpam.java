package spam;

import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AnitRoleSpam extends ListenerAdapter {

    private List<User> spamList = new ArrayList<>();

    public void onGuildMemberRoleAdd (GuildMemberRoleAddEvent event) {

        if (event.getGuild().getId().equals(Main.server)) {

            List<AuditLogEntry> list = event.getGuild().retrieveAuditLogs().type(ActionType.ROLE_CREATE).limit(1).complete();
            AuditLogEntry entry = list.get(0);

            if (!spamList.contains(entry.getUser())) {

                spamList.add(entry.getUser());

                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        spamList.remove(entry.getUser());

                        t.cancel();

                    }
                }, 10 * 1000);

            } else {

                event.getRoles().get(0).delete().queue();

                PrivateChannel dm = entry.getUser().openPrivateChannel().complete();

                //building embed

                EmbedBuilder banner = new EmbedBuilder();
                banner.setColor(0xed4245);
                banner.setImage("https://cdn.discordapp.com/attachments/880725442481520660/914518380353040384/banner_fehler.png");

                EmbedBuilder reply = new EmbedBuilder();
                reply.setColor(0xed4245);
                reply.setTitle(":no_entry_sign: **LIMIT ERREICHT!**");
                reply.setDescription("> Du kannst nur eine Rolle alle 10 Sekunden erstellen!");
                reply.setImage("https://cdn.discordapp.com/attachments/880725442481520660/905443533824077845/auto_faqw.png");

                dm.sendMessageEmbeds(banner.build(), reply.build()).queue();

            }

        }

    }

}
