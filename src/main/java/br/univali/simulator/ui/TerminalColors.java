package br.univali.simulator.ui;

/**
 * ANSI color codes for terminal output
 */
public final class TerminalColors {
    
    // Reset
    public static final String RESET = "\u001B[0m";
    
    // Regular Colors
    public static final String BLACK = "\u001B[0;30m";
    public static final String RED = "\u001B[0;31m";
    public static final String GREEN = "\u001B[0;32m";
    public static final String YELLOW = "\u001B[0;33m";
    public static final String BLUE = "\u001B[0;34m";
    public static final String PURPLE = "\u001B[0;35m";
    public static final String CYAN = "\u001B[0;36m";
    public static final String WHITE = "\u001B[0;37m";
    
    // Bold
    public static final String BLACK_BOLD = "\u001B[1;30m";
    public static final String RED_BOLD = "\u001B[1;31m";
    public static final String GREEN_BOLD = "\u001B[1;32m";
    public static final String YELLOW_BOLD = "\u001B[1;33m";
    public static final String BLUE_BOLD = "\u001B[1;34m";
    public static final String PURPLE_BOLD = "\u001B[1;35m";
    public static final String CYAN_BOLD = "\u001B[1;36m";
    public static final String WHITE_BOLD = "\u001B[1;37m";
    
    // Background
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    
    // High Intensity
    public static final String BLACK_BRIGHT = "\u001B[0;90m";
    public static final String RED_BRIGHT = "\u001B[0;91m";
    public static final String GREEN_BRIGHT = "\u001B[0;92m";
    public static final String YELLOW_BRIGHT = "\u001B[0;93m";
    public static final String BLUE_BRIGHT = "\u001B[0;94m";
    public static final String PURPLE_BRIGHT = "\u001B[0;95m";
    public static final String CYAN_BRIGHT = "\u001B[0;96m";
    public static final String WHITE_BRIGHT = "\u001B[0;97m";
    
    // Clear screen
    public static final String CLEAR_SCREEN = "\u001B[2J\u001B[H";
    
    /**
     * Colorize text with the specified color
     */
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
    
    /**
     * Color file names based on type
     */
    public static String colorizeFileName(String name, boolean isDirectory) {
        if (isDirectory) {
            return colorize(name, BLUE_BOLD);
        } else {
            return colorize(name, WHITE);
        }
    }
    
    /**
     * Color permissions string
     */
    public static String colorizePermissions(String permissions) {
        return colorize(permissions, YELLOW);
    }
    
    /**
     * Color user names
     */
    public static String colorizeUser(String username) {
        return colorize(username, GREEN);
    }
    
    /**
     * Color paths
     */
    public static String colorizePath(String path) {
        return colorize(path, CYAN);
    }
    
    /**
     * Color error messages
     */
    public static String colorizeError(String error) {
        return colorize(error, RED_BOLD);
    }
    
    /**
     * Color success messages
     */
    public static String colorizeSuccess(String success) {
        return colorize(success, GREEN_BOLD);
    }
} 