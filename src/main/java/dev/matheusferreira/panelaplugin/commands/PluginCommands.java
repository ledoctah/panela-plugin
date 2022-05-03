package dev.matheusferreira.panelaplugin.commands;

import dev.matheusferreira.panelaplugin.PanelaPlugin;
import dev.matheusferreira.panelaplugin.services.AuthenticateUserService;
import dev.matheusferreira.panelaplugin.services.RegisterUserService;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;

import static org.bukkit.Bukkit.getServer;

public class PluginCommands {
    public void register(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("\n\n\n\n\nVocê precisa informar a senha que deseja registrar. Exemplo:");
            sender.sendMessage("/register <senha>");
            return;
        }

        RegisterUserService registerUserService = new RegisterUserService();

        try {
            registerUserService.execute(sender.getName(), args[0]);

            sender.sendMessage("\n\n\n\n\nCadastro realizado. Agora você já pode fazer o login.");
            sender.sendMessage("/login <senha>");
        } catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        } catch (Error e) {
            sender.sendMessage("\n\n\n\n\nVocê já possui um cadastro ativo e pode fazer login.");
            sender.sendMessage("/login <senha>");
        }
    }

    public void login(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("\n\n\n\n\nVocê precisa informar a senha para logar. Exemplo:");
            sender.sendMessage("/login <senha>");
            return;
        }

        Player player = getServer().getPlayer(sender.getName());

        if (player == null) {
            throw new Error("Error finding user");
        }

        AuthenticateUserService authenticateUserService = new AuthenticateUserService();

        try {
            authenticateUserService.execute(player.getName(), args[0]);

            player.setGameMode(GameMode.SURVIVAL);

            PanelaPlugin.removeFrozenPlayer(sender.getName());

            player.sendMessage("Login realizado com sucesso.");
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new RuntimeException(e);
        } catch (Error e) {
            player.sendMessage("Não foi possível autenticar.");
            player.sendMessage("Verifique se você tem um cadastro ativo e se a senha está correta.");
        }
    }
}
