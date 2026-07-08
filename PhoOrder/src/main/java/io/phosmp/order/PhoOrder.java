package io.phosmp.order;

import io.phosmp.core.PhoPlugin;
import io.phosmp.core.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * PhoOrder — Hệ thống đặt hàng từ player
 *
 * Tác giả: Việt Hoàng | phosmp.meowcloud.qzz.io
 * Website: https://phosmp.meowcloud.qzz.io
 */
public final class PhoOrder extends PhoPlugin implements Listener {

    private static PhoOrder instance;

    @Override
    protected void onPhoEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        log.info("§a[PhoOrder] §fĐã khởi động thành công!");
        log.info("§7[PhoOrder] §fHệ thống đặt hàng từ player");
    }

    @Override
    protected void onPhoDisable() {
        log.info("§c[PhoOrder] §fĐã tắt!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cChỉ dùng được trong game!");
            return true;
        }

        if (!player.hasPermission("phosmp.order") && !player.hasPermission("phosmp.*")) {
            player.sendMessage(ColorUtils.error("Bạn không có quyền dùng lệnh này!"));
            return true;
        }

        handleCommand(player, label, args);
        return true;
    }

    private void handleCommand(Player player, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(player);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "help", "giupdo" -> sendHelp(player);
            case "reload" -> {
                if (player.hasPermission("phosmp.admin")) {
                    reloadConfig();
                    player.sendMessage(ColorUtils.success("Đã tải lại cấu hình PhoOrder!"));
                } else {
                    player.sendMessage(ColorUtils.error("Bạn không có quyền admin!"));
                }
            }
            default -> {
                player.sendMessage(ColorUtils.warn("Lệnh không hợp lệ! Dùng /" + label + " help"));
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // PhoOrder join event handler
    }

    private void sendHelp(Player player) {
        player.sendMessage("§b§l═══ PhoOrder ═══");
        player.sendMessage("§7Hệ thống đặt hàng từ player");
        player.sendMessage("§e/order help §7— Hiển thị trợ giúp");
        player.sendMessage("§e/order reload §7— Tải lại (Admin)");
        player.sendMessage("§7Tác giả: §eViệt Hoàng §7| §bphosmp.meowcloud.qzz.io");
    }

    public static PhoOrder getInstance() { return instance; }
}
