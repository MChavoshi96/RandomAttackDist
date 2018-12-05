import java.util.ArrayList;

public class Main {

    final static int numOfNodes = 3;

    public static void main(String[] args){
        int roundCount = 0;
        int decision = -1; //-1 means unknown

        ArrayList<Node> nodes = new ArrayList<>();
        Node n;
        for (int i = 0; i < numOfNodes; i++){
            n = new Node(numOfNodes,i, getRandomDecision());
            nodes.add(n);
        }
        printNodes(nodes);

        while(roundCount < numOfNodes){
            roundCount ++;
            System.out.println("round "+roundCount);
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
        for (Node node:
             nodes) {
            node.decide();
        }
        System.out.println(Node.getNumOfSentMessages()+" messages sent");
        System.out.println(Node.getNumOfReceivedMessages()+" messages received");
        System.out.println(Node.getNumOfLostMessages()+" messages lost");
        System.out.println(((double) Node.getNumOfLostMessages()/Node.numOfSentMessages)*100+" loss");
    }
    public static int getRandomDecision(){
        double rand = Math.random();
        if (rand < 0.5) return 0;
        return 1;
    }
    private static void printNodes(ArrayList<Node> nodes){
        System.out.println("***Nodes***");
        for (Node node : nodes){
            System.out.printf("Node %d : level => %s  val =>  %s\n",node.getId(),node.getLevel(),node.getVal());
        }
    }
}
