/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2020
 */
package com.github.ucchyocean.lc3.bridge;

import java.lang.reflect.Method;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class MultiverseCoreBridge {

    private Object worldManager;
    private Method getWorldMethod;
    private Method getAliasMethod;
    private Method getNameMethod;
    private Method getOrNullMethod;

    private MultiverseCoreBridge() {
    }

    public static MultiverseCoreBridge load(Plugin plugin) {
        try {
            // 5.x系のAPIシングルトンクラスをリフレクションで取得
            Class<?> apiClass = Class.forName("org.mvplugins.multiverse.core.api.MultiverseCoreApi");
            Method getApiMethod = apiClass.getMethod("get");
            Object apiInstance = getApiMethod.invoke(null);

            if (apiInstance != null) {
                MultiverseCoreBridge bridge = new MultiverseCoreBridge();
                
                // worldManagerの取得
                Method getWorldManagerMethod = apiInstance.getClass().getMethod("getWorldManager");
                bridge.worldManager = getWorldManagerMethod.invoke(apiInstance);

                // 各種メソッドのキャッシュ
                bridge.getWorldMethod = bridge.worldManager.getClass().getMethod("getWorld", String.class);
                
                // io.vavr.control.Option から実体を取り出すメソッド
                Class<?> optionClass = Class.forName("io.vavr.control.Option");
                bridge.getOrNullMethod = optionClass.getMethod("getOrNull");

                return bridge;
            }
        } catch (Exception e) {
            // 5.x系が見つからない、またはエラーの場合は何もしない
        }
        return null;
    }

    public String getWorldAlias(String worldName) {
        if (worldManager == null || worldName == null) {
            return null;
        }

        try {
            // worldManager.getWorld(worldName) -> Optionオブジェクトが返る
            Object optionObj = getWorldMethod.invoke(worldManager, worldName);
            if (optionObj == null) return null;

            // option.getOrNull() -> MultiverseWorldオブジェクトを取得
            Object mvWorld = getOrNullMethod.invoke(optionObj);
            if (mvWorld == null) return null;

            // メソッドの動的解析（初回のみ）
            if (getAliasMethod == null) {
                getAliasMethod = mvWorld.getClass().getMethod("getAlias");
                getNameMethod = mvWorld.getClass().getMethod("getName");
            }

            String alias = (String) getAliasMethod.invoke(mvWorld);
            if (alias != null && alias.length() > 0) {
                return alias;
            }

            return (String) getNameMethod.invoke(mvWorld);

        } catch (Exception e) {
            return null;
        }
    }

    public String getWorldAlias(World world) {
        if (world == null) {
            return null;
        }
        return getWorldAlias(world.getName());
    }
}