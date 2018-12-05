import java.util.ArrayList;

public class Node {
    static int numOfSentMessages = 0;
    static int numOfReceivedMessages = 0;
    static int numOfLostMessages = 0;
    ArrayList<Integer> val;
    ArrayList<Integer> level;
    int numOfNodes;
    int id;
    int key;
    int receiveCount = 0;
    public Node(int numOfNodes, int id, int initialValue){
        this.id = id;
        this.numOfNodes = numOfNodes;
        this.key = (id == 0) ? (int)(Math.random()*numOfNodes): -1;
        if (id == 0) System.out.println("Chosen key is "+key);
        initializeVal(numOfNodes, initialValue);
        initializeLevel(numOfNodes);
    }
    private void initializeVal(int numOfNodes, int initialValue){
        val = new ArrayList<>(numOfNodes);
        for (int i = 0; i < numOfNodes; i++){
            if (i == id)    val.add(i,initialValue);
            else    val.add(i,-1); //-1 means undefined
        }
    }
    private void initializeLevel(int numOfNodes){
        level = new ArrayList<>(numOfNodes);
        for (int j = 0; j < numOfNodes; j++){
            if (j == id)    level.add(j,0);
            else    level.add(j,-1);
        }
    }

    public ArrayList<Integer> getVal() {
        return val;
    }

    public ArrayList<Integer> getLevel() {
        return level;
    }

    public int getKey() {
        return key;
    }

    public int getId() {
        return id;
    }
    public void send(int destinationID, Message msg){
        numOfSentMessages ++;
        msg.setSenderID(this.id);
        msg.setReceiverID(destinationID);
        System.out.println(msg.toString());
    }
    /**
     * receives a message in form of (level, value, key)
     */
    public void receive (Message msg){
        int miss = Main.getRandomDecision();
        if (miss == 0){
            numOfReceivedMessages ++;
            ArrayList<Integer> level = msg.getLevel();
            ArrayList<Integer> value = msg.getVal();
            key = (msg.getKey()== -1)? key: msg.getKey();
            updateLevel(level);
            updateValue(value);
            receiveCount ++;
        }else{
            numOfLostMessages ++;
            System.out.println("!! Message to Node "+id+" lost!!");
        }
        if(receiveCount == numOfNodes-1){
            updateMyLevel();
            receiveCount = 0;
        }
    }
    public void decide (){
        if (key != -1 && level.get(id) >= key && isAllOne(val)){
            System.out.printf("Node %d decision is %d\n",id,1);
        }else
            System.out.printf("Node %d decision is %d\n",id,0);
    }
    private void updateLevel(ArrayList<Integer> recLevel){
        for (int i = 0; i < recLevel.size(); i++) {
            if (i != id){
                int newLevel = recLevel.get(i);
                int oldLevel = level.get(i);
                level.set(i, Math.max(newLevel, oldLevel));
            }
        }
    }
    private void updateMyLevel(){
        level.set(id, getMin(level)+1);
    }
    private int getMin(ArrayList<Integer> list){
        int min = list.get(0);
        for (int i : list){
            min = min < i ? min : i;
        }
        return min;
    }
    private void updateValue(ArrayList<Integer> recValue){
        //if,  for  some  i',  Vi,(j)  r  undefined  then  val(j)  :=  V/,(j)
        for (int i = 0; i < recValue.size(); i++) {
            int v = recValue.get(i);
            if (v != -1)    val.set(i,v);
        }
    }
    private boolean isAllOne(ArrayList<Integer> list){
        for (int i:
             list) {
            if (i == 0) return false;
        }
        return true;
    }

    public static int getNumOfSentMessages() {
        return numOfSentMessages;
    }

    public static int getNumOfReceivedMessages() {
        return numOfReceivedMessages;
    }

    public static int getNumOfLostMessages() {
        return numOfLostMessages;
    }
}
