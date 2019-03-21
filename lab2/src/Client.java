import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private DistributedMap map;
    private boolean running;

    public Client(DistributedMap map) {
        this.map = map;
        this.running = true;
    }

    public void start() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(running) {
                String input = br.readLine();
                String[] tokens = input.split(" ");
                if(tokens.length == 0) {
                    System.err.println("Error: No command provided");
                } else if (tokens.length == 1) {
                    System.err.println("Error: No key provided");
                } else {
                    executeCmd(Cmd.getByName(tokens[0]), tokens);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

    }

    private void executeCmd(Cmd command, String[] tokens) {
        try {
            boolean valuePresent = tokens.length >= 3;

            switch (command) {
                case GET:
                    Integer val = map.get(tokens[1]);
                    System.out.println("Got: " + (val == null ? "no value" : val.toString()));
                    break;
                case PUT:
                    if(!valuePresent) System.err.println("Error: This command needs value.");
                    else {
                        map.put(tokens[1], Integer.valueOf(tokens[2]));
                        System.out.println("Put " + tokens[2] + " at " + tokens[1] + " successfully.");
                    }
                    break;
                case REMOVE:
                    System.out.println("Removed value " + map.remove(tokens[1]).toString() + " at key " + tokens[1] + ".");
                    break;
                case CONTAINS:
                    System.out.println("There " + (map.containsKey(tokens[1]) ? " is a " : "is no ") + "value under key: " + tokens[1]);
                    break;
                case QUIT:
                    this.running = false;
                    System.out.println("Client quitting ...");
                    break;
                case BADCMD:
                    System.err.println("Error: Provide correct command (get, put, remove, quit).");
                    break;
            }
        } catch (IllegalArgumentException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
