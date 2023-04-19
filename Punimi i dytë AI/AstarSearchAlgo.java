import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AstarSearchAlgo{

//Funksioni heuristik është kalkuluar sipas distancave ndërmjet Qyteteve
        public static void main(String[] args){
                
                //Të inicializohen Qytetet në "nyje"
                Node n1 = new Node("Peja",70);
                Node n2 = new Node("Istog",60);
                Node n3 = new Node("Gjakova",65);
                Node n4 = new Node("Klina",50);
                Node n5 = new Node("Mitrovica",30);
                Node n6 = new Node("Prizreni",50);
                Node n7 = new Node("Ferizaji",60);
                Node n9 = new Node("Gjilani",37);
                Node n8 = new Node("Prishtina",0);

               
                
 
                //Krijohen shtigjet/skajet

                //Peja
                n1.adjacencies = new Edge[]{
                        new Edge(n2,24),
                        new Edge(n4,25),
                        new Edge(n3,35)
                };
                 
                 //Istogi
                n2.adjacencies = new Edge[]{
                        new Edge(n1,24),
                        new Edge(n4,24),
                        new Edge(n5, 42)
                };
                 

                 //Gjakova
                n3.adjacencies = new Edge[]{
                        new Edge(n1,35),
                        new Edge(n4,27),
                        new Edge(n6, 37)
                };
                 
                 //Klina
                n4.adjacencies = new Edge[]{
                        new Edge(n1,25),
                        new Edge(n2,24),
                        new Edge(n3,27),
                        new Edge(n8,54)
                };
                 

                 //Mitrovica
                n5.adjacencies = new Edge[]{
                        new Edge(n2,42),
                        new Edge(n8,38)
                };
                 
                 //Prizren
                n6.adjacencies = new Edge[]{
                        new Edge(n3,37),
                        new Edge(n7,56),
                        new Edge(n8,79)
                };
                 
                 //Ferizaj
                n7.adjacencies = new Edge[]{
                        new Edge(n6,56),
                        new Edge(n8,35),
                        new Edge(n8,38)
                };
                 
                 //Prishtina
                n8.adjacencies = new Edge[]{
                        new Edge(n4, 54),
                        new Edge(n5, 38),
                        new Edge(n6, 79),
                        new Edge(n7,56),
                        new Edge(n9,45)
                };
                 
                 //Gjilani
                n9.adjacencies = new Edge[]{
                        new Edge(n7,38),
                        new Edge(n8, 45)
                };

             //Si hyrje japin nyjen fillestare si dhe nyjen qe deshirojme ta arrijme   
             double km = AstarSearch(n1, n8);

                //Printon rrugen me të mire të mundshme për të dhënat qe i ka
                List<Node> path = printPath(n8);
                System.out.println("Rruga me e mirë: " + path);
                System.out.println("Kilometra të kaluar deri tek destinacioni: " + km);
          

        }
        //Printon të gjitha ato zgjidhje qe i ka shtuar funksioni me posht
        public static List<Node> printPath(Node target){
                List<Node> path = new ArrayList<Node>();
        
        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
        }
        //Algoritmi A*
        public static double AstarSearch(Node source, Node goal){

                Set<Node> explored = new HashSet<Node>();
                double km = 0.0;
                PriorityQueue<Node> queue = new PriorityQueue<Node>(20, 
                        new Comparator<Node>(){
                                 //Mbishkruan metoden compare
                                 
                                 //Krahason nyjet ndërmjet veti, nese nyja e ardhshme është me e vogel nga kostoja nga ajo e kaluara fute në list
                public int compare(Node i, Node j){
                    if(i.f_scores > j.f_scores){
                        return 1;
                    }

                    else if (i.f_scores < j.f_scores){
                        return -1;
                    }
        
                    else{
                        return 0;
                    }
                    
                }

                        }
                        );

                //Kostoja nga fillimi
                source.g_scores = 0;
                double totalKil = 0;

                queue.add(source);
                //Nese gjëndet ajo qe po lypet, atëherë programi ndryshon vlerën "booleane" në true dhe while loop nuk ekzekutohet përseri
                boolean found = false;

                while((!queue.isEmpty())&&(!found)){

                        //Ne fillim hynë nyja me se paku pike/kilometra
                        Node current = queue.poll();

                        explored.add(current);

                        //Mberritem tek destinimi
                        if(current.value.equals(goal.value)){
                                found = true;
                                break;
                        }

                        //Kontrollo çdo femijë të seciles nyje
                        for(Edge e : current.adjacencies){
                                //Kontrollon gjithashtu koston deri tek nyja tjeter
                                Node child = e.target;
                                double cost = e.cost;
                                double temp_g_scores = current.g_scores + cost;
                                double temp_f_scores = temp_g_scores + child.h_scores;
                                

                                //Nese femija i nyjes i dihet vlera dhe f_score është me e madhe, kaperce
                                
                                if((explored.contains(child)) && 
                                        (temp_f_scores >= child.f_scores)){
                                        continue;
                                }

                                //Nese nuk është në rresht dhe f_score është me i vogel, shtoje në rresht
                                
                                else if((!queue.contains(child)) || 
                                        (temp_f_scores < child.f_scores)){
                                        
                                        child.parent = current;
                                        child.g_scores = temp_g_scores;
                                        child.f_scores = temp_f_scores;
                
                                       totalKil = temp_f_scores;
                                        queue.add(child);

                                }

                        }

                }
                return totalKil;
        }        
}
//Krijojme trungun me nyje (meqe shembulli qe kemi të bëjmë përfaqesohet me nyje) 
class Node{

        public final String value;
        public double g_scores;
        public final double h_scores;
        public double f_scores = 0;
        public Edge[] adjacencies;
        public Node parent;

        public Node(String val, double hVal){
                value = val;
                h_scores = hVal;
        }

        public String toString(){
                return value;
        }

}
//Krijojme skajet e grafit se bashku me kosto të skajeve
class Edge{
        public final double cost;
        public final Node target;

        public Edge(Node targetNode, double costVal){
                target = targetNode;
                cost = costVal;
        }
}