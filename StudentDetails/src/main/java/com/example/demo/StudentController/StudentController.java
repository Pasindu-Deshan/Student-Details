package com.example.demo.StudentController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.opencsv.CSVWriter;

import ch.qos.logback.core.util.Loader;

@CrossOrigin("*")
@RestController
@RequestMapping("/student-server")
public class StudentController {
	
	
	@GetMapping("/students")
	public List<Student> getStudent(){
		List<Student> students = new ArrayList<>();
	    try (InputStream inputStream = getClass().getResourceAsStream("/stDetails.csv");
	    	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	        	//System.out.println(line);
	            String[] records = line.split(",");
	            students.add(new Student(Integer.valueOf(records[0]), records[1], records[2]));
	        }
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
		return students;
	}
	
	@GetMapping("/student")
	public ResponseEntity<?> getData(@RequestParam int studentNo){
		List<Student> students = new ArrayList<>();
		ResponseEntity<?> response = null;
		
		try (InputStream inputStream = getClass().getResourceAsStream("/stDetails.csv");
	    	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] records = line.split(",");
		        students.add(new Student(Integer.valueOf(records[0]), records[1], records[2]));
		    }
		    //Search studentNo
		    int isResponse = 0;
		    for (Student student : students) {
		    	if(studentNo == student.getStudentNo()) {
		    		response = ResponseEntity.ok(student);
		    		isResponse++;
		    	}
			}
		    if(isResponse == 0) {
		    	response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		    
		}catch(Exception ex) {
			ex.printStackTrace();
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
		
	}
	
	@PostMapping("/student")
	public ResponseEntity<?> insertData(@RequestBody Student student) throws IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(classLoader.getResource("stDetails.csv").getFile(), true))) {
			
			//Search duplicate student numbers
		    try (InputStream inputStream = getClass().getResourceAsStream("/stDetails.csv");
		    	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
		        String line;
		        while ((line = br.readLine()) != null) {
		            String[] records = line.split(",");
		            if(Integer.valueOf(records[0]).equals(student.getStudentNo())) {
		            	//System.out.println("Duplicate");
		            	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		            	
		            }

		        }
		    }catch(Exception ex) {
		        ex.printStackTrace();
		    }
		    
		     //Add student record
			String line = student.getStudentNo() + ", " + student.getFirstName() + ", " + student.getLastName();
				writer.newLine();
                writer.append(line);
               
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return new ResponseEntity<>(HttpStatus.OK);
	
	}
}
