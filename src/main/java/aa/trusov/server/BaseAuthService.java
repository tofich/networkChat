package aa.trusov.server;

import aa.trusov.AuthService;

import java.util.ArrayList;

public class BaseAuthService implements AuthService {

    private class Entry{
        private String login;
        private String pass;
        private String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }

    private ArrayList<Entry> entries;
    public BaseAuthService(){
        this.entries = new ArrayList<>();
        entries.add(new Entry("login1","pass1","nick1"));
        entries.add(new Entry("admin","admin","admin"));
        entries.add(new Entry("user","user","user"));
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        for(Entry entry : entries){
            if(entry.login.equalsIgnoreCase(login) && entry.pass.equals(pass)) return entry.nick;
        }
        return null;
    }
}
