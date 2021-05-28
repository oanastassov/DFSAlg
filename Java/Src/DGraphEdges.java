//-----------------------------------------------------
// Author: Olivia Anastassov
// Date: 11/4/21      
// Description: class
//              directed DGraph represented with edge list
//              read/write from/to file functions
// Used template provided by professor
//-----------------------------------------------------
import java.util.*;
import java.io.*;
public class DGraphEdges{
    private int nrVertices;
    private ArrayList<DEdge> edges;

//------------------------------------- 
// Constructors 
//-------------------------------------
    public DGraphEdges(){
        this.nrVertices = 0;
        this.edges = new ArrayList<DEdge>();
    }
    public DGraphEdges(int nrVertices){
        this.nrVertices = nrVertices;
        this.edges = new ArrayList<DEdge>();
    }
    public DGraphEdges(int nrVertices, ArrayList<DEdge> edges){
        this.nrVertices = nrVertices;
        this.edges = edges;
    }
//-------------------------------------
// Getters
//-------------------------------------
    public int getNrVertices(){
        return nrVertices;
    }
//-------------------------------------
// Setters
//-------------------------------------
public void setNrVertices(int nrVertices){
    this.nrVertices = nrVertices;
}
//-------------------------------------
// Serialize, Parse and Print
//-------------------------------------
    public String toString(){
        String string = "" + nrVertices;
        boolean firstElem = true;
        for(DEdge edge : edges){
            string += "\n" + (edge.getVertex1()) + " " + (edge.getVertex2());
        }
        return string;
    }
    public String toMathString(){
        String string = "{\n" + nrVertices + ",\n{\n";
        boolean firstElem = true;
        for(DEdge edge : edges){
            if(!firstElem){
                string += ",";
            }
            else{
                firstElem = false;
            }
            string += "{" + (edge.getVertex1()+1) + "," + (edge.getVertex2()+1) + "}";
        }
        string += "\n}\n}\n";
        return string;
    }
    public void printDGraph(){
        System.out.println(this.toString());
    }
    // private for parsing graph from not-perfectly-formatted text file
    private String skipEmptyLines(String line, Scanner myReader){
        while (myReader.hasNextLine()){
            if (line.length() == 0){
                line = myReader.nextLine();
            }
            else{
                String[] tokens = line.split(" ");
                if (tokens.length>0){
                    return line;
                }
            }
        }
        return "";
    }
    private String parseNrVerts(String line, Scanner myReader){
        line = skipEmptyLines(line, myReader); 
        if (line.length() == 0){
            // reached the end of file without nr vertices 
            // must init the graph as empty, somehow
            // must close the file 
            myReader.close();
            return "";
        }
        String[] nrVertsStringList = line.split(" "); 
        int nrV=Integer.parseInt(nrVertsStringList[0]);
        this.setNrVertices(nrV);
        line = myReader.nextLine();
        line = skipEmptyLines(line, myReader); 
        return line;
    }
    private void parseFile(Scanner myReader){
        // The first line has the number of nrVertices
        String line = myReader.nextLine();
        line = parseNrVerts(line,myReader); 
        if (line.length() == 0){
            // reached the end of file without edges 
            // must init the graph as empty, somehow 
            // must close the file
            myReader.close();
            return;
        }
        // Now attempt to process the edges
        // Next lines till the end of file is one edge per line
        int nrEdges = 0;
        while (myReader.hasNextLine()){
            nrEdges++;
            String[] edgeString = line.split(" ");
            String u = edgeString[0];
            String v = edgeString[1]; 
            addEdge(Integer.parseInt(u)-1, Integer.parseInt(v)-1); 
            line = myReader.nextLine();
        }
    }
//-------------------------------------
// I/O
//-------------------------------------
public void readFromTxtFile(String filename){
    try{
        File graphFile = new File(filename); 
        Scanner myReader = new Scanner(graphFile); 
        parseFile(myReader);
        myReader.close();
    }   
    catch (FileNotFoundException e) {
        System.out.println("ERROR: DGraphEdges, readFromTxtFile: file not found.");
        e.printStackTrace();
    }    
}
public void writeToTextFile(String filename){
    try {
        FileWriter myWriter = new FileWriter(filename); 
        myWriter.write(toString());
        myWriter.close();
    } 
    catch (IOException e) { 
        System.out.println("An error occurred."); 
        e.printStackTrace();
    } 
}
public void writeToMathematicaFile(String filename){ 
    try {
        FileWriter myWriter = new FileWriter(filename); 
        myWriter.write(toMathString()); 
        myWriter.close();
    } catch (IOException e) { 
        System.out.println("An error occurred."); 
        e.printStackTrace();
    } 
}
//-------------------------------------
// Modifiers
//-------------------------------------
    public void addVertices(int num){ 
        this.nrVertices += num;
    }
    public void addEdge(int u, int v){
        this.edges.add(new DEdge(u,v));
    }
//------------------------------------- 
// Converters 
//------------------------------------- 
    public DGraphAdj toAdjLists(){
    DGraphAdj DGraphAdjList = new DGraphAdj(nrVertices);
    for(int i = 0; i<edges.size(); i++){
        DGraphAdjList.addEdge(edges.get(i).getVertex1(),edges.get(i).getVertex2());
    }
    return DGraphAdjList;
    } 
}
