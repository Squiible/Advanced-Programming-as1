package main_code;

import java.io.*;

public abstract class MainFileHandler {
	
	int fileSize;
	int th_directory;
	File fp;
	String src_name;
	String destination_name;
	String file_type;
	
	public MainFileHandler() {}
	public MainFileHandler(String src_name) {
		this.src_name = src_name;
		this.fp = new File(this.src_name);
	}
	
	public MainFileHandler(String source_file, String dest_file) {
		this.src_name = source_file;
		this.destination_name = dest_file;
		this.fp = new File(this.src_name);
	}
	
	public abstract void readFile() throws IOException;
	public abstract void writeFile(String file_name) throws IOException;
	
	/*
	 * Introduce getters and setters methods for the class variables
	 */
	
	public File getFp() {
		return fp;
	}
	public void setFp(File fp) {
		this.fp = fp;
	}
	public String getSource_name() {
		return src_name;
	}
	public void setSource_name(String src_name) {
		this.src_name = src_name;
	}
	public String getDestination_name() {
		return destination_name;
	}
	public void setDestination_name(String destination_name) {
		this.destination_name = destination_name;
	}
	public int getFile_size() {
		return fileSize;
	}
	public void setFile_size(int fileSize) {
		this.fileSize = fileSize;
	}
	public int getIs_directory() {
		return th_directory;
	}
	public void setIs_directory(int th_directory) {
		this.th_directory = th_directory;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
}
