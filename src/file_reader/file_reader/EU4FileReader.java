package file_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EU4FileReader {
	
	/**
	 * Felsorolja a megadott el�r�si �t alatt tal�lhat� f�jlokat.
	 * @param folder El�r�si �t, pl: "./assets/common"
	 * @return A lista elemei az el�r�si �ton tal�lhat� f�jlok neve kiterjeszt�ssel.
	 */
	public List<String> listFilesForFolder(final File folder) {
		List<String> returnList = new ArrayList<>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            //System.out.println(fileEntry.getName());
	            returnList.add(fileEntry.getName().trim());
	        }
	    }
	    return returnList;
	}
	
	/**
	 * Visszaadja egy file tartalm�t egy List-ben
	 * @param fileName A f�jl neve el�r�si �ttal, pl: "./assets/common/technologies/mil.txt"
	 * @return A lista elemei a f�jlban tal�lhat� sorok.
	 */
	public List<String> getFileContent(String fileName) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<>();
		try {
		    File file = new File(fileName);
		    reader = new BufferedReader(new FileReader(file));
		    String line;
		    while ((line = reader.readLine()) != null) {
		        list.add(line);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		//System.out.println(list.toString());
		return list;
	}
	
	/**
	 * Visszaadja a megadott MIT-hez kapcsol�d� �sszes el�rhet� egys�get.
	 * @param mit Military technology, a j�t�k bemeneti param�tere
	 * @return Visszaad egy list�t az el�rhet� egys�gekr�l vagy null-t ha 0<mit<32 nem teljes�l.
	 */
	public List<String> getUnlockedUnits(int mit){
		if(mit > 32 || mit < 1) {
			return null;
		}
		List<String> militaryList = getFileContent("./assets/common/technologies/mil.txt");
		List<String> returnList = new ArrayList<>();
		//System.out.println("ITT KEZDODIK");
		for (String line : militaryList) {
			//System.out.println(line);
			if(line.contains("# Tech "+(mit+1))) {
				break;
			}
			if(line.contains("enable")) {
				line.trim();
				String[] lineArray = line.split("=");
				returnList.add(lineArray[1].trim());
			}
		};
		//System.out.println("ITT VEGZODIK");
		return returnList;
	}
	
}
