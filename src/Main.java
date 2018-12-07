
import java.util.ArrayList;

public class Main {
    static int numOfErrors = 0;
    final static int numOfNodes = 2;
    public static void run(){
        int roundCount = 0;
        int decision = -1; //-1 means unknown

        ArrayList<Node> nodes = new ArrayList<>();
        Node n;
        for (int i = 0; i < numOfNodes; i++){
            n = new Node(numOfNodes,i, getRandomDecision());
            nodes.add(n);
        }
        //printNodes(nodes);

        while(roundCount < numOfNodes){
            roundCount ++;
            //System.out.println("round "+roundCount);
            for (Node senderNode:
                    nodes) {
                Message msg;
                for (Node receiverNode:
                        nodes) {
                    if (senderNode.getId() != receiverNode.getId()) {
                        msg = new Message(senderNode.getVal(), senderNode.getLevel(), senderNode.getKey());
                        senderNode.send(receiverNode.getId(), msg);
                        receiverNode.receive(msg);
                    }
                }
            }
        }
        ArrayList<Integer> decisions = new ArrayList<>();
//        for (Node node:
//                nodes) {
//            node.decide();
//        }
        for (int i = 0; i < nodes.size() ; i++) {
            decisions.add(i,nodes.get(i).decide());
        }
        if (!isAllSame(decisions)) {
            numOfErrors++;
            System.out.println("decisions are "+Message.getVectorString(decisions));
        }
//        System.out.println(Node.getNumOfSentMessages()+" messages sent");
//        System.out.println(Node.getNumOfReceivedMessages()+" messages received");
//        System.out.println(Node.getNumOfLostMessages()+" messages lost");
//        System.out.println(((double) Node.getNumOfLostMessages()/Node.numOfSentMessages)*100+" loss");
    }
    public static void main(String[] args){
        int numOfIterations = 100;
        for (int i = 0; i < numOfIterations ; i++) {
            run();
        }
        System.out.println(numOfErrors);
        System.out.println("error percentage is "+((double) numOfErrors/numOfIterations)*100);
            }
    public static int getRandomDecision(){
        double rand = Math.random();
        if (rand < 0.5) return 0;
        return 1;
    }

    private static boolean isAllSame(ArrayList<Integer> list){
        int value = list.get(0);
        for (int i:
                list) {
            if (i != value) return false;
        }
        return true;
    }
}
