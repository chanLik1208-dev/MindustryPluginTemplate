package example;

import mindustry.mod.Plugin;
import mindustry.gen.Player;
import mindustry.net.Administration;

import java.util.HashMap;
import java.util.Map;

public class TeamPersistencePlugin extends Plugin {
    // 存储玩家队伍信息的映射
    private Map<String, Integer> playerTeams = new HashMap<>();

    @Override
    public void init() {
        // 监听玩家加入事件
        Events.on(PlayerJoin.class, event -> {
            Player player = event.player;
            String uuid = player.uuid();

            // 检查玩家是否有之前的队伍信息
            if (playerTeams.containsKey(uuid)) {
                // 设置玩家的队伍
                player.team(Team.get(playerTeams.get(uuid)));
            } else {
                // 如果没有记录，则将玩家分配到默认队伍并记录
                playerTeams.put(uuid, player.team().id);
            }
        });

        // 监听玩家离开事件
        Events.on(PlayerLeave.class, event -> {
            Player player = event.player;
            String uuid = player.uuid();

            // 保存玩家的队伍信息
            playerTeams.put(uuid, player.team().id);
        });
    }
}
