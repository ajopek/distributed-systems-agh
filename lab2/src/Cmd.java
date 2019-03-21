import java.rmi.Remote;

public enum Cmd {
    GET,
    PUT,
    REMOVE,
    CONTAINS,
    QUIT,
    BADCMD;

    public static Cmd getByName(String name) {
        Cmd cmd = null;
        switch (name) {
            case "get":
                cmd = GET;
                break;
            case "put":
                cmd = PUT;
                break;
            case "remove":
                cmd = REMOVE;
                break;
            case "quit":
                cmd = QUIT;
                break;
            case "contains":
                cmd = CONTAINS;
                break;
        }
        return cmd == null ? BADCMD : cmd;
    }
}
