package log;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.util.ArrayList;

public class ChatLog {
    private final File logDir = new File("logs");
    private File chatLogFile;

    public ChatLog() {
        if (!logDir.exists()) {
            logDir.mkdir();
        }
    }

    public void startLogging(String login) {
        chatLogFile = new File("logs/history_[" + login + "].txt");
        if (!chatLogFile.exists()) {
            System.out.println("Файл истории для " + login + " не существует");
            try {
                boolean newFile = chatLogFile.createNewFile();
                System.out.println("файл создан: " + newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readLog() {
        String s = "";
        ArrayList log = new ArrayList();
        int maxCount = 100;
        ReversedLinesFileReader lfr = null;
        try {
            lfr = new ReversedLinesFileReader(chatLogFile, null);
            s = lfr.readLine();
            log.add(s);
            int count = 1;
            while (s != null) {
                if (count == maxCount) break;
                s = lfr.readLine();
                log.add(s);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                lfr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        s = log.get(0).toString();
        for (int i = 1; i < log.size()-1; i++) {
            s = s + "\n" + log.get(i).toString();
        }
        return s;
    }



    public void writeLogMsg(String s) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(chatLogFile, true);
            fw.append(s + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
