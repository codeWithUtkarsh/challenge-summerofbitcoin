package com.challenge.sob.service;

import java.io.File;

import com.challenge.sob.exception.SobException;

public interface BlockGeneratorService {

	public File generateBlock(File csvFile) throws SobException;

}
