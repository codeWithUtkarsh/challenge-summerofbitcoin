package com.challenge.sob.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.sob.model.TransactionDetails;
import com.challenge.sob.service.BlockGeneratorService;
import com.opencsv.exceptions.CsvValidationException;

@RestController
public class Controller {

	@Autowired
	private BlockGeneratorService blockGeneratorService;
	
	@RequestMapping(value = "/generateBlock",method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public List<TransactionDetails> generateBlock(@RequestParam("file") MultipartFile file) throws CsvValidationException, IOException
	{
		//Converting Multipartfile to file
		String originalFileName = file.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		File csvFile = File.createTempFile(originalFileName, extension);
		file.transferTo(csvFile);
		
		return blockGeneratorService.generateBlock(csvFile);
	}
}
