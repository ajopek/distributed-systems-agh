import com.sun.scenario.effect.Merge;
import com.sun.xml.internal.ws.api.message.Message;
import org.jgroups.*;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.protocols.pbcast.STATE_TRANSFER;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.util.Base64;
import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class DistributedMap extends ReceiverAdapter implements SimpleStringMap {
    private final HashMap<String, Integer> storage;
    private JChannel channel;

    DistributedMap() {
        this.storage = new HashMap<>();
        channel = new JChannel(false);
        ProtocolStack stack=new ProtocolStack();
        channel.setProtocolStack(stack);
        stack.addProtocol(new UDP())
                .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new STATE_TRANSFER());
        try {
            stack.init();
            channel.setReceiver(this);
            channel.connect("MapCluster");
            channel.getState();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

    }

    public void getState(OutputStream output) throws Exception {
        synchronized(storage) {
            Util.objectToStream(storage, new DataOutputStream(output));
        }
    }

    public void setState(InputStream input) throws Exception {
        HashMap<String, Integer> map;
        map = (HashMap<String, Integer>) Util.objectFromStream(new DataInputStream(input));
        synchronized(storage) {
            //storage.clear();
            storage.putAll(map);
        }
    }

    public void viewAccepted(View new_view) {
        if(new_view instanceof MergeView) {
            ViewHandler vh = new ViewHandler(channel, (MergeView) new_view);
            vh.run();
        }
    }

    public boolean containsKey(String key) {
        return storage.containsKey(key);
    };

    public Integer get(String key) {
        return storage.get(key);
    };

    public void put(String key, Integer value) {
        try {
            channel.getState(null, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        storage.put(key, value);
    };

    public Integer remove(String key) {
        return storage.remove(key);
    };

    public void close() {
        channel.close();
    }

    private static class ViewHandler extends Thread {
        JChannel ch;
        MergeView view;

        private ViewHandler(JChannel ch, MergeView view) {
            this.ch=ch;
            this.view=view;
        }

        public void run() {
            List<View> subgroups=view.getSubgroups();
            View tmp_view=subgroups.get(0); // picks the first
            Address local_addr=ch.getAddress();
            if(!tmp_view.getMembers().contains(local_addr)) {
                try {
                    ch.getState(null, 30000);
                }
                catch(Exception ex) {
                }
            }
        }
    }
}
