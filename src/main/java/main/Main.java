package main;

import commands.DenyCommand;
import commands.LockCommand;
import commands.TrustCommand;
import commands.UnlockCommand;
import listener.ChannelCreateListener;
import listener.MemberJoinListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;

public class Main {

    public static JDA shardMan;
    public static JDABuilder builder;
    public static String token = "TOKEN";
    public static String server = "guildId";

    public static void main(String[] args) throws LoginException, InterruptedException, IOException {

        builder = JDABuilder.createDefault(token);

        builder.setActivity(Activity.watching("Splayfers Stream"));

        builder.setStatus(OnlineStatus.ONLINE);

        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES);

        EnumSet<CacheFlag> enumSet = EnumSet.of(CacheFlag.ONLINE_STATUS, CacheFlag.CLIENT_STATUS, CacheFlag.EMOJI, CacheFlag.VOICE_STATE);

        builder.enableCache(enumSet);

        //registering events

        //listener

        builder.addEventListeners(new ChannelCreateListener());
        builder.addEventListeners(new MemberJoinListener());

        //commands

        builder.addEventListeners(new LockCommand());
        builder.addEventListeners(new UnlockCommand());
        builder.addEventListeners(new TrustCommand());
        builder.addEventListeners(new DenyCommand());

        shardMan = builder.build();

        //slash commands

        shardMan.awaitReady().getGuildById(server).upsertCommand("lock", "\uD83D\uDD12〣Sperre den Kanal für andere Nutzer!").queue();
        shardMan.awaitReady().getGuildById(server).upsertCommand("unlock", "\uD83D\uDD13〣Entsperre den Kanal für andere Nutzer!").queue();
        shardMan.awaitReady().getGuildById(server).upsertCommand("trust", "✅〣Erlaube einen Nutzer in diesem Kanal!").addOption(OptionType.USER, "nutzer", "Nutzer, welcher hinzugefügt werden soll!", true).queue();
        shardMan.awaitReady().getGuildById(server).upsertCommand("deny", "❌〣Sperre einen Nutzer in diesem Kanal!").addOption(OptionType.USER, "nutzer", "Nutzer, welcher entfernt werden soll!", true).queue();

        //shardMan.awaitReady().getGuildById(server).updateCommands();

        //stop command

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        if(reader.readLine().equals("stop")) {

            reader.close();

            builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

            builder.setActivity(Activity.playing("Offline"));

            shardMan.shutdown();

            System.out.println("[Splayfer] Bot changed Status: Stopped");

            System.exit(0);

        } else if (reader.readLine().equals("checkfolder")) {

            //check data folders

            System.out.println("Alle Systeme laufen optimal!");
        }

    }

}
