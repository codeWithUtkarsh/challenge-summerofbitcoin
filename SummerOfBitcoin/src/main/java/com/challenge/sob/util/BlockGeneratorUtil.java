package com.challenge.sob.util;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.challenge.sob.exception.SobException;
import com.challenge.sob.model.TransactionDetails;
import com.opencsv.CSVReader;

@Component
public class BlockGeneratorUtil {	

	public List<TransactionDetails> csvReader(File csvFile) throws SobException
	{
		List<TransactionDetails> transactions = new ArrayList<>();
		try
		{
			CSVReader csvReader = new CSVReader(new FileReader(csvFile));

			String[] csvData = new String[4];
			int columnHeaderCount = 0;
			while((csvData = csvReader.readNext()) != null)	
			{
				if(++columnHeaderCount == 1)
					break;
			}

			while((csvData = csvReader.readNext()) != null)
			{
				TransactionDetails tx = new TransactionDetails();
				tx.setTxId(csvData[0]);

				String feeStr = csvData[1];
				tx.setFee(Integer.parseInt(feeStr));
				tx.setWeight(Integer.parseInt(csvData[2]));
				String parentTransactions = csvData[3];
				if(parentTransactions != null && !parentTransactions.isEmpty())
				{
					tx.setParentTxId(Arrays.asList(parentTransactions.split(";")));
				}
				transactions.add(tx);
			}
		}catch (Exception e) {
			throw new SobException(e.getMessage());
		}
		return transactions;
	}
}
