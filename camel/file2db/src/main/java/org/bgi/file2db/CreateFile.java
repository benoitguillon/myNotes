package org.bgi.file2db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {

	public static void main(String[] args) {
		int nbLines = 1000;
		int nbCols = 4;
		FileWriter writer = null;
		try {
			File output = new File("C:\\bgi\\tmp\\output.csv");
			writer = new FileWriter(output);
			for(int line=0; line<nbLines; line++){
				for(int col=0; col<nbCols; col++){
					writer.write("COL" + col + "-" + line);
					if(col!=nbCols-1){
						writer.write(";");
					}
				}
				writer.write(";2015-04-01");
				writer.write(System.getProperty("line.separator"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
		

	}

}
