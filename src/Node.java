public class Node {
    Type type;
    String str;
    Node left;
    Node right;

    Node(){}


    Node(Type type, String str) {
        this.type = type;
        this.str = str;
    }

    @Override
    public String toString() {
        if (type == Type.NUMBER) {
            return str;
        }
        return "(" + str + "," + left.toString() + "," + right.toString() + ")";
    }
}
