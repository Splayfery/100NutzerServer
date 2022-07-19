package listener;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class ChannelCreateListener extends ListenerAdapter {

    public void onChannelCreate (ChannelCreateEvent event) {

        //getting user

        List<AuditLogEntry> list = event.getGuild().retrieveAuditLogs().type(ActionType.CHANNEL_CREATE).limit(1).complete();
        AuditLogEntry entry = list.get(0);

        Member member = event.getGuild().getMemberById(entry.getUser().getId());
        Role everyone = event.getGuild().getRoleById("996368957441318923");

        List<Permission> allow = List.of(Permission.VIEW_CHANNEL);
        List<Permission> deny = List.of(Permission.MESSAGE_MANAGE, Permission.MANAGE_CHANNEL, Permission.MESSAGE_SEND, Permission.MANAGE_PERMISSIONS);
        List<Permission> creator = List.of(Permission.MESSAGE_MANAGE, Permission.MANAGE_CHANNEL, Permission.MANAGE_PERMISSIONS, Permission.MESSAGE_SEND);

        //permissionoverride

        switch (event.getChannelType()) {

            case TEXT:

                event.getGuild().getTextChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();
                event.getGuild().getTextChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(creator).queue();

                break;

            case VOICE:

                event.getGuild().getVoiceChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();
                event.getGuild().getVoiceChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(creator).queue();

                break;

            case CATEGORY:

                event.getGuild().getCategoryById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();
                event.getGuild().getCategoryById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(creator).queue();

                break;

            case STAGE:

                event.getGuild().getStageChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();
                event.getGuild().getStageChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(creator).queue();

                break;

            case NEWS:

                event.getGuild().getNewsChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(everyone).grant(allow).deny(deny).queue();
                event.getGuild().getNewsChannelById(event.getChannel().getId()).getPermissionContainer().upsertPermissionOverride(member).grant(creator).queue();

                break;

        }



    }

}
