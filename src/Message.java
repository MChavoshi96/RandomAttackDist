import java.util.ArrayList;

public class Message {
    ArrayList<Integer> val;
    ArrayList<Integer> level;
    int key;
    int receiverID;
    int senderID;

    public Message(ArrayList<Integer> val, ArrayList<Integer> level, int key) {
        this.val = val;
        this.level = level;
        this.key = key;
    }
    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }
    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }
    public ArrayList<Integer> getVal() {
        return val;
    }
    public int getKey() {
        return key;
    }
    public ArrayList<Integer> getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return String.format("#sent from %d to %d: (%s ,%s ,%d)",
                senderID, receiverID, getLevelString(),getValString(), key);
    }
    public String getValString() {
        return getVectorString(val);
    }
    public String getLevelString() {
        return getVectorString(level);
    }
    public String getVectorString(ArrayList<Integer> list){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Integer v: list) {
            sb.append(v);
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
