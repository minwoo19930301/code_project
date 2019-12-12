package com.example.fakeyolo;

import java.util.Random;

public class Content {
    private static final String[] content = new String[]{ ("Ask me anything"),
            ("Send me love"),
            ("Would you date me?"),
            ("Tell me a secret"),
            ("Your ops on me with emojis")};

    public String getRandom(){
        Random rand = new Random();
        int rand_num = rand.nextInt(content.length);
        return content[rand_num];
    }

}
