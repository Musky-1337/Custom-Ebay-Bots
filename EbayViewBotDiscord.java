package com.bayanrasooly;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EbayViewBotDiscord {
    public String link;
    static String token = "NTI5MjA5MTk0MDg4NDk3MjUx.DwwsEw.4YHDX9f9iTUztiCz1YnbCe432Io";
    public int i1;
    public static EbayListener lsn;

    public class MyRunnable implements Runnable {
        public void run(){
            try {
                getViews();
            } catch (IOException e) {
            }
        }
    }
    final static String[] DEFAULT_USER_AGENTS = {
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/65.0.3325.181 Chrome/65.0.3325.181 Safari/537.36",
            "Mozilla/5.0 (Linux; Android 7.0; Moto G (5) Build/NPPS25.137-93-8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.137 Mobile Safari/537.36",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11B554a Safari/9537.53",
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:59.0) Gecko/20100101 Firefox/59.0",
            "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0"};
    public static void main(String [] args) throws  IOException{
       // Console x  = new Console(System.in);

        lsn = new EbayListener();
        try{
            JDA api = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
            api.addEventListener(lsn);
        }
        catch(Throwable t){}
        while(true){}
    }
    public EbayViewBotDiscord(int numb, String s) throws  IOException{
        link = s;
        i1 = numb;
        for(int k = 0; k < 5; k++)
            new Thread(new MyRunnable()).start();
    }
    public void getViews() throws IOException {
        Document doc;
        if(i1 > 1500)
            i1 = 500;
        try{
            for(int i = 0; i < i1/5; i++){
                doc = Jsoup.connect(link)
                    .userAgent(DEFAULT_USER_AGENTS[(int)(Math.random()*7)])
                    .ignoreContentType(true).get();

                System.out.println("Successfully viewed #" + i);}
        }
        catch(Throwable t) {System.out.println("Network or URL Error.");}
    }
    static class EbayListener extends ListenerAdapter {
        private int i = 0;
        private ArrayList<String> restock = new ArrayList();
        private MessageChannel chan;

        public EbayListener() {
        }

        public void onMessageReceived(MessageReceivedEvent event) {
            Message message = event.getMessage();
            String content = message.getContentRaw();
            MessageChannel channel = event.getChannel();
            this.chan = channel;
            if (message.getContentRaw().indexOf("view") != -1) {
                String link = message.getContentRaw();
                link = link.substring(6);
                int ind = link.indexOf(" ");
                String viewNumber = link.substring(ind + 1);
                link = link.substring(0, link.indexOf(" "));
                int l = Integer.parseInt(viewNumber);
                channel.sendMessage("Completing " + l + " views").queue();
                System.out.println(l);

                try {
                    new EbayViewBotDiscord(l, link);
                } catch (Throwable var10) {
                }
            }

        }

        public void mod(String s) {
            new Listener();
        }
    }
}
