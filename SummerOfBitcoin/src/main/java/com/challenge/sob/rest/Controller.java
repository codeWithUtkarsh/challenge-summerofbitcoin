package com.challenge.sob.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.sob.exception.SobException;
import com.challenge.sob.service.BlockGeneratorService;

@RestController
public class Controller {

	@Autowired
	private BlockGeneratorService blockGeneratorService;

	@RequestMapping(value = "/generateBlock",method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces="application/zip")
	public ResponseEntity<InputStreamResource> generateBlock(@RequestParam("file") MultipartFile file) 
			throws SobException, IOException
	{
		String originalFileName = file.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		File csvFile = File.createTempFile(originalFileName, extension);
		file.transferTo(csvFile);

		File blockTextFile = blockGeneratorService.generateBlock(csvFile);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(blockTextFile));
		return ResponseEntity.ok().contentLength(blockTextFile.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=" + "output-block-text.txt")
				.body(resource);
	}
}
