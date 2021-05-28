//-----------------------------------------------------
// Author: 		Olivia Anastassov
// Date: 		April 16, 2021
// Description:	Driver for 
// 				Depth First Search 
// Used template provided by Professor Streinu
//-----------------------------------------------------
import java.io.*;

public class dMainGraph {
	final static boolean DEBUG = true; 
	private static String makeAbsoluteInputFilePath(String inputFilePath){
		File input = new File(inputFilePath);
		String absInputFilePath = "";
		try{
			absInputFilePath= input.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+inputFilePath);
		}

		return absInputFilePath;
	}
	private static String makeAbsoluteInputFilePathWithExt(String inputFilePath, String ext){
		File input = new File(inputFilePath);
	
		String inputFileFolder = input.getParent();
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
			
		String newInputFilePath = inputFileFolder + "/" + name + "." + ext;
		
		File newInput= new File(newInputFilePath);
		
		String absNewInputFilePath = "";
		try{
			absNewInputFilePath = newInput.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+newInputFilePath);
		}
		
		return absNewInputFilePath;
	}	
	private static String makeAbsoluteOutputFilePathWithExt(String inputFilePath, String outputFileFolder, String ext){
		File input = new File(inputFilePath);
	
		String parentPath = input.getParent();
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
			
		
		// make output file path with extension
		String outputFilePath = outputFileFolder + "/" + name + "." + ext;
		
		File output= new File(outputFilePath);
		
		String absOutputFilePath = "";
		try{
			absOutputFilePath = output.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+outputFilePath);
		}
		
		return absOutputFilePath;
	}
	
	private static void copyFileUsingStream(String inputFilePath, String outputFilePath) throws IOException {
	  
		File source = new File(inputFilePath);
		File dest = new File(outputFilePath);
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}

	//PRINT DEBUG
	public static void printDebug(String s){
		if(DEBUG){
			System.out.println(s);
		}
	}

	// MAIN
    public static void main(String[] args) {
        
		if (args.length < 2){	
			return;
		}
		
		//-----------------------------
		// Input and output files
		// taken from the command line
		//-----------------------------
		String inputFilePath = args[0];
		String outputFileFolder = args[1];
		
		String absInputFilePath = makeAbsoluteInputFilePath(inputFilePath);
		String absInputGrfFilePath = makeAbsoluteInputFilePathWithExt(inputFilePath,"grf");
		String absOutputGrfFilePath = makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"grf");
		String absOutputCmpFilePath = makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"cmp");
		
		// read graph
		printDebug("======  STEP 1: READ DGRAPH FROM TXT FILE");
		DGraphEdges dGraph = new DGraphEdges();
		dGraph.readFromTxtFile(absInputFilePath);
		printDebug("......  DONE STEP 1");

		printDebug("======  STEP 2: PRINT DGRAPH ");
		dGraph.printDGraph();
		printDebug("......  DONE STEP 2");

		printDebug("======  STEP 3: CONVERT TO ADJACENCY LISTS ");
		DGraphAdj dGraphAdj = dGraph.toAdjLists();
		printDebug("......  DONE STEP 3");

		printDebug("======  STEP 4: PRINT ADJACENCY LISTS ");
		dGraphAdj.printDGraph();
		printDebug("......  DONE STEP 4");

		printDebug("======  STEP 5: Compute DFS");
		DfsDataStructures dfsData = dGraphAdj.DFS();
		printDebug("......  DONE STEP 5");

		printDebug("======  STEP 6: Print DFS");
		dfsData.printDFS();
		printDebug("......  DONE STEP 6");

        printDebug("======  STEP 7: Print DFS");
        dfsData.writeToFile(absOutputCmpFilePath);
		printDebug("......  DONE STEP 7");
    
		printDebug("======  STEP 8: COPY GRF TO OUTPUT FOLDER");
		try {
			copyFileUsingStream(absInputGrfFilePath,absOutputGrfFilePath);
		}  
		catch (IOException e) {
            System.out.println("ERROR: IOException.");
            e.printStackTrace();
		}
		printDebug("......  DONE STEP 8");

	}
}
