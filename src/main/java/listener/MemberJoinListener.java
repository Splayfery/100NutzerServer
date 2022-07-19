package listener;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoinListener extends ListenerAdapter {

    public void onGuildMemberJoin (GuildMemberJoinEvent event) {

        if (!event.getUser().isBot()) {

            Role role = event.getGuild().getRoleById("996708137690210336");

            event.getGuild().addRoleToMember(event.getUser(), role).queue();

        }

    }

}
