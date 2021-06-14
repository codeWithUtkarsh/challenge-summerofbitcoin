package com.challenge.sob.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.sob.model.TransactionDetails;
import com.challenge.sob.util.BlockGeneratorUtil;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class BlockGeneratorService {

	@Autowired
	private BlockGeneratorUtil blockGeneratorUtil;
	
	public List<TransactionDetails> generateBlock(File csvFile) throws CsvValidationException, IOException
	{
		return blockGeneratorUtil.csvReader(csvFile);
	}
}
