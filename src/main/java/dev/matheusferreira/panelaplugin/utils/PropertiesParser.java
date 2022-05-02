package dev.matheusferreira.panelaplugin.utils;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.bukkit.Bukkit.getServer;

public class PropertiesParser {
    String rootPath = "";

    public PropertiesParser() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Panela");

        if (plugin != null) {
            String absolutePath = plugin.getDataFolder().getAbsolutePath();

            Path pluginsPath = Paths.get(absolutePath).getParent();

            this.rootPath = pluginsPath.getParent().toString() + "/panela.properties";
        }
    }

    private void initializeFile() throws IOException {
        Properties properties = new Properties();

        properties.setProperty("database-url", "jdbc:mysql://HOST/DATABASE?user=USERNAME&password=PASSWORD");

        File file = new File(this.rootPath);

        OutputStream inputStream = Files.newOutputStream(file.toPath());

        properties.store(inputStream, null);
    }

    private Properties readFile() throws IOException {
        InputStream inputStream;

        try {
            inputStream = Files.newInputStream(Paths.get(this.rootPath));
        } catch (NoSuchFileException exception) {
            this.initializeFile();

            inputStream = Files.newInputStream(Paths.get(this.rootPath));
        }

        Properties properties = new Properties();

        properties.load(inputStream);

        return properties;
    }

    private void saveFile(Properties properties) throws IOException {
        OutputStream outputStream = Files.newOutputStream(Paths.get(this.rootPath));

        properties.store(outputStream, null);
    }

    public String getProperty(String key) throws IOException {
        Properties file = this.readFile();

        return file.getProperty(key);
    }

    public void setProperty(String key, @Nullable String value) throws IOException {
        Properties properties = this.readFile();

        properties.setProperty(key, value);

        saveFile(properties);
    }
}
