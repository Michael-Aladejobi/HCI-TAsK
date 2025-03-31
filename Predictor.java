/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */

import java.util.*;

public class Predictor {
    private Set<String> dictionary;

    public Predictor() {
        dictionary = new HashSet<>(Arrays.asList(
                // Basic Words
                "HELLO", "WORLD", "YES", "NO", "OKAY", "PLEASE", "THANK", "SORRY", "LATER", "SOON",
                "NOW", "TOMORROW", "YESTERDAY", "TODAY", "GOOD", "BAD", "FINE", "BYE", "WELCOME",
                "MORNING", "EVENING", "NIGHT", "AFTERNOON", "FRIEND", "FAMILY", "LOVE", "HAPPY",
                "SAD", "MONEY", "HOUSE", "FOOD", "WATER", "DRINK", "COCONUT", "BEANS", "RICE", "MEAT",
                "CHICKEN", "FISH", "BREAD", "SUGAR", "MILK", "COFFEE", "TEA", "HUNGRY", "FULL",
                "EAT", "DRINK", "THIRSTY", "BOTTLE", "PLATE", "CUP", "TABLE", "CHAIR", "ROOM",
                "KITCHEN", "BATHROOM", "WINDOW", "DOOR", "FLOOR", "WALL", "ROOF", "LIGHT", "FAN",
                "AC", "BED", "PILLOW", "BLANKET", "TOOTHBRUSH", "SOAP", "TOWEL", "SHAMPOO", "CLOTHES",
                "SHOES", "SOCKS", "WATCH", "PHONE", "LAPTOP", "HEADPHONES", "CHARGER", "CABLE",
                "SCREEN", "TOUCH", "BUTTON", "CALL", "TEXT", "MESSAGE", "EMAIL", "CHAT", "VIDEO",
                "MUSIC", "SONG", "MOVIE", "PICTURE", "CAMERA", "PHOTO", "SNAP", "SELFIE", "POST",
                "TWEET", "LIKE", "SHARE", "COMMENT", "SUBSCRIBE", "CHANNEL", "YOUTUBE", "FACEBOOK",
                "INSTAGRAM", "TWITTER", "WHATSAPP", "TIKTOK", "SOCIAL", "MEDIA", "TREND", "HASHTAG",
                "VIRAL", "FAMOUS", "INFLUENCER", "SUBSCRIBER", "FOLLOWER", "FRIEND", "GROUP",
                "COMMUNITY", "NETWORK", "CONNECT", "INTERNET", "BROWSER", "WEBSITE", "GOOGLE",
                "BING", "SEARCH", "DOWNLOAD", "UPLOAD", "STREAM", "BUFFER", "ONLINE", "OFFLINE",
                "CLOUD", "SERVER", "DATABASE", "PASSWORD", "USERNAME", "LOGIN", "SIGNUP", "REGISTER",
                "ACCOUNT", "SECURITY", "PRIVACY", "SETTING", "UPDATE", "UPGRADE", "ERROR", "BUG",
                "FIX", "PATCH", "RESTART", "SHUTDOWN", "BOOT", "LOAD", "PROCESS", "PROGRAM",
                "CODE", "DEBUG", "RUN", "COMPILER", "SYNTAX", "VARIABLE", "CONDITION", "LOOP",
                "CLASS", "OBJECT", "METHOD", "FUNCTION", "ARRAY", "LIST", "STACK", "QUEUE",
                "TREE", "GRAPH", "EDGE", "NODE", "ALGORITHM", "SORT", "SEARCH", "RECURSION",
                "DATA", "ENCODE", "DECODE", "ENCRYPT", "DECRYPT", "FIREWALL", "ANTIVIRUS", "PROXY",
                "VPN", "HACK", "CYBER", "ATTACK", "DEFENSE", "SPAM", "MALWARE", "TROJAN", "VIRUS",
                "WORM", "PHISHING", "SCAM", "FRAUD", "BANK", "CARD", "CASH", "DEBIT", "CREDIT",
                "LOAN", "PAYMENT", "TRANSFER", "BALANCE", "MOBILE", "BILL", "RECHARGE", "PLAN",
                "NETWORK", "SIGNAL", "SPEED", "DOWNLOAD", "UPLOAD", "LATENCY", "BANDWIDTH",
                "MODEM", "ROUTER", "SWITCH", "GATEWAY", "PORT", "IP", "MAC", "ADDRESS", "DNS",
                "HTTP", "HTTPS", "FTP", "TCP", "UDP", "VPN", "TOR", "DARKWEB", "CLOUD", "BACKUP",
                "RECOVER", "DELETE", "RESTORE", "STORAGE", "HARDWARE", "SOFTWARE", "DEVICE",
                "GADGET", "SENSOR", "BLUETOOTH", "WIFI", "USB", "HDMI", "VGA", "AUDIO", "MICROPHONE",
                "SPEAKER", "VOLUME", "BRIGHTNESS", "RESOLUTION", "DISPLAY", "MONITOR", "SCREEN",
                "LAPTOP", "TABLET", "SMARTPHONE", "SMARTWATCH", "WEARABLE", "ALEXA", "SIRI",
                "GOOGLE", "CORTANA", "AI", "MACHINE", "LEARNING", "ROBOT", "AUTOMATION", "AUTONOMOUS",
                "VIRTUAL", "REALITY", "AUGMENTED", "MIXED", "CLOUD", "QUANTUM", "BLOCKCHAIN",
                "CRYPTO", "BITCOIN", "ETHEREUM", "NFT", "TOKEN", "WALLET", "TRADING", "EXCHANGE",
                "INVESTMENT", "STOCK", "MARKET", "BUSINESS", "ENTREPRENEUR", "STARTUP", "VENTURE",
                "FUND", "CAPITAL", "DEVELOPER", "DESIGNER", "ENGINEER", "ARCHITECT", "CONSULTANT",
                "MANAGER", "ANALYST", "STRATEGY", "PLANNING", "GROWTH", "INNOVATION", "RESEARCH",
                "DEVELOPMENT", "TESTING", "DEPLOYMENT", "MAINTENANCE", "SCALABILITY", "PERFORMANCE",
                "OPTIMIZATION", "EFFICIENCY", "PRODUCTIVITY", "QUALITY", "CONTROL", "USER",
                "EXPERIENCE", "RESPONSIVE", "ACCESSIBILITY", "INTERFACE", "NAVIGATION", "DESIGN",
                "COLORS", "FONTS", "LAYOUT", "TEMPLATE", "FRAMEWORK", "LIBRARY", "API", "SDK",
                "VERSION", "RELEASE", "BETA", "STABLE", "LEGACY", "MIGRATION", "UPGRADE",
                "SUPPORT", "DOCUMENTATION", "GUIDE", "TUTORIAL", "COURSE", "LEARNING", "EDUCATION",
                "TEACHING", "TRAINING", "COACHING", "MENTOR", "STUDENT", "SCHOOL", "UNIVERSITY",
                "COLLEGE", "CLASS", "EXAM", "RESULT", "GRADE", "CERTIFICATE", "DIPLOMA", "DEGREE"));

    }

    public List<String> predictNextWords(String input) {
        List<String> predictions = new ArrayList<>();
        for (String word : dictionary) {
            if (word.startsWith(input)) {
                predictions.add(word);
            }
        }
        return predictions;
    }
}

// import java.util.*;

// public class Predictor {
// private Set<String> dictionary;

// public Predictor() {
// dictionary = new HashSet<>(
// Arrays.asList("HELLO", "WORLD", "JAVA", "FEATURE", "PHONE", "PREDICT",
// "PREBLE", "JAVE"));
// }

// public List<String> predictNextWords(String input) {
// List<String> predictions = new ArrayList<>();
// for (String word : dictionary) {
// if (word.startsWith(input)) {
// predictions.add(word);
// }
// }
// return predictions;
// }
// }