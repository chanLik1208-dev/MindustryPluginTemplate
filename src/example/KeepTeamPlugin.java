import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Administration;
import mindustry.Vars;


public class KeepTeamPlugin extends Plugin {

    @Override
    public void init() {
        // 监听玩家加入事件
        Event(PlayerJoin(mindustry.gen.Player))-> {
            // 检查玩家是否有之前的队伍记录s
            if (event.player.customData().has("team")) {
                // 将玩家分配到之前的队伍
                event.player.team(team.get(event.player.customData().getInt("team")));
            }
        };

        // 监听玩家离开事件
        Event(PlayerLeave(mindustry.gen.Player))-> {
            // 保存玩家的队伍信息
            event.player.customData().put("team", event.player.team().id);
        };
    }
}
